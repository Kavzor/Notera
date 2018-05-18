package nu.rolandsson.jakob.noterav5.model.persistence;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.CategoryAndNotes;
import nu.rolandsson.jakob.noterav5.model.entity.Note;


/**
 * Created by Martin on 2018-04-23.
 */

public class CategoryRepository {
    public static class AfterResult {
        public int insertId;
        public AfterStatus status;
    }

    public enum AfterStatus {
        SUCCESS, FAILURE
    }
    public interface After {
        void after(AfterResult result);
    }

    private CategoriesDao mCategoriesDao;
    private LiveData<List<CategoryAndNotes>> mCategories;

    public CategoryRepository(Application application) {
        CategoryRoomDB db = CategoryRoomDB.getDatabase(application);
        mCategoriesDao = db.categoriesDao();
        mCategories = mCategoriesDao.loadCategoriesAndNotes();
    }

    public LiveData<List<CategoryAndNotes>> getAll() {
        return mCategories;
    }

    public void set(Category categories, After afterInsert) {
        new InsertAsync(mCategoriesDao, afterInsert).execute(categories);
    }

    public void set(Note note, After afterUpdate) {
        new UpdateAsync(mCategoriesDao, afterUpdate).execute(note);
    }

    public void remove(Note note) {
        new DeleteNoteAsync(mCategoriesDao, null).execute(note);
    }

    public void remove(Category category) {
        new DeleteCategoryAsync(mCategoriesDao, null).execute(category);
    }

    public LiveData<List<CategoryAndNotes>> filterCategory(String critera) {
        return mCategoriesDao.filterCategory(critera);
    }

    private static class DeleteCategoryAsync extends CategoryAsync<Category> {

        public DeleteCategoryAsync(CategoriesDao categoriesDao, After after) {
            super(categoriesDao, after);
        }

        @Override
        protected Void doInBackground(Category... categories) {
            mCategoriesDao.deleteCategory(categories[0]);
            return null;
        }
    }
    private static class DeleteNoteAsync extends CategoryAsync<Note> {

        public DeleteNoteAsync(CategoriesDao categoriesDao, After after) {
            super(categoriesDao, after);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mCategoriesDao.deleteNote(notes[0]);
            return null;
        }
    }

    private static class UpdateAsync extends CategoryAsync<Note> {

        UpdateAsync(CategoriesDao categoriesDao, After after) {
            super(categoriesDao, after);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            int rowsUpdated = mCategoriesDao.setNote(notes[0]);
            if(rowsUpdated == 0) {
                mCategoriesDao.addNote(notes[0]);
            }
            afterResult.status = AfterStatus.SUCCESS;
            mAfterCallback.after(afterResult);
            return null;
        }
    }
    private static class InsertAsync extends CategoryAsync<Category> {

        InsertAsync(CategoriesDao categoriesDao, After after) {
            super(categoriesDao, after);
        }

        @Override
        protected Void doInBackground(Category... categories) {
            int rowsUpdated = mCategoriesDao.setCategory(categories[0]);
            if(rowsUpdated == 0) {
                afterResult.insertId =  (int) mCategoriesDao.addCategory(categories[0]);
            }
            afterResult.status = AfterStatus.SUCCESS;
            mAfterCallback.after(afterResult);
            return null;
        }
    }

    private static abstract class CategoryAsync<P> extends AsyncTask<P, Void, Void> {
        CategoriesDao mCategoriesDao;
        After mAfterCallback;
        AfterResult afterResult;

        public CategoryAsync(CategoriesDao categoriesDao, After after) {
            mCategoriesDao = categoriesDao;
            mAfterCallback = after;
            afterResult = new AfterResult();
        }
    }
}
