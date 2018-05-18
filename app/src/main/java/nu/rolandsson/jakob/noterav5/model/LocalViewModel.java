package nu.rolandsson.jakob.noterav5.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public class LocalViewModel extends ViewModel {

    private MutableLiveData<Boolean> mDisplayFavorites;

    public void setDisplayFavorites(boolean display) {
        mDisplayFavorites.setValue(display);
    }

    public LiveData<Boolean> getDisplayFavorites() {
        if(mDisplayFavorites == null) {
            mDisplayFavorites = new MutableLiveData<>();
        }
        return mDisplayFavorites;
    }
/*
    private MutableLiveData<Category>
    private MutableLiveData<Note> mSelectedNote;

    public void toggleStar(Category category) {
        mSelectedCategory.setValue(category);
    }

    public void toggleStar(Note note) {
        mSelectedNote.setValue(note);
    }

    public MutableLiveData<Category> getSelectedCategory() {
        if(mSelectedCategory == null) {
            mSelectedCategory = new MutableLiveData<>();
        }
        return mSelectedCategory;
    }*/
}
