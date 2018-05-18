package nu.rolandsson.jakob.noterav5.fragment.callback;

import nu.rolandsson.jakob.noterav5.model.persistence.CategoryRepository;

public interface FragmentCallback extends HasFragmentCallback {
    default void finish(CategoryRepository.AfterResult result) {}
    default void finish() { }
}
