package fr.isen.bert.emmanueldroidburger.command.checks

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
fun validateInputs(nom: String, prenom: String, adresse: String,
                   num_tel: String, burger: String, heure: String): String? {

    val regexName = "^[a-zA-ZÀ-ÿ\\s'-]+$"
    val regexAddress = "^\\d{1,3}\\s[A-Za-zÀ-ÿ\\s'-]+$"
    val regexPhoneNumber = "^0\\d{9}$"

    if (nom.isEmpty()) {
        return "Le champ 'Nom' est requis."
    }
    if (!nom.matches(regexName.toRegex())) {
        return "Le nom doit contenir uniquement des lettres."
    }
    if (prenom.isEmpty()) {
        return "Le champ 'Prénom' est requis."
    }
    if (!prenom.matches(regexName.toRegex())) {
        return "Le prénom doit contenir uniquement des lettres."
    }
    if (adresse.isEmpty()) {
        return "Le champ 'Adresse' est requis."
    }
    if (!adresse.matches(regexAddress.toRegex())) {
        return "L'adresse doit commencer par 1 à 3 chiffres suivis du nom de la rue."
    }
    if (num_tel.isEmpty()) {
        return "Le champ 'Numéro de téléphone' est requis."
    }
    if (!num_tel.matches(regexPhoneNumber.toRegex())) {
        return "Le numéro de téléphone doit contenir exactement 10 chiffres et commencer par 0."
    }
    if (burger.isEmpty() || burger == "Choisir Burger") {
        return "Veuillez sélectionner un burger."
    }
    if (heure.isEmpty()) {
        return "L'heure de livraison ne peut pas être vide."
    }
    // Ajouter une validation pour s'assurer que l'heure de livraison est dans le futur ou à un moment raisonnable
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    dateFormat.isLenient = false
    try {
        val date = dateFormat.parse(heure) ?: throw IllegalArgumentException("Format de date invalide")
        if (date.before(Calendar.getInstance().time)) {
            return "L'heure de livraison doit être dans le futur."
        }
    } catch (e: Exception) {
        return "Format de l'heure de livraison invalide. Utilisez le format 'AAAA-MM-JJ HH:mm'."
    }

    return null
}