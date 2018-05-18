package nu.rolandsson.jakob.noterav5.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nu.rolandsson.jakob.noterav5.R;
import nu.rolandsson.jakob.noterav5.fragment.callback.DismissCallback;

public class DismissFragment<T> extends Fragment {

    private DismissCallback<T> mDismissCallback;
    private T mDismissItem;

    public DismissFragment<T> setDismissCallback(DismissCallback<T> dismissCallback) {
        mDismissCallback = dismissCallback;
        return this;
    }

    public DismissFragment<T> setDismissItem(T item) {
        mDismissItem = item;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dismiss, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button restoreBtn = view.findViewById(R.id.frag_dismiss_restorebtn);
        Button removeBtn = view.findViewById(R.id.frag_dismiss_removebtn);

        restoreBtn.setOnClickListener(v -> {
            mDismissCallback.restore(mDismissItem);
        });
        removeBtn.setOnClickListener(v -> mDismissCallback.remove(mDismissItem));
    }
}
