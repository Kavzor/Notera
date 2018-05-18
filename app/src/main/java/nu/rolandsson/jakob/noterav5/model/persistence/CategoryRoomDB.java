package nu.rolandsson.jakob.noterav5.model.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.Note;


/**
 * Created by Martin on 2018-04-23.
 */

@Database(entities = {Category.class, Note.class}, version = 1)
public abstract class CategoryRoomDB extends RoomDatabase {
    public abstract CategoriesDao categoriesDao();

    private static CategoryRoomDB instance;

    static CategoryRoomDB getDatabase(Context context) {
        synchronized (CategoryRoomDB.class) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        CategoryRoomDB.class, "category_database").
                        addCallback(sRoomDatabaseCallback). //for testing purpose
                        build();
            }
        }
        return instance;
    }

    private static Callback sRoomDatabaseCallback =
            new Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(instance).execute();
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CategoriesDao mDao;

        PopulateDbAsync(CategoryRoomDB db) {
            mDao = db.categoriesDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.nukeAllTables();
            Log.v("Test DB", "Adding mock data");
            Category category = new Category("Hello");
            long insertId = mDao.addCategory(category);
            Log.v("Test DB", "Id: " + insertId);
            category.setId((int) insertId);

            Note firstNote = new Note("First note");
            firstNote.setText("Some text");

            Note secondNote = new Note("Second note");
            secondNote.setText("sjdnajksdnajk jaksndjaksnd ajsndajksdnksa");
            category.addNote(firstNote);
            category.addNote(secondNote);

            mDao.insertCategoriesAndNotes(category, category.getNotes());

            Category secondCategory = new Category("Airplanes");
            long secondInsertId = mDao.addCategory(secondCategory);
            secondCategory.setId((int) secondInsertId);

            Note thirdNote = new Note("Engines");
            thirdNote.setText("Engines of an airplane are awesome and super cooooool!");

            Note fourthNote = new Note("Cars");
            fourthNote.setText("I don't like cars, I'm not particularly interested in cars because they are cars");

            secondCategory.addNote(thirdNote);
            secondCategory.addNote(fourthNote);

            mDao.insertCategoriesAndNotes(secondCategory, secondCategory.getNotes());
            return null;
        }
    }
}
