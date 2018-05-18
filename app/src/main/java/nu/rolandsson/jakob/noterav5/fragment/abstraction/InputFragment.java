package nu.rolandsson.jakob.noterav5.fragment.abstraction;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import nu.rolandsson.jakob.noterav5.model.CategoryViewModel;
import nu.rolandsson.jakob.noterav5.model.ViewModel;


/**
 * Created by Martin on 2018-04-24.
 */

public abstract class InputFragment extends DialogFragment {

    protected CategoryViewModel mCategoryViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            ViewModel viewModel = (ViewModel) context;
            mCategoryViewModel = viewModel.getCategoryViewModel();
        }
        catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ViewModel");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getInputLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button finishBtn = view.findViewById(getFinishButton());
        finishBtn.setOnClickListener(this::onFinish);

        View focusView = view.findViewById(getFocusedView());
        focusView.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public static void createInputDialog(FragmentActivity activity, InputFragment fragment,
                                         Bundle args) {
        fragment.setArguments(args);
        FragmentTransaction transaction = activity.
                getSupportFragmentManager().beginTransaction();

        transaction.addToBackStack(null);
        fragment.show(transaction, "Input Dialog");
    }

    protected abstract void onFinish(View view);
    protected abstract int getFinishButton();
    protected abstract int getInputLayout();
    protected abstract int getFocusedView();
}
