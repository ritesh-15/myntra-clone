package com.example.myntra
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.common.Navigation
import com.example.myntra.presentation.checkout_screen.CheckoutViewModel
import com.example.myntra.ui.theme.MyntraTheme
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultListener
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    private lateinit var navHostController:NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyntraTheme {
               navHostController = rememberNavController()
                Navigation(navController = navHostController)
            }
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
    }

}
