package com.phoneme.ticketing.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.UserAuth;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.user.network.OTPVerifactionResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import android.content.Editor;

public class OTPVerificationActivity extends AppCompatActivity {
    private TextView phone;
    private EditText otp;
    private Button button;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verifcation);
        Intent intent=getIntent();
        final String phonenumber=intent.getStringExtra("phone");
        phone=(TextView)findViewById(R.id.phone_number);
        phone.setText(phonenumber);

        otp=(EditText)findViewById(R.id.otp);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otpnumber=otp.getText().toString();
                postOtp(otpnumber,phonenumber);
            }
        });
    }
    private void postOtp(String otp, String phone){
        Toast.makeText(getApplicationContext(),"postOTP", Toast.LENGTH_LONG).show();
        HashMap<String, String> map=new HashMap<>();
        map.put("otp",otp);
        map.put("phone",phone);
        GetDataService service= RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<OTPVerifactionResponse> call=service.postOTP(map);
        Toast.makeText(getApplicationContext(),"postOTP2", Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<OTPVerifactionResponse>() {
            @Override
            public void onResponse(Call<OTPVerifactionResponse> call, Response<OTPVerifactionResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    if( response.body().isOtpVerified() && response.body().getJwttoken()!=null && !response.body().getJwttoken().isEmpty() && response.body().getJwttoken().length()>0){
                        Toast.makeText(getApplicationContext(),"Otp matches.You're logged in", Toast.LENGTH_LONG).show();
                        setDatainSharedPreferences(response.body().getJwttoken());
                        ActivityStart();
                    }else{
                        Toast.makeText(getApplicationContext(),"OTP doesn't match", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OTPVerifactionResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"postOTP throwable "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void ActivityStart(){
//        Intent intent=new Intent(this, MainActivity2.class);
        Intent intent=new Intent(this, RegistrationActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    private void setDatainSharedPreferences(String token){
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//        Editor editor = pref.edit();
//        editor.putString("jwttoken",token);
//        editor.commit();

        UserAuth userAuth=new UserAuth(this);
        userAuth.setJwtToken(token);
    }

}