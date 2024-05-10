package fr.isen.bert.emmanueldroidburger.request.send

interface ApiResponseCallbackCommand {
    fun onSuccess(result: String)
    fun onFailure(errorMessage: String)
}