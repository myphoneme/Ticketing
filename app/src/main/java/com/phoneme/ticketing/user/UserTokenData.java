package com.phoneme.ticketing.user;

import com.phoneme.ticketing.ui.user.UserModel;

import org.json.JSONObject;

public class UserTokenData {
    private String firstName,name,id,mobile_no,created,status,email,password,image,created_by,role;
    private String userid;
    private int nbf,exp;
    private UserModel userModel;
    public void setUserTokenData(String decodedToken) {
        try {
            JSONObject jsonObj = new JSONObject(decodedToken);

            nbf= jsonObj.getInt("nbf");
            exp= jsonObj.getInt("exp");

            //JSONObject obj=(JSONObject) jsonObj.getJSONObject("data");

            JSONObject jsonData = new JSONObject(jsonObj.get("data").toString());
            name= jsonData.getString("name");
            id= jsonData.getString("id");
            mobile_no= jsonData.getString("mobile_no");
            created= jsonData.getString("created");
            status= jsonData.getString("status");
            email= jsonData.getString("email");
            password= jsonData.getString("password");
            image= jsonData.getString("image");
            created_by= jsonData.getString("created_by");
            role= jsonData.getString("role");
            exp= jsonData.getInt("exp");

        }catch(Exception e){

        }
    }
    public String getId(){
        return this.id;
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
    public int getNbf(){
        return this.nbf;
    }
    public String getUserid(){
        return this.userid;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getName(){
        return this.name;
    }
}
