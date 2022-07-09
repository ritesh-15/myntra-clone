package com.example.myntra.common.utils

import android.content.Context
import com.example.myntra.common.Constants
import com.example.myntra.domain.model.Tokens


sealed class TokenHandler {

    companion object {

        fun saveTokens(context: Context, tokens: Tokens) {
            val pref = context.getSharedPreferences("instagram-clone", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString(Constants.ACCESS_TOKEN, tokens.accessToken)
                putString(Constants.REFRESH_TOKEN, tokens.refreshToken)
                apply()
            }
        }

        fun getTokens(context: Context): Tokens {
            val pref = context.getSharedPreferences("myntra-clone", Context.MODE_PRIVATE)
            val accessToken = pref.getString(Constants.ACCESS_TOKEN, null)
            val refreshToken = pref.getString(Constants.REFRESH_TOKEN, null)
            return Tokens(accessToken = accessToken?: "", refreshToken = refreshToken?:"")
        }

    }

}