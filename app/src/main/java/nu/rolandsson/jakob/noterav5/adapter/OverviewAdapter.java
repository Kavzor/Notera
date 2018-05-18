package nu.rolandsson.jakob.noterav5.adapter;

import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nu.rolandsson.jakob.noterav5.R;
import nu.rolandsson.jakob.noterav5.fragment.abstraction.NoteraUpdate;
import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public class OverviewAdapter extends NoteraListAdapter<Category> {

    private static final String TAG = OverviewAdapter.class.getName();

    private List<Category> categories;
    private List<Category> starCategories;

    public OverviewAdapter(NoteraUpdate<Category> noteraUpdate) {
        super(noteraUpdate);
    }

    @Override
    public int getAdapterView() {
        return R.layout.view_category_item;
    }


    @Override
    protected int getMarkView() {
        return R.id.view_overview_category_name;
    }

    @Override
    protected int getFavView() {
        return R.id.view_overview_star_btn;
    }

    @Override
    protected int getTrashView() {
        return R.id.view_overview_remove_btn;
    }

    @Override
    public void bindView(ViewHolder holder, Category category, int adapterPosition) {
        super.bindView(holder, category, adapterPosition);
        TextView categoryTitle = holder.get(getMarkView());
        categoryTitle.setText(category.getName());

        ImageButton starButton = holder.get(getFavView());
        starButton.setImageResource(
                category.isStarred() ? R.drawable.ic_star_full : R.drawable.ic_star_empty);
    }
}
