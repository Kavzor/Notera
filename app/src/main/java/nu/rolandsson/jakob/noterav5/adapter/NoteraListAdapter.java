package nu.rolandsson.jakob.noterav5.adapter;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nu.rolandsson.jakob.noterav5.fragment.abstraction.NoteraUpdate;
import nu.rolandsson.jakob.noterav5.fragment.abstraction.OnNoteraUpdate;
import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public abstract class NoteraListAdapter<E extends Comparable<E>> extends AbstractRecycleAdapter<E> {

    private static final String TAG = NoteraListAdapter.class.getName();

    private static final int NO_VIEW = -1;

    private NoteraUpdate<E> mNoteraUpdate;

    NoteraListAdapter(NoteraUpdate<E> noteraUpdate) {
        mNoteraUpdate = noteraUpdate;
    }

    protected int getMarkView() {
        return NO_VIEW;
    }

    protected int getFavView() {
        return NO_VIEW;
    }

    protected int getTrashView() {
        return NO_VIEW;
    }

    @Override
    public void setItems(List<E> items) {
        Collections.sort(items);
        super.setItems(items);
    }

    @Override
    public void bindView(ViewHolder holder, E item, int adapterPosition) {
        setupView(holder, getMarkView(), v -> mNoteraUpdate.markSelected(item));
        setupView(holder, getFavView(), v -> mNoteraUpdate.toggleStar(item));
        setupView(holder, getTrashView(), v -> mNoteraUpdate.trash(item));
    }

    private void setupView(ViewHolder holder, int viewRef, View.OnClickListener clickListener) {
        if(viewRef != NO_VIEW) {
            holder.get(viewRef).setOnClickListener(clickListener);
        }
    }
}
