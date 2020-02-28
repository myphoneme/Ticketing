package com.phoneme.ticketing.ui.techsupportdashboard.model;

import com.google.gson.annotations.SerializedName;

public class TechsupportUserDataDashboardModel {
    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("totalopenticketcount")
    private int totalopenticketcount;

    @SerializedName("totalcloseticketcount")
    private int totalcloseticketcount;

    @SerializedName("totalavgcount")
    private int totalavgcount;

    @SerializedName("color")
    private String color;

    @SerializedName("role")
    private String role;

    @SerializedName("id")
    private String id;

    @SerializedName("mobile_no")
    private String mobile_no;

    public String getMobile_no(){
        return this.mobile_no;
    }

    @SerializedName("created")
    private String created;

    @SerializedName("status")
    private String status;

    @SerializedName("email")
    private String email;

    public String getEmail(){
        return this.email;
    }
    @SerializedName("password")
    private String password;

    @SerializedName("created_by")
    private String create_by;

    @SerializedName("designation")
    private String designation;

    @SerializedName("totalopenticketsrightnow")
    private String totalopenticketsrightnow;

    public String getTotalopenticketsrightnow(){
        return this.totalopenticketsrightnow;
    }

    public String getDesignation(){
        return this.designation;
    }

    public String getCreate_by(){
        return this.create_by;
    }

    public String getPassword(){
        return this.password;
    }
    public String getStatus(){
        return this.status;
    }
    public String getCreated(){
        return this.created;
    }

    public String getId(){
        return this.id;
    }

    public String getRole(){
        return this.role;
    }

    public int getTotalopenticketcount(){
        return this.totalopenticketcount;
    }
    public int getTotalcloseticketcount(){
        return this.totalcloseticketcount;
    }
    public int getTotalavgcount(){
        return this.totalavgcount;
    }
    public String getColor(){
        return this.color;
    }
//    private String total_tickets_assigned;
//    private String solved_tickets;
//    private String today_solved_tickets;

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

//    public void setTotal_tickets_assigned(String total_tickets_assigned){
//        this.total_tickets_assigned=total_tickets_assigned;
//    }
//    public String getTotal_tickets_assigned(){
//        return this.total_tickets_assigned;
//    }
//    public void setSolved_tickets(String solved_tickets){
//        this.solved_tickets=solved_tickets;
//    }
//    public String getSolved_tickets(){
//        return this.solved_tickets;
//    }
//
//    public void setToday_solved_tickets(String today_solved_tickets){
//        this.today_solved_tickets=today_solved_tickets;
//    }
//    public String getToday_solved_tickets(){
//        return this.today_solved_tickets;
//    }

    public String getImage(){
        return this.image;
    }
}
