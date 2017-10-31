package com.eutechpro.movies.discovery;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.eutechpro.movies.Genre;
import com.eutechpro.movies.R;
import com.eutechpro.movies.common.ResourcesUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class GenresSelector extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG = "GenresSelector";
    private OnGenreSelectedListener onGenreSelectedListener;
    private List<Genre>             genres;

    public GenresSelector(Context context) {
        super(context);
        init();
    }

    public GenresSelector(Context context, int mode) {
        super(context, mode);
        init();
    }

    public GenresSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GenresSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GenresSelector(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        init();
    }

    private void init() {
        setEnabled(false);
        genres = new Gson().fromJson(ResourcesUtil.readFromRawFile(R.raw.genres, getResources()), new TypeToken<List<Genre>>() {
        }.getType());

        List<String> dataSet = generateDataSet(genres);
        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dataSet);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAdapter(dataAdapter);

        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int position, long id) {
                if (onGenreSelectedListener == null || view == null){
                    return;
                }
                if (!isEnabled()){
                    setEnabled(true);
                    return;
                }
                if (position == 0){
                    onGenreSelectedListener.genreSelected(null);
                } else {
                    onGenreSelectedListener.genreSelected(genres.get(position - 1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                onGenreSelectedListener.genreSelected(null);
            }
        });
    }

    @NonNull
    private List<String> generateDataSet(List<Genre> genres) {
        List<String> data = new ArrayList<>();
        data.add("Select genre");
        for (Genre genre : genres) {
            data.add(genre.getGenreName());
        }


        return data;
    }


    public void setOnGenreSelectedListener(OnGenreSelectedListener onGenreSelectedListener) {
        this.onGenreSelectedListener = onGenreSelectedListener;
    }

    /**
     * Callback for getting information about user's action on this element.<br/>
     * IMPORTANT: In case of selecting default value, it will return null as selected value!
     */
    interface OnGenreSelectedListener {
        /**
         * <b>IMPORTANT: In case of selecting default value, it will return null as selected value!</b>
         *
         * @param genre ID of selected genre, or null in case of selecting default value
         */
        void genreSelected(Genre genre);
    }
}
