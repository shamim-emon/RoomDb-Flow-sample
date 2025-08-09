package com.example.roomdb_flow_sample

import kotlinx.coroutines.flow.Flow

class XRepository(private val dao: XDao) {
    val allX: Flow<List<X>> = dao.getAll()

    fun search(query: String): Flow<List<X>> = dao.search(query)

    suspend fun insert(x: X) = dao.insert(x)
    suspend fun update(x: X) = dao.update(x)
    suspend fun delete(x: X) = dao.delete(x)
    fun getById(id: Long): Flow<X?> = dao.getById(id)
}