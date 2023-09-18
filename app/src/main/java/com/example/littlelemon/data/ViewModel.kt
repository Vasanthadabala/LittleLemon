package com.example.littlelemon.data

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(application: Application):AndroidViewModel(application) {
    private val database: AppDataBase
    init {
        database =
            Room.databaseBuilder(application, AppDataBase::class.java, "database1").build()
    }

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text","plain"))
        }
    }

    private  suspend fun fetchMenuData():List<MenuItemNetwork>
    {
        return client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetworkData>().menu // Declaring End point for accesing data
    }

    private  fun SaveMenuToLocalDatabase(menuItems:List<MenuItemNetwork>){
        val menuItemsRoom = menuItems.map {
            it.data()
        }
        database.menuDao().insertAll(menuItemsRoom)
    }

    @SuppressLint("SuspiciousIndentation")
    fun fetchMenuDataIfNeeded()
    {
        viewModelScope.launch(Dispatchers.IO) {
            val menuDao = database.menuDao()
            if(menuDao.isEmpty())
            try {
                val menuItemNetwork = fetchMenuData()
                SaveMenuToLocalDatabase(menuItemNetwork)
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    fun getAllDatabaseMenuItems():LiveData<List<MenuItemEntity>>
    {
        return database.menuDao().getAllMenuItems()
    }
}