package nu.rolandsson.jakob.noterav5;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nu.rolandsson.jakob.noterav5.component.PageComponent;
import nu.rolandsson.jakob.noterav5.fragment.CategoryFragment;
import nu.rolandsson.jakob.noterav5.fragment.NoteFragment;
import nu.rolandsson.jakob.noterav5.fragment.OverviewFragment;
import nu.rolandsson.jakob.noterav5.fragment.abstraction.PageFragment;
import nu.rolandsson.jakob.noterav5.model.CategoryViewModel;
import nu.rolandsson.jakob.noterav5.model.LocalViewModel;
import nu.rolandsson.jakob.noterav5.model.SelectedViewModel;
import nu.rolandsson.jakob.noterav5.model.TrashViewModel;
import nu.rolandsson.jakob.noterav5.model.ViewModel;

public class NoteraActivity extends AppCompatActivity implements ViewModel {

    private static final String TAG = NoteraActivity.class.getName();
    private static final int FIRST_PAGE = 0;

    private List<MenuItem> mCheckableItems;

    private CategoryViewModel mCategoryViewModel;
    private SelectedViewModel mSelectedViewModel;
    private TrashViewModel mTrashViewModel;
    private LocalViewModel mLocalViewModel;

    private PageComponent mPageComponent;
    private DrawerLayout mDrawerLayout;

    private List<PageFragment> pages = new ArrayList<PageFragment>() {{
            add(new OverviewFragment());
            add(new CategoryFragment());
            add(new NoteFragment());
    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notera);

        mCheckableItems = new LinkedList<>();

        Toolbar toolbar = findViewById(R.id.view_toolbar);
        NavigationView navigationView = findViewById(R.id.view_navigation);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mPageComponent = findViewById(R.id.view_page_content);


        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPageComponent.setAdapter(pagerAdapter);

        buildToolBar(toolbar);

        navigationView.setNavigationItemSelectedListener(item -> {
            if(item.isCheckable()) {
                if(!mCheckableItems.contains(item)) {
                    mCheckableItems.add(item);
                }
                for(MenuItem menuItem : mCheckableItems) {
                    menuItem.setChecked(false);
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
            }
            switch (item.getItemId()) {
                case R.id.nav_category_categories:
                    mPageComponent.setCurrentItem(FIRST_PAGE);
                    getLocalViewModel().setDisplayFavorites(false);
                    return true;
                case R.id.nav_category_starred:
                    mPageComponent.setCurrentItem(FIRST_PAGE);
                    getLocalViewModel().setDisplayFavorites(true);
            }
            return false;
        });

        getSelectedViewModel().getSelectedCategory().observe(this, e -> mPageComponent.showNext());
        getSelectedViewModel().getSelectedNote().observe(this, n -> mPageComponent.showNext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void buildToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
    }

    @Override
    public void onBackPressed() {
        if(mPageComponent.getCurrentItem() == FIRST_PAGE) {
            super.onBackPressed();
        }
        else {
            mPageComponent.showPrevious();
        }
    }

    @Override
    public CategoryViewModel getCategoryViewModel() {
        Log.d(TAG, "Getting view model");
        if(mCategoryViewModel == null) {
            mCategoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        }
        return mCategoryViewModel;
    }

    @Override
    public TrashViewModel getTrashViewModel() {
        if(mTrashViewModel == null) {
            mTrashViewModel = ViewModelProviders.of(this).get(TrashViewModel.class);
        }
        return mTrashViewModel;
    }

    @Override
    public SelectedViewModel getSelectedViewModel() {
        if(mSelectedViewModel == null) {
            mSelectedViewModel = ViewModelProviders.of(this).get(SelectedViewModel.class);
        }
        return mSelectedViewModel;
    }

    @Override
    public LocalViewModel getLocalViewModel() {
        if(mLocalViewModel == null) {
            mLocalViewModel = ViewModelProviders.of(this).get(LocalViewModel.class);
        }
        return mLocalViewModel;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pages.get(position);
        }

        @Override
        public int getCount() {
            return pages.size();
        }
    }
}

