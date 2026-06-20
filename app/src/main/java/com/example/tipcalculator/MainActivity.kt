package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.CurrencyRupee
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

val IconButtonSizeModifier = Modifier.size(25.dp)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp() {
                TipCalculator()
            }
        }
    }
}

@Composable
fun TipCalculator() {
    Surface(modifier = Modifier.padding(12.dp)) {
        Column() {
            MainContent()
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    TipCalculatorTheme() {
        Surface(
            color = MaterialTheme.colorScheme.background, modifier = Modifier
        ) {
            content()
        }
    }
}

@Composable
fun TopHeader(
    totalPerPerson: Double = 0.0
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))), color = Color(0xFF82AFDE)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Total Per Person", style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "₹$total",
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 34.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun MainContent() {

    val splitBy = remember {
        mutableStateOf(1)
    }
    val totalTipAmt = remember {
        mutableStateOf(0.0)
    }
    val totalPerPerson = remember {
        mutableStateOf(0.0)
    }
    BillForm(
        splitByState = splitBy,
        tipAmountState = totalTipAmt,
        totalPerPersonState = totalPerPerson
    )
}

@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    range: IntRange = 1..100,
    splitByState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
    onValChange: (String) -> Unit = {}
) {
    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliderPositionState.value * 100).toInt()

    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }


// 2. Calculate total per person automatically
    val totalPerPerson = if (totalBillState.value.isNotEmpty() && splitByState.value > 0) {
        calculatePerPersonTotal(
            totalBill = totalBillState.value.toDouble(),
            splitBy = splitByState.value,
            tipPercentage = tipPercentage
        )
    } else 0.0


//    val range = IntRange(start = 1, endInclusive = 100)
    val keyboardController = LocalSoftwareKeyboardController.current
//    var value by remember { mutableStateOf(0) }
    TopHeader(totalPerPerson = totalPerPerson)
    Surface(
        modifier = Modifier
            .shadow(2.dp)
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFF1B1BCE), // its the color of border of the surface

        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InputField(
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) {
                        return@KeyboardActions
                    }
                    keyboardController?.hide()

                })
            if (validState) {
                Row(
                    modifier = Modifier.padding(3.dp), horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Split in ",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically),
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(
                            modifier = Modifier, imageVector = Icons.Default.Remove, onClick = {
                                splitByState.value = if (splitByState.value > 1) {
                                    splitByState.value - 1
                                } else {
                                    1
                                }
                                totalPerPersonState.value = calculatePerPersonTotal(
                                    totalBill = totalBillState.value.toDouble(),
                                    splitBy = splitByState.value,
                                    tipPercentage = tipPercentage
                                )
//
                            })
                        Text(
                            text = "${splitByState.value}",
                            modifier = Modifier
                                .padding(4.dp)
                                .align(alignment = Alignment.CenterVertically),
                            fontSize = 20.sp
                        )
                        RoundIconButton(
                            modifier = Modifier, imageVector = Icons.Default.Add, onClick = {
                                splitByState.value = if (splitByState.value < range.last) {
                                    splitByState.value + 1
                                } else {
                                    0
                                }
                                // this is for the  hand to hand updates in values of totalperperson and tipamount
                                totalPerPersonState.value = calculatePerPersonTotal(
                                    totalBill = totalBillState.value.toDouble(),
                                    splitBy = splitByState.value,
                                    tipPercentage = tipPercentage
                                )
                            })
                    }
                }
//tip row
                Row(
                    modifier = Modifier.padding(
                        horizontal = 3.dp, vertical = 12.dp
                    )
                ) {
                    Text(
                        text = "Tip Amount",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(100.dp))
                    Text(
                        text = "${tipAmountState.value}",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontSize = 20.sp
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {

                    Row(
                        modifier = Modifier.padding(
                            horizontal = 3.dp, vertical = 12.dp
                        )
                    ) {
                        Text(
                            text = "Tip Percentage ",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(75.dp))
                        Text(
                            text = "$tipPercentage%",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = 20.sp
                        )
                    }
                    Slider(
                        value = sliderPositionState.value, onValueChange = { newVal ->
                            sliderPositionState.value = newVal
                            val newTipPercentage = (newVal * 100).toInt()
                            tipAmountState.value = calculateTotalTip(
                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = newTipPercentage
                            )
                            totalPerPersonState.value = calculatePerPersonTotal(
                                totalBill = totalBillState.value.toDouble(),
                                splitBy = splitByState.value,
                                tipPercentage = tipPercentage
                            )
                        }, modifier = Modifier.padding(
                            start = 16.dp, end = 16.dp
                        )
//                        , steps = 10 steps in dividing the tip percentage
                    )
                }

            }
        }
    }
}


fun calculateTotalTip(
    totalBill: Double, tipPercentage: Int
): Double {
    return if (totalBill > 1 && totalBill.toString().isNotEmpty()) {
        (totalBill * tipPercentage) / 100
    } else {
        0.0
    }
}

fun calculatePerPersonTotal(
    totalBill: Double, splitBy: Int, tipPercentage: Int
): Double {
    val bill = calculateTotalTip(totalBill = totalBill, tipPercentage = tipPercentage) + totalBill
    return (bill / splitBy)
}

@Composable
fun RoundIconButton(
    modifier: Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    elevation: Dp = 4.dp
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick.invoke() }
            .then(IconButtonSizeModifier),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
    ) {
        //making a box will help in centralize the icons in the card itself , bcs no other property works in side the card ;
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector, contentDescription = "Plus or minus icon ", tint = tint
            )
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.CurrencyRupee, contentDescription = "Money Icon "
            )
        },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground // its the text color inside the input field

        ),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, imeAction = imeAction
        ),
        keyboardActions = onAction,
        modifier = Modifier
            .fillMaxWidth() // use this to fill the max size of the input field  where the amount of the bill is entered ;
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
    )

}


//
//// adding more things
//@Composable
//fun ScreenDemo(model: CounterViewModel) {
//
//    //source: https://www.rockandnull.com/jetpack-compose-viewmodel/
//    Column(modifier = Modifier.padding(14.dp)) {
//        Demo("This is ${model.getCounter()}", counterViewModel = model) {
//            if (it) {
//                model.increaseCounter()
//            } else {
//                model.decreaseCounter()
//            }
//        }
//        if (model.getCounter() > 12) {
//            Text(text = "I love this so much!!")
//        }
//    }
//}
//
//@Composable
//fun Demo(
//    text: String,
//    counterViewModel: CounterViewModel,
//    onclick: (Boolean) -> Unit = {},
//) {
//    val isVal = remember {
//        mutableStateOf(false)
//    }
//    Column(verticalArrangement = Arrangement.Center) {
//        var isRange by remember {
//            mutableStateOf(false)
//        }
//        isRange = counterViewModel.getCounter() == 12
//        Text(text = text, color = if (isRange) Color.Red else Color.LightGray)
//
//
//        Row(verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween) {
//            Button(
//                onClick = {
//                    isVal.value = true
//                    onclick(isVal.value)
//                },
//            ) {
//
//                BasicText(text = "Add 1")
//            }
//
//            Button(
//                onClick = {
//                    isVal.value = false
//                    onclick(isVal.value)
//                },
//            ) {
//
//                BasicText(text = "Minus 1")
//            }
//
//        }
//
//    }
//
//
//}
//
//@Composable
//private fun TipSlider(
//    modifier: Modifier = Modifier,
//    sliderState: MutableState<Float>,
//    totalTipState: MutableState<Double>,
//    totalBillState: MutableState<String>,
//) {
//    val tipPercentage = (sliderState.value.toInt())
//
//    val percentage = buildAnnotatedString {
//        withStyle(style = SpanStyle(fontSize = 32.sp)) { append(tipPercentage.toString()) }
//        append(" %")
//    }
//    //Slider
//    Column(verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally) {
//
//        Text(text = percentage.toString())
//        Spacer(modifier = Modifier.height(14.dp))
//        Slider(value = sliderState.value,
//            onValueChange = {
//                sliderState.value = it
//                totalTipState.value = calculateTotalTip(totalBill = totalBillState.value.toDouble(),
//                    tipPercent = tipPercentage)
//                // Log.d("AMT", "TipSlider: $tipPercentage")
//
////                totalTipState.value = calculateTotalTip(tota
////                    tipPercent = tipPercentage).roundToInt().toString()
//            },
//            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
//            steps = 5,
//            valueRange = (0f..100f))
//
//    }
//
//}
//


















