package com.example.dogify.breeds.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.dogify.R
import com.example.dogify.breeds.model.Breed
import com.example.dogify.breeds.viewmodel.BreedImagesViewModel
import kotlinx.serialization.Serializable


@Composable
fun BreedPics(vm: BreedImagesViewModel) {

    val breedPics by vm.breedPics.collectAsState()

    LaunchedEffect(vm) {
        vm.getBreedPics()
    }

    val onClickFavorite: (Breed) -> Unit = {
        vm.toggleFavorite(it)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(breedPics) { item ->
            BreedPicItem(
                breed = item,
                onClickFavorite
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BreedPicItem(
    breed: Breed,
    onClickFavorite: (Breed) -> Unit
) {
    Card(onClick = {
        onClickFavorite(breed)
    }) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box{
                GlideImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                        .height(300.dp),
                    model = breed.breedImageUrl,
                    contentDescription = "Image of specific dog breed",
                    loading = placeholder(R.drawable.placeholder_dog),
                    failure = placeholder(R.drawable.placeholder_dog),
                    contentScale = ContentScale.Crop,
                )
                FloatingActionButton(onClick = {
                    onClickFavorite(breed)
                }) {
                    Icon(imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "")
                }
            }
        }
    }
}

@Serializable
data class BreedPic(
    val breedName: String,
    val subBreedName: String?
)

