package nu.rolandsson.jakob.noterav5.model.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by Martin on 2018-04-24.
 */

public class CategoryAndNotes {
    @Embedded
    public Category category;

    @Relation(parentColumn = "category_id", entityColumn = "category")
    public List<Note> notes;
}
