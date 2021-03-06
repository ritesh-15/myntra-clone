package com.example.myntra.presentation.complete_sign_up

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.authentication.body.ActivateBody
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.model.User
import com.example.myntra.domain.usecases.authentication.ActivateAccountUseCase
import com.example.myntra.domain.usecases.user.InsertUserUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivateAccountViewModel @Inject constructor(
    private val activateAccountUseCase: ActivateAccountUseCase,
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ActivateAccountState())
    val state: State<ActivateAccountState> = _state

    val password = mutableStateOf("")
    val name = mutableStateOf("")
    val phoneNumber = mutableStateOf("")

    fun onPasswordChange(it:String){
        password.value = it
    }

    fun onNameChange(it:String){
        name.value = it
    }

    fun onPhoneNumberChange(it:String){
        phoneNumber.value = it
    }

    private fun storeIntoCache(user: User) {
        viewModelScope.launch {
            insertUserUseCase.invoke(user).collect {
                when(it){
                    is Resource.Success -> {
                        // TODO
                    }
                }
            }
        }
    }


    fun activateAccount() {

        if (name.value.isEmpty()){
            _state.value = ActivateAccountState(error = "Name is required!")
            return
        }

        if (phoneNumber.value.isEmpty()){
            _state.value = ActivateAccountState(error = "Phone number is required!")
            return
        }

        if (password.value.isEmpty()){
            _state.value = ActivateAccountState(error = "Password is required!")
            return
        }

        val response = activateAccountUseCase.invoke(ActivateBody(name.value, phoneNumber.value, password.value))

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = ActivateAccountState(loading = true)
                    }

                    is Resource.Success -> {

                        if (it.data != null){
                            storeIntoCache(it.data.user)
                        }

                        _state.value = ActivateAccountState(loading = false, data = it.data)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value = ActivateAccountState(error = error.message, loading = false)
                        } else {
                            _state.value = ActivateAccountState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
