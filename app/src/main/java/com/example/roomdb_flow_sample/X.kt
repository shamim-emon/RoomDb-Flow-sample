package com.example.roomdb_flow_sample

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

// models.kt
data class Y(val label: String, val value: Int)

@Entity(tableName = "x_table")
data class X(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val details: List<Y>
)

// Converters.kt
class Converters {
    private val gson = com.google.gson.Gson()
    private val listType = object : com.google.gson.reflect.TypeToken<List<Y>>() {}.type

    @TypeConverter
    fun fromYList(list: List<Y>?): String = gson.toJson(list)

    @TypeConverter
    fun toYList(json: String?): List<Y> =
        if (json.isNullOrEmpty()) emptyList() else gson.fromJson(json, listType)
}