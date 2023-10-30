package com.example.littlelemon.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.R

@ExperimentalMaterial3Api
@Composable
fun TopAppBar(navController:NavHostController) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(90.dp)
                    .padding(vertical = 20.dp, horizontal = 20.dp)
            )
        },
        actions = {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        navController.navigate(Profile.route)
                    }.padding(end = 1.dp)
            )
        }
    )
}