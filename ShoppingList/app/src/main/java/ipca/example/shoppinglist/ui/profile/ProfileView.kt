package ipca.example.shoppinglist.ui.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import ipca.example.shoppinglist.Screen
import ipca.example.shoppinglist.ui.theme.ShoppingListTheme

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    val viewModel: ProfileViewModel = viewModel()
    val state = viewModel.state.value
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Input field for name
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            placeholder = { Text("Enter name") },
            value = state.name ?: "",
            onValueChange = viewModel::onNameChange
        )

        // Display user email
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = state.user?.email ?: "No email available"
        )

        // Save button
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClick = {
                viewModel.saveUser()
                navController.popBackStack()
            }
        ) {
            Text("Save")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                coroutineScope.launch {
                    FirebaseAuth.getInstance().signOut() // termina sessão no Firebase
                    kotlinx.coroutines.delay(500) //timer

                    // volta para a tela de login e limpa o historico de navegação
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        ) {
            Text("Log Out")
        }

    }

    // Fetch user data on first load
    LaunchedEffect(key1 = true) {
        viewModel.getUser()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileViewPreview() {
    ShoppingListTheme {
        ProfileView()
    }
}

