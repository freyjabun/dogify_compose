package com.example.dogify.breedpics.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.dogify.R
import com.example.dogify.breedpics.model.BreedPicEntry
import com.example.dogify.breedpics.viewmodel.BreedPicsVM
import kotlinx.serialization.Serializable

@Composable
fun BreedPics(breedName: String? = null){
    val viewModel: BreedPicsVM = viewModel()
    val breedPics = viewModel.breedPics.collectAsState().value

    println("Status of breedName: $breedName")
    LaunchedEffect(breedName) {
        if (breedName != null) {
            viewModel.getBreedPics(breedName)
        }
    }
    var liked by rememberSaveable { mutableStateOf(false) }
    LazyRow (modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){
        items(breedPics){
            breedPicEntry -> BreedPicItem(
                breedPicEntry = breedPicEntry
            )
        }
    }
}

@Composable
fun BreedPicItem(breedPicEntry: BreedPicEntry){
    Column (modifier = Modifier.fillMaxSize()){
        Box(){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(breedPicEntry.breedImage)
                    .build(),
                fallback = painterResource(R.drawable.placeholder_dog),
                placeholder = painterResource(R.drawable.placeholder_dog),
                contentScale = ContentScale.Crop,

                contentDescription = "Picture of dog by breed"
            )


        }
        Text(text = breedPicEntry.breedName,
            textAlign = TextAlign.Center)
    }
}

@Serializable
data class BreedPic(
    val breedName: String
)