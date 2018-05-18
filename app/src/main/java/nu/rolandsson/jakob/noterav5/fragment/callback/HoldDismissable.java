package nu.rolandsson.jakob.noterav5.fragment.callback;

public interface HoldDismissable<T> {
    DismissCallback<T> getDismissCallback();
}
