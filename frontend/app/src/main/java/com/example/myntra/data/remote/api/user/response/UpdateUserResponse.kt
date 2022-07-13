package com.example.myntra.data.remote.api.user.response

import com.example.myntra.domain.model.User

data class UpdateUserResponse(
    val ok:Boolean,
    val user:User,
    val message:String
)