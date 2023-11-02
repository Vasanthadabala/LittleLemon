@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon.screen

import android.content.Context
import android.widget.Toast
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
import com.example.littlelemon.R
import com.example.littlelemon.navigation.Home
import com.example.littlelemon.navigation.Signin
import com.example.littlelemon.navigation.Signup

@Composable
fun SignUpScreen(navController:NavHostController)
{
    var firstname by remember { mutableStateOf(TextFieldValue("")) }
    var lastname by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }

    val context= LocalContext.current

    val firstName = firstname.text
    val lastName = lastname.text
    val mail = email.text
    val sharedPreferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val editor=sharedPreferences.edit()
    editor.putString("FirstName", firstName).apply()
    editor.putString("LastName", lastName).apply()
    editor.putString("Mail",mail).apply()

    Column(Modifier.padding(0.dp)) {
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .size(90.dp)
                .padding(vertical = 20.dp, horizontal = 20.dp)
                .align(Alignment.CenterHorizontally))
        Text(text = "Lets get to know you",
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            textAlign= TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF41544E))
                .padding(40.dp))
        Text(text = "Personal Information", fontSize = 20.sp, fontWeight = FontWeight.W500,
            modifier= Modifier.padding(top = 60.dp, bottom = 40.dp, start = 12.dp)
        )
        Text(
            text = "First name",
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(top = 10.dp, start = 14.dp)
        )
        OutlinedTextField(value = firstname, onValueChange ={newText->firstname=newText},
            singleLine = true,
            placeholder = { Text(text = "Firstname") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 14.dp),
            shape = RoundedCornerShape(16)
        )
        Text(
            text = "Last name",
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(top = 10.dp, start = 14.dp)
        )
        OutlinedTextField(value = lastname , onValueChange ={newText->lastname=newText},
            singleLine = true,
            placeholder = { Text(text = "Lastname") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 14.dp),
            shape = RoundedCornerShape(16)
        )
        Text(
            text = "Email",
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(top = 10.dp, start = 14.dp)
        )
        OutlinedTextField(value = email, onValueChange = {newText->email=newText},
            singleLine = true,
            placeholder = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            shape = RoundedCornerShape(16)
        )
        Button(onClick = {
            if (firstname.text.isBlank()|| lastname.text.isBlank() || email.text.isBlank())
            {
                Toast.makeText(context, "Registration unsuccessful. Please enter all data", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                editor.putBoolean("isSignedup",true).apply()
                navController.navigate(Home.route)
                {
                    popUpTo(navController.graph.id){
                        inclusive = true
                    }
                }
            }
        },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 1.dp,
                pressedElevation = 5.dp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 20.dp, start = 14.dp, end = 14.dp),
            shape = RoundedCornerShape(24),
            colors = ButtonDefaults.buttonColors(Color.Yellow)
        ) {
            Text(text = "Sign up", textAlign = TextAlign.Center, fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(2.dp))
        }
        Row {
            Text(
                text = "Sign In To Account",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 80.dp)
            )
            Text(
                text = "Signin",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 10.dp, top = 2.dp)
                    .clickable { navController.navigate(Signin.route)})
        }

    }

}