package com.phoneme.ticketing.ui.dashboard.model;

import com.google.gson.annotations.SerializedName;

public class DashdataModel {
    //initially public will be private later
    @SerializedName("openticketcount")
    public Integer openticket;

    @SerializedName("closeticketcount")
    public Integer closeticketcount;

    @SerializedName("totalprojects")
    public Integer totalprojects;

    @SerializedName("uniquevisitors")
    public Integer uniquevisitors;
}
