package com.example.myntra.common.nav_drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myntra.R
import com.example.myntra.common.Screen
import com.example.myntra.ui.theme.primary

@Composable
fun DrawerHeader(
    userName: String? = null,
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(primary)
            .padding(16.dp)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_user_solid),
            contentDescription = null,
            modifier = Modifier
                .width(35.dp)
                .height(35.dp),
            tint = Color.White
        )


        Spacer(modifier = Modifier.height(18.dp))

        if (userName != null) {
            Text(text = userName,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = MaterialTheme.typography.subtitle1.fontFamily
            )
        } else {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.LoginSignUp.route)
                }
            ) {
                Text(text = "Log In",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White,
                    fontFamily = MaterialTheme.typography.subtitle1.fontFamily
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(text = "Sign Up",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White,
                    fontFamily = MaterialTheme.typography.subtitle1.fontFamily
                )
            }
        }

    }
}

@Composable
fun DrawerBody(
    items: List<NavDrawerItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle.Default,
    onClick: (NavDrawerItem) -> Unit,
    navController: NavController,
) {

    LazyColumn(modifier = modifier.background(Color.White)) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(item)
                    }
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = item.icon, contentDescription = null,
                    modifier = Modifier
                        .width(22.dp)
                        .height(22.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(text = item.title,
                    modifier = Modifier.weight(1f),
                    style = itemTextStyle,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontFamily = MaterialTheme.typography.subtitle1.fontFamily
                )
            }

            Divider()
        }
    }

}