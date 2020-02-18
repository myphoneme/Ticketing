package com.phoneme.ticketing;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.helper.SavedUserData;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.phoneme.ticketing.UserAuth;
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final int MY_PERMISSIONS_REQUEST_CODE = 1;
    private NavController navController;
    private DrawerLayout drawer;
    private UserAuth userAuth;
    String token=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userAuth=new com.phoneme.ticketing.UserAuth(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        getUserData(navigationView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_ticketing, R.id.nav_techsupportdashboard,
                R.id.nav_company, R.id.nav_project, R.id.nav_ticket_add, R.id.nav_project_add, R.id.nav_user_list, R.id.nav_company_add, R.id.nav_company_edit, R.id.nav_user_add, R.id.nav_infra_monitoring, R.id.nav_techsupport_user_productivity_detail, R.id.nav_user_profile, R.id.nav_share, R.id.nav_logout, R.id.nav_ticketing_view)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        NavigationView nav = (NavigationView)findViewById(R.id.nav_view);

        Menu menu = nav.getMenu();
        if(userAuth.getRole().equals("2")){

            menu.removeItem(R.id.nav_project_add);
            menu.removeItem(R.id.nav_project);
            menu.removeItem(R.id.nav_techsupportdashboard);
            menu.removeItem(R.id.nav_company);
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        userAuth.saveFCMToken(token);
                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("TAG", token);
                        //Toast.makeText(MainActivity.this,"fcmtokenra="+token, Toast.LENGTH_SHORT).show();

                        Boolean registration_done=getIntent().getBooleanExtra("registration",false);
                        if(!userAuth.isFcmTokenUploaded()){
                            Toast.makeText(MainActivity.this,"uploading="+token, Toast.LENGTH_SHORT).show();
                            //uploadFcmToken(token);
                            if (checkPermissions()) {
                                //startApplication();
                                HashMap<String, String> map=setData(token);
                                uploadFcmToken2(map);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("msg");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        setPermissions();
                                    }
                                });
                                builder.show();
                            }


                            //checkPermissions();

                        }else{
                            Toast.makeText(MainActivity.this,"uploadedalreadyfcmtokenra="+token, Toast.LENGTH_LONG).show();
                            Log.d("fcmtoken",token);
                        }
                    }
                });

//        Boolean registration_done=getIntent().getBooleanExtra("registration",false);
//        if(registration_done && userAuth.isFcmTokenUploaded()){
//            if (checkPermissions()) {
//                //startApplication();
//                HashMap<String,String> map=setData(token);
//                uploadFcmToken2(map);
//            } else {
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setMessage("msg");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        setPermissions();
//                    }
//                });
//                builder.show();
//            }
//        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(this, "dashboardfragmentonViewCreated configuration changed called", Toast.LENGTH_LONG).show();
        int currentOrientation = getResources().getConfiguration().orientation;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape
            Toast.makeText(this, "dashboardfragmentonViewCreated landscape", Toast.LENGTH_LONG).show();
        }
        else {
            // Portrait
            Toast.makeText(this, "dashboardfragmentonViewCreated portrait", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();

//        Intent a = new Intent(Intent.ACTION_MAIN);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);
    }

    private HashMap<String, String> setData(String token){
        HashMap<String, String> map=new HashMap<>();
        map.put("token",token);
        map.put("callerid","calleiddata");
        map.put("country","countrydata");
        map.put("software","softwaredata");
        map.put("device","devicedata");
        //map.put("manu","manudata");
        map.put("manu",android.os.Build.MANUFACTURER);
        //map.put("name","mapdata");
       //map.put("name", android.os.Build.MODEL);
        map.put("name", userAuth.getName());
        map.put("simid","simiddata");
        map.put("appstring",userAuth.getAppString());
        map.put("phone","phonedata");
        map.put("app","appdata");
//        map.put("imei","imeidata");
        map.put("imei",getImeiData());
        Toast.makeText(getApplicationContext(),"userdata manu="+android.os.Build.MANUFACTURER, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"userdata name="+android.os.Build.MODEL, Toast.LENGTH_SHORT).show();
        return map;
    }
    private void uploadFcmToken2(final HashMap<String, String> map){

        GetDataService service= RetrofitClientInstance.APISetupScalars(this).create(GetDataService.class);//This one to get String response
        //GetDataService service= RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        HashMap<String,String> map=new HashMap<>();
//        map.put("token",token);
        Toast.makeText(MainActivity.this,"uploadFcmToken2="+map.get("token"), Toast.LENGTH_SHORT).show();
        Call<String> call=service.postCompletUserData(map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body()!=null ){
                    String res=response.body().toString();//Call Sent successful

                    //if(res.equals("Call Sent")){
                    if(res.startsWith("Call Sent successful")){//This line needs to be modified
                        UserAuth userAuth=new UserAuth(getApplicationContext());
                        userAuth.setTokenUploaded(true);
                        String id=getId(res);
                        userAuth.setgcmmasterId(id);
                            Toast.makeText(MainActivity.this,"afteruploading="+res, Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this,"afteruploading="+map.get("token"), Toast.LENGTH_SHORT).show();
                        Log.d("fcmtoken",map.get("token"));
                    }else{
                        UserAuth userAuth=new UserAuth(getApplicationContext());
                        userAuth.setTokenUploaded(false);
                        Toast.makeText(MainActivity.this,"Token Not uploaded="+map.get("token"), Toast.LENGTH_SHORT).show();
                        Log.d("fcmtoken",map.get("token"));
                    }

                }else{
                    UserAuth userAuth=new UserAuth(getApplicationContext());
                    userAuth.setTokenUploaded(false);
                    Toast.makeText(MainActivity.this,"Token Not uploaded="+map.get("token"), Toast.LENGTH_LONG).show();
                    Log.d("fcmtoken",map.get("token"));

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UserAuth userAuth=new UserAuth(getApplicationContext());
                userAuth.setTokenUploaded(false);
                Toast.makeText(MainActivity.this,"Token Not uploadederror="+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error","errorra:"+t.getMessage());
            }
        });

        //UserAuth userAuth=new UserAuth(getApplicationContext());
        //userAuth.setTokenUploaded(true);

    }

    private String getId(String text){
        int index=text.indexOf('=');
        String id=text.substring(index+1);
        return "";
    }
//    private void uploadFcmToken(final String token){
//
//        GetDataService service= RetrofitClientInstance.APISetup(this).create(GetDataService.class);
//        //GetDataService service= RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        HashMap<String,String> map=new HashMap<>();
//        map.put("token",token);
//        Call<FCMTokenUploadResponse> call=service.postFCMToken(map);
//        call.enqueue(new Callback<FCMTokenUploadResponse>() {
//            @Override
//            public void onResponse(Call<FCMTokenUploadResponse> call, Response<FCMTokenUploadResponse> response) {
//                if(response.isSuccessful() && response.body()!=null && response.body().isTokenUploaded()){
//                    UserAuth userAuth=new UserAuth(getApplicationContext());
//                    userAuth.setTokenUploaded(true);
//                    Toast.makeText(MainActivity.this,"afteruploading="+token, Toast.LENGTH_SHORT).show();
//                }else{
//                    UserAuth userAuth=new UserAuth(getApplicationContext());
//                    userAuth.setTokenUploaded(false);
//                    Toast.makeText(MainActivity.this,"Token Not uploaded="+token, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FCMTokenUploadResponse> call, Throwable t) {
//                UserAuth userAuth=new UserAuth(getApplicationContext());
//                userAuth.setTokenUploaded(false);
//                Toast.makeText(MainActivity.this,"Token Not uploadederror="+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //UserAuth userAuth=new UserAuth(getApplicationContext());
//        //userAuth.setTokenUploaded(true);
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Implement sharepreferences saved data here
    private void getUserData(NavigationView navigationView){
        View hView =  navigationView.getHeaderView(0);
        TextView name=(TextView)hView.findViewById(R.id.username);
        TextView email=(TextView)hView.findViewById(R.id.email);
        ImageView userImage=(ImageView)hView.findViewById(R.id.imageView);

        UserAuth userAuth=new UserAuth(this);
        final String userid=userAuth.getId();
        //String userName=userAuth.getName2();
        //String userName3=userAuth.getName3();
        SavedUserData userData=new SavedUserData();

        //name.setText(userData.getName());
        //name.setText(userName3);
        name.setText(""+userAuth.getName());
        //name.setText(userAuth.getJwtToken());
        email.setText(userAuth.getEmail());
        //Picasso.get().load(userData.getImage()).into(userImage);


        SimpleDraweeView posterImage = (SimpleDraweeView) hView.findViewById(R.id.imagedrawee);

        //Uri imgURI = Uri.parse(userData.getImage());
        Uri imgURI = Uri.parse(userAuth.getImage());
        posterImage.setImageURI(imgURI);
        posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();

                args.putString("user_id",userid);
                drawer.closeDrawer((int) Gravity.LEFT);
                navController.navigate(R.id.nav_user_profile,args);
            }
        });

    }
//
//    private void getUserData2(NavigationView navigationView){
//        SavedUserData userData=new SavedUserData();
//        View hView =  navigationView.getHeaderView(0);
//        TextView name=(TextView)hView.findViewById(R.id.username);
//        TextView email=(TextView)hView.findViewById(R.id.email);
//        ImageView userImage=(ImageView)hView.findViewById(R.id.imageView);
//        name.setText(userData.getName());
//        email.setText(userData.getEmail());
//        Picasso.get().load(userData.getImage()).into(userImage);
//
//
//        SimpleDraweeView posterImage = (SimpleDraweeView) hView.findViewById(R.id.imagedrawee);
//        Uri imgURI = Uri.parse(userData.getImage());
//        posterImage.setImageURI(imgURI);
//
//    }

    private String getImeiData(){
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Toast.makeText(MainActivity.this,"Imei Number="+telephonyManager.getDeviceId().toString(), Toast.LENGTH_LONG).show();
            return telephonyManager.getDeviceId().toString();
        }catch(SecurityException e){
            Log.d("securityexception",e.getMessage());
        }
        return "";
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSIONS_REQUEST_CODE) {
            return;
        }
        boolean isGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }

        if (isGranted) {
            //startApplication();
            getImeiData();
        } else {
            Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();

        }
    }

    private void setPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_PHONE_STATE        }, MY_PERMISSIONS_REQUEST_CODE);
    }



}
