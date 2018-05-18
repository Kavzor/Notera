package nu.rolandsson.jakob.noterav5.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.LinkedList;
import java.util.List;

import nu.rolandsson.jakob.noterav5.R;
import nu.rolandsson.jakob.noterav5.adapter.OverviewAdapter;
import nu.rolandsson.jakob.noterav5.fragment.abstraction.InputFragment;
import nu.rolandsson.jakob.noterav5.fragment.abstraction.PageFragment;
import nu.rolandsson.jakob.noterav5.model.entity.Category;

public class OverviewFragment extends PageFragment {

    private static final String TAG = OverviewFragment.class.getName();

    private List<Category> mCategories;
    private List<Category> mFavoriteCategories;
    private boolean mDisplayFavorites;

    private SearchView mSearchView;
    private RecyclerView mCategoryList;

    private OverviewAdapter mOverviewAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_overview;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFavoriteCategories = new LinkedList<>();
        mCategories = new LinkedList<>();

        mOverviewAdapter = new OverviewAdapter(getCategoryHandler());

        View newItemView = view.findViewById(R.id.new_item_include);
        mCategoryList = view.findViewById(R.id.frag_overview_search_list);
        mSearchView = view.findViewById(R.id.frag_overview_search_field);

        mCategoryList.setAdapter(mOverviewAdapter);
        mCategoryList.setLayoutManager(getVerticalListLayoutManager());

        newItemView.setOnClickListener( v -> InputFragment.createInputDialog(
                getActivity(), new CategoryInputFragment(), null));

        mSearchView.setOnQueryTextListener(new QuerySelector());
    }

    @Override
    public void onDisplayFavorites(boolean displayFavorites) {
        mDisplayFavorites = displayFavorites;
        refreshContent();
    }

    @Override
    public void onFilteredContentUpdate(List<Category> categoryList) {
        //mOverviewAdapter.setItems(categoryList);
        mCategories = categoryList;
        refreshContent();
    }

    @Override
    public void onStarContentUpdate(List<Category> categoryList) {
        mFavoriteCategories = categoryList;
        refreshContent();
    }

    public void refreshContent() {
        mOverviewAdapter.setItems(mDisplayFavorites ? mFavoriteCategories : mCategories);
    }

    @Override
    public void onTrash(Category category) {

    }

    public class QuerySelector implements SearchView.OnQueryTextListener {
        private static final String EMPTY_INITIALIZATION_QUERY = "";

        private QuerySelector() {
            filter(EMPTY_INITIALIZATION_QUERY);
        }

        private void filter(String query) {
            getCategoryHandler().filter(query);
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            filter(newText);
            return true;
        }
    }
}
