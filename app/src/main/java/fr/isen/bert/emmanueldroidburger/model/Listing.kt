package fr.isen.bert.emmanueldroidburger.model

data class Listing (
    val id_shop: Int,
    val id_user: String
)

data class Orders (
    val data: List<Order>
)

data class Order (
    val id_sender: String,
    val id_receiver: String,
    val sender: String,
    val receiver: String,
    val code: String,
    val type_msg: String,
    val message: String,
    val create_date: String
)