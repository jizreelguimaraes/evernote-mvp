package br.com.jizreel.evernotekt

import br.com.jizreel.evernotekt.add.Add
import br.com.jizreel.evernotekt.add.presentation.AddPresenter
import br.com.jizreel.evernotekt.home.presentation.HomePresenter
import br.com.jizreel.evernotekt.model.Note
import br.com.jizreel.evernotekt.model.RemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class AddPresenterTestes {

    @MockK
    private lateinit var mockView: Add.View
    @MockK
    private lateinit var mockDataSource: RemoteDataSource
    private lateinit var addPresenter: AddPresenter

    private val fakeAllNotes : List<Note> get() = arrayListOf(
        Note(1, "Nota A", "NotaA Desc", "01/10/2012", "Seja bem vindo a nota A"),
        Note(2, "Nota B", "NotaB Desc", "01/12/2012", "Seja bem vindo a nota B"),
        Note(3, "Nota C", "NotaC Desc", "01/10/2014", "Seja bem vindo a nota C")
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        addPresenter = AddPresenter(mockView, mockDataSource, Schedulers.trampoline())
    }

    @Test
    fun `test must not add note with empty body`() {

        every { mockView.displayError(any()) } just Runs
        addPresenter.createNote("", "")

        verify(exactly = 1) { mockView.displayError("Título e corpo da nota não podem ser vazios") }
    }

    @Test
    fun `test must add note`() {

        val note = Note(title = "Nota A", body = "NotaA Desc")

        every { mockDataSource.createNote(note) } returns Observable.just(note)

        addPresenter.createNote("Nota A", "NotaA Desc")

        verify(exactly = 1) { mockDataSource.createNote(note) }
    }
}