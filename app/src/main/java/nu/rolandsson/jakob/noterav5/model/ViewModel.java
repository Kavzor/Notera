package nu.rolandsson.jakob.noterav5.model;

public interface ViewModel {
    CategoryViewModel getCategoryViewModel();
    TrashViewModel getTrashViewModel();
    SelectedViewModel getSelectedViewModel();

    LocalViewModel getLocalViewModel();
}
