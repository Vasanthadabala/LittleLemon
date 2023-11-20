package com.example.littlelemon.screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = {ProfileTopBar(navController = navController)},
    ) {
        ProfileScreenComponent()
    }
}

@ExperimentalMaterial3Api
@Composable
fun ProfileScreenComponent() {

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)

    val userName = sharedPreferences.getString("UserName","")?:"Guest User"
    val savedMail = sharedPreferences.getString("Mail", "")

    val userId = Firebase.auth.currentUser?.uid
    var firebaseUsername by remember { mutableStateOf(userName) }

    var isEditMode by remember { mutableStateOf(false) }
    var editedUsername by remember { mutableStateOf(firebaseUsername) }


    userId?.let {
        val usersRef = Firebase.database.getReference("users").child(it)
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usernameFromDatabase = snapshot.child("username").getValue(String::class.java)
                if (usernameFromDatabase != null) {
                    firebaseUsername = usernameFromDatabase
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("ProfileScreen", "Error reading from database: ${error.message}")
            }
        })
    }


    Column(
        Modifier
            .padding(0.dp)
            .fillMaxWidth()
    )
    {
        Text(
            text = "Profile Information",
            textAlign = TextAlign.Start,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 80.dp, bottom = 40.dp, start = 12.dp)
        )
        Column {
            Text(
                text = "User name",
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(top = 10.dp, start = 14.dp)
            )
            OutlinedTextField(
                value = if (isEditMode) editedUsername else firebaseUsername,
                onValueChange = {
                    editedUsername = it
                },
                readOnly = !isEditMode,
                singleLine = true,
                enabled = isEditMode,
                textStyle = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.W600,
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
                    fontWeight = FontWeight.W600,
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
                isEditMode = !isEditMode
                if (!isEditMode) {
                    // Save the edited username to Firebase
                    saveUserInfoToDatabase(userId, editedUsername, savedMail.orEmpty())
                    Toast.makeText(context, "Username Saved", Toast.LENGTH_SHORT).show()
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
                text = if(isEditMode)"Save" else "Edit",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}



@ExperimentalMaterial3Api
@Composable
fun ProfileTopBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(text = "Profile",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )},
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(26.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )
}

private fun saveUserInfoToDatabase(userId: String?, username: String, email: String) {
    val firedb = Firebase.database
    val usersRef = firedb.getReference("users")

    userId?.let {
        val userRef = usersRef.child(it)
        userRef.child("username").setValue(username)
        userRef.child("email").setValue(email)
    }
}