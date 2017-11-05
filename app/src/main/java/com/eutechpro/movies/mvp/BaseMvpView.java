package com.eutechpro.movies.mvp;

/**
 *
 */
public interface BaseMvpView<P> {
    /**
     * Bind Presenter to the View so we have bi-directional communication.
     *
     * @param presenter Presenter to bind to.
     */
    void bindPresenter(P presenter);

    /**
     * Unbind all things that might cause mem leaks.
     */
    void unBind();

    /**
     * Bind this view with instance of {@link MvpViewActivityCallback}.
     * <br/>
     * That will enrich the View with possibilities of drawing different Android UI components
     *
     * @param activityCallback Instance of MvpActivityCallback
     */
    void bindActivityCallback(MvpViewActivityCallback activityCallback);
}
