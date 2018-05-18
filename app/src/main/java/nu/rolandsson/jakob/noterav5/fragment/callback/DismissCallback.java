package nu.rolandsson.jakob.noterav5.fragment.callback;

public interface DismissCallback<T> {
    void restore(T item);
    void remove(T item);
}
