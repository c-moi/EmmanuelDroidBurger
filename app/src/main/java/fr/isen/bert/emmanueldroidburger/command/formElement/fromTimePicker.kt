package fr.isen.bert.emmanueldroidburger.command.formElement

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import fr.isen.bert.emmanueldroidburger.R
import fr.isen.bert.emmanueldroidburger.ui.theme.ColorPlaceholder
import java.util.Calendar



@Composable
fun SimpleTimePickerTest(time: MutableState<String>, context: Context) {
    Box (
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bottom_bread),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer(
                    scaleX = 1.25f,
                    scaleY = 1.2f,
                    alpha = 0.4f
                )
        )
        Button(
            onClick = { showDateTimePickerDialog(time, context) },
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0x00FFFFFF),  // Couleur de fond du bouton
                contentColor = Color.Black  // Couleur du texte et de l'icône
            ),
            elevation = ButtonDefaults.elevation( // Ici, vous réglez les valeurs d'élevation
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            )
        ) {
            Text(
                text = if (time.value.isEmpty()) "Heure Livraison" else time.value,
                style = TextStyle(
                    color = ColorPlaceholder,  // Définir la couleur du texte
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

fun showDateTimePickerDialog(timeState: MutableState<String>, context: Context) {
    Log.d("here", "working")
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val min = calendar.get(Calendar.MINUTE)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val timePickerDialog = TimePickerDialog(
                context,
                { _, selectedHour, selectedMinute ->
                    timeState.value = String.format("%04d-%02d-%02d %02d:%02d", selectedYear, selectedMonth + 1, selectedDay, selectedHour, selectedMinute)
                }, hour, min, true)
                timePickerDialog.show()
        }, year, month, day)

    datePickerDialog.show()
}