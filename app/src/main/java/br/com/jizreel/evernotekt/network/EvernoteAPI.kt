package br.com.jizreel.evernotekt.network

import br.com.jizreel.evernotekt.model.Note
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *
 * Setembro, 24 2019
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */

interface EvernoteAPI {

    @GET("/")
    fun listNotes(): Observable<List<Note>>

    @GET("/{id}")
    fun getNote(@Path("id") id: Int): Observable<Note>

    @POST("/create")
    fun createNote(@Body note: Note): Observable<Note>

}