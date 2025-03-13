package com.example.dogify.breeds.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.dogify.R
import com.example.dogify.breeds.model.Breed
import com.example.dogify.breeds.viewmodel.BreedListViewModel
import kotlinx.serialization.Serializable

@Composable
fun BreedList(navController: NavController, viewModel: BreedListViewModel) {

    LaunchedEffect(Unit) {
        viewModel.loadAllBreeds()

    }

    val breedList = viewModel.breedList.collectAsState().value

    if (breedList.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(horizontal = 5.dp)
        ) {
            items(breedList) { breedEntry ->
                DogBreedItem(breedEntry, navController)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DogBreedItem(breed: Breed, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable {
                navController.navigate(
                    BreedPic(
                        breedName = breed.breedName,
                        subBreedName = breed.subBreedName
                    )
                )
            },
    )
    {
        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally)
        {
            GlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp)), model = breed.breedImageUrl,
                contentDescription = "Image of dog",
                loading = placeholder(R.drawable.placeholder_dog),
                failure = placeholder(R.drawable.placeholder_dog),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            val breedLabel = if (breed.subBreedName.isNullOrEmpty()) {
                breed.breedName
            } else {
                breed.breedName + " " + breed.subBreedName
            }
            Text(
                text = breedLabel,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Serializable
object BreedList