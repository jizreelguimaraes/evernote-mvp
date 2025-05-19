package br.com.jizreel.evernotekt.home.presentation

import br.com.jizreel.evernotekt.home.Home
import br.com.jizreel.evernotekt.model.Note
import br.com.jizreel.evernotekt.model.RemoteDataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class HomePresenter(
    private val view: Home.View,
    private val dataSource: RemoteDataSource,
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()
) : Home.Presenter {

    private val compositeDisposable = CompositeDisposable()

    private val notesObserver: DisposableObserver<List<Note>>
        get() = object : DisposableObserver<List<Note>>() {
            override fun onNext(notes: List<Note>) {
                if(notes.isNotEmpty()) {
                    view.displayNotes(notes)
                } else {
                    view.displayEmptyNotes()
                }
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view.displayError("Erro ao carregar notas")
            }

            override fun onComplete() {
                println("complete")
            }

        }

    private val notesObservable: Observable<List<Note>> get() = dataSource.listNotes()

    override fun getAllNotes() {
        val disposable = notesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(mainScheduler)
            .subscribeWith(notesObserver)

        compositeDisposable.add(disposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}