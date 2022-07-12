package com.example.myntra.presentation.refresh_screen

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.Constants
import com.example.myntra.common.utils.Resource
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.authentication.RefreshUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RefreshViewModel @Inject constructor(
    private val refreshUseCase: RefreshUseCase,
    private val pref: SharedPreferences,
) : ViewModel() {

    private val _state = mutableStateOf(RefreshState())
    val state: State<RefreshState> = _state

    init {
        refresh()
    }

    fun refresh() {
        val response = refreshUseCase.invoke()

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = RefreshState(loading = true)
                    }

                    is Resource.Success -> {

                        // save the token
                        pref.edit().apply {
                            putString(Constants.ACCESS_TOKEN, it.data?.tokens?.accessToken)
                            putString(Constants.REFRESH_TOKEN, it.data?.tokens?.refreshToken)
                            apply()
                        }

                        _state.value = RefreshState(loading = false, data = it.data)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value = RefreshState(error = error.message, loading = false)

                        } else {
                            _state.value = RefreshState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
