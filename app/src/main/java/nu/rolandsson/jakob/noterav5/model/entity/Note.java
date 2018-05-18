package nu.rolandsson.jakob.noterav5.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Martin on 2018-04-23.
 */

@Entity(tableName = "note_table"/*,
        foreignKeys = @ForeignKey(
                entity = Category.class, parentColumns = "category_id", childColumns = "category"
        )*/
)
public class Note implements Serializable, Comparable<Note> {


    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "text")
    private String mText;

    @ColumnInfo(name = "category")
    private int mCategory;

    public Note() {
        this("");
    }

    public Note(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getText() {
        return mText;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int categoryId) {
        mCategory = categoryId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    @Override
    public int compareTo(@NonNull Note other) {
        return this.getId() - other.getId();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        Note note = (Note) o;
        return note.getTitle().equals(getTitle());
    }
}
