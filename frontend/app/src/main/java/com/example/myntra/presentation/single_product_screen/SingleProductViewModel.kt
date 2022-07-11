package com.example.myntra.presentation.single_product_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.products.GetSingleCategoryUseCase
import com.example.myntra.domain.usecases.products.GetSingleProductUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleProductViewModel @Inject constructor(
    private val getSingleProductUseCase: GetSingleProductUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SingleProductVIewModelState())
    val state: State<SingleProductVIewModelState> = _state

    fun getSingleProduct(id:String) {
        val response = getSingleProductUseCase.invoke(id)

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = SingleProductVIewModelState(loading = true)
                    }

                    is Resource.Success -> {
                        _state.value = SingleProductVIewModelState(loading = false, data = it.data)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value = SingleProductVIewModelState(error = error.message, loading = false)
                        } else {
                            _state.value = SingleProductVIewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
