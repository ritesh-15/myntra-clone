package com.example.myntra.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.myntra.common.Constants
import com.example.myntra.domain.model.Tokens


sealed class TokenHandler {

    companion object {

        fun saveTokens(pref:SharedPreferences, tokens: Tokens) {
            pref.edit().apply {
                putString(Constants.ACCESS_TOKEN, tokens.accessToken)
                putString(Constants.REFRESH_TOKEN, tokens.refreshToken)
                apply()
            }
        }

        fun getTokens(pref:SharedPreferences): Tokens {
            val accessToken = pref.getString(Constants.ACCESS_TOKEN, null)
            val refreshToken = pref.getString(Constants.REFRESH_TOKEN, null)
            return Tokens(accessToken = accessToken?: "", refreshToken = refreshToken?:"")
        }

    }

}