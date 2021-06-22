package com.seek.hrm.Parsing;

import com.google.gson.annotations.SerializedName;
import com.seek.hrm.POJO.HistoryMeasure;

import java.util.List;

public class HistoriesResponse {
    @SerializedName("histories")
    List<HistoryMeasure> historyMeasure;

    public List<HistoryMeasure> getHistoryMeasure() {
        return historyMeasure;
    }

    public void setHistoryMeasure(List<HistoryMeasure> historyMeasure) {
        this.historyMeasure = historyMeasure;
    }
}
