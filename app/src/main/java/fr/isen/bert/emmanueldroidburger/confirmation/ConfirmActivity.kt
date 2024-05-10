package fr.isen.bert.emmanueldroidburger.confirmation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.isen.bert.emmanueldroidburger.R
import fr.isen.bert.emmanueldroidburger.command.CommandHeader
import fr.isen.bert.emmanueldroidburger.model.Msg
import fr.isen.bert.emmanueldroidburger.request.get.ApiResponseCallbackOrderlist
import fr.isen.bert.emmanueldroidburger.request.get.getOrdersFromServer
import fr.isen.bert.emmanueldroidburger.ui.theme.EmmanuelDroidBurgerTheme
import fr.isen.bert.emmanueldroidburger.ui.theme.shadowBoxcolor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ConfirmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmmanuelDroidBurgerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ResultCommand()
                }
            }
        }
    }
}


@SuppressLint("InflateParams")
@Composable
fun ResultCommand() {
    val context = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val topPadding = screenHeight * -0.065f

    Box {
        Column(
            modifier = Modifier
                .background(shadowBoxcolor)
                .fillMaxWidth()
        ) {
            val greenToast = Toast(context)
            val greenLayout = LayoutInflater.from(context).inflate(R.layout.custom_toast_green, null)
            greenToast.view = greenLayout
            greenToast.duration = Toast.LENGTH_LONG
            greenToast.show()

            
            CommandHeader()
            CommandHistory()
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
fun CommandHistory() {
    var ongoingOrders by remember { mutableStateOf(emptyList<Msg>()) }
    var expiredOrders by remember { mutableStateOf(emptyList<Msg>()) }

    // Appel de la fonction pour récupérer les commandes depuis le serveur
    getOrdersFromServer(object : ApiResponseCallbackOrderlist {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onSuccess(orders: List<Msg>) {
            // Répartition des commandes en cours et expirées
            val (newOngoingOrders, newExpiredOrders) = splitOrdersByDeliveryDate(orders)
            ongoingOrders = newOngoingOrders
            expiredOrders = newExpiredOrders // Mise à jour de la variable expiredOrders
        }
        override fun onFailure(errorMessage: String) {
            Log.e("Order retrieval error", errorMessage)
        }
    })

    // Utilisation de la variable expiredOrders dans le LazyColumn
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 75.dp)
    ) {

        if (ongoingOrders.isNotEmpty()) {
            items(ongoingOrders.size) { index ->
                CommandItem(order = ongoingOrders[index], isExpired = false)
            }
        } else {
            item {
                Text(
                    text = "Il n'y a pas de commande en cours",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        item {
            Divider(
                color = Color.Black, // Couleur de la ligne
                thickness = 2.dp, // Épaisseur de la ligne
                modifier = Modifier.padding(vertical = 32.dp) // Espace autour de la ligne
            )
        }
        if (expiredOrders.isNotEmpty()) {
            items(expiredOrders.size) { index ->
                CommandItem(order = expiredOrders[index], isExpired = true)
            }
        } else {
            item {
                Text(
                    text = "Il n'y pas pas de commande expirée",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Composable
fun CommandItem(order: Msg, isExpired: Boolean) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(if (isExpired) Color(0xFFFFB9B5) else Color(0xFFdeffdb))
            .border(3.dp, Color.White)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = if (isExpired) "Commande Terminée" else "Commande en cours",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Première colonne : prénom, nom et adresse
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // prénom
                    Text(
                        text = "Prénom :",
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                    )
                    Text(
                        text = order.firstname,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.End)
                    )
                    // nom
                    Text(
                        text = "Nom :",
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                    )
                    Text(
                        text = order.lastname,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.End)
                    )
                    // adresse
                    Text(
                        text = "Adresse :",
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                    )
                    Text(
                        text = order.address,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.End)
                    )
                }
                Spacer(modifier = Modifier.width(45.dp))
                // Deuxième colonne : burger choisi et heure de livraison
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 20.dp)
                ) {
                    // burger
                    Text(
                        text = "Burger :",
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                    )
                    Text(
                        text = order.burger,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(bottom = 25.dp)
                            .align(Alignment.End)
                    )
                    // heure de livraison
                    Text(
                        text = "Heure de livraison :",
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                    )
                    Text(
                        text = order.delivery_time,
                        style = TextStyle(color = if (isExpired) Color.Red else Color.Green),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.End)
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun splitOrdersByDeliveryDate(orders: List<Msg>): Pair<List<Msg>, List<Msg>> {
    val currentOrders = mutableListOf<Msg>()
    val expiredOrders = mutableListOf<Msg>()

    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    for (order in orders) {
        try {
            val deliveryDate = dateFormat.parse(order.delivery_time)
            val currentDate = Date()

            println("Date de livraison : ${deliveryDate?.let { dateFormat.format(it) }}")
            println("Date actuelle : ${dateFormat.format(currentDate)}")

            if (deliveryDate != null && deliveryDate.after(currentDate)) {
                currentOrders.add(order)
            } else {
                expiredOrders.add(order)
            }
        } catch (e: Exception) {
            println("Erreur de parsing de date pour le message : ${order.delivery_time}")
            e.printStackTrace()
        }
    }

    return Pair(currentOrders, expiredOrders)
}