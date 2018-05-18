package nu.rolandsson.jakob.noterav5.fragment.abstraction;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import nu.rolandsson.jakob.noterav5.component.PageComponent;
import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.CategoryAndNotes;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public interface NoteraUpdate<E> {

    void trash(E element);
    void markSelected(E element);
    void save(E element);

    default void toggleStar(E element) { }
    default void filter(String critera) { }
}
