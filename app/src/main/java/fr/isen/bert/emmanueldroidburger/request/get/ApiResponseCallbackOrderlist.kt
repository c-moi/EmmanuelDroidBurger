package fr.isen.bert.emmanueldroidburger.request.get

import fr.isen.bert.emmanueldroidburger.model.Msg


interface ApiResponseCallbackOrderlist {
        fun onSuccess(orders: List<Msg>)
        fun onFailure(errorMessage: String)
}