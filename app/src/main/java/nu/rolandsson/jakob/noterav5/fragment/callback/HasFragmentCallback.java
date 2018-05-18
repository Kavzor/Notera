package nu.rolandsson.jakob.noterav5.fragment.callback;

public interface HasFragmentCallback {
    default boolean hasFinalizer() {
        return false;
    }
    default FragmentCallback getCallback() {
        throw new UnsupportedOperationException("Fragment failed to return FragmentCallback");
    }
}
