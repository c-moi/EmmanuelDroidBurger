package fr.isen.bert.emmanueldroidburger.request.get

import com.google.gson.Gson
import fr.isen.bert.emmanueldroidburger.model.Listing
import fr.isen.bert.emmanueldroidburger.model.Msg
import fr.isen.bert.emmanueldroidburger.model.Orders
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory


fun getOrdersFromServer(callback: ApiResponseCallbackOrderlist) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://test.api.catering.bluecodegames.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ApiGet::class.java)
    val call = service.getOrders(Listing(1, "354"))
    call.enqueue(object : Callback<Orders> {
        override fun onResponse(call: Call<Orders>, response: Response<Orders>) {
            if (response.isSuccessful) {
                val orders = response.body()?.data
                if (!orders.isNullOrEmpty()) {
                    val messages = orders.map { order ->
                        val gson = Gson()
                        val message = gson.fromJson(order.message, Msg::class.java)
                        message
                    }
                    callback.onSuccess(messages)
                } else {
                    callback.onFailure("La liste des commandes est vide")
                }
            } else {
                callback.onFailure("Erreur lors de la récupération des commandes: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<Orders>, t: Throwable) {
            callback.onFailure("Échec de la récupération des commandes: ${t.message}")
        }
    })
}