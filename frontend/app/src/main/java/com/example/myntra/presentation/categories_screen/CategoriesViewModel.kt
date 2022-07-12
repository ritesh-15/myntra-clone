package com.example.myntra.presentation.categories_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.products.GetAllCategoriesUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CategoriesViewModelState())
    val state: State<CategoriesViewModelState> = _state

    init {
        getAllCategories()
    }

    fun getAllCategories() {

        val response = getAllCategoriesUseCase.invoke()

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        if (it.data != null){
                            _state.value = CategoriesViewModelState(loading = false, categories = it.data)
                        }else{
                            _state.value = CategoriesViewModelState(loading = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.value = CategoriesViewModelState(loading = false, categories = it.data)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value = CategoriesViewModelState(error = error.message, loading = false)
                        } else {
                            _state.value = CategoriesViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
