package com.example.dogify.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogify.favorites.model.FavoritesModel
import com.example.dogify.favorites.model.FavoritesRepository
import com.example.dogify.utils.FavoritesDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoritesVM(db: FavoritesDatabase) : ViewModel() {

    private val repo = FavoritesRepository(db.dao)

    val favFlow: StateFlow<List<FavoritesModel>> = repo.getFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )
}
