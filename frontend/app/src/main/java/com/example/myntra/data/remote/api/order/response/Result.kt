package com.example.myntra.data.remote.api.order.response

import com.example.myntra.domain.model.Pagination

data class Result(
    val next: Pagination?,
    val previous: Pagination?
)