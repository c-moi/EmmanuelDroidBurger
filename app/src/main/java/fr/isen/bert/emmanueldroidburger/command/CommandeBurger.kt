package fr.isen.bert.emmanueldroidburger.command

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.bert.emmanueldroidburger.R
import fr.isen.bert.emmanueldroidburger.command.checks.validateInputs
import fr.isen.bert.emmanueldroidburger.command.formElement.FromString
import fr.isen.bert.emmanueldroidburger.command.formElement.SimpleTimePickerTest
import fr.isen.bert.emmanueldroidburger.command.formElement.FromDropDown
import fr.isen.bert.emmanueldroidburger.request.send.sendOrderToServer
import fr.isen.bert.emmanueldroidburger.ui.theme.Boxcolor
import fr.isen.bert.emmanueldroidburger.ui.theme.shadowBoxcolor
import fr.isen.bert.emmanueldroidburger.confirmation.ConfirmActivity
import fr.isen.bert.emmanueldroidburger.request.send.ApiResponseCallbackCommand


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommandeBurger() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val topPadding = screenHeight * -0.065f

    Box {
        Column(
            modifier = Modifier
                .background(shadowBoxcolor)
                .fillMaxWidth()
        ) {
            CommandHeader()
            CommandForm()
            CommandFooter()
        }
        Image (
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = topPadding)
                .scale(0.25f)
        )
    }
}

@Composable
fun CommandHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.15f)
            .background(Boxcolor)
            .padding(30.dp)
    ) {
        Text(
            text = "DroidBurger",
            color = Color.Black,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun loadBurgerList(context: Context): List<String> {
    val ressources = context.resources
    val burgerArray = ressources.getStringArray(R.array.burger_list)
    return burgerArray.toList()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommandForm() {
    val context = LocalContext.current

    val firstname = remember { mutableStateOf("") }
    val lastname = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val burger = remember { mutableStateOf("Choisir Burger") }
    val delivery_time = remember { mutableStateOf("") }

    val testString = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
            .padding(top = 80.dp)
            .padding(horizontal = 75.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // nom
        FromString(firstname, R.drawable.top_bread, "Nom")
        // prenom
        FromString(lastname, R.drawable.salad, "Prenom")
        // adresse
        FromString(address, R.drawable.tomato, "Adresse")
        // num_tel
        FromString(phone, R.drawable.cheese, "Numero de Téléphone")
        // burger
        FromDropDown(burger, context)
        // heure de livraison
        SimpleTimePickerTest(delivery_time, context)

        Button(
            onClick = {
                testString.value = validateInputs(firstname.value, lastname.value, address.value,
                    phone.value, burger.value, delivery_time.value)
                if (testString.value == null) {
                    sendOrderToServer(firstname.value, lastname.value, address.value,
                        phone.value, burger.value, delivery_time.value, object : ApiResponseCallbackCommand {
                            override fun onSuccess(result: String) {
                                val intent = Intent(context, ConfirmActivity::class.java)
                                context.startActivity(intent)
                            }

                            override fun onFailure(errorMessage: String) {
                                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                            }
                        })
                }
            },
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF7F0A04),  // Couleur de fond du bouton
                contentColor = Color.White  // Couleur du texte et de l'icône
            )
        ) {
            Text("Commander")
        }

        testString.value?.let {
            AlertDialog(
                onDismissRequest = { testString.value = null },
                title = { Text("Erreur de validation") },
                text = { Text(it) },  // Affiche le message d'erreur spécifique
                confirmButton = {
                    Button(onClick = { testString.value = null }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
fun CommandFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .background(Boxcolor)
    )
}