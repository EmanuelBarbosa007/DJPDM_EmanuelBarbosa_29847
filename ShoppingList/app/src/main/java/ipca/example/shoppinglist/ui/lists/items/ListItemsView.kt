package ipca.example.shoppinglist.ui.lists.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun ListItemsView(
    modifier: Modifier = Modifier,
    listId : String,
    navController: NavController = rememberNavController()
                  ){

    val viewModel : ListItemsViewModel = viewModel()
    val state = viewModel.state.value

    var totalPrice by remember { mutableStateOf(0.0) }

    LaunchedEffect(state.items) {
        totalPrice = state.items.filter { !it.checked }.sumOf { it.price ?: 0.0 }
    }

    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd) {

        Column(modifier = modifier.fillMaxSize()) {
            state.items.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Name: ${item.name}",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Qtd: ${item.qtd}",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Price: ${item.price}",
                        modifier = Modifier.weight(1f)
                    )
                    Checkbox(
                        checked = item.checked,
                        onCheckedChange = { isChecked ->
                            viewModel.updateItemChecked(listId, item, isChecked)
                        },
                        modifier = Modifier.weight(0.5f)
                    )
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Total Price: $totalPrice",
                modifier = Modifier.padding(16.dp)
            )
        }
        Row {
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .size(64.dp),
                onClick = {

                    navController.navigate(Screen.AddItem.route.replace("{listId}", listId))
                }) {
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
    }


    LaunchedEffect (key1 = true){
        viewModel.getItems(listId)
    }

}

@Preview(showBackground = true)
@Composable
fun ListItemsViewPreview(){
    ShoppingListTheme {
        ListItemsView(listId = "")
    }
}