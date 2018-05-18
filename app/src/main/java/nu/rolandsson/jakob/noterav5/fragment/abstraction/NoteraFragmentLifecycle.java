package nu.rolandsson.jakob.noterav5.fragment.abstraction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import nu.rolandsson.jakob.noterav5.model.CategoryViewModel;
import nu.rolandsson.jakob.noterav5.model.LocalViewModel;
import nu.rolandsson.jakob.noterav5.model.SelectedViewModel;
import nu.rolandsson.jakob.noterav5.model.TrashViewModel;
import nu.rolandsson.jakob.noterav5.model.ViewModel;
import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.CategoryAndNotes;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public abstract class NoteraFragmentLifecycle extends Fragment implements OnNoteraUpdate {

    private static final String TAG = NoteraFragmentLifecycle.class.getName();

    protected CategoryLoader mCategoryLoader = new CategoryLoader();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCategoryLoader.loadCache();
    }


    protected void observeLifeCycle(ViewModel viewModel) {
        SelectedViewModel selectedViewModel = viewModel.getSelectedViewModel();
        TrashViewModel trashViewModel = viewModel.getTrashViewModel();
        CategoryViewModel categoryViewModel = viewModel.getCategoryViewModel();
        LocalViewModel localViewModel = viewModel.getLocalViewModel();

        categoryViewModel.getAll().observe(getActivity(), mCategoryLoader::loadCategories);
        localViewModel.getDisplayFavorites().observe(getActivity(), this::onDisplayFavorites);

        selectedViewModel.getSelectedNote().observe(getActivity(), note -> {
            Log.d(TAG, "Note selected: " + note.getTitle());
            mCategoryLoader.setSelected(note);
            this.onRefreshContent(note);
        });
        selectedViewModel.getSelectedCategory().observe(getActivity(), category -> {
            mCategoryLoader.setSelected(category);
            this.onRefreshContent(category);
        });

        trashViewModel.getTrashCategory().observe(getActivity(), this::onTrash);
        trashViewModel.getTrashNote().observe(getActivity(), this::onTrash);
    }

    protected class CategoryLoader {
        private Category mSelectedCategory;
        private Note mSelectedNote;
        private List<Category> mCategories;

        CategoryLoader() {
            mCategories = new LinkedList<>();
        }

        protected void setSelected(Category category) {
            mSelectedCategory = category;
        }

        protected void setSelected(Note note) {
            mSelectedNote = note;
        }

        protected void loadFilteredCategories(List<CategoryAndNotes> data) {
            loadDataAsFiltered(true, data);
        }

        protected void loadCategories(List<CategoryAndNotes> data) {
            loadDataAsFiltered(false, data);
        }

        private void loadDataAsFiltered(boolean filtered, List<CategoryAndNotes> data) {
            Log.d(TAG, "Loading catagories, size: " + data.size());
            List<Category> categories = new LinkedList<>();
            List<Category> starredCategories = new LinkedList<>();
            for(CategoryAndNotes categoryAndNotes : data) {
                Category category = loadCategory(categoryAndNotes);
                categories.add(category);
                if(category.isStarred()) {
                    starredCategories.add(category);
                }
                Log.d(TAG, "Added category: " + categoryAndNotes.category.getName());
            }
            if(filtered) {
                onFilteredContentUpdate(categories);
            }
            else {
                onAllContentUpdate(categories);
            }
            onStarContentUpdate(starredCategories);
            cacheResult(categories);
        }

        private Category loadCategory(CategoryAndNotes categoryAndNotes) {
            Category category = categoryAndNotes.category;
            category.setNotes(categoryAndNotes.notes);
            if(mSelectedCategory != null && category.equals(mSelectedCategory)) {
                onRefreshContent(category);
                for(Note note : category.getNotes()) {
                    if(mSelectedNote != null && note.equals(mSelectedNote)) {
                        onRefreshContent(note);
                    }
                }
            }
            return category;
        }

        private void loadCache() {
            if(mCategories.size() > 0) {
                onFilteredContentUpdate(mCategories);
            }
        }

        private void cacheResult(List<Category> categories) {
            mCategories.clear();
            mCategories.addAll(categories);
        }
    }
}
