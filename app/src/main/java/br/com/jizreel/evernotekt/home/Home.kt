package br.com.jizreel.evernotekt.home

import br.com.jizreel.evernotekt.model.Note

interface Home {

    interface Presenter {
        fun getAllNotes()
        fun stop()
    }

    interface View {
        fun displayNotes(notes: List<Note>)
        fun displayEmptyNotes()
        fun displayError(message: String)
    }
}