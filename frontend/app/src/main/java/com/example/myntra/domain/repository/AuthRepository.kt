package com.example.myntra.domain.repository

import com.example.myntra.data.api.authentication.body.*
import com.example.myntra.data.api.authentication.response.*

interface AuthRepository {

    suspend fun login(body:LoginBody):LoginResponse

    suspend fun register(body:RegisterBody):RegisterResponse

    suspend fun verifyOtp(body:VerifyOtpBody):VerifyOtpResponse

    suspend fun resendOtp(body:ResendOtpBody):ResendOtpResponse

    suspend fun activate(body:ActivateBody):ActivateAccountResponse

    suspend fun refresh():RefreshResponse

    suspend fun logout():LogoutResponse

}