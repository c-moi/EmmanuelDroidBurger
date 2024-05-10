package fr.isen.bert.emmanueldroidburger.model

data class Command(
    val id_shop: Int,
    val id_user: String,
    val msg: String
)

data class Msg(
    val firstname: String,
    val lastname: String,
    val address: String,
    val phone: String,
    val burger: String,
    val delivery_time: String
)