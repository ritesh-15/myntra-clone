package com.example.myntra.presentation.verify_otp_screen

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.Constants
import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.authentication.body.VerifyOtpBody
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.model.User
import com.example.myntra.domain.usecases.authentication.VerifyOtpUseCase
import com.example.myntra.domain.usecases.user.InsertUserUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val pref:SharedPreferences,
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {

    private val _state = mutableStateOf(VerifyOtpState())
    val state: State<VerifyOtpState> = _state

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

    fun verifyOtp(email: String, hash:String, otp:Int) {

        // check if valid email or not
        if (email.isEmpty()){
            _state.value = VerifyOtpState(error= "Email address is required!")
            return
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _state.value = VerifyOtpState(error= "Email address is not valid!")
            return
        }

        if(hash.isEmpty()){
            _state.value = VerifyOtpState(error= "Something went wrong!")
            return
        }

        val response = verifyOtpUseCase.invoke(VerifyOtpBody(email = email, hash = hash, otp = otp))

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = VerifyOtpState(loading = true)
                    }

                    is Resource.Success -> {

                        // save the token
                        pref.edit().apply {
                            putString(Constants.ACCESS_TOKEN, it.data?.tokens?.accessToken)
                            putString(Constants.REFRESH_TOKEN, it.data?.tokens?.refreshToken)
                            apply()
                        }

                        if (it.data != null){
                            storeIntoCache(it.data.user)
                        }

                        _state.value = VerifyOtpState(loading = false, data = it.data)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value = VerifyOtpState(error = error.message, loading = false)
                        } else {
                            _state.value = VerifyOtpState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
