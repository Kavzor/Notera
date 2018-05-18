package nu.rolandsson.jakob.noterav5.model.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.CategoryAndNotes;
import nu.rolandsson.jakob.noterav5.model.entity.Note;


/**
 * Created by Martin on 2018-04-23.
 */

@Dao
public abstract class CategoriesDao {


    @Query("SELECT * FROM category_table")
    public abstract LiveData<List<CategoryAndNotes>> loadCategoriesAndNotes();

    public void insertCategoriesAndNotes(Category category, List<Note> notesList) {
        Note[] notes = new Note[notesList.size()];
        for (int i = 0; i < notesList.size(); i++) {
            notes[i] = notesList.get(i);
            notes[i].setCategory(category.getId());
        }
        setCategory(category);
        addNotes(notes);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addNotes(Note...notes);

    @Update
    public abstract int setCategory(Category category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long addCategory(Category category);

    @Update
    public abstract int setNote(Note note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addNote(Note note);

    @Transaction
    public void nukeAllTables() {
        nukeCategoryTable();
        nukeNotesTable();
    }
    @Query("DELETE FROM category_table")
    public abstract void nukeCategoryTable();

    @Query("DELETE FROM note_table")
    public abstract void nukeNotesTable();


    // Currently testing
    @Delete
    public abstract void deleteCategory(Category category);

    @Delete
    public abstract void deleteNote(Note note);

    @Query("SELECT * FROM category_table WHERE name LIKE :critera")
    public abstract LiveData<List<CategoryAndNotes>> filterCategory(String critera);
}
