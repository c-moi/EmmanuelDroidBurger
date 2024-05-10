package fr.isen.bert.emmanueldroidburger.request.send

import android.util.Log
import com.google.gson.Gson
import fr.isen.bert.emmanueldroidburger.model.Command
import fr.isen.bert.emmanueldroidburger.model.Msg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun sendOrderToServer(
    firstname: String, lastname: String, address: String,
    phone: String, burger: String, delivery_time: String,
    callback: ApiResponseCallbackCommand
) {
    val data = Msg(firstname, lastname, address, phone, burger, delivery_time)
    val gson = Gson()
    val msgJson = gson.toJson(data)

    val commandRequest = Command(1, "354", msgJson)
    val orderRequestJson = gson.toJson(commandRequest)
    Log.d("API Call", "Complete JSON data to send: $orderRequestJson")

    val retrofit = Retrofit.Builder()
        .baseUrl("http://test.api.catering.bluecodegames.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ApiPost::class.java)
    val call = service.postOrder(commandRequest)
    call.enqueue(object : Callback<Command> {
        override fun onResponse(call: Call<Command>, response: Response<Command>) {
            if (response.isSuccessful) {
                Log.d("sendSuccess", "Commande envoyée avec succès")
                callback.onSuccess("Commande envoyée avec succès")
            } else {
                when (response.code()) {
                    400 -> Log.d("", "Requête incorrecte : ${response.errorBody()?.string()}")
                    401 -> Log.d("", "Authentification requise ou échouée")
                    500 -> Log.d("", "Erreur serveur interne")
                    else -> Log.d("", "Erreur lors de l'envoi de la commande: ${response.errorBody()?.string()}")
                }
                callback.onFailure("Erreur lors de l'envoi de la commande")
            }
        }
        override fun onFailure(call: Call<Command>, t: Throwable) {
            Log.d("sendFailure", "Échec de l'envoi de la commande: ${t.message}")
            callback.onFailure("Échec de l'envoi de la commande")
        }
    })
}