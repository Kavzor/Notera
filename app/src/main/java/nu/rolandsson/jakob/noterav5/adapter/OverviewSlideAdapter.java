package nu.rolandsson.jakob.noterav5.adapter;

import android.util.Log;
import android.widget.TextView;

import nu.rolandsson.jakob.noterav5.R;
import nu.rolandsson.jakob.noterav5.fragment.abstraction.NoteraUpdate;
import nu.rolandsson.jakob.noterav5.model.entity.Category;

public class OverviewSlideAdapter extends NoteraListAdapter<Category> {
    public OverviewSlideAdapter(NoteraUpdate<Category> noteraUpdate) {
        super(noteraUpdate);
    }
    @Override
    public int getAdapterView() {
        return R.layout.view_overview_item;
    }

    @Override
    public void bindView(ViewHolder holder, Category category, int adapterPosition) {
        super.bindView(holder, category, adapterPosition);
        TextView titleText = holder.get(R.id.view_overview_category_name);
        titleText.setText(category.getName());

        if(adapterPosition == 0) {
            titleText.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, R.drawable.ic_arrow_right, 0);
        }
        else if(adapterPosition == getItems().size() - 1) {
            titleText.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_arrow_left, 0, 0, 0);
        }
        else {
            titleText.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_arrow_left, 0, R.drawable.ic_arrow_right, 0);
        }
    }
}
