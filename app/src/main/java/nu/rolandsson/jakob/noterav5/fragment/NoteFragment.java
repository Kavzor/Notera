package nu.rolandsson.jakob.noterav5.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nu.rolandsson.jakob.noterav5.R;
import nu.rolandsson.jakob.noterav5.fragment.abstraction.PageFragment;
import nu.rolandsson.jakob.noterav5.model.entity.Category;
import nu.rolandsson.jakob.noterav5.model.entity.Note;

public class NoteFragment extends PageFragment {

    private static final String TAG = NoteFragment.class.getName();
    private Note mNote;
    private Category mCategory;

    private View mView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_note;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Creating view");
        mView = view;

        getTitleView().setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                //if(!noteExist(mNote)) {
                    mNote.setTitle(getTitleView().getText().toString());
                    getNoteHandler().save(mNote);
                    Log.d(TAG, "Save title");
                /*}
                else {
                    notifyUserNoteExist();
                }*/
            }
        });

        getTextView().setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                //if(!noteExist(mNote)) {
                    mNote.setText(getTextView().getText().toString());
                    getNoteHandler().save(mNote);
                    Log.d(TAG, "Save text");
                //}
                //else {
                    //notifyUserNoteExist();
                //}
            }
        });
    }

    private void notifyUserNoteExist() {
        Toast.makeText(getContext(),
                "Note: " + mNote.getTitle() + " already exist in this category",
                Toast.LENGTH_SHORT).show();
    }

    private boolean noteExist(Note note) {
        return mCategory.getNotes().contains(note);
    }

    @Override
    public void onRefreshContent(Category category) {
        mCategory = category;
    }

    @Override
    public void onRefreshContent(Note note) {
        Log.d(TAG, "Setting note: " + note.getTitle());
        mNote = note;
        if(mView != null) {
            getTitleView().setText(note.getTitle());
            getTextView().setText(note.getText());
        }
    }

    private EditText getTitleView() {
        return mView.findViewById(R.id.frag_note_title);
    }

    private EditText getTextView() {
        return mView.findViewById(R.id.frag_note_text);
    }
}
