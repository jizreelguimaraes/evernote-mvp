package br.com.jizreel.evernotekt.model

import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

/**
 *
 * Setembro, 24 2019
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
data class Note(
    var id: Int = 0,
    var title: String? = null,
    var desc: String? = null,
    var date: String? = null,
    var body: String? = null
): Serializable {

    val createdDate : String get() {
        val locale = Locale("pt", "BR")
        return try {
            val  date = SimpleDateFormat("dd/MM/yyyy", locale).parse(date ?: "")
            SimpleDateFormat("MM yyyy", locale).format(date).capitalize()
        } catch (e: ParseException) {
            ""
        }
    }
}