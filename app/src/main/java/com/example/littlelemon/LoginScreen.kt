@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController)
{
    val context= LocalContext.current
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    val mail=email.text.toString()
    val sharedPreferences=context.getSharedPreferences("MY_PRE",Context.MODE_PRIVATE)
    val editor=sharedPreferences.edit()
    editor.putString("Mail",mail).apply()

    Column(Modifier.padding(0.dp)) {
        Image(painter = painterResource(id = R.drawable.logo ),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp)
                .padding(vertical = 20.dp, horizontal = 25.dp)
                .align(Alignment.CenterHorizontally))
        Text(text = "WelcomeBack",
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            textAlign= TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF41544E))
                .padding(40.dp))
        Text(text = "Login", fontSize = 20.sp, fontWeight = FontWeight.W500,
            modifier=Modifier.padding(20.dp)
        )
        OutlinedTextField(value = email, onValueChange ={newText->email=newText},
            label = { Text(text = "Email")},
            singleLine = true,
            placeholder = { Text(text = "Email")},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 14.dp),
            shape = RoundedCornerShape(16)
        )
        OutlinedTextField(value = password , onValueChange ={newText->password=newText},
            label = { Text(text = "Password")},
            singleLine = true,
            placeholder = { Text(text = "Password")},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 14.dp),
            shape = RoundedCornerShape(16)
        )
        Button(onClick = {
            editor.putBoolean("isLoggedin",true).apply()
            navController.navigate(Home.route){
                popUpTo(navController.graph.id){
                    inclusive = true
                }
            }
            },
            enabled = !email.text.isBlank() && !password.text.isBlank(),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 5.dp,
                pressedElevation = 10.dp,
                disabledElevation = 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 25.dp),
            shape = RoundedCornerShape(32),
            colors = ButtonDefaults.buttonColors(Color.Yellow)
        ) {
            Text(text = "Sign in", textAlign = TextAlign.Center, fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(2.dp))
        }
        Row(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Create Account?",
                fontSize = 18.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 100.dp),
            )
            Text(
                text = "Sign up",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable(onClick = {navController.navigate(Signup.route)})
                    .padding(start = 8.dp)
            )
        }
    }
}