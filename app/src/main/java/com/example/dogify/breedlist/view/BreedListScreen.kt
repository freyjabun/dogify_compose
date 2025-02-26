package com.example.dogify.breedlist.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.dogify.R
import com.example.dogify.breedlist.model.BreedEntry
import com.example.dogify.breedlist.viewmodel.BreedListVM
import com.example.dogify.breedpics.view.BreedPic
import com.example.dogify.breedpics.view.BreedPics
import kotlinx.serialization.Serializable

@Composable
fun BreedList(viewModel: BreedListVM, navController: NavController){
    LaunchedEffect(Unit) {
        viewModel.loadAllBreeds()
    }

    val breedList = viewModel.breedList.collectAsState().value

    if (breedList.isEmpty()) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ){
            CircularProgressIndicator()
        }
    }
    else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(breedList) {
                breedEntry -> DogBreedItem(breedEntry, navController)
            }
        }
    }
}

@Composable
fun DogBreedItem(breedEntry: BreedEntry, navController: NavController){
    Column (modifier = Modifier.clickable {
        navController.navigate(BreedPic(
                breedName = breedEntry.breedName
            )
        )
    },
        horizontalAlignment = Alignment.CenterHorizontally){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                    .data(breedEntry.breedImageUrl)
                    .build(),
            contentDescription = "Picture of dog",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder_dog),
            fallback = painterResource(R.drawable.placeholder_dog)
        )
        Text(text = breedEntry.breedName,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Serializable
object BreedList