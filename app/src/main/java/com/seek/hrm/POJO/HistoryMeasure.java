package com.seek.hrm.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HistoryMeasure {
    @SerializedName( "user_id" )
    private int user_id;
    @SerializedName( "create_at" )
    private Date time;
    @SerializedName( "result" )
    private int result;

    public HistoryMeasure(int id, Date time, int measureResult) {
        this.user_id = id;
        this.time = time;
        this.result = measureResult;
    }
    public HistoryMeasure(Date time, int measureResult) {
        this.time = time;
        this.result = measureResult;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    static public void deleteItemById(List<HistoryMeasure> list, int Id){
        Iterator itr = list.iterator();
        while (itr.hasNext())
        {
            HistoryMeasure x = (HistoryMeasure)itr.next();
            if (x.getUser_id() == Id){
                itr.remove();
                return;
            }

        }

    }
}
