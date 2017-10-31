package com.eutechpro.movies.discovery;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class YearSelector extends android.support.v7.widget.AppCompatSpinner {
    private static final String TAG = "YearSelector";
    private Integer                selectedYear;
    private OnYearSelectedListener onYearSelectedListener;

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
        setEnabled(false);
        List<String> years = new ArrayList<>();
        years.add("Select year");
        for (int i = 2017; i > 1900; i--) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAdapter(dataAdapter);

        setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int index, long id) {
                if (onYearSelectedListener == null || view == null){
                    return;
                }
                if (!isEnabled()){
                    setEnabled(true);
                    return;
                }
                if (index == 0){
                    selectedYear = null;
                    onYearSelectedListener.yearSelected(null);
                } else {
                    selectedYear = Integer.valueOf(years.get(index));
                    onYearSelectedListener.yearSelected(Integer.valueOf(years.get(index)));
                }
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
