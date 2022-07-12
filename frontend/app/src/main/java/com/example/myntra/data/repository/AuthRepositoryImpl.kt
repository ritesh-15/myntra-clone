package com.example.myntra.data.repository

import com.example.myntra.data.remote.api.authentication.AuthenticationInterface
import com.example.myntra.data.remote.api.authentication.body.*
import com.example.myntra.data.remote.api.authentication.response.*
import com.example.myntra.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthenticationInterface,
) : AuthRepository {
    override suspend fun login(body: LoginBody): LoginResponse {
        return api.login(body = body)
    }

    override suspend fun register(body: RegisterBody): RegisterResponse {
        return api.register(body = body)
    }

    override suspend fun verifyOtp(body: VerifyOtpBody): VerifyOtpResponse {
        return api.verifyOtp(body)
    }

    override suspend fun resendOtp(body: ResendOtpBody): ResendOtpResponse {
       return api.resendOtp(body)
    }

    override suspend fun activate(body: ActivateBody): ActivateAccountResponse {
        return api.activate(body)
    }

    override suspend fun refresh(): RefreshResponse {
        return api.refresh()
    }

    override suspend fun logout(): LogoutResponse {
       return api.logout()
    }
}