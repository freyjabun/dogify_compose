package com.example.dogify.breedpics.view

import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.bumptech.glide.integration.compose.placeholder
import com.example.dogify.R
import com.example.dogify.breedpics.model.BreedPicEntry
import com.example.dogify.breedpics.viewmodel.BreedPicsVM
import kotlinx.serialization.Serializable

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BreedPics(breedPic: BreedPic) {

    val viewModel = viewModel<BreedPicsVM> {
        BreedPicsVM(breedPic)
    }

    val breedPics by viewModel.breedPics.collectAsState()

    LaunchedEffect(breedPic) {
        viewModel.getBreedPics()
    }

//    GlideImage(
//        model = "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
//        contentDescription = "HELP",
//        loading = placeholder(R.drawable.placeholder_dog)
//    )
//    AsyncImage(
//        modifier = Modifier.size(150.dp)
//            .background(Color.DarkGray),
//        model = ImageRequest.Builder(LocalContext.current)
//            .data("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg")
//            .build(),
////        fallback = painterResource(R.drawable.placeholder_dog),
////        placeholder = painterResource(R.drawable.placeholder_dog),
////        contentScale = ContentScale.Crop,
//        contentDescription = "Picture of dog by breed"
//    )
    //Text(text = breedPics.toString())
    LazyColumn(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        items(breedPics) { breedPicEntry ->
            BreedPicItem(
                breedPicEntry = breedPicEntry
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BreedPicItem(breedPicEntry: BreedPicEntry) {

    Card {
        Column(modifier = Modifier.padding(10.dp)
            .background(Color.LightGray)){
            GlideImage(modifier = Modifier.fillMaxSize()
                .height(300.dp),
                model = breedPicEntry.breedImage,
                contentDescription = "Image of specific dog breed",
                loading = placeholder(R.drawable.placeholder_dog),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8 .dp))
            val breedLabel = if (breedPicEntry.subBreedName.isNullOrEmpty()){
                breedPicEntry.breedName
            } else {
                breedPicEntry.breedName + " " + breedPicEntry.subBreedName
            }
            Text(text = breedLabel)
        }

    }
}

@Serializable
data class BreedPic(
    val breedName: String,
    val subBreedName: String?
)

