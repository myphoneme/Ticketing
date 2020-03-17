package com.phoneme.ticketing.ui.user.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.UserAuth;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.helper.SavedUserData;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.user.UserModel;
import com.phoneme.ticketing.ui.user.network.UserEditGetResponse;
import com.phoneme.ticketing.ui.user.network.UserEditResponse;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class UserProfileFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {
    private EditText username, mobile,designation;
    private TextView email,name_txtview,designation_txtview,ticket_oepn,tickets_closed,tickets_assigned,mobile_number,email_value,my_tickets;
    private SavedUserData userData;
    private SimpleDraweeView userimage;
    private Button submit;
    private String[] status = {"Active", "Inactive"};
    private String statustext;
    private Spinner spin;
    private UserModel userDataFromServer;
    ImageButton imageButton;
    private Uri mCropImageUri;

    Bitmap bitmap;
    private static final int REQUEST_CODE_READ_EXTERNAL_PERMISSION = 2;
    String image_name;
    private String imagePath=new String();
    private String userid;
    private boolean imageSelected = false;
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_profile_new_yash_design, container, false);
        userData = new SavedUserData();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        try {
            userid = getArguments().getString("user_id");
//        }catch(Exception e){
//
//        }
        //String id=savedInstanceState.getString("user_id");
        UserAuth userAuth = new UserAuth(getContext());
        username = (EditText) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        mobile = (EditText) view.findViewById(R.id.mobile_number);
        userimage = (SimpleDraweeView) view.findViewById(R.id.user_image);
        submit = (Button) view.findViewById(R.id.submit);
        spin = (Spinner) view.findViewById(R.id.spinner);
        designation=(EditText)view.findViewById(R.id.designation);
        name_txtview=(TextView)view.findViewById(R.id.name_text_view);
        designation_txtview=(TextView)view.findViewById(R.id.designation_text_view);
        ticket_oepn=(TextView)view.findViewById(R.id.tickets_open);
        tickets_closed=(TextView)view.findViewById(R.id.tickets_closed);
        tickets_assigned = (TextView)view.findViewById(R.id.tickets_assigned);
        mobile_number=(TextView)view.findViewById(R.id.mobile_number_value);
        email_value=(TextView)view.findViewById(R.id.email_value);
        my_tickets=(TextView)view.findViewById(R.id.my_tickets);
        imageButton = (ImageButton) view.findViewById(R.id.quick_start_cropped_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
            }
        });
        getUserData(userid);
        username.setEnabled(false);
        mobile.setEnabled(false);
        if (userid.equals(userAuth.getId())||userAuth.getRole().equalsIgnoreCase("0")) {
//            userimage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(galleryIntent, 0);
//                }
//            });
            userimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSelectImageClick(view);
                }
            });

            username.setEnabled(true);
            mobile.setEnabled(true);
            spin.setOnItemSelectedListener(this);
        }
        my_tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args2 = new Bundle();
                args2.putString("user_id", userid);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_ticketing,args2);
            }
        });


        tickets_assigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ticket_oepn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tickets_closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


//set from fixed data
//        username.setText(userData.getName());
//        email.setText(userData.getEmail());
//        mobile.setText(userData.getMobile());
//        Uri uri=Uri.parse(userData.getImage());
//        userimage.setImageURI(uri);

/*        username.setText(userDataFromServer.getName());
        email.setText(userDataFromServer.getEmail());
        mobile.setText(userDataFromServer.getMobile_no());
        Uri uri=Uri.parse(userData.getImage());//This need to change as user image is set from local data
        userimage.setImageURI(uri);*/
        final String profileUserId = userAuth.getId();
        if (userid.equals(userAuth.getId())) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Submitted", Toast.LENGTH_SHORT).show();
                    //  if(profileUserId.equals(userid)){
                    String name = username.getText().toString();
                    String designation_title=designation.getText().toString();
                    String mobilenum = mobile.getText().toString();
                    String status_new = statustext;
                    String userid = userDataFromServer.getId();
                    String email = userDataFromServer.getEmail();
//                    postUpdateWithouImage(name, mobilenum, status_new, userid, email);//Used when post without image
                    HashMap<String,RequestBody> map= new HashMap<>();
                    RequestBody fname=createPartFromString(name);
                    map.put("fname",fname);
                    RequestBody designationbody=createPartFromString(designation_title);
                    map.put("designation",designationbody);
                    RequestBody Mobile_no=createPartFromString(mobilenum);
                    map.put("Mobile_no",Mobile_no);
                    RequestBody status=createPartFromString(status_new);
                    map.put("status",status);
                    RequestBody emailbody=createPartFromString(email);
                    map.put("email",emailbody);

                    //We shouldn't need either of below 2 lines because jwt tokens contain id which will be verified in backend
                    //map.put("id",userid);
                    //map.put("id",profileUserId);

                    postUpdateWithImage(map);
                    //}
                }
            });
        } else {
            Toast.makeText(getContext(), "You are not allowed to change this.", Toast.LENGTH_SHORT).show();

        }

    }
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(getActivity());
    }
    private void setData(UserModel data) {
        Toast.makeText(getContext(), "setData" + data.getName(), Toast.LENGTH_SHORT).show();
        username.setText(data.getName());
        email.setText(data.getEmail());
        mobile.setText(data.getMobile_no());
        designation.setText(data.getDesignation());
        name_txtview.setText(data.getName());
        designation_txtview.setText(data.getDesignation());
        if (data.getStatus().equals("1")) {
            spin.setSelection(0);
        } else {
            spin.setSelection(1);
        }
        Uri uri = Uri.parse(data.getImage());//This need to change as user image is set from local data
        userimage.setImageURI(uri);
        /*if(data.getTickets_status().getOpen()!=null){

        }*/
        ticket_oepn.setText(""+data.getTickets_status().getOpen());
        tickets_closed.setText(""+data.getTickets_status().getClose());
        int totalticketsassigned=data.getTickets_status().getOpen()+data.getTickets_status().getClose()+data.getTickets_status().getWaiting_for_close();
        tickets_assigned.setText(""+totalticketsassigned);
        mobile_number.setText(data.getMobile_no());
        email_value.setText(data.getEmail());
    }

    private void postUpdateWithouImage(String name, String mobilenum, String status_new, String userid, String email) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserEditResponse> call = service.postUserdata2(name, mobilenum, status_new, userid, email);
        //Toast.makeText(getContext(),"beforeenque",Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<UserEditResponse>() {
            @Override
            public void onResponse(Call<UserEditResponse> call, Response<UserEditResponse> response) {
                Toast.makeText(getContext(), response.body().getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserEditResponse> call, Throwable t) {
                Toast.makeText(getContext(), "onfailure after submit" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postUpdateWithImage(HashMap<String,RequestBody> map){
        File file;
        Toast.makeText(getContext(),"postUpdateWithImage 1", Toast.LENGTH_SHORT).show();
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);

        if (imagePath != null && !imagePath.isEmpty()) {
            //String newimagePath = compressImage(imagePath);
            //file = new File(newimagePath);
            file = new File(imagePath);//This one working

        }else{
            file = new File("");
        }
        Toast.makeText(getContext(),"postUpdateWithImage 2", Toast.LENGTH_SHORT).show();
        final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("userfile", file.getName(), requestBody);//these 3 lines extra

        /*
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("Description", description);
        map.put("AboutUS", aboutus);*/
        Call<UserEditResponse> call;
        if(imageSelected ) {
            call = service.postUserdataWithImage(body, map);
        }else{
            call= service.postUserdataWithoutImage(map);
        }
        Toast.makeText(getContext(),"After call", Toast.LENGTH_SHORT).show();

        call.enqueue(new Callback<UserEditResponse>() {
            @Override
            public void onResponse(Call<UserEditResponse> call, Response<UserEditResponse> response) {
                Toast.makeText(getContext(),"Update Successfull new token"+response.body().getJwttoken(), Toast.LENGTH_SHORT).show();
                UserAuth auth=new UserAuth(getContext());
                auth.setJwtToken(response.body().getJwttoken());
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.popBackStack();
            }

            @Override
            public void onFailure(Call<UserEditResponse> call, Throwable t) {
                Toast.makeText(getContext(),"onFailure="+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getActivity().getApplicationContext(), status[position], Toast.LENGTH_LONG).show();
        if (status[position].equals("Active")) {
            statustext = "1";
        } else if (status[position].equals("Inactive")) {
            statustext = "0";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    //@Override
    public void onActivityResult____1(int requestCode, int resultCode, Intent data) {

        System.out.println("imageselectedra0");
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == getActivity().RESULT_OK && null != data) {
                System.out.println("imageselectedra1");
                Uri selectedImage = data.getData();

                /*BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                //BitmapFactory.decodeFile(new File(selectedImage.getPath()).getAbsolutePath(), options);
                BitmapFactory.decodeFile(new File(selectedImage.getPath()).getAbsolutePath(), options);

//                BitmapFactory.decodeFile(selectedImage.getPath().getA, options);
//                String path = selectedImage.getPath().getAbsolutePath();


                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;
                Toast.makeText(getContext(), "Please select equal height "+imageHeight+" "+imageWidth, Toast.LENGTH_SHORT).show();
                if(imageHeight==imageWidth){
                    Toast.makeText(getContext(), "Please select equal height ra", Toast.LENGTH_SHORT).show();
                    //return;
                }*/
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(
                        getContext().getContentResolver().openInputStream(selectedImage),
                        null,
                        options2);
                //Bitmap image = BitmapFactory.decodeStream(this.getContext().getContentResolver().openInputStream(new File(selectedImage.getPath()).getAbsolutePath()), null, null);
                int imageHeight2 = options2.outHeight;
                int imageWidth2 = options2.outWidth;
                Toast.makeText(getContext(), "Please select equal height222 "+imageHeight2+" "+imageWidth2, Toast.LENGTH_SHORT).show();

                Uri imageUri = CropImage.getPickImageResultUri(getContext(), data);
                startCropImageActivity(imageUri);



                System.out.println("imageselectedra2");

                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(selectedImage)
                        .setResizeOptions(new ResizeOptions(50, 50))
                        .build();
                userimage.setController(
                        Fresco.newDraweeControllerBuilder()
                                .setOldController( userimage.getController())
                                .setImageRequest(request)
                                .build());
                userimage.setImageURI(selectedImage);
                System.out.println("imageselectedra3");
                imageSelected = true;
                System.out.println("imageselectedra4");

                imagePath=getRealPathFromURI(selectedImage);
                System.out.println("imageselectedra5");
                //uploadFile(selectedImage, "My Image");
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    ((ImageButton) getView().findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                    Toast.makeText(getContext(), "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(getContext(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            //Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    //@SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getContext(), data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //((ImageButton) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                userimage.setImageURI(result.getUri());
                Toast.makeText(getContext(), "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getContext(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setAspectRatio(1,1)
                .start(getActivity());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(getContext(), "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void getUserData(String id) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);//Might have to change this
        Call<UserEditGetResponse> call = service.getUserData(id);
        call.enqueue(new Callback<UserEditGetResponse>() {
            @Override
            public void onResponse(Call<UserEditGetResponse> call, Response<UserEditGetResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null && response.body().getUserData() != null) {
                    userDataFromServer = response.body().getUserData();
                    Toast.makeText(getContext(), "response success " + userDataFromServer.getName(), Toast.LENGTH_SHORT).show();
                    userid = userDataFromServer.getId();
                    setData(userDataFromServer);
                }
            }

            @Override
            public void onFailure(Call<UserEditGetResponse> call, Throwable t) {
                Toast.makeText(getContext(), "onfailure" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

