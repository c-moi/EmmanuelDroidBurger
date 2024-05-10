package fr.isen.bert.emmanueldroidburger.request.send

import fr.isen.bert.emmanueldroidburger.model.Command
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiPost {
    @POST("user/order")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun postOrder(
        @Body message: Command
    ): Call<Command>
}