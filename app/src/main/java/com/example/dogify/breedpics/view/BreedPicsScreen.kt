package com.example.dogify.breedpics.view

import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dogify.R
import com.example.dogify.breedpics.model.BreedPicEntry
import com.example.dogify.breedpics.viewmodel.BreedPicsVM
import com.example.dogify.ui.theme.DogifyTheme
import kotlinx.serialization.Serializable


@Composable
fun BreedPics(vm: BreedPicsVM) {

    val breedPics by vm.breedPics.collectAsState()

    LaunchedEffect(vm) {
        vm.getBreedPics()
    }

    val onClickFavorite: (BreedPicEntry) -> Unit = {
        vm.toggleFavorite(it)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(breedPics) { breedPicEntry ->
            BreedPicItem(
                breedPicEntry = breedPicEntry,
                onClickFavorite
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BreedPicItem(
    breedPicEntry: BreedPicEntry,
    onClickFavorite: (BreedPicEntry) -> Unit
) {
    Card(onClick = {
        onClickFavorite(breedPicEntry)
    }) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(){
                GlideImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                        .height(300.dp),
                    model = breedPicEntry.breedImage,
                    contentDescription = "Image of specific dog breed",
                    loading = placeholder(R.drawable.placeholder_dog),
                    failure = placeholder(R.drawable.placeholder_dog),
                    contentScale = ContentScale.Crop,
                )

                FloatingActionButton(onClick = {
                    onClickFavorite(breedPicEntry)
                }) {
                    Icon(imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "")
                }

            }

            Spacer(modifier = Modifier.height(8.dp))
            val breedLabel = if (breedPicEntry.subBreedName.isNullOrEmpty()) {
                breedPicEntry.breedName
            } else {
                breedPicEntry.breedName + " " + breedPicEntry.subBreedName
            }
//            Row (horizontalArrangement = Arrangement.spacedBy(40.dp)
//            , verticalAlignment = Alignment.CenterVertically){
//                Text(
//                    text = breedLabel,
//                    fontSize = 25.sp
//                )
//                Button(onClick = {
//                    onClickFavorite(breedPicEntry)
//                },
//
//                ) {
//                    Icon(
//                        imageVector = Icons.Outlined.FavoriteBorder,
//                        contentDescription = "Favorites Button Icon"
//                    )
//                }
//            }

        }

    }
}

@Serializable
data class BreedPic(
    val breedName: String,
    val subBreedName: String?
)

