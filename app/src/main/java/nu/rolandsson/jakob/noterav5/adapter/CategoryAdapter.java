package nu.rolandsson.jakob.noterav5.adapter;

import android.util.Log;
import android.widget.Filter;
import android.widget.TextView;

import java.io.FilterReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nu.rolandsson.jakob.noterav5.R;
import nu.rolandsson.jakob.noterav5.fragment.abstraction.NoteraUpdate;
import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public class CategoryAdapter extends NoteraListAdapter<Note> {
    public CategoryAdapter(NoteraUpdate<Note> noteraUpdate) {
        super(noteraUpdate);
    }

    private static final String TAG = CategoryAdapter.class.getName();


    @Override
    public int getAdapterView() {
        return R.layout.view_note_item;
    }

    @Override
    protected int getMarkView() {
        return R.id.view_note_item_title;
    }

    @Override
    protected int getTrashView() {
        return R.id.view_note_item_remove;
    }


    @Override
    public void bindView(ViewHolder holder, Note note, int adapterPosition) {
        super.bindView(holder, note, adapterPosition);
        TextView noteTitle = holder.get(getMarkView());
        noteTitle.setText(note.getTitle());
    }
}
