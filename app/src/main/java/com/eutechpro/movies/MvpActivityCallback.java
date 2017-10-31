package com.eutechpro.movies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;

/**
 * We might need to reach to the {@link Activity} from a Presenter.
 * <br/>
 * We do not want to have strong connection to it, we we will use this interface for calling anything we need to do in an Activity.
 * <br/>
 * Most common use case is launching another activity, launching system dialogs...
 */
@SuppressWarnings("SameParameterValue")
public interface MvpActivityCallback {
    /**
     * Show toast with appropriate String resource as message. <br/>
     * If you want to kill activity upon displaying toast, then pass in shouldKillActivity = true
     */
    void showToast(@StringRes int messageId, boolean shouldKillActivity);

    /** Show toast with appropriate String resource as message. */
    void showToast(@StringRes int messageId, String params, boolean shouldKillActivity);


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
