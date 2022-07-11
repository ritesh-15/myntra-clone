package com.example.myntra.presentation.single_category_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.products.GetSingleCategoryUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleCategoryViewModel @Inject constructor(
    private val getSingleCategoryUseCase: GetSingleCategoryUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SingleCategoryViewModelState())
    val state: State<SingleCategoryViewModelState> = _state

    fun getSingleCategory(id:String) {
        val response = getSingleCategoryUseCase.invoke(id)

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = SingleCategoryViewModelState(loading = true)
                    }

                    is Resource.Success -> {
                        _state.value = SingleCategoryViewModelState(loading = false, data = it.data)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value = SingleCategoryViewModelState(error = error.message, loading = false)
                        } else {
                            _state.value = SingleCategoryViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
