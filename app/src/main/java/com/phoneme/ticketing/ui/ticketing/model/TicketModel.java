package com.phoneme.ticketing.ui.ticketing.model;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.user.UserModel;

public class TicketModel {
    @SerializedName("id")  //id
    private String id;

    @SerializedName("ticket_no") //ticket_no
    private String ticket_no;

    @SerializedName("project_id") //project_id
    private String project_id;

    @SerializedName("name") //name
    private String name;

    @SerializedName("desc") //desc
    private String desc;

    @SerializedName("status") //status
    private String status;

    @SerializedName("created_at") //created_at
    private String created_at;


    @SerializedName("closed_at") //closed_at
    private String closed_at;

    @SerializedName("created_by") //created_by
    private String created_by;

    @SerializedName("thread_id") //thread_id
    private String thread_id;

    @SerializedName("image") //image
    private String image;

    @SerializedName("priority") //priority
    private String priority;

    @SerializedName("last_updated_by") //last_updated_by
    private String last_updated_by;

    @SerializedName("image_type") //image_type
    private String image_type;

    @SerializedName("Activity") //Activity
    private String Activity;

    @SerializedName("project_name")//project_name
    private String project_name;

    @SerializedName("created_by_name") //created_by_name
    private String created_by_name;

    @SerializedName("image_of_user_creating_thread") //created_by_name_image
    private String image_of_user_creating_thread;

    @SerializedName("last_updated_by_name")
    private String last_updated_by_name;

    @SerializedName("user")
    private UserModel user;

    public UserModel getUser(){
        return this.user;
    }

    public String getLast_updated_by_name(){
        return this.last_updated_by_name;
    }

    public void setImage_of_user_creating_thread(String url){
        this.image_of_user_creating_thread=url;
    }
    public String getImage_of_user_creating_thread(){
        return this.image_of_user_creating_thread;
    }

    public String getCreated_by_name(){
        return this.created_by_name;
    }
    public String getProject_name(){
        return this.project_name;
    }

    public String getActivity(){
        return this.Activity;
    }

    public String getImage_type(){
        return this.image_type;
    }
    public String getLast_updated_by(){
        return this.last_updated_by;
    }

    public String getPriority(){
        return this.priority;
    }
    public String getImage(){
        return this.image;
    }

    public String getThread_id(){
        return this.thread_id;
    }
    public String getCreated_by(){
        return this.created_by;
    }
    public String getClosed_at(){
        return this.closed_at;
    }
    public String getCreated_at(){
        return this.created_at;
    }

    public String getStatus(){
        return this.status;
    }
    public String getDesc(){
        return this.desc;
    }
    public String getName(){
        return this.name;
    }

    public String getProject_id(){
        return this.project_id;
    }

    public String getTicket_no(){
        return this.ticket_no;
    }
    public String getId(){
        return this.id;
    }
}
