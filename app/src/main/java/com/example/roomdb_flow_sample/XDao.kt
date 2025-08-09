package com.example.roomdb_flow_sample

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface XDao {
    @Query("SELECT * FROM x_table ORDER BY id DESC")
    fun getAll(): Flow<List<X>>

    @Query("SELECT * FROM x_table WHERE id = :id")
    fun getById(id: Long): Flow<X?>

    @Query("SELECT * FROM x_table WHERE name LIKE '%' || :query || '%' ORDER BY id DESC")
    fun search(query: String): Flow<List<X>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(x: X)

    @Update
    suspend fun update(x: X)

    @Delete
    suspend fun delete(x: X)
}