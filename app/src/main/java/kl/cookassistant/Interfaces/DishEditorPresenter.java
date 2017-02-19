package kl.cookassistant.Interfaces;

import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DishEditor.DishEditorActivity;

/**
 * Created by Li on 1/11/2017.
 */

public interface DishEditorPresenter {

    void onAddIngredientButtonClicked();

    void onIngredientCancelButtonClicked();

    void onIngredientConfirmButtonClicked();
}
