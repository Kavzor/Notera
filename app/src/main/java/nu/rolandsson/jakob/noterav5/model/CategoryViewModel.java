package nu.rolandsson.jakob.noterav5.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.CategoryAndNotes;
import nu.rolandsson.jakob.noterav5.model.entity.Note;
import nu.rolandsson.jakob.noterav5.model.persistence.CategoryRepository;


/**
 * Created by Martin on 2018-04-23.
 */

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository mRepository;
    private LiveData<List<CategoryAndNotes>> mCategories;
    private LiveData<Category> mSelectedCategory;
    private LiveData<Note> mSelectedNote;


    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CategoryRepository(application);
        mCategories = mRepository.getAll();
    }

    public LiveData<List<CategoryAndNotes>> getAll() {
        return mCategories;
    }


    public void set(Category category, CategoryRepository.After afterInsert) {
        mRepository.set(category, afterInsert);
    }

    public void set(Note note, CategoryRepository.After after) {
        mRepository.set(note, after);
    }

    public void remove(Note note) {
        mRepository.remove(note);
    }

    public void remove(Category category) {
        mRepository.remove(category);
    }

    public LiveData<List<CategoryAndNotes>> filter(String critera) {
        return mRepository.filterCategory("%" + critera + "%");
    }
}
