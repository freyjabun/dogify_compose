package com.example.dogify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.dogify.breedlist.view.BreedList
import com.example.dogify.breedlist.viewmodel.BreedListVM
import com.example.dogify.breedpics.view.BreedPic
import com.example.dogify.breedpics.view.BreedPics
import com.example.dogify.breedpics.viewmodel.BreedPicsVM
import com.example.dogify.favorites.view.Favorites
import com.example.dogify.favorites.viewmodel.FavoritesVM
import com.example.dogify.nav.navItems
import com.example.dogify.ui.theme.DogifyTheme
import com.example.dogify.utils.FavoritesDatabase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogifyTheme {
                val navController = rememberNavController()
                val configuration = LocalConfiguration.current
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                val roomDb = FavoritesDatabase.getDatabase(LocalContext.current)

                Scaffold(modifier = Modifier.fillMaxSize()
                    ,
                    bottomBar = {
                        NavigationBar(){
                            navItems.forEachIndexed{index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        navController.navigate(item.route)
                                    },
                                    label = {
                                        Text(text = item.title)
                                        },
                                    icon = {
                                        BadgedBox(
                                            badge = {
                                                if(item.badgeCount != null) {
                                                    Badge {
                                                        Text(text = item.badgeCount.toString())
                                                    }
                                                } else if (item.hasNews) {
                                                    Badge()
                                                }
                                            }
                                        ){
                                            Icon(imageVector = if (index == selectedItemIndex){
                                                item.selectedIcon
                                            }else item.unselectedIcon,
                                                contentDescription = item.title)
                                        }
                                    }
                                )
                            }
                        }
                    }

                ) {
                    innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BreedList,
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable<BreedList>
                        {
                            BreedList(navController)
                        }
                        composable<BreedPic>
                        {
                            val breedPic: BreedPic = it.toRoute<BreedPic>()
                            val breedPicVM = viewModel<BreedPicsVM>{BreedPicsVM(
                                breedPic = breedPic,
                                db = roomDb)}

                            BreedPics(breedPicVM)
                        }
                        composable<Favorites>
                        {
                            val favoriteVM = viewModel<FavoritesVM>{FavoritesVM(
                                db = roomDb
                            )}
                            Favorites(favoriteVM)
                        }
                    }
                }
            }
        }
    }
}
