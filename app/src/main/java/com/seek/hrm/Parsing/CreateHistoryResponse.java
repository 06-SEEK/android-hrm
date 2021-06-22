package com.seek.hrm.Parsing;

import com.google.gson.annotations.SerializedName;
import com.seek.hrm.POJO.HistoryMeasure;

public class CreateHistoryResponse {
    @SerializedName("history")
    HistoryMeasure historyMeasure;

    public HistoryMeasure getHistoryMeasure() {
        return historyMeasure;
    }

    public void setHistoryMeasure(HistoryMeasure historyMeasure) {
        this.historyMeasure = historyMeasure;
    }
}
