package nu.rolandsson.jakob.noterav5.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public class TrashViewModel extends ViewModel {
    private MutableLiveData<Category> mCategoryTrash;
    private MutableLiveData<Note> mNoteTrash;



    public void trash(Category category) {
        mCategoryTrash.setValue(category);
    }

    public void trash(Note note) {
        mNoteTrash.setValue(note);
    }


    public MutableLiveData<Category> getTrashCategory() {
        if(mCategoryTrash == null) {
            mCategoryTrash = new MutableLiveData<>();
        }
        return mCategoryTrash;
    }

    public MutableLiveData<Note> getTrashNote() {
        if(mNoteTrash == null) {
            mNoteTrash = new MutableLiveData<>();
        }
        return mNoteTrash;
    }


}
