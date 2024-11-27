package ipca.example.spacefighter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GameOverView(
    modifier: Modifier = Modifier,
    onPlayAgainClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Game Over",
                fontSize = 100.sp
            )

            Spacer(modifier = Modifier.height(24.dp)) // Espaçamento entre o título e o botão

            // Botão para jogar novamente
            Button(onClick = { onPlayAgainClick() }) {
                Text(text = "Play Again")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GameOverViewPreview() {
    GameOverView(
        onPlayAgainClick = {}
    )
}


