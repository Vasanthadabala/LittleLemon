package com.example.littlelemon

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Database(entities = [MenuItemEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase:RoomDatabase()
{
    abstract fun menuDao():MenuDao //connecting dao interface to function so we can access and perform operations menu items.
}
@Dao
interface MenuDao{
    @Query("SELECT * FROM menu_items")
    suspend fun getAllMenuItems():LiveData<List<MenuItemEntity>>//
    @Insert
    suspend fun insertAll(menuItems: List<MenuItemEntity>)
}

@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String
)
