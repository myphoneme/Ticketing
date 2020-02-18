package com.phoneme.ticketing.ui.dashboard.model;

public class DashboardModel1 {
    public String count;
    public String colorcode;
    public String name;
    public String opentickets;
    public String closetickets;

    public DashboardModel1(String count){
        this.count=count;
    }
    public DashboardModel1(String count, String colorcode){
        this.count=count;
        this.colorcode=colorcode;
    }
    public DashboardModel1(String count, String colorcode, String name){
        this.count=count;
        this.colorcode=colorcode;
        this.name=name;
    }

    public DashboardModel1(String count, String colorcode, String name, String opentickets, String closetickets){
        this.count=count;
        this.colorcode=colorcode;
        this.name=name;
        this.opentickets=opentickets;
        this.closetickets=closetickets;
    }
}
