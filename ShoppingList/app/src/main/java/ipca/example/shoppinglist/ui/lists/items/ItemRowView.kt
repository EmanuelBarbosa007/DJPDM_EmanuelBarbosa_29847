package ipca.example.shoppinglist.ui.lists.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.example.shoppinglist.models.Item
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun ItemRomView(
    modifier: Modifier = Modifier,
    item : Item,
    onCheckedChange : ()->Unit = {}
){
    Row {
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
            checked =item.checked,
            onCheckedChange = {onCheckedChange()}
        )

    }

}

@Preview(showBackground = true)
@Composable
fun ItemRomViewPreview(){
    ShoppingListTheme {
        ItemRomView(item = Item(
            docId = "",
            name = "Lápis",
            qtd = 2.0,
            price = 2.0,
            checked = false
        )
        )
    }
}