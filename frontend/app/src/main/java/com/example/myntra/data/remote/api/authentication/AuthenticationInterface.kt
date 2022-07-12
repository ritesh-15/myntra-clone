package com.example.myntra.data.remote.api.authentication

import com.example.myntra.data.remote.api.authentication.body.*
import com.example.myntra.data.remote.api.authentication.response.*
import retrofit2.http.*

interface AuthenticationInterface {

    @POST("auth/register")
    suspend fun register(
        @Body body:RegisterBody
    ):RegisterResponse

    @POST("auth/login")
    suspend fun login(
        @Body body:LoginBody
    ):LoginResponse

    @POST("auth/resend-otp")
    suspend fun resendOtp(
        @Body body:ResendOtpBody
    ):ResendOtpResponse

    @POST("auth/verify-otp")
    suspend fun verifyOtp(
        @Body body:VerifyOtpBody
    ):VerifyOtpResponse

    @PUT("auth/activate")
    suspend fun activate(
        @Body body:ActivateBody
    ):ActivateAccountResponse

    @GET("auth/refresh")
    suspend fun refresh(
    ):RefreshResponse

    @DELETE("auth/logout")
    suspend fun logout(
    ):LogoutResponse
}