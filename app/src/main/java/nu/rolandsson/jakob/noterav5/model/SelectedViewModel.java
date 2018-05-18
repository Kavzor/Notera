package nu.rolandsson.jakob.noterav5.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public class SelectedViewModel extends ViewModel {

    private static final String TAG = SelectedViewModel.class.getName();

    private MutableLiveData<Category> mSelectedCategory;
    private MutableLiveData<Note> mSelectedNote;

    public void select(Category category) {
        mSelectedCategory.setValue(category);
    }

    public void select(Note note) {
        Log.d(TAG, "Selecting note: " + note.getTitle());
        mSelectedNote.setValue(note);
    }

    public MutableLiveData<Category> getSelectedCategory() {
        if(mSelectedCategory == null) {
            mSelectedCategory = new MutableLiveData<>();
        }
        return mSelectedCategory;
    }

    public MutableLiveData<Note> getSelectedNote() {
        if(mSelectedNote == null) {
            mSelectedNote = new MutableLiveData<>();
        }
        return mSelectedNote;
    }
}
