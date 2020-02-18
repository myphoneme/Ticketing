package com.phoneme.ticketing.ui.user.fragments;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.UserAuth;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.user.network.UserAddPostResponse;


import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.phoneme.ticketing.ui.user.fragments.UserProfileFragment.MULTIPART_FORM_DATA;

public class UserCreateFragment extends Fragment implements
        AdapterView.OnItemSelectedListener{

    private EditText username, mobile,email,designation;

    private SimpleDraweeView userimage;
    private Button submit;
    private Spinner spin;
    private String[] status = {"Active", "Inactive"};
    private String statustext;
    private String imagePath=new String();
    private boolean imageSelected = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_user_create, container, false);

        return root;
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        UserAuth userAuth = new UserAuth(getContext());

        username = (EditText) view.findViewById(R.id.name);
        email = (EditText) view.findViewById(R.id.email);
        mobile = (EditText) view.findViewById(R.id.mobile_number);
        userimage = (SimpleDraweeView) view.findViewById(R.id.user_image);
        submit = (Button) view.findViewById(R.id.submit);
        spin = (Spinner) view.findViewById(R.id.spinner);
        designation=(EditText)view.findViewById(R.id.designation);
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Submitted", Toast.LENGTH_SHORT).show();
                //  if(profileUserId.equals(userid)){
                String name = username.getText().toString();
                String designation_title=designation.getText().toString();
                String mobilenum = mobile.getText().toString();
                String status_new = statustext;
                //String userid = userDataFromServer.getId();
                String useremail =  email.getText().toString();
//                    postUpdateWithouImage(name, mobilenum, status_new, userid, email);//Used when post without image
                HashMap<String,RequestBody> map= new HashMap<>();
                RequestBody fname=createPartFromString(name);
                map.put("fname",fname);

                RequestBody designation_body=createPartFromString(designation_title);
                map.put("designation",designation_body);
                RequestBody Mobile_no=createPartFromString(mobilenum);
                map.put("Mobile_no",Mobile_no);
                RequestBody status=createPartFromString(status_new);
                map.put("status",status);
                RequestBody emailbody=createPartFromString(useremail);
                map.put("email",emailbody);

                //We shouldn't need either of below 2 lines because jwt tokens contain id which will be verified in backend
                //map.put("id",userid);
                //map.put("id",profileUserId);

                //postUpdateWithImage(map);
                postUserAddedWithImage(map);
                //}
            }
        });
    }
    public void postUserAddedWithImage(HashMap<String,RequestBody> map){
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

        Call<UserAddPostResponse> call;
        if(imageSelected ) {
            call = service.postUserAddWithImage(body, map);
        }else{
            call= service.postUserAddWithoutImage(map);
        }
        Toast.makeText(getContext(),"After call", Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<UserAddPostResponse>() {
            @Override
            public void onResponse(Call<UserAddPostResponse> call, Response<UserAddPostResponse> response) {
                if(response!=null && response.isSuccessful() && response.body()!=null ) {
                    if( response.body().getAdded()){
                        Toast.makeText(getContext(), "Message " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.popBackStack();
                    }else{
                        Toast.makeText(getContext(), "Message failed " + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    }
            }

            @Override
            public void onFailure(Call<UserAddPostResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Message Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == getActivity().RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                userimage.setImageURI(selectedImage);
                imageSelected = true;

                imagePath=getRealPathFromURI(selectedImage);
                //uploadFile(selectedImage, "My Image");
            }
        } catch (Exception e) {
            //Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
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

}
