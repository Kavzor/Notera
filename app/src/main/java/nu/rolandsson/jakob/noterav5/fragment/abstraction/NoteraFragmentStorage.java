package nu.rolandsson.jakob.noterav5.fragment.abstraction;

import android.content.Context;
import android.util.Log;

import nu.rolandsson.jakob.noterav5.model.CategoryViewModel;
import nu.rolandsson.jakob.noterav5.model.SelectedViewModel;
import nu.rolandsson.jakob.noterav5.model.TrashViewModel;
import nu.rolandsson.jakob.noterav5.model.ViewModel;
import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public abstract class NoteraFragmentStorage extends NoteraFragmentLifecycle {

    private static final String TAG = NoteraFragmentStorage.class.getName();

    private TrashViewModel mTrashViewModel;
    private SelectedViewModel mSelectedViewModel;
    private CategoryViewModel mCategoryViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            ViewModel viewModel = (ViewModel) context;
            mTrashViewModel = viewModel.getTrashViewModel();
            mSelectedViewModel = viewModel.getSelectedViewModel();
            mCategoryViewModel = viewModel.getCategoryViewModel();
            observeLifeCycle(viewModel);
        }
        catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ViewModel");
        }
    }

    public NoteraUpdate<Note> getNoteHandler() {
        return new NoteraUpdate<Note>() {
            @Override
            public void trash(Note note) {
                mCategoryViewModel.remove(note);
                mTrashViewModel.trash(note);
            }

            @Override
            public void markSelected(Note note) {
                Log.d(TAG, "Marking note: " + note.getTitle());
                mSelectedViewModel.select(note);
            }

            @Override
            public void save(Note note) {
                mCategoryViewModel.set(note, result -> {
                    Log.d(TAG, "Saving note");
                });
            }
        };
    }

    public NoteraUpdate<Category> getCategoryHandler() {
        return new NoteraUpdate<Category>() {
            @Override
            public void trash(Category category) {
                mCategoryViewModel.remove(category);
                mTrashViewModel.trash(category);
            }

            @Override
            public void markSelected(Category category) {
                mSelectedViewModel.select(category);
            }

            @Override
            public void save(Category category) {
                mCategoryViewModel.set(category, result -> {
                    Log.d(TAG, "Saving category");
                });
            }

            @Override
            public void toggleStar(Category category) {
                category.setStarred(!category.isStarred());
                mCategoryViewModel.set(category, result -> {
                    Log.d(TAG, "Category: " + category.getName() + " toggled star");
                });
            }

            @Override
            public void filter(String criteria) {
                mCategoryViewModel.filter(criteria).observe(getActivity(), mCategoryLoader::loadFilteredCategories);
            }
        };
    }
}
