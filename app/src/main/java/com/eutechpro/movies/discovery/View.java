package com.eutechpro.movies.discovery;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eutechpro.movies.BaseActivity;
import com.eutechpro.movies.R;
import com.eutechpro.movies.data.Movie;
import com.eutechpro.movies.mvp.MvpViewActivityCallback;

import java.util.List;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

 class View implements Mvp.View {
     private static final int     NUMBER_OF_COLUMN_PORTRAIT   = 2;
     private static final int     NUMBER_OF_COLUMNS_LANDSCAPE = 4;
     private static final boolean DO_NO_KILL_ACTIVITY         = false;

     private MvpViewActivityCallback   activityCallback;
     private Mvp.Presenter             presenter;
     private MoviesRecyclerViewAdapter adapter;
     private SwipeRefreshLayout        swipeRefreshLayout;
     private RecyclerView              recyclerView;
     private GridLayoutManager         layoutManager;
     private boolean loadingAlreadyStarted = false;//Used to mark state when we initialised loadingAlreadyStarted and now waiting for a response.
     private YearSelector   yearSelector;
     private GenresSelector genresSelector;

     public View(BaseActivity activity) {
        activity.setContentView(R.layout.discovery_layout);

         initYearSelector(activity);
         initGenresSelector(activity);

         initRecyclerView(activity);

         initInfiniteScroll();

         initLoader(activity);
    }

    @Override
    public void bindPresenter(Mvp.Presenter presenter) {
        this.presenter = presenter;
        yearSelector.setOnYearSelectedListener(year -> {
            showLoader();
            presenter.filterByYear(year == null ? 0 : year);
        });
        genresSelector.setOnGenreSelectedListener(genre -> {
            showLoader();
            presenter.filterByGenre(genre);
        });
        showLoader();
    }

    @Override
    public void bindActivityCallback(MvpViewActivityCallback activityCallback) {
        this.activityCallback = activityCallback;
    }

    @Override
    public void unBind() {
        this.activityCallback = null;
        this.presenter = null;
        this.yearSelector.setOnYearSelectedListener(null);
        this.genresSelector.setOnGenreSelectedListener(null);
    }

    @Override
    public void drawMovies(List<Movie> movies) {
        adapter.setDataSet(movies);
        loadingAlreadyStarted = false;
        hideLoader();
    }

    @Override
    public void showError() {
        activityCallback.showToast(R.string.discovery_error_loading, DO_NO_KILL_ACTIVITY);
    }

    private void hideLoader() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showLoader() {
        swipeRefreshLayout.setRefreshing(true);
    }

     private void initYearSelector(BaseActivity activity) {
         yearSelector = activity.findViewById(R.id.year_selector);
     }

     private void initGenresSelector(BaseActivity activity) {
         genresSelector = activity.findViewById(R.id.genres_selector);
     }

     private void initRecyclerView(BaseActivity activity) {
         adapter = new MoviesRecyclerViewAdapter(activity);
         adapter.setOnListItemClickListener(movie -> presenter.openMovieDetails(movie.getId()));

         recyclerView = activity.findViewById(R.id.recycler_view);
         recyclerView.setHasFixedSize(true);
         if (activity.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT){
             layoutManager = new GridLayoutManager(activity, NUMBER_OF_COLUMN_PORTRAIT);
         } else {
             layoutManager = new GridLayoutManager(activity, NUMBER_OF_COLUMNS_LANDSCAPE);
         }
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setAdapter(adapter);
     }

     private void initInfiniteScroll() {
         if (recyclerView == null){
             throw new IllegalStateException("You have to initialise RecyclerView first!!!");
         }
         recyclerView.addOnScrollListener(new ListReachedEndScroller(layoutManager, () -> {
             if (loadingAlreadyStarted){
                 return;
             }
             presenter.loadMore();
             loadingAlreadyStarted = true;
             showLoader();
         }));
     }

     private void initLoader(BaseActivity activity) {
         swipeRefreshLayout = activity.findViewById(R.id.swipe_to_refresh);
         swipeRefreshLayout.setEnabled(false);
     }

    /**
     * Listener for registering end of RecyclerView and notifying outer world about it.
     */
    private static class ListReachedEndScroller extends RecyclerView.OnScrollListener {
        private final GridLayoutManager  layoutManager;
        private final EndReachedListener listener;

        ListReachedEndScroller(GridLayoutManager layoutManager, EndReachedListener listener) {
            this.layoutManager = layoutManager;
            this.listener = listener;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount   = layoutManager.getItemCount();
            int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
            if (pastVisibleItems + visibleItemCount >= totalItemCount){
                listener.endReached();
            }
        }

        private interface EndReachedListener {
            void endReached();
        }

    }
}
