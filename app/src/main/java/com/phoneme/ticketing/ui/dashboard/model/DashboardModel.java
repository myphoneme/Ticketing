package com.phoneme.ticketing.ui.dashboard.model;

import java.util.List;

public class DashboardModel {
    private String total;
    private String iconurl;
    private String heading;
    private String colorHex;
    private String type;
    private List<DashboardSubheading> dashboardSubheadings;
    public void setIconurl(String iconurl){
        this.iconurl=iconurl;
    }
    public void setHeading(String heading){
        this.heading=heading;
    }
    public String getIconurl(){
        return this.iconurl;
    }
    public String getHeading(){
        return this.heading;
    }
    public void setDashboardSubheadings(List<DashboardSubheading> dashboardSubheadings){
        this.dashboardSubheadings=dashboardSubheadings;
    }
    public List<DashboardSubheading> getDashboardSubheadings(){
        return this.dashboardSubheadings;
    }

    public void setColorHex(String colorHex){
        this.colorHex=colorHex;
    }
    public String getColorHex(){
        return this.colorHex;
    }

    public void setTotal(String total){
        this.total=total;
    }
    public String getTotal(){
        return this.total;
    }
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type=type;
    }

}
