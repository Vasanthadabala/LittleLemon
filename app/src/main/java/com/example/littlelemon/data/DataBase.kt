package com.example.littlelemon.data

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
    abstract fun menuDao(): MenuDao //connecting dao interface to function so we can access and perform operations menu items.
}
@Dao
interface MenuDao{
    @Query("SELECT * FROM menu_items")
    fun getAllMenuItems():LiveData<List<MenuItemEntity>>
    @Insert
    fun insertAll(menuItems: List<MenuItemEntity>)
    @Query("SELECT (SELECT COUNT(*) FROM menu_items) == 0")
    fun isEmpty(): Boolean
}

@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String,
)
