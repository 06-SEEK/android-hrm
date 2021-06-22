package com.seek.hrm.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.os.Bundle;

import com.seek.hrm.Network.IResultCallback;
import com.seek.hrm.OutputAnalyzer;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scottyab.HeartBeatView;


import java.util.Calendar;

import com.seek.hrm.R;
import com.seek.hrm.POJO.HistoryMeasure;
import com.seek.hrm.Utils.SQLiteHelper;
import com.seek.hrm.api.HistoryService;

import static com.seek.hrm.Activities.MainActivity.MESSAGE_PROGRESS_REALTIME;
import static com.seek.hrm.Activities.MainActivity.MESSAGE_UPDATE_FINAL;
import static com.seek.hrm.Activities.MainActivity.MESSAGE_UPDATE_REALTIME;

public class MeasureFragment extends Fragment {

    private View rootView;
    private OutputAnalyzer analyzer;
    private  CameraService cameraService;
    private final boolean justShared = false;
    Activity activity;
    TextureView cameraTextureView;
    TextView measure;
    HeartBeatView heart;

    void init(){
       activity = getActivity();
        measure = rootView.findViewById(R.id.measure);
       cameraService = new CameraService(activity);
       heart = rootView.findViewById(R.id.heartbeat);
       heart.stop();
       TextView clickMeasure = rootView.findViewById(R.id.measure);
       clickMeasure.setOnClickListener(v -> onClickNewMeasurement());
       clickMeasure.setVisibility(View.GONE);
        cameraTextureView= rootView.findViewById(R.id.textureView2);
   }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView =  inflater.inflate(R.layout.fragment_measurement,container,false);
        init();
        return rootView;
    }



    @Override
    public void onStart() {
        super.onStart();
        cameraService.stop();
        if (analyzer != null) analyzer.stop();
        analyzer = new OutputAnalyzer(activity, rootView.findViewById(R.id.graphTextureView), mainHandler);
    }

    @Override
    public void onResume() {
        super.onResume();
        measure.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        cameraService.stop();
        if (analyzer != null) analyzer.stop();
        analyzer = new OutputAnalyzer(activity, rootView.findViewById(R.id.graphTextureView), mainHandler);
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraService.stop();
        if (analyzer != null) analyzer.stop();
        analyzer = new OutputAnalyzer(activity, rootView.findViewById(R.id.graphTextureView), mainHandler);
    }

    @SuppressLint("HandlerLeak")
    private final Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int i = 0;
            if (msg.what == MESSAGE_UPDATE_REALTIME) {
                ((TextView) rootView.findViewById(R.id.textView)).setText(msg.obj.toString());
            }
            if (msg.what == MESSAGE_PROGRESS_REALTIME) {

                ProgressBar progress = rootView.findViewById(R.id.progress);
                progress.setProgress(Integer.parseInt(msg.obj.toString()));
            }

            if (msg.what == MESSAGE_UPDATE_FINAL) {

                HeartBeatView heart = rootView.findViewById(R.id.heartbeat);
                heart.stop();
                ((TextView)rootView.findViewById(R.id.textView)).setTextColor(Color.YELLOW);
                showAlertResult(msg.obj.toString());
                rootView.findViewById(R.id.measure).setVisibility(View.VISIBLE);


            }
        }
    };

    void showAlertResult(String result ){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("MEASURE RESULT");
        builder.setMessage("Result: " +result +" BPM. Do you want to save measurement result");
        builder.setPositiveButton("Save", (dialog, which) -> {
//            SQLiteHelper db = new SQLiteHelper(getContext());
//            db.addResult( new HistoryMeasure( Calendar.getInstance().getTime(),Integer.parseInt( result)));
            //API ADD RESULT MEASUREMENT, HERE !!!!
            HistoryService.createHistory(Integer.parseInt(result), success -> {
                if (success){
                    Toast.makeText(getActivity(), "Save successfully!", Toast.LENGTH_SHORT).show();
                }
            });

        });
        builder.setNegativeButton("Cancel",(dialog, which) -> {

        });
        builder.show();
    }



    public void onClickNewMeasurement() {
    analyzer = new OutputAnalyzer(activity, rootView.findViewById(R.id.graphTextureView), mainHandler);
    heart.start();
    TextView measure = rootView.findViewById(R.id.measure);
    measure.setVisibility(View.GONE);
    TextureView cameraTextureView = rootView.findViewById(R.id.textureView2);
    SurfaceTexture previewSurfaceTexture = cameraTextureView.getSurfaceTexture();
    ProgressBar progress = rootView.findViewById(R.id.progress);
    progress.setProgress(0);
    ((TextView) rootView.findViewById(R.id.textView)).setTextColor(Color.WHITE);
    if ((previewSurfaceTexture != null) && !justShared) {
        // this first appears when we close the application and switch back - TextureView isn't quite ready at the first onResume.
        Surface previewSurface = new Surface(previewSurfaceTexture);
        cameraService.start(previewSurface);
        analyzer.measurePulse(cameraTextureView, cameraService);
    }
}
}
