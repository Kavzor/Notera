package nu.rolandsson.jakob.noterav5.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Martin on 2018-04-23.
 */

@Entity(tableName = "category_table")
public class Category implements Comparable<Category>{

    @Ignore
    //@Relation(parentColumn = "category_id", entityColumn = "category")
    private List<Note> mNotes;

    @ColumnInfo(name = "name")
    @NonNull
    private String mName;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    private int mId;

    @ColumnInfo(name = "starred")
    private boolean isStarred;

    public Category(String name) {
        mName = name;
        mNotes = new ArrayList<>();
    }

    public void addNote(Note note) {
        mNotes.add(note);
    }

    public List<Note> getNotes() {
        return mNotes;
    }
    public void setNotes(List<Note> notes) {
        mNotes = notes;
    }

    public String getName() {
        return mName;
    }

    public int getId(){
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        Category category = (Category) o;
        return category.getName().equals(getName());
    }

    @Override
    public int compareTo(@NonNull Category other) {
        return this.getId() - other.getId();
    }
}
