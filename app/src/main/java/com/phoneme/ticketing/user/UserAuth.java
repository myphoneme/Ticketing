package com.phoneme.ticketing.user;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.phoneme.ticketing.user.UserTokenData;

import java.io.UnsupportedEncodingException;

public class UserAuth {
    private Context mcontext;
    private String MYPREF="userdata";
    private static String[] split;
    public UserAuth(Context context){
        this.mcontext=context;
    }
    public Boolean isJwtTokenValid(){
        /*SharedPreferences sharedpreferences= mcontext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        if (!sharedpreferences.contains("jwttoken")) {
            return false;
        }else{
            String jwtToken=sharedpreferences.getString("jwttoken","");
        }
        return true;*/
        if(getJwtToken().length()>0){
            //Expiry date also has to be checked here
            return true;
        }
        return false;
    }
    public String getJwtToken(){
        SharedPreferences sharedpreferences= mcontext.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        if (!sharedpreferences.contains("jwttoken")) {
            return "";
        }else{
            String jwtToken=sharedpreferences.getString("jwttoken","");
            return jwtToken;
        }
    }
    public void clearData(){
        SharedPreferences pref = this.mcontext.getSharedPreferences(MYPREF, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
    public void setJwtToken(String token){
        SharedPreferences pref = this.mcontext.getSharedPreferences(MYPREF, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("jwttoken",token);
        editor.commit();
    }
    public String getName4(){
        String token=getJwtToken();
        String[] jwtParts = token.split("\\.");
        byte[] payloadDecodeByte = Base64.decode(jwtParts[1], Base64.URL_SAFE);
        UserTokenData utd = new UserTokenData();
        try {
            utd.setUserTokenData(new String(payloadDecodeByte, "UTF-8"));
            //return utd.getUserid();
            return ""+utd.getNbf();
        } catch (Exception e) {
            Log.d("LOG_TAG", e.getMessage());
        }
        //return null;
        return "";
    }
    public String getName(){
        String token=getJwtToken();
        String[] jwtParts = token.split("\\.");
        byte[] payloadDecodeByte = Base64.decode(jwtParts[1], Base64.URL_SAFE);
        UserTokenData utd = new UserTokenData();
        try {
            utd.setUserTokenData(new String(payloadDecodeByte, "UTF-8"));
            //return utd.getUserid();
            return ""+utd.getName();
        } catch (Exception e) {
            Log.d("LOG_TAG", e.getMessage());
        }
        //return null;
        return "";
    }
    public String getRole(){
        String token=getJwtToken();
        String[] jwtParts = token.split("\\.");
        byte[] payloadDecodeByte = Base64.decode(jwtParts[1], Base64.URL_SAFE);
        UserTokenData utd = new UserTokenData();
        try {
            utd.setUserTokenData(new String(payloadDecodeByte, "UTF-8"));
            //return utd.getUserid();
            return ""+utd.getRole();
        } catch (Exception e) {
            Log.d("LOG_TAG", e.getMessage());
        }
        //return null;
        return "";
    }
    public String getEmail(){
        String token=getJwtToken();
        String[] jwtParts = token.split("\\.");
        byte[] payloadDecodeByte = Base64.decode(jwtParts[1], Base64.URL_SAFE);
        UserTokenData utd = new UserTokenData();
        try {
            utd.setUserTokenData(new String(payloadDecodeByte, "UTF-8"));
            //return utd.getUserid();
            return ""+utd.getEmail();
        } catch (Exception e) {
            Log.d("LOG_TAG", e.getMessage());
        }
        //return null;
        return "";
    }

    public String getDesignation(){
        String token=getJwtToken();
        String[] jwtParts = token.split("\\.");
        byte[] payloadDecodeByte = Base64.decode(jwtParts[1], Base64.URL_SAFE);
        UserTokenData utd = new UserTokenData();
        try {
            utd.setUserTokenData(new String(payloadDecodeByte, "UTF-8"));
            //return utd.getUserid();
            return ""+utd.getDesignation();
        } catch (Exception e) {
            Log.d("LOG_TAG", e.getMessage());
        }
        //return null;
        return "";
    }
    public String getImage(){
        String token=getJwtToken();
        String[] jwtParts = token.split("\\.");
        byte[] payloadDecodeByte = Base64.decode(jwtParts[1], Base64.URL_SAFE);
        UserTokenData utd = new UserTokenData();
        try {
            utd.setUserTokenData(new String(payloadDecodeByte, "UTF-8"));
            //return utd.getUserid();
            return ""+utd.getImage();
        } catch (Exception e) {
            Log.d("LOG_TAG", e.getMessage());
        }
        //return null;
        return "";
    }
    public String getAppString(){
        SharedPreferences sharedpreferences= mcontext.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        String appString=sharedpreferences.getString("appstring","");
        return appString;
    }
    public String getGCMMASTERId(){
        Toast.makeText(mcontext,"getGCMMASTERId1abc", Toast.LENGTH_LONG).show();

        String token=getJwtToken();
        String[] jwtParts = token.split("\\.");
        byte[] payloadDecodeByte = Base64.decode(jwtParts[1], Base64.URL_SAFE);
        UserTokenData utd = new UserTokenData();
        try {
            utd.setUserTokenData(new String(payloadDecodeByte, "UTF-8"));
            //return utd.getUserid();
            Toast.makeText(mcontext,"getGCMMASTERId2abc "+utd.getGcmmasterid(), Toast.LENGTH_LONG).show();
            //return ""+utd.getGcmmasterid();
            return utd.getGcmmasterid();
        } catch (Exception e) {
            Log.d("LOG_TAG", e.getMessage());
        }
        //return null;
        Toast.makeText(mcontext,"getGCMMASTERId3", Toast.LENGTH_LONG).show();
        return "";
    }

    public Boolean isFcmTokenUploaded(){
        SharedPreferences sharedpreferences= mcontext.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        if (!sharedpreferences.contains("tokenuploaded")) {
            return false;
        }else{
            return sharedpreferences.getBoolean("tokenuploaded",false);
            //return jwtToken;
        }
        //return
    }
    public void setTokenUploaded(Boolean yn){
        SharedPreferences pref = this.mcontext.getSharedPreferences(MYPREF, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("tokenuploaded",yn);
        editor.commit();
    }

    public void setAppString(String appString){
        SharedPreferences pref = this.mcontext.getSharedPreferences(MYPREF, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("appstring",appString);
        editor.commit();
    }
    public void saveFCMToken(String fcmtoken){
        SharedPreferences pref = this.mcontext.getSharedPreferences(MYPREF, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("fcmtoken",fcmtoken);
        editor.commit();
    }
    public void setgcmmasterId(String id){
        SharedPreferences pref = this.mcontext.getSharedPreferences(MYPREF, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("gcm_master_id",id);
        editor.commit();
    }
    public String getgcmmasterId(){
        SharedPreferences sharedpreferences= mcontext.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        String appString=sharedpreferences.getString("gcm_master_id","");
        return appString;
    }
    public String getFCMToken(){
        SharedPreferences sharedpreferences= mcontext.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        String appString=sharedpreferences.getString("fcmtoken","");
        return appString;
    }

    public String getId(){
        String token=getJwtToken();
        String[] jwtParts = token.split("\\.");
        byte[] payloadDecodeByte = Base64.decode(jwtParts[1], Base64.URL_SAFE);
        UserTokenData utd = new UserTokenData();
        try {
            utd.setUserTokenData(new String(payloadDecodeByte, "UTF-8"));
            //return utd.getUserid();
            return ""+utd.getId();
        } catch (Exception e) {
            Log.d("LOG_TAG", e.getMessage());
        }
        //return null;
        return "";

    }
//    public String getName5(){
//        String token=getJwtToken();
//        if(token.length()<=0){
//            return "";
//        }
//
//        JWT parsedJWT = new JWT(token);
//        Claim subscriptionMetaData = parsedJWT.getClaim("userId");
//
//        String parsedValue = subscriptionMetaData.asString();
//        return parsedValue;
//    }
//    public String getName6(){
//        String token=getJwtToken();
//        if(token.length()<=0){
//            return "";
//        }
//        String payload="";
//        try {
//            payload = decoded(token);
//            //JWT jwt = new JWT(credentials.getIdToken());
//            return payload;
//        }catch(Exception e){
//
//        }
//        return "";
//    }




    public static String decoded(String JWTEncoded) throws Exception {
        try {
            split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
            Log.d("JWT_DECODED", "Signiture: " + getJson(split[2]));
        } catch (UnsupportedEncodingException e) {
            //Error
        }
        return getJson(split[1]);
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

}
