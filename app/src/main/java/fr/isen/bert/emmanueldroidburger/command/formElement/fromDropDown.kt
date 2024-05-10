package fr.isen.bert.emmanueldroidburger.command.formElement

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.bert.emmanueldroidburger.R
import fr.isen.bert.emmanueldroidburger.command.loadBurgerList
import fr.isen.bert.emmanueldroidburger.ui.theme.ColorPlaceholder



@Composable
fun FromDropDown(burger: MutableState<String>, context: Context) {
    val burgerOptions = loadBurgerList(context = context)
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.meat),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer(
                    scaleX = 1f,
                    scaleY = 1.2f,
                    alpha = 0.4f
                )
        )
        TextField(
            value = burger.value,
            onValueChange = {},
            singleLine = true,
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            textStyle = TextStyle(
                color = ColorPlaceholder,
                fontSize = 16.sp,
                lineHeight = 5.sp,
                textAlign = TextAlign.Center
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dérouler",
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .widthIn(max = 260.dp)
                .heightIn(max = 150.dp)
                .padding(vertical = 5.dp, horizontal = 5.dp)
                .background(Color(0xFFedcfa8))
        ) {
            burgerOptions.forEach { label ->
                DropdownMenuItem(onClick = {
                    burger.value = label
                    expanded = false
                },
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }
    }
}