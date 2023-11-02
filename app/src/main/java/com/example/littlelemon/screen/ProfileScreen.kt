package com.example.littlelemon.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
<<<<<<< HEAD
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
=======
>>>>>>> 56d216785f4db1071ef8d8c11d968190a4c3ecd0
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.R
<<<<<<< HEAD
import com.example.littlelemon.navigation.Login
=======
import com.example.littlelemon.navigation.Signup
>>>>>>> 56d216785f4db1071ef8d8c11d968190a4c3ecd0

@ExperimentalMaterial3Api
@Composable
fun ProfileScreen(navController: NavHostController) {
<<<<<<< HEAD
    Scaffold(
        topBar = {ProfileTopBar(navController = navController)},
    ) {
        ProfileScreenComponent(navController = navController)
    }
}

@ExperimentalMaterial3Api
@Composable
fun ProfileScreenComponent(navController: NavHostController) {
=======
>>>>>>> 56d216785f4db1071ef8d8c11d968190a4c3ecd0

    val context = LocalContext.current
    val sharedPrefernces = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val firstName = sharedPrefernces.getString("FirstName","")
    val lastName = sharedPrefernces.getString("LastName", "")
    val savedMail = sharedPrefernces.getString("Mail", "")
    val editor = sharedPrefernces.edit()
    Column(
        Modifier
            .padding(0.dp)
            .fillMaxWidth()
    )
    {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .size(90.dp)
                .padding(vertical = 20.dp, horizontal = 25.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Profile Information",
            textAlign = TextAlign.Start,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 80.dp, bottom = 40.dp, start = 12.dp)
        )
        Column {
            Text(
                text = "First name",
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(top = 10.dp, start = 14.dp)
            )
            OutlinedTextField(
                value = firstName.toString(),
                onValueChange = {},
                readOnly = true,
                enabled = false,
                textStyle = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp,
                    color = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 14.dp),
                shape = RoundedCornerShape(15)
            )
            Text(
                text = "Last name",
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(top = 10.dp, start = 14.dp)
            )
            OutlinedTextField(
                value = lastName.toString(),
                onValueChange = {},
                readOnly = true,
                enabled = false,
                textStyle = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp,
                    color = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 14.dp),
                shape = RoundedCornerShape(15),
            )
            Text(
                text = "Email",
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(top = 10.dp, start = 14.dp)
            )
            OutlinedTextField(
                value = savedMail.toString(),
                onValueChange = {},
                readOnly = true,
                enabled = false,
                textStyle = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp,
                    color = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 14.dp),
                shape = RoundedCornerShape(15),
            )
        }
        Button(
            onClick = {
                clearSharedPreferences(context)
                editor.putBoolean("isLoggedin",false).apply()
                navController.navigate(Signup.route){
                    popUpTo(navController.graph.id){
                        inclusive = true
                    }
                }
            },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 1.dp,
                pressedElevation = 5.dp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 200.dp, bottom = 20.dp, start = 14.dp, end = 14.dp),
            shape = RoundedCornerShape(24),
            colors = ButtonDefaults.buttonColors(Color.Yellow)
        ) {
            Text(
                text = "Log out", textAlign = TextAlign.Center, fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}

fun clearSharedPreferences(context: Context)
{
    val sharedPrefernces = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val editor = sharedPrefernces.edit()
    editor.clear()
    editor.apply()
}

@ExperimentalMaterial3Api
@Composable
fun ProfileTopBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(text = "Profile", fontWeight = FontWeight.W500) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )
}