package br.com.jizreel.evernotekt.add

interface Add {

    interface Presenter {
        fun createNote(title: String, body: String)
        fun getNote(id: Int)
        fun stop()
    }

    interface View {
        fun displayError(message: String)
        fun displayNote(title: String, body: String)
        fun returnToHome()
    }
}