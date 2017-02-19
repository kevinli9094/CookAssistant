package kl.cookassistant.Interfaces;

/**
 * Created by Li on 1/28/2017.
 */

public interface TagsManagerPresenter {
    void onBackPressed();

    void onAddNewIngredientsClicked();
    void onDeleteSelectedClicked();
    void onCombineSelectedClicked();
    void onAddToDishClicked();
    void onSearchKnownClicked();
    void onSearchUnknownClicked();
}
