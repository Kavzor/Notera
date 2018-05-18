package nu.rolandsson.jakob.noterav5.fragment;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import nu.rolandsson.jakob.noterav5.R;
import nu.rolandsson.jakob.noterav5.fragment.abstraction.InputFragment;
import nu.rolandsson.jakob.noterav5.model.entity.Category;


/**
 * Created by Martin on 2018-04-24.
 */

public class CategoryInputFragment extends InputFragment {

    private static final String TAG = CategoryInputFragment.class.getName();

    @Override
    protected int getInputLayout() {
        return R.layout.fragment_dialog_category;
    }
    @Override
    protected int getFinishButton() {
        return R.id.create_category_btn;
    }

    @Override
    protected int getFocusedView() { return R.id.create_category_title; }

    @Override
    protected void onFinish(View view) {
        EditText titleField = getView().findViewById(R.id.create_category_title);
        titleField.performClick();

        Category category = new Category(titleField.getText().toString());

        mCategoryViewModel.set(category, after -> {
            Log.d(TAG, "Added category");
        });

        dismiss();
    }
}
