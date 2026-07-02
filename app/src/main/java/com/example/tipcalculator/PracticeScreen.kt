package com.example.tipcalculator

import Inputfield
import RoundIconButton
import TopHeader
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PracticeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        MainContent()
    }
}

@Composable
fun MainContent() {
    val totalBillState = remember { mutableStateOf("") }
    val splitIn = remember { mutableStateOf("1") }
    val tipAmount = remember { mutableStateOf("") }
    val total = remember(totalBillState.value, tipAmount.value, splitIn.value) {
        val bill = totalBillState.value.toDoubleOrNull() ?: 0.0
        val tip = tipAmount.value.toDoubleOrNull() ?: 0.0
        val split = splitIn.value.toIntOrNull()?.takeIf { it > 0 } ?: 1
        (bill + tip) / split
    }

    TopHeader(
        totalPerPerson = total
    )
    BillForm(totalBillState, tipAmount, splitIn)
}

@Composable
fun BillForm(
    totalBillState: MutableState<String>,
    tipAmount: MutableState<String>,
    splitIn: MutableState<String>,
    onValueChange: (String) -> Unit = {},
) {

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    Card(
        modifier = Modifier
            .height(
                400.dp
            )
            .fillMaxWidth()
            .padding(
                20.dp
            )
    ) {

        Column() {
            Inputfield(
                valueState = totalBillState,
                Label = "Enter Bill Amount",
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                onAction = KeyboardActions {
                    if (!validState) {
                        onValueChange(totalBillState.value.trim())
                        keyboardController?.hide()
                    }
                }
            )
            if (validState) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Split in",
                        fontSize = 20.sp
                    )
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RoundIconButton(
                            text = "-",
                            onClick =
                                {
                                    val current = splitIn.value.toIntOrNull() ?: 1
                                    if (current > 1) {
                                        splitIn.value = (current - 1).toString()
                                    }
                                }

                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = splitIn.value,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        RoundIconButton(
                            "+",
                            onClick = {
                                val current = splitIn.value.toIntOrNull() ?: 1
                                splitIn.value = (current + 1).toString()
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.width(70.dp))
                    Text(
                        text = "Tip",
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(120.dp))

                    Text(
                        text = "₹${tipAmount.value}",
                        fontSize = 25.sp
                    )
                }

                Inputfield(
                    valueState = tipAmount,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .width(30.dp),

                    Label = "Enter Tip Amount",
                    singleLine = true,
                    enabled = true,
                    onAction = KeyboardActions {},
                )

            }
        }
    }
}





