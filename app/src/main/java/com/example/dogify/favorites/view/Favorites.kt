package com.example.dogify.favorites.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Composable
fun Favorites() {
    Column (modifier = Modifier.fillMaxWidth()){
        Text(text = "Favorites page!")
    }
}

@Serializable
object Favorites