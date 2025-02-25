package com.example.dogify.breedpics.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Composable
fun BreedPics(){
    Column (){
        Text(text = "Pictures of breeds, only accessible from BreedList!")
    }
}

@Serializable
object BreedPics