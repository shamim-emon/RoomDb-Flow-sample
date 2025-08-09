package com.example.roomdb_flow_sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class XViewModel(private val repo: XRepository) : ViewModel() {

    // Expose DB flow as StateFlow for easy UI consumption
    val xs: StateFlow<List<X>> = repo.allX
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // Example: search with debounce
    private val _query = MutableStateFlow("")
    fun setQuery(q: String) { _query.value = q }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResults: StateFlow<List<X>> = _query
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { q -> if (q.isBlank()) repo.allX else repo.search(q) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun insert(x: X) = viewModelScope.launch { repo.insert(x) }
    fun update(x: X) = viewModelScope.launch { repo.update(x) }
    fun delete(x: X) = viewModelScope.launch { repo.delete(x) }
}