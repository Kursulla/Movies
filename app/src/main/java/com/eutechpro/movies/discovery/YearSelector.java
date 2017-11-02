package com.eutechpro.movies.discovery;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.eutechpro.movies.R;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class YearSelector extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG      = "YearSelector";
    public static final  int    MAX_YEAR = 2020;
    public static final  int    MIN_YEAR = 1900;
    private Integer                selectedYear;
    private OnYearSelectedListener onYearSelectedListener;
    private boolean                userSelected;
    private List<String>           years;

    public YearSelector(Context context) {
        super(context);
        init();
    }

    public YearSelector(Context context, int mode) {
        super(context, mode);
        init();
    }

    public YearSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public YearSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public YearSelector(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        init();
    }

    private void init() {
        initData();
        initTouchListener();
        initSelectionListener();
    }

    /** Prepare data to feed spinner */
    private void initData() {
        years = new ArrayList<>();
        years.add(getContext().getString(R.string.year_selector_default_value));
        for (int i = MAX_YEAR; i > MIN_YEAR; i--) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAdapter(dataAdapter);
    }

    /** Init OnTouchListener to prevent crazy unwanted selections on rotation, recreation... */
    private void initTouchListener() {
        setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                view.performClick();
                userSelected = true;
            }

            return true;
        });
    }

    private void initSelectionListener() {
        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int index, long id) {
                if (!userSelected){
                    return;
                }
                userSelected = false;
                if (onYearSelectedListener == null){
                    Log.e(TAG, "onItemSelected: ", new IllegalStateException("Unable to select value! There is no instance of OnYearSelectedListener assigned!"));
                    return;
                }
                if (index == 0){
                    selectedYear = null;
                } else {
                    selectedYear = Integer.valueOf(years.get(index));
                }
                onYearSelectedListener.yearSelected(selectedYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                onYearSelectedListener.yearSelected(null);
            }
        });
    }

    public Integer getSelectedYear() {
        return selectedYear;
    }

    public void setOnYearSelectedListener(OnYearSelectedListener onYearSelectedListener) {
        this.onYearSelectedListener = onYearSelectedListener;
    }

    /**
     * Callback for getting information about user's action on this element.<br/>
     * IMPORTANT: In case of selecting default value, it will return null as selected value!
     */
    interface OnYearSelectedListener {
        /**
         * <b>IMPORTANT: In case of selecting default value, it will return null as selected value!</b>
         *
         * @param year ID of selected genre, or null in case of selecting default value
         */
        void yearSelected(Integer year);
    }
}
