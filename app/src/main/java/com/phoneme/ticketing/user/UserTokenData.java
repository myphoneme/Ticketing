package com.phoneme.ticketing.user;

import com.phoneme.ticketing.ui.user.UserModel;

import org.json.JSONObject;

public class UserTokenData {
    private String firstName,name,id,mobile_no,created,status,email,password,image,created_by,role,designation,gcmmasterid;
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

            try{
                name= jsonData.getString("name");
            }catch(Exception e){

            }
            try{
                mobile_no= jsonData.getString("mobile_no");

            }catch(Exception e){

            }
            try{
                created= jsonData.getString("created");
            }catch(Exception e){

            }
            try{
                status= jsonData.getString("status");
            }catch(Exception e){

            }
            try{
                email= jsonData.getString("email");
            }catch(Exception e){

            }
            try{
                password= jsonData.getString("password");
            }catch(Exception e){

            }
            try{
                image= jsonData.getString("image");
            }catch(Exception e){

            }
            try{
                created_by= jsonData.getString("created_by");
            }catch(Exception e){

            }
            try{
                role= jsonData.getString("role");
            }catch(Exception e){

            }
            try{
                id= jsonData.getString("id");
            }catch(Exception e){

            }

            //exp assigned earlier
//            try{
//                exp= jsonData.getInt("exp");
//            }catch(Exception e){
//
//            }
            try{
                designation=jsonData.getString("designation");
            }catch(Exception e){

            }
            try{
                gcmmasterid=jsonData.getString("gcm_master_id");
            }catch(Exception e){

            }



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
    public String getDesignation(){return this.designation;}
    public String getGcmmasterid(){return this.gcmmasterid;}
}
