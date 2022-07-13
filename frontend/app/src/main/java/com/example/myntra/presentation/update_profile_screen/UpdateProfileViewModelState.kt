package com.example.myntra.presentation.update_profile_screen
import com.example.myntra.domain.model.User

data class UpdateProfileViewModelState(
    val loading: Boolean = false,
    val user:User? = null,
    val error:String? = null,
    val updated:Boolean = false
)