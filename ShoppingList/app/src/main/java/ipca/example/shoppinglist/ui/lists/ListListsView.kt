package ipca.example.shoppinglist.ui.lists

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.shoppinglist.R
import ipca.example.shoppinglist.Screen
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme
import ipca.example.shoppinglist.repositories.ListItemsRepository
import kotlinx.coroutines.launch


@Composable
fun ListListsView(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    val viewModel: ListListsViewModel = viewModel()
    val state = viewModel.state.value
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            items(state.lists) { list ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.ListItems.route.replace("{listId}", list.docId ?: ""))
                        }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = list.name ?: "Unnamed List",
                        modifier = Modifier.weight(1f)
                    )

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                ListItemsRepository.deleteList(list.docId!!)
                                viewModel.loadLists() // Atualiza pagina após apagar lista
                            }
                        }
                    ) {
                        Text("Delete")
                    }
                }
            }
        }


        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(64.dp),
            onClick = {
                navController.navigate(Screen.AddList.route)
            }
        ) {
            Image(
                modifier = Modifier
                    .scale(2.0f)
                    .size(64.dp),
                colorFilter = ColorFilter.tint(Color.White),
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "add"
            )
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.loadLists()
    }
}


@Preview(showBackground = true)
@Composable
fun ListListViewPreview(){
    ShoppingListTheme {
        ListListsView()
    }
}