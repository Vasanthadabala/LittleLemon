package com.example.littlelemon.navigation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.littlelemon.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@ExperimentalMaterial3Api
@Composable
fun HomeTopBar(navController:NavHostController)
{
    val userId = Firebase.auth.currentUser?.uid
    var imageUrl by remember { mutableStateOf<Uri?>(null) }

    userId?.let { userId ->
        val storageRef = Firebase.storage.reference.child("profile_images/$userId.jpg")

        // Fetch the image URL
        storageRef.downloadUrl.addOnSuccessListener {
            imageUrl = it
        }
    }
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(60.dp)
                    .padding(top = 5.dp, bottom = 5.dp, start = 25.dp)
            )
        },
        actions = {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(40.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberImagePainter(imageUrl?.toString()),
                    contentDescription = "Profile",
                    modifier = Modifier.clickable { navController.navigate(Profile.route) }
                )
            }
        }
    )
}