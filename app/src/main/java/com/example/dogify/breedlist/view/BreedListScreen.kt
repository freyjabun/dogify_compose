package com.example.dogify.breedlist.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dogify.breedpics.view.BreedPics
import kotlinx.serialization.Serializable

@Composable
fun BreedList(navController: NavController){
    Column (modifier = Modifier.fillMaxWidth())
    {
        Text(text = "List of dawgs",
            fontSize = 50.sp)
        Button(
            onClick = {
                navController.navigate(BreedPics)
                      },
        ) {
            Text("Cool Dogs Here")
        }
    }
}

@Serializable
object BreedList