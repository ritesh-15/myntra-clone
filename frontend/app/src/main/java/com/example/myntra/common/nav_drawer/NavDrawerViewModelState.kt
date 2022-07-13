package com.example.myntra.common.nav_drawer

import com.example.myntra.domain.model.User

data class NavDrawerViewModelState(
    val loading:Boolean = false,
    val user:User? = null,
    val error:String? = null
)