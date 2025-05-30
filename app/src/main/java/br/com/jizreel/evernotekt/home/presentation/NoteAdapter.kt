package br.com.jizreel.evernotekt.home.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.evernotekt.R
import br.com.jizreel.evernotekt.model.Note
import kotlinx.android.synthetic.main.list_item_note.view.*

/**
 *
 * Setembro, 24 2019
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
class NoteAdapter(private val notes: List<Note>, val onClickListener: (Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteView =
        NoteView(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item_note,
                    parent,
                    false
                )
        )

    override fun onBindViewHolder(holder: NoteView, position: Int) =
        holder.bind(notes[position])

    override fun getItemCount(): Int = notes.size

    inner class NoteView constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) {
            with(itemView) {
                note_title.text = note.title
                note_desc.text = note.desc
                note_date.text = note.date

                setOnClickListener {
                    onClickListener.invoke(note)
                }
            }
        }

    }

}
