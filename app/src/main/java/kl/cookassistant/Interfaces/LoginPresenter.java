package kl.cookassistant.Interfaces;

import android.content.Context;

/**
 * Created by Li on 10/5/2016.
 */

public interface LoginPresenter {
    void OnLoginButtonClick();
    void OnRegisterButtonClick();
    long tryGoogleLogin(String ID);
}
