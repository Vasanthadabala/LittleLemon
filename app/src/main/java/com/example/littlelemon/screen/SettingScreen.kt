package com.example.littlelemon.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.littlelemon.R
import com.example.littlelemon.navigation.BottomBar
import com.example.littlelemon.navigation.MenuItemDetails
import com.example.littlelemon.navigation.Profile
import com.example.littlelemon.navigation.Signin
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun SettingScreen(navController : NavHostController)
{
    Scaffold(
        bottomBar = { BottomBar(navController = navController ) }
    ) {
        Column(Modifier.padding(top = 50.dp)) {
            SettingScreenComponent(navController)
        }
    }
}

@Composable
fun SettingScreenComponent(navController: NavHostController){
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val userId = Firebase.auth.currentUser?.uid
    var firebaseUsername by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf<Uri?>(null) }

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

    userId?.let { userId ->
        val storageRef = Firebase.storage.reference.child("profile_images/$userId.jpg")

        // Fetch the image URL
        storageRef.downloadUrl.addOnSuccessListener {
            imageUrl = it
        }
    }

    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Settings",
            textAlign = TextAlign.Start,
            fontSize = 28.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.padding(top = 10.dp, bottom = 40.dp, start = 12.dp)
        )
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(15),
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { navController.navigate(Profile.route) }
        )
        {
            Row {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(80.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberImagePainter(imageUrl?.toString()),
                        contentDescription = ""
                    )
                }
                Column(
                    modifier = Modifier.padding(top = 20.dp, start = 10.dp)
                ) {
                    Text(
                        text = firebaseUsername,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(15.dp)
                    )
                }
            }
        }
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(15),
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { navController.navigate(Profile.route) }
        ){
            Text(
                text = "About",
                fontSize = 18.sp,
                fontWeight = FontWeight.W500
            )
        }
        Button(
            onClick = {
                clearSharedPreferences(context)
                editor.putBoolean("isLoggedin", false).apply()
                navController.navigate(Signin.route) {
                    popUpTo(navController.graph.id) {
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
                text = "Log out",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}
fun clearSharedPreferences(context: Context)
{
    val sharedPreferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
}