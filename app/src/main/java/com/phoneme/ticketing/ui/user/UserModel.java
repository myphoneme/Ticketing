package com.phoneme.ticketing.ui.user;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("mobile_no")
    private String mobile_no;

    @SerializedName("created")
    private String created;

    @SerializedName("status")
    private String status;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("image")
    private String image;

    @SerializedName("created_by")
    private String created_by;

    @SerializedName("role")
    private String role;

    @SerializedName("designation")
    private String designation;

    public String getDesignation(){
        return this.designation;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getMobile_no(){
        return this.mobile_no;
    }

    public String getCreated(){
        return this.created;
    }

    public String getStatus(){
        return this.status;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public String getImage(){
        return this.image;
    }

    public String getCreated_by(){
        return this.created_by;
    }

    public String getRole(){
        return this.role;
    }


//    "id": "1",
//            "name": "admin",
//            "mobile_no": "6390222080",
//            "created": "2019-09-18 11:20:35",
//            "status": "1",
//            "email": "admin@myphoneme.com",
//            "password": "e6e061838856bf47e1de730719fb2609",
//            "image": "https://www.phoneme.in/anujitbhu/ticketing/assets/images/userimage/avatar5.png",
//            "created_by": "",
//            "role": "0"
}
