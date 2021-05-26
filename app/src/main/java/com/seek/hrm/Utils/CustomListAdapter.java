package com.seek.hrm.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.seek.hrm.R;
import com.seek.hrm.POJO.HistoryMeasure;

public class CustomListAdapter extends BaseAdapter {

    private final List<HistoryMeasure> listData;
    private final LayoutInflater layoutInflater;
    Context context;

    public CustomListAdapter(List<HistoryMeasure> listData, Context context) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.flagView = (ImageView) convertView.findViewById(R.id.imageView_flag);
            holder.time = (TextView) convertView.findViewById(R.id.textView_time);
            holder.measureResult = (TextView) convertView.findViewById(R.id.textView_result);
            holder.deleteBtn = (ImageButton) convertView.findViewById(R.id.deleteBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        HistoryMeasure historyMeasure = this.listData.get(position);
        holder.time.setText(formatTime(historyMeasure.getTime()));
        holder.measureResult.setText(Integer.toString(historyMeasure.getResult()));
        holder.deleteBtn.setOnClickListener(v ->
            showAlertDelete(historyMeasure)
        );
        return convertView;
    }

    void showAlertDelete(HistoryMeasure historyMeasure){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to delete measurement result: "+ historyMeasure.getResult() +" at: "+
                formatTime(historyMeasure.getTime()))
                .setPositiveButton("OK", (dialog, which) -> {

                    SQLiteHelper db = new SQLiteHelper(context);

                    if(db.delete(historyMeasure.getUser_id())){
                        HistoryMeasure.deleteItemById(this.listData,historyMeasure.getUser_id());
                        Toast.makeText(context, "DELETE SUCCESSFULLY", Toast.LENGTH_LONG).show();
                            updateData(historyMeasure.getUser_id());
                        //API DELETE RESULT MEASUREMENT, HERE !!!!

                    }else{
                        Toast.makeText(context, "DELETE FAILED", Toast.LENGTH_LONG).show();
                    }



                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .show();

    }

    void updateData(int id){
        notifyDataSetChanged();
        Intent intent = new Intent("delete-result");
        intent.putExtra("result",id);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    static class ViewHolder {
        ImageView flagView;
        TextView time;
        TextView measureResult;
        ImageButton deleteBtn;
    }

    static public String formatTime(Date time){
        SimpleDateFormat formatPattern = new SimpleDateFormat("dd/MM/yyyy    HH:mm:ss");
        return formatPattern.format(time.getTime());

    }
    static public String formatShortTime(Date time){
        SimpleDateFormat formatPattern = new SimpleDateFormat("HH:mm:ss dd/MM");
        return formatPattern.format(time.getTime());

    }


}
