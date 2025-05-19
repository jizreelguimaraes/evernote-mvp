package br.com.jizreel.evernotekt.add.presentation

import br.com.jizreel.evernotekt.add.Add
import br.com.jizreel.evernotekt.model.Note
import br.com.jizreel.evernotekt.model.RemoteDataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class AddPresenter(
    private val view: Add.View,
    private val dataSource: RemoteDataSource,
    private val schedulers: Scheduler = AndroidSchedulers.mainThread()
) : Add.Presenter {

    private val compositeDisposable = CompositeDisposable()

    private val createNoteObserver: DisposableObserver<Note>
        get() = object : DisposableObserver<Note>() {
            override fun onNext(t: Note) {
                view.returnToHome()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view.displayError("Erro ao salvar nota")
            }

            override fun onComplete() {
                println("complete")
            }

        }

    private val getNoteObserver: DisposableObserver<Note>
        get() = object : DisposableObserver<Note>() {
            override fun onNext(note: Note) {

                view.displayNote(note.title ?: "", note.body ?: "")
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view.displayError("Erro ao carregar notas")
            }

            override fun onComplete() {
                println("complete")
            }
        }

    override fun createNote(title: String, body: String) {

        if(title.isEmpty() || body.isEmpty()) {
            view.displayError("Título e corpo da nota não podem ser vazios")
            return
        }

        val note = Note(title = title, body = body)

        val disposable = dataSource.createNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(createNoteObserver)

        compositeDisposable.add(disposable)
    }

    override fun getNote(id: Int) {

        val disposable = dataSource.getNote(id)
            .subscribeOn(Schedulers.io())
            .observeOn(schedulers)
            .subscribeWith(getNoteObserver)

        compositeDisposable.add(disposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}