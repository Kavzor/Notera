package nu.rolandsson.jakob.noterav5.fragment.abstraction;

import java.util.List;

import nu.rolandsson.jakob.noterav5.component.PageComponent;
import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public interface OnNoteraUpdate {

    default void onAllContentUpdate(List<Category> categoryList) { }

    default void onRefreshContent(Category category) { }

    default void onRefreshContent(Note note) { }

    default void onTrash(Category category) { }

    default void onTrash(Note note) { }

    default void onFilteredContentUpdate(List<Category> filteredCategories) {}

    default void onStarContentUpdate(List<Category> categoryList) { }

    default void onDisplayFavorites(boolean displayFavorites) { }
}
