package com.phoneme.ticketing.ui.ticketing.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.UserAuth;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.project.model.ProjectModel;
import com.phoneme.ticketing.ui.ticketing.network.TicketCreatGetResponse;
import com.phoneme.ticketing.ui.ticketing.network.TicketCreatePostResponse;
import com.phoneme.ticketing.ui.user.UserModel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class TicketCreateFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private List<String> priorityList=new ArrayList<>();
    private List<ProjectModel> projectList=new ArrayList<>();
    private List<UserModel> possibleUsersForAllocation=new ArrayList<>();
    private Spinner ticket_project_spinner,ticket_priority_spinner;
    private SimpleDraweeView iconUpload;
    private Uri outputUri;
    //private TextView ;
    private Button Submit;
    private EditText ticketDescription,ticketTitle;
    private LinearLayout scrollView;
    private RadioGroup radioGroupUserAllocate;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_ticket_add,container,false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        UserAuth userAuth=new UserAuth(getContext());
        radioGroupUserAllocate=(RadioGroup)view.findViewById(R.id.user_allocate_single_user_radio_group);
        iconUpload=(SimpleDraweeView)view.findViewById(R.id.upload_icon);
        ticketTitle=(EditText)view.findViewById(R.id.ticket_title);
        ticketDescription = (EditText)view.findViewById(R.id.ticket_descriptoin);
        ticket_project_spinner=(Spinner)view.findViewById(R.id.ticket_project_spinner);
        scrollView=(LinearLayout) view.findViewById(R.id.scrollView);

        ticket_project_spinner.setOnItemSelectedListener(this);

        ticket_priority_spinner=(Spinner)view.findViewById(R.id.ticket_priority_spinner);
        Submit =(Button)view.findViewById(R.id.submit);
        ticketTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFullAlertDialog(view);
            }
        });

        ticketDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFullAlertDialog(view);
            }
        });
        if(userAuth.getRole().equals("2")){
            getBasicData();
            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    collectData();
                }
            });
        }else{
            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"You are not allowed to create ticket", Toast.LENGTH_LONG).show();
                }
            });
        }

        iconUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_PICK);
                //intent.putExtra(MediaStore.Files.FileColumns.DISPLAY_NAME, outputUri);
                //startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("*/*");
                startActivityForResult(galleryIntent, 0);
            }
        });

//        ArrayAdapter priorityAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,priorityList);
//        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ticket_priority_spinner.setAdapter(priorityAdapter);
//        ticket_priority_spinner.setSelection(getIndex(ticket_priority_spinner, priorityList.get(0)));
    }

    private void setSpinnerData(){
        ArrayAdapter priorityAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,priorityList);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ticket_priority_spinner.setAdapter(priorityAdapter);
        ticket_priority_spinner.setSelection(getIndex(ticket_priority_spinner, priorityList.get(0)));
        Toast.makeText(getContext(),"priorityList="+priorityList.size(), Toast.LENGTH_LONG).show();
        String[] projectName = new String[this.projectList.size()];
        for(int i=0;i<this.projectList.size();i++){
            projectName[i]=this.projectList.get(i).getName();
        }
        ArrayAdapter projectAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,projectName);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ticket_project_spinner.setAdapter(projectAdapter);
        ticket_project_spinner.setSelection(0);
    }
    private void setUserAllocationRadioButton(){
        if(possibleUsersForAllocation!=null && possibleUsersForAllocation.size()>0) {
            for (int k = 0; k < possibleUsersForAllocation.size(); k++) {
                RadioButton rb=new RadioButton(getContext());
                rb.setText(possibleUsersForAllocation.get(k).getName());
                rb.setTag(possibleUsersForAllocation.get(k).getId());
                radioGroupUserAllocate.addView(rb);
            }
        }
    }
    private void setUserAllocationCheckboxes(){
        if(possibleUsersForAllocation!=null && possibleUsersForAllocation.size()>0) {
            for (int k = 0; k < possibleUsersForAllocation.size(); k++) {
                CheckBox cb = new CheckBox(getContext());
                cb.setText(possibleUsersForAllocation.get(k).getName());
                cb.setTag(possibleUsersForAllocation.get(k).getId());
                scrollView.addView(cb);
            }
        }
    }
    private List<String> getAllocatedUsersForTicketRadio(){
         List<String> userid=new ArrayList<String>();
         int id=radioGroupUserAllocate.getCheckedRadioButtonId();
         RadioButton radioButton=getView().findViewById(id);

         userid.add(radioButton.getTag().toString());
         return userid;
    }
    private List<String> getAllocatedUsersForTicket(){
        int count = scrollView.getChildCount();
        List<String> listofAllocatedUserIds=new ArrayList<>();
        //int count2=0;
        for (int i = 0; i < count; i++) {
            View v = scrollView.getChildAt(i);
            if (v instanceof CheckBox) {
                if (((CheckBox) v).isChecked()) {
                    String userid=v.getTag().toString();
                    listofAllocatedUserIds.add(userid);
                    //int id = v.getId();
                    //String userid = Integer.toString(id);
                    //listofUserId.add(userid);
                    Toast.makeText(getContext(), "useridadded=" + userid, Toast.LENGTH_SHORT).show();
                }
            }
        }
        return listofAllocatedUserIds;
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private int getIndex(Spinner spinner, String myString){
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }
    private void getBasicData(){
//        GetDataService service= RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<TicketCreatGetResponse> call=service.getTicketCreate();//Might have to pass userid in future
        Toast.makeText(getContext(),"getBasicData=", Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<TicketCreatGetResponse>() {
            @Override
            public void onResponse(Call<TicketCreatGetResponse> call, Response<TicketCreatGetResponse> response) {
                if(response!=null && response.isSuccessful()&& response.body()!= null ){
                    if(response.body().isAllowed()){
                        priorityList=response.body().getPrioritylist();
                        projectList=response.body().getProjectList();
                        possibleUsersForAllocation=response.body().getPossibleUsers();
                        setSpinnerData();
                        //setUserAllocationCheckboxes();
                        setUserAllocationRadioButton();
                    }else{
                        Toast.makeText(getContext(),"You are not allowed to create Ticket", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TicketCreatGetResponse> call, Throwable t) {
                Toast.makeText(getContext(),"getBasicData throwable="+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //fetch priority list
        //fetch project list
    }
    private void collectData(){

        int priority_position=ticket_priority_spinner.getSelectedItemPosition();
        int project_positon = ticket_project_spinner.getSelectedItemPosition();
        Toast.makeText(getContext(),"priority_position="+priority_position, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"project_positon"+project_positon, Toast.LENGTH_SHORT).show();

        String title=ticketTitle.getText().toString();
        String description=ticketDescription.getText().toString();
        String priority=priorityList.get(priority_position);

        String project_id=projectList.get(project_positon).getId();

        HashMap<String, String> map=new HashMap<String, String>();
        map.put("ticket_title",title);
        map.put("ticket_desc",description);
        map.put("project_id",project_id);
        map.put("priority",priority);

        //List<String> allocateduserids=getAllocatedUsersForTicket();
        List<String> allocateduserids=getAllocatedUsersForTicketRadio();
        if(allocateduserids==null || allocateduserids.isEmpty() || allocateduserids.size()==0){
            Toast.makeText(getContext(),"You must allocate atleast one user", Toast.LENGTH_LONG).show();
            return;
        }
        UserAuth userAuth=new UserAuth(getContext());
        if(userAuth.getRole().equals("2")){
            postTicketAdd(map,allocateduserids);
        }else{
            Toast.makeText(getContext(),"You are not allowed to create Ticket", Toast.LENGTH_LONG).show();
        }

    }
    private void postTicketAddWithImage(HashMap<String, String> map){
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<TicketCreatePostResponse> call=service.postTicketAddWithImage(map);


    }
    private void postTicketAdd(HashMap<String, String> map,List<String> usrs){
        //GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        List<String> users=new ArrayList<>();
        users.add("55");
        users.add("54");
//        Call<TicketCreatePostResponse> call=service.postTicketAdd(map);//pre biru code
        Call<TicketCreatePostResponse> call=service.postTicketAddWithUser(map,usrs);//post biru code
        call.enqueue(new Callback<TicketCreatePostResponse>() {
            @Override
            public void onResponse(Call<TicketCreatePostResponse> call, Response<TicketCreatePostResponse> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().isAllowed() && response.body().getSuccess()){
                    //Toast.makeText(getContext(),"Ticket created.Ticket number is "+response.body().getTicket_number(),Toast.LENGTH_LONG).show();
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.popBackStack();
                }else{
                    Toast.makeText(getContext(),"Ticket couldn't be created", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TicketCreatePostResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode,resultCode,result);
        System.out.println("datasan0 resultCode=");
        //System.out.println("datasan0 resultCode="+resultCode+" outputuri="+outputUri+" extra"+result.getExtras().get("data"));


        if (requestCode == 1) {
            System.out.println("datasan1"+result);
            if  (resultCode == RESULT_OK){
                System.out.println("datasan2"+result.getData());
            }
        }
        //super.onActivityResult( requestCode,  resultCode,  result);
    }

    private void showFullAlertDialog(final View view){
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);//to be uncommented for notification
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Animation_Design_BottomSheetDialog); //to be commented for notification
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.custom_edit_view, viewGroup, false);
        final EditText userInput = (EditText) dialogView.findViewById(R.id.edittext);
        if(view==ticketDescription){
            userInput.setText(ticketDescription.getText());
        }else if(view==ticketTitle){
            userInput.setText(ticketTitle.getText());
        }
        dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Button buttonOk=dialogView.findViewById(R.id.buttonOk);
        Button buttonCancel=dialogView.findViewById(R.id.buttonCancel);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view==ticketDescription){
                    ticketDescription.setText(userInput.getText().toString());
                }else if(view==ticketTitle){
                    ticketTitle.setText(userInput.getText().toString());
                }

                alertDialog.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
}
