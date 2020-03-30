package com.phoneme.ticketing.user;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.phoneme.ticketing.MainActivity;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.user.UserAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {
    private Button registrationButton,skipButton;
    private EditText name,mobileNumber,weight,height,dob;
    private String dobString,mobileString,nameString,weightString,heightString;
    private ArrayList<Calendar> alldates = new ArrayList<Calendar>();
    private String[]  vaccine_months,blood_group,gender_group,yesno_group;
    private ImageView skipIcon;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    private Spinner bloodgroup,gender,smoke,drink;
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        gender=(Spinner)findViewById(R.id.gender_group);
        smoke=(Spinner)findViewById(R.id.spinner_smoke);
        drink=(Spinner)findViewById(R.id.spinner_drink);
        skipIcon=(ImageView)findViewById(R.id.icon_skip);
        yesno_group=getResources().getStringArray(R.array.yesno);
        blood_group=getResources().getStringArray(R.array.blood_group);
        gender_group = getResources().getStringArray(R.array.sex);
        bloodgroup = (Spinner) findViewById(R.id.editText4);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, blood_group);
        bloodgroup.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, yesno_group);
        smoke.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, yesno_group);
        drink.setAdapter(adapter3);

        myCalendar = Calendar.getInstance();
        skipIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity(true);
            }
        });
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        name=(EditText)findViewById(R.id.edit_registration_name);
        registrationButton=(Button)findViewById(R.id.button);
        skipButton=(Button)findViewById(R.id.button_skip);
        mobileNumber=(EditText)findViewById(R.id.edit_mobile);
        dob=(EditText)findViewById(R.id.edit_registration_dob);

        weight=(EditText)findViewById(R.id.edit_registration_weight);
        height=(EditText)findViewById(R.id.edit_registration_height);

        dob.setText(sdf.format(myCalendar.getTime()));
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //alldates.clear();
                updateLabel(year,monthOfYear,dayOfMonth);

            }

            private void updateLabel(int year,int monthOfYear,int dayOfMonth) {
                Log.v("TAG","MONTH:"+monthOfYear);
                String temp;
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                //Toast.makeText(getApplicationContext(), "My calendar"+myCalendar.getTime(), Toast.LENGTH_LONG).show();
                dob.setText(sdf.format(myCalendar.getTime()));
                //individual_vaccine_dates="";
//                for(int k=0;k<vaccine_months.length;k++)
//                {
//                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                    myCalendar.set(Calendar.YEAR, year);
//                    myCalendar.set(Calendar.MONTH, monthOfYear);
//
//                    int temp1=Math.round(Float.parseFloat(vaccine_months[k])*30);
//                    Log.i("temp1",""+temp1);
//
//                    myCalendar.add(Calendar.DATE,temp1);
//                    temp=sdf.format(myCalendar.getTime());
//                    //&&01/10/2016&&D&&||&&01/11/2016&&P&&||&&01/12/2016&&P&&||&&01/07/2016&&D&&||&&01/04/2016&&D&&||&&01/02/2016&&P&&||&&01/01/2016&&D&&||&&01/12/2016&&D&&||&&01/12/2016&&P&&
//                   // individual_vaccine_dates+="&&"+temp+"&&P&&%%";
//
//                    myCalendar.set(Calendar.HOUR_OF_DAY, 13);
//                    myCalendar.set(Calendar.MINUTE, 12);
//                    Log.i("fog","for log"+sdf.format(myCalendar.getTime()));
//
//                    Calendar cal = Calendar.getInstance();
//                    cal = (Calendar)myCalendar.clone();
//                    alldates.add(cal);
//                    //For Local Notifications
//
//
//                }


//                individual_weight="";
//                individual_height="";


//                for(int k=0;k<baby_monthsnumbers.length;k++)
//                {
//
//                    //&&1.34&&||&&5.66&&||&&7.88&&||&&01/07/2016&&D&&||&&01/04/2016&&D&&||&&01/02/2016&&P&&||&&01/01/2016&&D&&||&&01/12/2016&&D&&||&&01/12/2016&&P&&
//                    individual_weight+="0&&";
//                    individual_height+="0&&";
//
//                }


                //Log.i("Final date String",individual_vaccine_dates);
                //Log.i("Weight",individual_weight);
                //Log.i("Height",individual_height);


            }

        };
        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog dialog=  new DatePickerDialog(RegistrationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                dialog.show();

                ;
                // Toast.makeText(getApplicationContext(), "In On click", Toast.LENGTH_LONG).show();
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dobString=dob.getText().toString();
                mobileString=mobileNumber.getText().toString();
                nameString=name.getText().toString();
                weightString=weight.getText().toString();
                heightString=height.getText().toString();
                int bloodgroupvalue= bloodgroup.getSelectedItemPosition();
                int gendergroupvalue=gender.getSelectedItemPosition();
                String gendertext=getResources().getStringArray(R.array.sex)[gendergroupvalue];
                String bloodgrouptext=getResources().getStringArray(R.array.blood_group)[bloodgroupvalue];
                String work=new String();
                String smoketext=getResources().getStringArray(R.array.yesno)[smoke.getSelectedItemPosition()];;
                String drinktext=getResources().getStringArray(R.array.yesno)[drink.getSelectedItemPosition()];;
                Toast.makeText(getApplicationContext(),nameString+" "+weightString+" "+heightString+" "+mobileString+" "+dobString+" "+bloodgrouptext+" "+gendertext, Toast.LENGTH_LONG).show();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar dob = Calendar.getInstance();
                    dob.setTime(sdf.parse(dobString));
                    int age=getAge(dob);
                    Toast.makeText(getApplicationContext(),age+"", Toast.LENGTH_LONG).show();
                    String appstring="NAME:"+nameString+",MOBILE:"+mobileString+",GENDER:"+gendertext+",AGE:"+age+",HEIGHTF:"+heightString+",HEIGHTI:"+heightString+",WEIGHTF:"+weightString+",WORK:"+work+",SMOKE:"+smoketext+",DRINK:"+drinktext;
                    Toast.makeText(getApplicationContext(),appstring, Toast.LENGTH_LONG).show();
                    saveInSharedPreferences(appstring);
                    startMainActivity(false);
                }catch (ParseException e){

                }catch(Exception e){

                }
                //LocalDate birthdate = LocalDate.parse(dobString);
                //LocalDate now = new LocalDate();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity(true);
            }
        });
    }
    private void saveInSharedPreferences(String appstring){
        UserAuth userAuth=new UserAuth(this);
        userAuth.setAppString(appstring);
    }
    private void startMainActivity(Boolean skip){
        Intent intent1;
        if(skip) {
            intent1 = new Intent(this, MainActivity.class);
            intent1.putExtra("registration",false);
        }else{
            intent1 = new Intent(this, MainActivity.class);
            intent1.putExtra("registration",true);
//            UserAuth userAuth=new UserAuth(this);
//            if(userAuth.isFcmTokenUploaded()){
//                updateUserData();
//            }
        }
        startActivity(intent1);
        finish();
    }

    private void updateUserData(){

    }

    // Returns age given the date of birth
    public static int getAge(Calendar dob) throws Exception {
        Calendar today = Calendar.getInstance();

        int curYear = today.get(Calendar.YEAR);
        int dobYear = dob.get(Calendar.YEAR);

        int age = curYear - dobYear;

        // if dob is month or day is behind today's month or day
        // reduce age by 1
        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = dob.get(Calendar.MONTH);
        if (dobMonth > curMonth) { // this year can't be counted!
            age--;
        } else if (dobMonth == curMonth) { // same month? check for day
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = dob.get(Calendar.DAY_OF_MONTH);
            if (dobDay > curDay) { // this year can't be counted!
                age--;
            }
        }

        return age;
    }
}
