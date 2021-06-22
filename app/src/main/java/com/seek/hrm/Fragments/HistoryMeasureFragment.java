package com.seek.hrm.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.enums.Position;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.seek.hrm.Network.IDataResultCallback;
import com.seek.hrm.R;
import com.seek.hrm.Utils.CustomListAdapter;
import com.seek.hrm.POJO.HistoryMeasure;
import com.seek.hrm.Utils.SQLiteHelper;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.TooltipPositionMode;
import com.seek.hrm.api.HistoryService;

import static android.content.Context.MODE_PRIVATE;

public class HistoryMeasureFragment extends Fragment {

    List<HistoryMeasure> historyMeasureList;
    AnyChartView anyChartView;
    Cartesian cartesian;
    ListView historyList;
    TextView distribute;
    private View rootView;

    void init(){
       // createDatabase();
        historyMeasureList=new ArrayList<>();
        historyList = rootView.findViewById(R.id.history_list);
        distribute = rootView.findViewById(R.id.distribute);
        anyChartView = rootView.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(rootView.findViewById(R.id.progress_bar));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("delete-result"));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_history_measurement,container,false);
        init();
//        SQLiteHelper db = new SQLiteHelper(getContext());
//        try {
//            historyMeasureList = db.get(15);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getHistory();
    }

    //    SQLiteDatabase createDatabase(){
//        return  getContext().openOrCreateDatabase("measureapp.db",MODE_PRIVATE,null);
//    }

    private void getHistory(){

        HistoryService.getHistory((success, data) -> {
            if (success){
                historyMeasureList = data;
                CustomListAdapter adapter = new CustomListAdapter(historyMeasureList,getActivity());
                historyList.setAdapter(adapter);
                cartesian = AnyChart.column();
                anyChartView.setChart(cartesian);
                chartDataUpdate();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            getHistory();

//            SQLiteHelper db = new SQLiteHelper(getContext());
//            try {
//                historyMeasureList = db.get(15);//lấy tối đa 15 kết quả để in list
//                distribute.setText(getDisribute());//Thống kê
//                if(historyMeasureList.size()>7){//Lấy tối đa 7 kết quả cuối để vẽ chart
//                    //chart(historyMeasureList.subList(0,6));
//                    chartDataUpdate();
//                }else{
//                    //chart(historyMeasureList);
//                    chartDataUpdate();
//                }
//                //Tính thống kê
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            CustomListAdapter adapter = new CustomListAdapter(historyMeasureList,getActivity());
//
//            historyList.setAdapter(adapter);

        }
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int resultId = intent.getIntExtra("result",-1);
//            HistoryMeasure.deleteItemById(historyMeasureList,resultId);
            distribute.setText(getDisribute());
            chartDataUpdate();
        }
    };


    String getDisribute(){
        if(historyMeasureList.size()==0) return "";
        int total=historyMeasureList.get(0).getResult();
        int hight = historyMeasureList.get(0).getResult();
        int low = historyMeasureList.get(0).getResult();
        for (int i = 1;i<historyMeasureList.size();i++) {
            total+= historyMeasureList.get(i).getResult();
            if(historyMeasureList.get(i).getResult()>hight){
                hight=historyMeasureList.get(i).getResult();
            }
            if(historyMeasureList.get(i).getResult()<low){
                low=historyMeasureList.get(i).getResult();
            }
        }
        return "Average Measurement: "+ total/historyMeasureList.size()+". Highest: "+hight+" BPM. Lowest "+low+" BPM";
    }
    void chartDataUpdate(){
        List<DataEntry> data = new ArrayList<>();
        if(historyMeasureList.size()>10){
            for (int i = historyMeasureList.size()-10;i<historyMeasureList.size();i++) {
                data.add(new ValueDataEntry(CustomListAdapter.formatShortTime(historyMeasureList.get(i).getCreatedAt())
                        ,historyMeasureList.get(i).getResult()));
            }
        }else{
            for (int i = 0;i<historyMeasureList.size();i++) {
                data.add(new ValueDataEntry(CustomListAdapter.formatShortTime(historyMeasureList.get(i).getCreatedAt())
                        ,historyMeasureList.get(i).getResult()));
            }
        }

        cartesian.data(data);
        cartesian.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value} BPM");


        cartesian.animation(true);
        cartesian.title("Recent Measurement Result");
        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");
        cartesian.yAxis(0).title("BPM");
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

    }
}
