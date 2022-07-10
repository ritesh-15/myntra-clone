package com.example.myntra.presentation.home_screen

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.Constants
import com.example.myntra.common.utils.Resource
import com.example.myntra.data.api.authentication.body.LoginBody
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.authentication.LoginUseCase
import com.example.myntra.domain.usecases.products.GetAllProductsUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVIewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(HomeScreenVIewModelState())
    val state: State<HomeScreenVIewModelState> = _state

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        val response = getAllProductsUseCase.invoke()

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = HomeScreenVIewModelState(loading = true)
                    }

                    is Resource.Success -> {
                        _state.value = HomeScreenVIewModelState(loading = false,
                            data = it.data,
                            products = emptyList())
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java).message
                            _state.value = HomeScreenVIewModelState(error = error, loading = false)
                        } else {
                            _state.value =
                                HomeScreenVIewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }

    }

}