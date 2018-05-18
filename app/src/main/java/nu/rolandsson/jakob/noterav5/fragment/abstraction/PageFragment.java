package nu.rolandsson.jakob.noterav5.fragment.abstraction;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nu.rolandsson.jakob.noterav5.component.PageComponent;
import nu.rolandsson.jakob.noterav5.fragment.DismissFragment;
import nu.rolandsson.jakob.noterav5.fragment.callback.HasFragmentCallback;
import nu.rolandsson.jakob.noterav5.model.ViewModel;

public abstract class PageFragment extends NoteraFragmentStorage implements HasFragmentCallback {

    protected abstract int getLayout();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    public LinearLayoutManager getVerticalListLayoutManager() {
        return new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
    }

    public LinearLayoutManager getHorizontalListLayoutManager() {
        return new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
    }

    public static<T> DismissFragment<T> getDismissFragment() {
        return new DismissFragment<>();
    }

    protected void addFragment(int container, Fragment fragment) {
        getFragmentManager().
                beginTransaction().
                replace(container, fragment).
                commit();

    }

    protected void removeFragment(Fragment fragment) {
        getFragmentManager().
                beginTransaction().
                remove(fragment).
                commit();
    }
}
