@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@ExperimentalGlideComposeApi
class MainActivity : ComponentActivity() {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            }
            )
        }
    }
    private  suspend fun fetchMenuData():List<MenuItemNetwork>
    {
        return client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetworkData>().menu // Declaring End point for accesing data
    }
    
    private val database by lazy{
        Room.databaseBuilder(applicationContext,AppDataBase::class.java,"database1").build()
        //Stores Instance of Room database(more like dbms->database which manages the data of room)
    }

    private  fun SaveMenuToLocalDatabase(menuItems:List<MenuItemNetwork>){
        val menuItemsRoom = menuItems.map {
            it.data()
        }
        database.menuDao().insertAll(menuItemsRoom)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            if(database.menuDao().isEmpty()){
                val menuItemNetwork = fetchMenuData()
                SaveMenuToLocalDatabase(menuItemNetwork)
            }
        }
        setContent {
            LittleLemonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavigation(database)
                }
            }
        }
    }
}
@ExperimentalMaterial3Api
@ExperimentalGlideComposeApi
@Composable
fun MyNavigation(database:AppDataBase)
{
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = Login.route)
    {
        composable(Login.route)
        {
            LoginScreen(navController)
        }
        composable(Signup.route)
        {
            SignUpScreen(navController)
        }
        composable(Home.route)
        {
            HomeScreen(navController,database)
        }
        composable(Profile.route)
        {
            ProfileScreen(navController)
        }
    }
}
