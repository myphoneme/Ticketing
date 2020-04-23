package com.phoneme.ticketing;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
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
import androidx.core.app.NotificationCompat;
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
import com.phoneme.ticketing.ui.ticketing.fragments.TicketMyListFragment;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;
import com.phoneme.ticketing.ui.ticketing.network.TicketResponse;
import com.phoneme.ticketing.ui.ticketing.sorting.TicketCreatedByCompare;
import com.phoneme.ticketing.ui.ticketing.sorting.TicketCreatedTimeCompare;
import com.phoneme.ticketing.user.UserAuth;
import com.phoneme.ticketing.user.network.GCMMASTERADDEDResponse;
import com.phoneme.ticketing.user.network.GCM_Master_Data_Response;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final int MY_PERMISSIONS_REQUEST_CODE = 1;
    private NavController navController;
    private DrawerLayout drawer;
    private UserAuth userAuth;
    String token = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userAuth = new UserAuth(getApplicationContext());
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
                R.id.nav_dashboard, R.id.nav_my_ticketing, R.id.nav_ticketing, R.id.nav_techsupportdashboard,
                R.id.nav_company, R.id.nav_project_engagement, R.id.nav_project_list, R.id.nav_ticket_add, R.id.nav_project_add, R.id.nav_user_list, R.id.nav_company_add, R.id.nav_company_edit, R.id.nav_user_add, R.id.nav_infra_monitoring, R.id.nav_techsupport_user_productivity_detail, R.id.nav_user_profile, R.id.nav_share, R.id.nav_logout, R.id.nav_ticketing_view)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = nav.getMenu();
        if (!userAuth.getRole().equals("0")) {
            menu.removeItem(R.id.nav_company);
        }
        if (userAuth.getRole().equals("2")) {

            menu.removeItem(R.id.nav_project_add);
            menu.removeItem(R.id.nav_my_ticketing);
            //menu.removeItem(R.id.nav_project);
            //menu.removeItem(R.id.nav_techsupportdashboard);
            //menu.removeItem(R.id.nav_company);
        }
        if (userAuth.getRole().equals("1")) {
            menu.removeItem(R.id.nav_project_engagement);
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

                        Boolean registration_done = getIntent().getBooleanExtra("registration", false);
                        //if(!userAuth.isFcmTokenUploaded()){
                        if (userAuth.getGCMMASTERId() == null || userAuth.getGCMMASTERId().length() == 0 || userAuth.getGCMMASTERId().equalsIgnoreCase("0") || userAuth.getGCMMASTERId().equalsIgnoreCase("null")) {
                            //Toast.makeText(MainActivity.this,"uploading="+token, Toast.LENGTH_SHORT).show();
                            //uploadFcmToken(token);
                            if (checkPermissions()) {
                                //startApplication();
                                HashMap<String, String> map = setData(token);
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

                        } else if (!userAuth.isGCNTokenUpdated()) {
                            HashMap<String, String> map = setData(token);
                            //updateUserDetailsTable(map);
                            map.put("gcm_master_id", userAuth.getGCMMASTERId());
                            Toast.makeText(getApplicationContext(), "hellora-1" + map.get("appstring"), Toast.LENGTH_LONG).show();
                            getGCMMASTERData(userAuth.getGCMMASTERId());
                            //updateGCMMASTERTable(map);
                        } else {
                            Toast.makeText(MainActivity.this, "uploadedalreadyfcmtokenra=" + token, Toast.LENGTH_LONG).show();
                            Log.d("fcmtoken", token);
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

        getMyTicketData();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Toast.makeText(this, "dashboardfragmentonViewCreated configuration changed called", Toast.LENGTH_LONG).show();
        int currentOrientation = getResources().getConfiguration().orientation;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape
            //Toast.makeText(this, "dashboardfragmentonViewCreated landscape", Toast.LENGTH_LONG).show();
        } else {
            // Portrait
            //Toast.makeText(this, "dashboardfragmentonViewCreated portrait", Toast.LENGTH_LONG).show();

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

    private HashMap<String, String> setData(String token) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("callerid", "calleiddata");
        map.put("country", "countrydata");
        map.put("software", "softwaredata");
        map.put("device", "devicedata");
        //map.put("manu","manudata");
        map.put("manu", android.os.Build.MANUFACTURER);
        //map.put("name","mapdata");
        //map.put("name", android.os.Build.MODEL);
        map.put("name", userAuth.getName());
        map.put("simid", "simiddata");
        map.put("appstring", userAuth.getAppString());
        map.put("phone", "phonedata");
        map.put("app", "appdata");
//        map.put("imei","imeidata");
        map.put("imei", getImeiData());
        //Toast.makeText(getApplicationContext(),"userdata manu="+android.os.Build.MANUFACTURER, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),"userdata name="+android.os.Build.MODEL, Toast.LENGTH_SHORT).show();
        return map;
    }

    private void uploadFcmToken2(final HashMap<String, String> map) {

        GetDataService service = RetrofitClientInstance.APISetupScalars(this).create(GetDataService.class);//This one to get String response
        //GetDataService service= RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        HashMap<String,String> map=new HashMap<>();
//        map.put("token",token);
        //Toast.makeText(MainActivity.this,"uploadFcmToken2="+map.get("token"), Toast.LENGTH_SHORT).show();
        Call<String> call = service.postCompletUserData(map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String res = response.body().toString();//Call Sent successful
                    //String res=response.body().getAdded_id();
                    //if(res.equals("Call Sent")){
                    if (res.startsWith("Call Sent successful")) {//This line needs to be modified
                        //if(true){
                        UserAuth userAuth = new UserAuth(getApplicationContext());
                        userAuth.setTokenUploaded(true);

                        String id = getId(res);
                        Log.d("gcmid", "id=" + id);
                        userAuth.setgcmmasterId(id);
                        Toast.makeText(MainActivity.this, "afteruploading id=" + res, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this,"afteruploading="+map.get("token"), Toast.LENGTH_SHORT).show();
                        Log.d("fcmtoken", map.get("token"));
                        updateUserDetailsTable(id);
                    } else {
                        UserAuth userAuth = new UserAuth(getApplicationContext());
                        userAuth.setTokenUploaded(false);
                        Toast.makeText(MainActivity.this, "Token Not uploaded=" + map.get("token"), Toast.LENGTH_SHORT).show();
                        Log.d("fcmtoken", map.get("token"));
                    }

                } else {
                    UserAuth userAuth = new UserAuth(getApplicationContext());
                    userAuth.setTokenUploaded(false);
                    Toast.makeText(MainActivity.this, "Token Not uploaded=" + map.get("token"), Toast.LENGTH_LONG).show();
                    Log.d("fcmtoken", map.get("token"));

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UserAuth userAuth = new UserAuth(getApplicationContext());
                userAuth.setTokenUploaded(false);
                Toast.makeText(MainActivity.this, "Token Not uploadederror=" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error", "errorra:" + t.getMessage());
            }
        });

        //UserAuth userAuth=new UserAuth(getApplicationContext());
        //userAuth.setTokenUploaded(true);

    }

    private String getId(String text) {
        int index = text.indexOf('=');
        String id = text.substring(index + 1);
        return id;
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
    private void getUserData(NavigationView navigationView) {
        View hView = navigationView.getHeaderView(0);
        TextView name = (TextView) hView.findViewById(R.id.username);
        TextView email = (TextView) hView.findViewById(R.id.email);
        ImageView userImage = (ImageView) hView.findViewById(R.id.imageView);
        TextView appVersion = (TextView) hView.findViewById(R.id.app_version);
        appVersion.setText("App Version:" + BuildConfig.VERSION);
        UserAuth userAuth = new UserAuth(this);
        final String userid = userAuth.getId();
        //String userName=userAuth.getName2();
        //String userName3=userAuth.getName3();
        SavedUserData userData = new SavedUserData();

        //name.setText(userData.getName());
        //name.setText(userName3);
        name.setText("" + userAuth.getName());
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

                args.putString("user_id", userid);
                drawer.closeDrawer((int) Gravity.LEFT);
                navController.navigate(R.id.nav_user_profile, args);
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

    private String getImeiData() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            //Toast.makeText(MainActivity.this,"Imei Number="+telephonyManager.getDeviceId().toString(), Toast.LENGTH_LONG).show();
            return telephonyManager.getDeviceId().toString();
        } catch (SecurityException e) {
            Log.d("securityexception", e.getMessage());
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
                Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_CODE);
    }

    private void updateUserDetailsTable(String gcmid) {
        GetDataService service = RetrofitClientInstance.APISetupScalars(this).create(GetDataService.class);//This one to get String response
        HashMap<String, String> map = new HashMap<>();
        map.put("gcm_master_id", gcmid);
        Call<String> call = service.postUpdateUserDataTable(map);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplicationContext(), "updateUserDetailsTable success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "updateUserDetailsTable failed" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateGCMMASTERTable(HashMap<String, String> map) {
        GetDataService service = RetrofitClientInstance.APISetupScalars(this).create(GetDataService.class);//This one to get String response
        Call<String> call = service.postUpdateGCMTable(map);
        //Toast.makeText(getApplicationContext(), "hello"+map.get("appstring"), Toast.LENGTH_LONG).show();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("updateGCMMASTERTable=", "success");
                userAuth.setGCMUpdated(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("updateGCMMASTERTable=", "failed");
            }
        });
    }

    private void getGCMMASTERData(String id) {
        GetDataService service = RetrofitClientInstance.APISetupScalars(this).create(GetDataService.class);//This one to get String response
        Call<String> call = service.getGCMMasterDataForaUser(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    userAuth.setAppString(response.body().toString());
                    Toast.makeText(getApplicationContext(), "hellora000" + response.body().toString(), Toast.LENGTH_LONG).show();
                    HashMap<String, String> map = setData(token);
                    //updateUserDetailsTable(map);
                    //Toast.makeText(getApplicationContext(), "hellora0"+map.get("appstring"), Toast.LENGTH_LONG).show();
                    map.put("gcm_master_id", userAuth.getGCMMASTERId());
                    //Toast.makeText(getApplicationContext(), "hellora1"+map.get("appstring"), Toast.LENGTH_LONG).show();

                    updateGCMMASTERTable(map);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void getMyTicketData() {
        //GetDataService service= RetrofitClientInstance.getRetrofitInstance(getContext()).create(GetDataService.class);//getContext() means apply jwttoken in header
        GetDataService service = RetrofitClientInstance.APISetup(this).create(GetDataService.class);

        Call<TicketResponse> call = service.getMyTickets();
        UserAuth userAuth = new UserAuth(getApplicationContext());
        System.out.println("jwttoken" + userAuth.getJwtToken());
        Toast.makeText(getApplicationContext(), "getMyTicketData1=", Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<TicketResponse>() {
            @Override
            public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                //Toast.makeText(getApplicationContext(), "MaingetMyTicketData1a=" , Toast.LENGTH_LONG).show();
                if (response.isSuccessful()) {
                    //  Toast.makeText(getApplicationContext(), "MaingetMyTicketData2=" , Toast.LENGTH_LONG).show();

                    System.out.println("Response successfull ra\n" + response.body().getListOfTickets());
                    AnalyzeData(response.body().getListOfTickets());
                    //ticketModelList.clear();
                    //ticketModelList = response.body().getListOfTickets();
//                    setRecyclerView(response.body().getListOfTickets());
                    //progressbarlayout.setVisibility(View.GONE);
                    //setRecyclerView(ticketModelList);
                    //adapter.notifyDataSetChanged();

                } else {
                    //progressbarlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TicketResponse> call, Throwable t) {
                //Toast.makeText(getContext(), "onFailure " + t.getMessage(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getContext(),"onFailure"+t.getCause().getMessage(),Toast.LENGTH_LONG).show();
                System.out.println("ticketlistfragment onFailure " + t.getMessage());
                //progressbarlayout.setVisibility(View.GONE);
            }
        });
    }

    private void AnalyzeData(List<TicketModel> listofTickets) {
        if (listofTickets != null && !listofTickets.isEmpty() && listofTickets.size() > 0) {
            TicketCreatedTimeCompare ticketCreatedtimeCompare = new TicketCreatedTimeCompare();
            Collections.sort(listofTickets, Collections.reverseOrder(ticketCreatedtimeCompare));
            Toast.makeText(getApplicationContext(), "Analyze size=" + listofTickets.size(), Toast.LENGTH_LONG).show();
            for (int i = 0; i < listofTickets.size(); i++) {
                if (listofTickets.get(i).getStatus().equalsIgnoreCase("1")) {
                    //Toast.makeText(getApplicationContext(), "time="+getTime_ago(listofTickets.get(i).getCreated_at()), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "timedays=" + getDays(listofTickets.get(i).getCreated_at()), Toast.LENGTH_LONG).show();
                    int dayspassed = Integer.parseInt(getDays(listofTickets.get(i).getCreated_at()));
                    if (listofTickets.get(i).getPriority().equalsIgnoreCase("low")) {
                        Toast.makeText(getApplicationContext(), "priority=low", Toast.LENGTH_LONG).show();
                        if (dayspassed > 3) {
                            //showNotification(dayspassed-3,listofTickets.get(i));
                        }
                    } else if (listofTickets.get(i).getPriority().equalsIgnoreCase("medium")) {
                        Toast.makeText(getApplicationContext(), "priority=medium", Toast.LENGTH_LONG).show();
                        if (dayspassed > 5) {
                            //showNotification(dayspassed-5,listofTickets.get(i));
                        }
                    } else if (listofTickets.get(i).getPriority().equalsIgnoreCase("high")) {
                        Toast.makeText(getApplicationContext(), "priority=high", Toast.LENGTH_LONG).show();
                        if (dayspassed > 7) {
                            //showNotification(dayspassed-7,listofTickets.get(i));
                        }
                    }
                }
            }

        }
    }

    private void showNotification(int extradays,TicketModel ticketModel){

//
//
//        //int type=getSharedPreferences("login_info",MODE_PRIVATE).getInt("usertype",-1);
//        int type=2;
//
////        Map<String, String> data = remoteMessage.getData();
////        String body = data.get("body");
////        String title = data.get("title");
//
//
//        String body="body";
//        String title="title";
//
//        Intent intent;
//        intent = new Intent(getApplicationContext(), TicketMyListFragment.class);
////        if(type==2){
////            intent = new Intent(getApplicationContext(), ViewResponses.class);
////        }
////        else
////            intent = new Intent(getApplicationContext(), OldQuotation.class);
//
//        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 101, intent, 0);
//
//        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//
//        NotificationChannel channel = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//
//            AudioAttributes att = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
//                    .build();
//
//            channel = new NotificationChannel("222", "my_channel", NotificationManager.IMPORTANCE_HIGH);
//            nm.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(
//                        getApplicationContext(), "222")
//                        .setContentTitle(title)
//                        .setAutoCancel(true)
//                        .setLargeIcon(((BitmapDrawable)getDrawable(R.drawable.ticketingicon)).getBitmap())
//                        .setSmallIcon(R.drawable.ticketingicon)
//                        //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.electro))
//                        .setContentText(body)
//                        .setSmallIcon(R.drawable.ticketingicon)
//                        .setContentIntent(pi)
//                ;
//
//        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
//        nm.notify(101, builder.build());

    }
    private String getTime_ago(String date) {

        //String sDate1="31/12/1998";
        //"2019-12-04 12:18:45"
        //HH:mm:ss
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            //Date currentDate=new Date();

            PrettyTime p = new PrettyTime();
            return p.format(date1);
//            System.out.println(p.format(date1));
        } catch (ParseException e) {

        }
        return "";
    }

    private String getDays(String datera2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2;
        try {
            date2 = df.parse(datera2);
            Date date1 = new Date();
            //Date date1=df.parse(new Date());
            long diff = date1.getTime() - date2.getTime();
            return "" + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (Exception e) {

        }

//        long daysBetween = Duration.between(date1, date2).toDays();
        return "";
    }
}
