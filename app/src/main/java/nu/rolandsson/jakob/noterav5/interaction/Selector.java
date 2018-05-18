package nu.rolandsson.jakob.noterav5.interaction;

public interface Selector<E> {
    default void  drag(E element){
        throw new UnsupportedOperationException("display has not been implemented");
    }
    default void swipe(E element){
        throw new UnsupportedOperationException("create has not been implemented");
    }
    default void click(E element){
        throw new UnsupportedOperationException("click has not been implemented");
    }
    default void longClick(E element){
        throw new UnsupportedOperationException("longClick has not been implemented");
    }
}
