package com.phoneme.ticketing.ui.inframonitoring;

import com.google.gson.annotations.SerializedName;

public class InfraMonitoringResponse {
    @SerializedName("pingresult")
    private String pingresult;

    @SerializedName("accesslogresult")
    private String accesslogresult;

    @SerializedName("errorlogresult")
    private String errorlogresult;

    @SerializedName("mysqllogresult")
    private String mysqllogresult;

    @SerializedName("executedoutput")
    private String executedoutput;

    public String getPingresult(){
        return this.pingresult;
    }
    public String getAccesslogresult(){
        return this.accesslogresult;
    }
    public String getErrorlogresult(){
        return this.errorlogresult;
    }
    public String getMysqllogresult(){
        return this.mysqllogresult;
    }

    public String getExecutedoutput(){
        return this.executedoutput;
    }
}
