import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundIconButton(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .clip(
                shape = CircleShape
            )
            .size(30.dp)
            .padding(1.dp)
    ) {
        Text(
            text = text,
            color = Color.Red,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable { onClick() }
        )
    }
}
@Composable
fun TopHeader(
    modifier: Modifier = Modifier,
    totalPerPerson: Double = 0.0
) {
    Surface(
        color = Color(0xFF5197DC),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(20.dp)
            .clip(
                shape = RoundedCornerShape(16.dp)
            ),

        ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Total Per Person",
                fontSize = 30.sp
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
fun Inputfield(
    valueState: MutableState<String>,
    Label: String,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onAction: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next
) {
    OutlinedTextField(
        valueState.value,
        onValueChange = { valueState.value = it },
        modifier = modifier
            .padding(20.dp),
        enabled = enabled,
        label = {
            Text(
                text = Label
            )
        },
        singleLine = singleLine,
        leadingIcon = {
            Icon(
                Icons.Default.CurrencyRupee,
                contentDescription = "Ruppees icon"
            )
        },
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        )
    )
}
