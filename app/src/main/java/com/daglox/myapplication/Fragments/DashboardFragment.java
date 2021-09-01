package com.daglox.myapplication.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.daglox.myapplication.Model.EnvironmentResponse;
import com.daglox.myapplication.POJO.EnvironmentItem;
import com.daglox.myapplication.Presenter.DashboardFragmentPresenter;
import com.daglox.myapplication.Presenter.HomeFragmentPresenter;
import com.daglox.myapplication.Presenter.IHomeFragmentPresenter;
import com.daglox.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;


public class DashboardFragment extends Fragment implements IDashboardFragment{

    private FloatingActionButton fabRefresh;
    private Spinner spinnerSelection;
    private Switch switchSelection;
    private TextView tvTitle;
    private String TitleText;
    private LineChart chart;
    private DashboardFragmentPresenter dashboardFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardFragmentPresenter = new DashboardFragmentPresenter(this, getContext());
        spinnerSelection = v.findViewById(R.id.spinnerPlot);
        switchSelection = v.findViewById(R.id.switch1);
        chart = v.findViewById(R.id.chartEnvironment);
        fabRefresh = v.findViewById(R.id.btnRefresh2);
        tvTitle=v.findViewById(R.id.tvTitle);

        spinnerSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection;
                int check;
                selection=spinnerSelection.getSelectedItem().toString();
                TitleText=selection;
                if(switchSelection.isChecked()){
                    tvTitle.setText(TitleText+" last 7 days");
                    check = 1;

                }
                else {
                    tvTitle.setText(TitleText + " today");
                    check = 0;
                }

                dashboardFragmentPresenter.selectedValue(selection,check);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switchSelection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvTitle.setText(TitleText+" last 7 days");
                    dashboardFragmentPresenter.getWeeklyArray();
                } else {
                    tvTitle.setText(TitleText+" today");
                    dashboardFragmentPresenter.getCurrentArray();
                }
            }
        });

        dashboardFragmentPresenter.getCurrentArray();
        return v;
    }

    @Override
    public void PlotEnvironmentInfo(ArrayList<EnvironmentItem> environmentItems,String selection) {

        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setPinchZoom(false);
        chart.fitScreen();
        ArrayList<Entry> Temperature=new ArrayList<>();
        ArrayList<Entry> Humidity=new ArrayList<>();
        ArrayList<String> datetime=new ArrayList<>();

        for (int i=0;i<environmentItems.size();i++){
            Temperature.add(new Entry(i,environmentItems.get(i).getTemperature()));
            Humidity.add(new Entry(i,environmentItems.get(i).getHumidity()));
            String datet=environmentItems.get(i).getDatetime();
            String SplitDate[]=datet.split(" ");
            datetime.add(SplitDate[0]+"\n"+SplitDate[1]);
        }
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(datetime));
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1.0f);
        xAxis.setLabelCount(2);
        xAxis.setAvoidFirstLastClipping(true);
        chart.getLegend().setWordWrapEnabled(true);
        chart.animateX(1000);
        ArrayList<Entry> sec;
        if (selection.equals("Temperature")){
            sec=Temperature;
        }
        else{
            sec=Humidity;
        }

        LineDataSet set1;
        set1=new LineDataSet(sec,selection);
        set1.setColor(Color.GREEN);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(3f);

        LineData lineData=new LineData(set1);
        chart.setData(lineData);

    }
}