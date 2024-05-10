package fr.isen.bert.emmanueldroidburger.request.get

import fr.isen.bert.emmanueldroidburger.model.Listing
import fr.isen.bert.emmanueldroidburger.model.Orders
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiGet {
    @POST("listorders")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getOrders(
        @Body message: Listing
    ): Call<Orders>
}