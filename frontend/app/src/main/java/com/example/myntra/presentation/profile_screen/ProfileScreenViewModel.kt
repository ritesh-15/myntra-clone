package com.example.myntra.presentation.profile_screen

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.Constants
import com.example.myntra.common.nav_drawer.NavDrawerViewModelState
import com.example.myntra.common.utils.Resource
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.authentication.LogoutUseCase
import com.example.myntra.domain.usecases.authentication.RefreshUseCase
import com.example.myntra.domain.usecases.user.GetUserUseCase
import com.example.myntra.domain.usecases.user.RemoveUserFromCacheUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val pref: SharedPreferences,
    private val getUserUseCase: GetUserUseCase,
    private val removeUserFromCacheUseCase: RemoveUserFromCacheUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(ProfileScreenViewModelState())
    val state: State<ProfileScreenViewModelState> = _state

    init {
        getUser()
    }

    private fun removeFromCache() {
        viewModelScope.launch {
            removeUserFromCacheUseCase.invoke().collect {
                when (it) {
                    is Resource.Success -> {
                        _state.value = ProfileScreenViewModelState()
                    }
                    else -> {
                        _state.value = ProfileScreenViewModelState()
                    }
                }
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.invoke().collect {
                when (it) {
                    is Resource.Success -> {
                        _state.value = ProfileScreenViewModelState(user = it.data)
                    }
                    else -> {
                        _state.value = ProfileScreenViewModelState()
                    }
                }
            }
        }
    }

    fun logout() {
        val response = logoutUseCase.invoke()

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = ProfileScreenViewModelState(loading = true)
                    }

                    is Resource.Success -> {

                        // remove the token
                        pref.edit().apply {
                            remove(Constants.ACCESS_TOKEN)
                            remove(Constants.REFRESH_TOKEN)
                            apply()
                        }

                        removeFromCache()

                        _state.value = ProfileScreenViewModelState(loading = false, data = it.data)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value =
                                ProfileScreenViewModelState(error = error.message, loading = false)

                        } else {
                            _state.value =
                                ProfileScreenViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
