package nu.rolandsson.jakob.noterav5.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;

import java.util.List;

import nu.rolandsson.jakob.noterav5.R;
import nu.rolandsson.jakob.noterav5.adapter.CategoryAdapter;
import nu.rolandsson.jakob.noterav5.adapter.OverviewSlideAdapter;
import nu.rolandsson.jakob.noterav5.fragment.abstraction.PageFragment;
import nu.rolandsson.jakob.noterav5.fragment.callback.DismissCallback;
import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public class CategoryFragment extends PageFragment {

    private static final String TAG = CategoryFragment.class.getName();

    private RecyclerView mSlideOverview;
    private RecyclerView mNotesList;

    protected DismissFragment<Note> mDismissFragment;
    protected CategoryAdapter mCategoryAdapter;
    protected OverviewSlideAdapter mOverviewAdapter;


    @Override
    protected int getLayout() {
        return R.layout.fragment_category;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SnapHelper snapHelper = new PagerSnapHelper();

        mCategoryAdapter = new CategoryAdapter(getNoteHandler());
        mOverviewAdapter = new OverviewSlideAdapter(getCategoryHandler());

        View newItemView = view.findViewById(R.id.new_item_include);
        mNotesList = view.findViewById(R.id.frag_category_list);
        mSlideOverview = view.findViewById(R.id.frag_category_slide);

        mNotesList.setAdapter(mCategoryAdapter);
        mNotesList.setLayoutManager(getVerticalListLayoutManager());

        mSlideOverview.setAdapter(mOverviewAdapter);
        mSlideOverview.setLayoutManager(getHorizontalListLayoutManager());

        snapHelper.attachToRecyclerView(mSlideOverview);

        newItemView.setOnClickListener(v -> {
            Note note = new Note();
            note.setCategory(getActiveOverviewCategory().getId());
            getNoteHandler().save(note);
            getNoteHandler().markSelected(note);
        });

        mSlideOverview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                onRefreshContent(getActiveOverviewCategory());
            }
            }
        });
    }

    private LinearLayoutManager getOverviewLayoutManager() {
        return (LinearLayoutManager) mSlideOverview.getLayoutManager();
    }
    private Category getActiveOverviewCategory() {
        return getCategoryInLayout(getOverviewLayoutManager());
    }

    private Category getCategoryInLayout(LinearLayoutManager layoutManager) {
        return mOverviewAdapter.getItem(layoutManager.findFirstVisibleItemPosition());
    }

    private DismissCallback<Note> getDismissCallback() {
        return new DismissCallback<Note>() {
            @Override
            public void restore(Note note) {
                Log.d(TAG, "Restoring: " + note.getTitle());
                getNoteHandler().save(note);
                mCategoryAdapter.addItem(note);
                closeDismissFragment();
            }

            @Override
            public void remove(Note note) {
                closeDismissFragment();
            }
        };
    }

    protected void closeDismissFragment() {
        if(mDismissFragment != null) {
            removeFragment(mDismissFragment);
        }
    }

    @Override
    public void onAllContentUpdate(List<Category> categoryList) {
        mOverviewAdapter.setItems(categoryList);
    }

    @Override
    public void onRefreshContent(Category category) {
        mCategoryAdapter.setItems(category.getNotes());
        mSlideOverview.scrollToPosition(mOverviewAdapter.getItemId(category));
    }

    @Override
    public void onTrash(Note note) {
        Log.d(TAG, "Note: " + note.getTitle());
        mCategoryAdapter.removeItem(note);

        mDismissFragment = getDismissFragment();
        mDismissFragment.setDismissCallback(getDismissCallback());
        mDismissFragment.setDismissItem(note);

        addFragment(R.id.bottom_toolbar_container, mDismissFragment);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            removeFragment(mDismissFragment);
        }, 10000);
    }
}
