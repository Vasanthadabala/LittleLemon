package com.example.littlelemon.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@ExperimentalGlideComposeApi
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

@ExperimentalGlideComposeApi
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

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }


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
            .padding(5.dp)
            .fillMaxSize()
    )
    {
        Text(
            text = "Profile Information",
            textAlign = TextAlign.Start,
            fontSize = 26.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.padding(top = 80.dp, bottom = 40.dp, start = 12.dp)
        )
        ImageSelector(
            onImageSelected = { uri ->
                selectedImageUri = uri
                if (isEditMode) {
                    // Upload the selected image to Firebase Storage
                    userId?.let { uploadImageToFirebaseStorage(context, it, uri, savedMail.orEmpty(), firebaseUsername) }
                }
            },
            onEditModeToggle = {
                isEditMode = !isEditMode
            }
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
                    .padding(vertical = 12.dp, horizontal = 14.dp)
                    .clickable {isEditMode = !isEditMode  },
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
                if (isEditMode) {
                    // Save the edited username to Firebase
                    saveUserInfoToDatabase(userId, editedUsername, savedMail.orEmpty(),selectedImageUri?.toString().orEmpty())
                    Toast.makeText(context, "User Information Saved", Toast.LENGTH_SHORT).show()
                }
                isEditMode = !isEditMode
            },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 1.dp,
                pressedElevation = 5.dp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 180.dp, bottom = 20.dp, start = 14.dp, end = 14.dp),
            shape = RoundedCornerShape(24),
            colors = ButtonDefaults.buttonColors(Color.Yellow)
        ) {
            Text(
                text = if(!isEditMode) "Edit" else "Save",
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

private fun saveUserInfoToDatabase(userId: String?, username: String, email: String,imageUrl: String) {
    val firedb = Firebase.database
    val usersRef = firedb.getReference("users")

    userId?.let {
        val userRef = usersRef.child(it)
        userRef.child("username").setValue(username)
        userRef.child("email").setValue(email)
        userRef.child("imageUrl").setValue(imageUrl)
    }
}

private fun uploadImageToFirebaseStorage(context:Context,userId: String, imageUri: Uri,savedMail:String,firebaseUsername:String) {
    var storageRef = Firebase.storage.reference
    val profileImageRef = storageRef.child("profile_images/$userId.jpg")

    profileImageRef.putFile(imageUri)
        .addOnSuccessListener {
            // Get the download URL and save it to the database
            profileImageRef.downloadUrl.addOnSuccessListener { uri ->
                // Call your function to save the image URL to the database
                saveUserInfoToDatabase(userId, firebaseUsername, savedMail.orEmpty(), uri.toString())
            }
        }
        .addOnFailureListener { exception ->
            Log.e("ProfileScreen", "Image upload failed: ${exception.message}")
            Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
        }
}

@ExperimentalGlideComposeApi
@Composable
fun ImageSelector(onImageSelected: (Uri) -> Unit,onEditModeToggle: () -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val userId = Firebase.auth.currentUser?.uid

    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            onImageSelected(it)
            imageUri = it
        }
    }


    userId?.let { userId ->
        val storageRef = Firebase.storage.reference.child("profile_images/$userId.jpg")
        var imageUrl by remember { mutableStateOf<Uri?>(null) }

        // Fetch the image URL
        storageRef.downloadUrl.addOnSuccessListener {
            imageUrl = it
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .size(150.dp)
                .clickable {
                    // Open the image picker
                    getContent.launch("image/*")
                    onEditModeToggle()
                }, contentAlignment = Alignment.Center
        ) {
            if (imageUrl != null) {
                GlideImage(
                    model = imageUrl?.toString(),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(120.dp).clip(CircleShape)
                )
//                Image(
//                    painter = rememberImagePainter(imageUrl?.toString()),
//                    contentDescription = "Profile Picture",
//                    modifier = Modifier
//                        .size(140.dp)
//                        .clip(CircleShape)
//                )
            } else {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Add Photo",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}