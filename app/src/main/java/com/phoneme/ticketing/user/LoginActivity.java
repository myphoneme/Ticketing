package com.phoneme.ticketing.user;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.phoneme.ticketing.MainActivity;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.UserAuth;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.user.network.PhonePostResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText phone;
    private Button button;
    private String phonenumber=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserAuth userAuth=new UserAuth(this);

        if(userAuth.isJwtTokenValid()){
            Intent intent1=new Intent(this, MainActivity.class);
//            Intent intent1=new Intent(this, RegistrationActivity.class);
            startActivity(intent1);
            finish();
        }
        phone=(EditText)findViewById(R.id.phone_number);
        button=(Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Button clicked", Toast.LENGTH_SHORT).show();
                phonenumber=phone.getText().toString();
                postLoginData(phonenumber);
            }
        });
    }

    private void postLoginData(String phone){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        HashMap<String, String> map=new HashMap<>();
        map.put("phone",phone);
        Call<PhonePostResponse> call=service.postPhone(map);
        call.enqueue(new Callback<PhonePostResponse>() {
            @Override
            public void onResponse(Call<PhonePostResponse> call, Response<PhonePostResponse> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null && response.body()!=null && response.body().getMessage()!=null)
                    Toast.makeText(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_LONG).show();
                    ActivityStart();
                }
            }

            @Override
            public void onFailure(Call<PhonePostResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error:"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void ActivityStart(){
        Intent intent=new Intent(this, OTPVerificationActivity.class);
        intent.putExtra("phone",phonenumber);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(this, "loginc dashboardfragmentonViewCreated configuration changed called", Toast.LENGTH_LONG).show();
        int currentOrientation = getResources().getConfiguration().orientation;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape
            Toast.makeText(this, "login dashboardfragmentonViewCreated landscape", Toast.LENGTH_LONG).show();
        }
        else {
            // Portrait
            Toast.makeText(this, "login dashboardfragmentonViewCreated portrait", Toast.LENGTH_LONG).show();

        }
    }
}
