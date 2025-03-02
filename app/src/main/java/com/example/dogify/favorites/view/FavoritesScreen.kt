package com.example.dogify.favorites.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.dogify.R
import com.example.dogify.favorites.model.FavoritesModel
import com.example.dogify.favorites.viewmodel.FavoritesVM
import kotlinx.serialization.Serializable

@Composable
fun Favorites(vm: FavoritesVM) {

//    val onClickFavorite: (BreedPicEntry) -> Unit = {
//        vm.toggleFavorite(it)
//    }

    val favorites by vm.favFlow.collectAsState()

    LazyColumn (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)){
        items(favorites) { favoriteItem ->
            FavoritesItem(
                favorite = favoriteItem
            )
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavoritesItem(favorite: FavoritesModel,) {
    Card {
        Column (modifier = Modifier.padding(10.dp)){
            GlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .height(300.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = favorite.breedImage,
                contentDescription = "Favorite Image",
                loading = placeholder(R.drawable.placeholder_dog),
                failure = placeholder(R.drawable.placeholder_dog),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            val breedLabel = if (favorite.subBreedName.isNullOrEmpty()){
                favorite.breedName
            } else {
                favorite.breedName + " " + favorite.subBreedName
            }
            Text(text = breedLabel,
                fontSize = 25.sp)
        }
    }

}

@Serializable
object Favorites