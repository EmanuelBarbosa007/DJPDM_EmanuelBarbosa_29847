package ipca.examples.calulator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.examples.calulator.ui.components.CalcButton
import ipca.examples.calulator.ui.theme.CalulatorTheme

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {


    var displayText by remember { mutableStateOf("0") }
    var previousValue by remember { mutableStateOf<Double?>(null) }
    var operation by remember { mutableStateOf<String?>(null) }
    var isNewValue by remember { mutableStateOf(false) }


    val onNumPressed: (String) -> Unit = { num ->
        if (displayText == "0" || isNewValue) {
            displayText = num
            isNewValue = false
        } else {
            displayText += num
        }
    }


    val onOperationPressed: (String) -> Unit = { op ->
        previousValue = displayText.toDoubleOrNull()
        operation = op
        isNewValue = true
    }

    
    val onEqualsPressed: () -> Unit = {
        val currentValue = displayText.toDoubleOrNull()
        if (previousValue != null && currentValue != null && operation != null) {
            val result = when (operation) {
                "+" -> previousValue!! + currentValue
                "-" -> previousValue!! - currentValue
                "*" -> previousValue!! * currentValue
                "/" -> if (currentValue != 0.0) previousValue!! / currentValue else "Error"
                else -> "Error"
            }
            displayText = result.toString()
            previousValue = null
            operation = null
            isNewValue = true
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textAlign = TextAlign.Right,
            text = displayText,
            style = MaterialTheme.typography.displayLarge
        )
        Row(modifier = Modifier.weight(1f)) {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "C",
                onClick = { displayText = "0"; previousValue = null; operation = null; isNewValue = false }
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "(",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = ")",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "%",
                onClick = onNumPressed
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "7",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "8",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "9",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "+",
                isOperation = true,
                onClick = { onOperationPressed("+") }
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "4",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "5",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "6",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "-",
                isOperation = true,
                onClick = { onOperationPressed("-") }
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "1",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "2",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "3",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "/",
                isOperation = true,
                onClick = { onOperationPressed("/") }
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "0",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = ".",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "=",
                isOperation = true,
                onClick = { onEqualsPressed() }
            )

            CalcButton(
                modifier = Modifier.weight(1f),
                label = "*",
                isOperation = true,
                onClick = { onOperationPressed("*") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalulatorTheme {
        CalculatorScreen()
    }
}