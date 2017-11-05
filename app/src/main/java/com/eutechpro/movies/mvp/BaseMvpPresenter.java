package com.eutechpro.movies.mvp;

import android.app.Activity;

/**
 *
 */

public interface BaseMvpPresenter<V> {
    /** We need to bind Presenter with a View in order to have Bi-Directional communication. */
    void bindView(V view);

    /**
     * We might need to reach to the {@link Activity} from a Presenter.
     *
     * @param activityCallback Callback to reach Activity from a presenter.
     */
    void bindActivityCallback(MvpPresenterActivityCallback activityCallback);

    /** Unbind all things that might cause mem leaks. */
    void unBind();

}
