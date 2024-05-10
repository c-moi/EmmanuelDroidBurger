package fr.isen.bert.emmanueldroidburger.command.formElement

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.bert.emmanueldroidburger.ui.theme.ColorPlaceholder



@Composable
fun FromString(variable: MutableState<String>, img: Int, placeholder: String = "Nom") {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = img),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer(
                    scaleX = 2f,
                    scaleY = 1.5f,
                    alpha = 0.4f
                )
        )
        BasicTextField(
            value = variable.value,
            onValueChange = { newValue -> variable.value = newValue },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            ),
            decorationBox = { innerTextField ->
                if (variable.value.isEmpty()) {
                    Text(placeholder, style = TextStyle(
                        color = ColorPlaceholder,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    )
                }
                innerTextField()
            }
        )
    }
}