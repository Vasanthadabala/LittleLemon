@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable
fun SignUpScreen(navController:NavHostController)
{

    var username by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    val context= LocalContext.current

    val name = username.text
    val mail = email.text

    val sharedPreferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val editor=sharedPreferences.edit()
    editor.putString("UserName",name).apply()
    editor.putString("Mail",mail).apply()

    val auth = Firebase.auth

    Column(
        verticalArrangement = Arrangement.Top
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(90.dp)
                    .padding(vertical = 20.dp, horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Lets get to know you",
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF41544E))
                    .padding(30.dp)
            )
            Text(
                text = "Personal Information",
                fontSize = 22.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)
            )
            Text(
                text = "Username",
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)
            )
            OutlinedTextField(
                value = username, onValueChange = { newText -> username = newText },
                singleLine = true,
                placeholder = { Text(text = "Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                shape = RoundedCornerShape(16)
            )
            Text(
                text = "Email",
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)
            )
            OutlinedTextField(
                value = email, onValueChange = { newText -> email = newText },
                singleLine = true,
                placeholder = { Text(text = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                shape = RoundedCornerShape(16)
            )
            Text(
                text = "Password",
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)
            )
            OutlinedTextField(
                value = password, onValueChange = { newText -> password = newText },
                singleLine = true,
                placeholder = { Text(text = "Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 14.dp),
                shape = RoundedCornerShape(16)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Button(
                onClick = {
                    if (username.text.isBlank() || password.text.isBlank() || email.text.isBlank()) {
                        Toast.makeText(
                            context,
                            "Registration unsuccessful. Please enter all data",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        signUpWithEmailAndPassword(
                            auth,
                            email.text,
                            password.text,
                            username.text,
                            navController,
                            context
                        )
                    }
                },
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 1.dp,
                    pressedElevation = 5.dp,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                shape = RoundedCornerShape(24),
                colors = ButtonDefaults.buttonColors(Color.Yellow)
            ) {
                Text(
                    text = "Sign up", textAlign = TextAlign.Center, fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(2.dp)
                )
            }
            Row(
                modifier = Modifier.padding(start = 80.dp, top = 10.dp)
            ) {
                Text(
                    text = "Sign In To Account",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "SignIn",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable { navController.navigate(Signin.route) })
            }
        }

    }

}

private fun signUpWithEmailAndPassword(
    auth: FirebaseAuth,
    email: String,
    password:String,
    username:String,
    navController: NavHostController,
    context: Context
) {
    val sharedPreferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val editor=sharedPreferences.edit()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveUserInfoToDatabase(auth.currentUser?.uid,username,email)
                Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                editor.putBoolean("isSignedin",true).apply()
                navController.navigate(Home.route)
                {
                    popUpTo(navController.graph.id){
                        inclusive = true
                    }
                }
            } else {
                Toast.makeText(context, "Registration failed.", Toast.LENGTH_SHORT).show()
            }
        }
}

private fun saveUserInfoToDatabase(userId:String?,username: String,email: String){
    val firedb = Firebase.database

    val usersRef = firedb.getReference("users")

    userId?.let {
        val userRef = usersRef.child(it)
        userRef.child("username").setValue(username)
        userRef.child("email").setValue(email)
    }
}