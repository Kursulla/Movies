package com.eutechpro.movies.mvp;

import android.app.Activity;
import android.support.annotation.StringRes;

/**
 * We might need to reach to the {@link Activity} from a View.
 * <br/>
 * We do not want to have strong connection to it, we we will use this interface for calling view related things in an Activity.
 * <br/>
 * Most common use cases are launching system dialogs, showing toasts...
 */
@SuppressWarnings("unused")
public interface MvpViewActivityCallback {
    /**
     * Show toast with appropriate String resource as message. <br/>
     * If you want to kill activity upon displaying toast, then pass in shouldKillActivity = true
     */
    void showToast(@StringRes int messageId, boolean shouldKillActivity);

    /** Show toast with appropriate String resource as message. */
    void showToast(@StringRes int messageId, String params, boolean shouldKillActivity);
}
