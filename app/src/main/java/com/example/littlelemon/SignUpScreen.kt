@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SignUpScreen(navController:NavHostController)
{
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }

    Column(Modifier.padding(0.dp)) {
        Image(painter = painterResource(id = R.drawable.logo ),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp)
                .padding(vertical = 20.dp, horizontal = 25.dp)
                .align(Alignment.CenterHorizontally))
        Text(text = "Hey, Get On Board",
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            textAlign= TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57))
                .padding(40.dp))
        Text(text = "Create Account", fontSize = 20.sp, fontWeight = FontWeight.W500,
            modifier= Modifier.padding(20.dp)
        )
        OutlinedTextField(value = username, onValueChange ={newText->username=newText},
            label = { Text(text = "Username") },
            placeholder = { Text(text = "Username") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 14.dp),
            shape = RoundedCornerShape(16)
        )
        OutlinedTextField(value = email , onValueChange ={newText->email=newText},
            label = { Text(text = "Email") },
            placeholder = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 14.dp),
            shape = RoundedCornerShape(16)
        )
        OutlinedTextField(value = password, onValueChange = {newText->password=newText},
            placeholder = { Text(text = "Email") },
            label = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            shape = RoundedCornerShape(16)
        )
        OutlinedTextField(value = confirmPassword, onValueChange = {newText->confirmPassword=newText},
            placeholder = { Text(text = "Confirm Password") },
            label = { Text(text = "Confirm Password") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 10.dp, horizontal = 16.dp),
            shape = RoundedCornerShape(16)
        )
        Button(onClick = { navController.navigate(Home.route) },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 5.dp,
                pressedElevation = 10.dp,
                disabledElevation = 0.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 25.dp),
            shape = RoundedCornerShape(32),
            colors = ButtonDefaults.buttonColors(Color.Yellow)
        ) {
            Text(text = "Sign up", textAlign = TextAlign.Center, fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(2.dp))
        }
        Row(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Back to",
                fontSize = 18.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 140.dp),
            )
            Text(
                text = "Sign in",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable(onClick = {navController.navigate(Login.route)})
                    .padding(start = 5.dp)
            )
        }

    }
}