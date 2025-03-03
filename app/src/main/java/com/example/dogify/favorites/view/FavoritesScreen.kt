package com.example.dogify.favorites.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.dogify.R
import com.example.dogify.breeds.model.Breed
import com.example.dogify.favorites.viewmodel.FavoritesViewModel
import kotlinx.serialization.Serializable

@Composable
fun Favorites(vm: FavoritesViewModel) {

    val onClickFavorite: (Breed) -> Unit = {
        vm.toggleFavorite(it)
    }

    val favorites by vm.filteredFavoritesFlow.collectAsState()
    val breedsInFavorites by vm.allBreedsFlow.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ){
        Column (
            modifier = Modifier.fillMaxSize()
        ){
            BreedDropdown(breeds = breedsInFavorites, onBreedSelected = {breed ->
                vm.selectBreed(breed)
            }, onClearFilter = {
                vm.clearBreedFilter()
            })
            Spacer(modifier = Modifier.height(16.dp))

            if (favorites.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Favorite's list is empty",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            else {
                LazyColumn(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(favorites) { favorite ->
                        FavoritesItem(
                            breed = favorite,
                            onClickFavorite
                        )
                    }
                }
            }

        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavoritesItem(
    breed: Breed,
    onClickFavorite: (Breed) -> Unit
) {
    Card {
        Column(modifier = Modifier.padding(10.dp)) {
            Box {
                GlideImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(400.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = breed.breedImageUrl,
                    contentDescription = "Favorite Image",
                    loading = placeholder(R.drawable.placeholder_dog),
                    failure = placeholder(R.drawable.placeholder_dog),
                    contentScale = ContentScale.Crop
                )
                FloatingActionButton(
                    onClick = {
                        onClickFavorite(breed)
                    },
                    modifier = Modifier.padding(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "X"
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            val breedLabel = if (breed.subBreedName.isNullOrEmpty()) {
                breed.breedName
            } else {
                breed.breedName + " " + breed.subBreedName
            }
            Text(
                text = breedLabel,
                fontSize = 25.sp
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDropdown(breeds: List<Breed>, onBreedSelected: (Breed) -> Unit, onClearFilter: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedBreed by remember { mutableStateOf<Breed?>(null) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(16.dp)
    ) {
        // Display the selected breed, or default to "Select Breed"
        TextField(
            value = selectedBreed?.breedName ?: "Select Breed",
            onValueChange = {},
            readOnly = true,
            label = { Text("Breed") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown Icon"
                )
            },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    selectedBreed = null
                    onClearFilter()
                    expanded = false
                },
                text = { Text(text = "Show all favorites") }
            )
            breeds.forEach { breed ->
                val breedLabel = if (breed.subBreedName.isNullOrEmpty()){
                    breed.breedName
                } else {
                    breed.breedName + " " + breed.subBreedName
                }
                DropdownMenuItem(onClick = {
                    selectedBreed = breed
                    onBreedSelected(breed)
                    expanded = false
                },
                    text = { Text(text = breedLabel) })
            }
        }
    }
}
@Serializable
object Favorites