package com.eutechpro.movies.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * We might need to reach to the {@link Activity} from a Presenter.
 * <br/>
 * We do not want to have strong connection to it, we we will use this interface for calling anything we need to do in an Activity.
 * <br/>
 * Most common use case are launching another activity, starting services...
 */
@SuppressWarnings("unused")
public interface MvpPresenterActivityCallback {
    /**
     * Open desired {@link Activity} and pass in {@link Bundle} with necessary data. <br/>
     * If you want to kill activity upon displaying toast, then pass in shouldKillActivity = true
     */
    void openActivity(Bundle bundle, Class activityClass, boolean shouldKillActivity);

    /**
     * Open desired {@link Activity} and pass in {@link Bundle} with necessary data.<br/>
     * In case we need a result from following operation, we can set request code.
     */
    void openActivity(Bundle bundle, int requestCode, Class activityClass, boolean shouldKillActivity);

    /**
     * "Fire" desired Intent!
     */
    void openActivity(Intent intent, boolean shouldKillActivity);
}
