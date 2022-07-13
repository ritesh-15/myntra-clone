package com.example.myntra.common.nav_drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.domain.usecases.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavDrawerViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(NavDrawerViewModelState())
    val state: StateFlow<NavDrawerViewModelState> = _state

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.invoke().collect {
                when (it) {
                    is Resource.Success -> {
                        _state.value = NavDrawerViewModelState(loading = false, user = it.data)
                    }
                    else -> {
                        _state.value = NavDrawerViewModelState(loading = true)
                    }
                }
            }
        }
    }

}