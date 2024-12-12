package ipca.example.shoppinglist.ui.lists

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ipca.example.shoppinglist.models.ListItems
import ipca.example.shoppinglist.repositories.ListItemsRepository
import kotlinx.coroutines.launch

class ListListsViewModel : ViewModel() {

    var state = mutableStateOf(ListListsState())
        private set

    fun loadLists() {
        viewModelScope.launch {
            ListItemsRepository.getLists { lists ->
                state.value = state.value.copy(lists = lists)
            }
        }
    }
}

data class ListListsState(
    val lists: List<ListItems> = emptyList()
)
