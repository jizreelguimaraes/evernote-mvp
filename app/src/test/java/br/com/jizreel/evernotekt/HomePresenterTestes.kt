package br.com.jizreel.evernotekt

import br.com.jizreel.evernotekt.home.Home
import br.com.jizreel.evernotekt.home.presentation.HomePresenter
import br.com.jizreel.evernotekt.model.Note
import br.com.jizreel.evernotekt.model.RemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class HomePresenterTestes {

    @MockK
    private lateinit var mockView: Home.View
    @MockK
    private lateinit var mockDataSource: RemoteDataSource
    private lateinit var homePresenter: HomePresenter

    private val fakeAllNotes : List<Note> get() = arrayListOf(
        Note(1, "Nota A", "NotaA Desc", "01/10/2012", "Seja bem vindo a nota A"),
        Note(2, "Nota B", "NotaB Desc", "01/12/2012", "Seja bem vindo a nota B"),
        Note(3, "Nota C", "NotaC Desc", "01/10/2014", "Seja bem vindo a nota C")
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        homePresenter = HomePresenter(mockView, mockDataSource, Schedulers.trampoline())
    }

    @Test
    fun `test must get all notes`() {

        every { mockDataSource.listNotes() } returns Observable.just(fakeAllNotes)

        homePresenter.getAllNotes()

        verify(exactly = 0) { mockView.displayError(any()) }
        verify(exactly = 1) { mockDataSource.listNotes() }
        verify(exactly = 1) { mockView.displayNotes(fakeAllNotes) }
    }

    @Test
    fun `test must show empty notes`() {

        every { mockDataSource.listNotes() } returns Observable.just(ArrayList())

        homePresenter.getAllNotes()

        verify(exactly = 1) { mockView.displayEmptyNotes() }
    }
}