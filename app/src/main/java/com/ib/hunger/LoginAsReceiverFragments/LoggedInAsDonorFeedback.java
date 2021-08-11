package com.ib.hunger.LoginAsReceiverFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.ib.hunger.R;
import com.ib.hunger.loginasReceiverAdapters.FeedbackAdapter;
import com.ib.hunger.LoginAsReceiver_Models.Feedback_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoggedInAsDonorFeedback extends Fragment {
    RecyclerView feedbackRv;
    HorizontalBarChart mBarChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logged_in_as_receiver_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        feedbackRv = view.findViewById(R.id.lad_feedback_recyclerView);
        feedbackRv.setLayoutManager(new LinearLayoutManager(getContext()));
        feedbackRv.setAdapter(new FeedbackAdapter(getContext(), datalist()));
        mBarChart = view.findViewById(R.id.horizontalBarChart);
        setData(5, 5);
    }

    private void setData(int count, int range) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        float barWidth = 2f;
        float spaceForBar = 10f;
        for (int i = 0; i < count; i++) {
            float val = (float) Math.random() * range;
            barEntries.add(new BarEntry(i * spaceForBar, val));
        }
        BarDataSet set = new BarDataSet(barEntries, "status");
        BarData data = new BarData(set);
        data.setBarWidth(barWidth);
        mBarChart.setData(data);
        mBarChart.setPinchZoom(false);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private List<Feedback_model> datalist() {
        List<Feedback_model> data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            data.add(new Feedback_model(Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_baseline_account_circle_24),
                    "Samreen Fathima", "Visited for Shanti Mall", getContext().getResources().getString(R.string.sample_text),
                    "12.jan.20", (float) 3.4));
        }
        for (int i = 0; i < 3; i++) {
            data.add(new Feedback_model(Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_baseline_account_circle_24),
                    "Samreen Fathima", "Visited for Shanti Mall", getContext().getResources().getString(R.string.sample_text),
                    "12.jan.20", (float) 5));
        }
        return data;
    }


//    public static void barchart(BarChart barChart, ArrayList<BarEntry> arrayList, final ArrayList<String> xAxisValues) {
//        barChart.setDrawBarShadow(false);
//        barChart.setFitBars(true);
//        barChart.setDrawValueAboveBar(true);
//        barChart.setMaxVisibleValueCount(25);
//        barChart.setPinchZoom(true);
//
//        barChart.setDrawGridBackground(true);
//        BarDataSet barDataSet = new BarDataSet(arrayList, "Status");
//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        BarData barData = new BarData(barDataSet);
//        barData.setBarWidth(0.9f);
//        barData.setValueTextSize(0f);
//
//        barChart.setBackgroundColor(Color.TRANSPARENT); //set whatever color you prefer
//        barChart.setDrawGridBackground(false);
//
//        Legend l = barChart.getLegend(); // Customize the ledgends
//        l.setTextSize(10f);
//        l.setFormSize(10f);
//        //To set components of x axis
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setTextSize(13f);
//        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));
//        xAxis.setDrawGridLines(false);
//        barChart.setData(barData);
//
//    }
}