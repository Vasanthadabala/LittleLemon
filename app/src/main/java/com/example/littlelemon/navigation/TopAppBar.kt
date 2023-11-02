package com.example.littlelemon.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
<<<<<<< HEAD
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
=======
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
>>>>>>> 56d216785f4db1071ef8d8c11d968190a4c3ecd0
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.R

@ExperimentalMaterial3Api
@Composable
<<<<<<< HEAD
fun TopBar(navController:NavHostController)
{
    TopAppBar(
=======
fun TopAppBar(navController:NavHostController) {
    CenterAlignedTopAppBar(
>>>>>>> 56d216785f4db1071ef8d8c11d968190a4c3ecd0
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(90.dp)
<<<<<<< HEAD
                    .padding(vertical = 20.dp, horizontal = 25.dp))
        },
        actions = {
            IconButton(onClick = { navController.navigate(Profile.route)}) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier.size(34.dp)
                )
            }
=======
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
>>>>>>> 56d216785f4db1071ef8d8c11d968190a4c3ecd0
        }
    )
}