package com.phoneme.ticketing.ui.ticketing.fragments;

import android.graphics.Rect;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.project.model.ProjectModel;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;
import com.phoneme.ticketing.ui.ticketing.model.UserIdModel;
import com.phoneme.ticketing.ui.ticketing.model.UserModelForTicket;
import com.phoneme.ticketing.ui.ticketing.network.TicketEditPostResponse;
import com.phoneme.ticketing.ui.ticketing.network.TicketEditResponse;
import com.phoneme.ticketing.user.UserAuth;
import com.phoneme.ticketing.ui.user.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketEditFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private List<ProjectModel> projectList;
    private List<String> priorityList=new ArrayList<>();
    private List<UserIdModel> allocatedUserForATicket=new ArrayList<>();
    private List<UserModelForTicket> possibleUsersForAllocation=new ArrayList<>();
    private TicketModel ticketData;
    private Spinner spinner,priority;
    private TextView description,name;
    private Button Submit,Reset;
    private LinearLayout scrollView;
    private RadioGroup radioGroupUserAllocate;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_ticket_edit,container,false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        String ticketid=getArguments().getString("ticket_id");
//        Toast.makeText(getContext(),"TicketEditFragment ticketid="+ticketid,Toast.LENGTH_SHORT).show();
        String id=new String();
        if(ticketid!=null && ticketid.length()>0){
            id=ticketid;
        }else{ //else part not there in final code
            id="237";// "167" "301" "237" "274" "292"
        }
        radioGroupUserAllocate=(RadioGroup)view.findViewById(R.id.user_allocate_single_user_radio_group);

        spinner = (Spinner)view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        name =(TextView) view.findViewById(R.id.title) ;
        description=(TextView) view.findViewById(R.id.description);
        priority =(Spinner)view.findViewById(R.id.priority);

        Reset = (Button)view.findViewById(R.id.reset);
        Submit =(Button)view.findViewById(R.id.submit);
        scrollView=(LinearLayout) view.findViewById(R.id.scrollView);
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
                showFullAlertDialog(view);
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFullAlertDialog(view);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTicketEdit();
            }
        });
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });
        getTicketEditData(id);
    }

    private void showFullAlertDialog(final View view){
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Animation_Design_BottomSheetDialog);
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.custom_edit_view, viewGroup, false);
        final EditText userInput = (EditText) dialogView.findViewById(R.id.edittext);
        if(view==description){
            userInput.setText(description.getText());
        }else if(view==name){
            userInput.setText(name.getText());
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
                if(view==description){
                    description.setText(userInput.getText().toString());
                }else if(view==name){
                    name.setText(userInput.getText().toString());
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
//    private void resetAllocatedUsersRadioButtion(){
//        int count=radioGroupUserAllocate.getChildCount();
//        for(int i=0;i<count;i++){
//            if(possibleUsersForAllocation.get(i).getAllocated()){
//            }
//        }
//    }
    private void setUserAllocationRadioButton(){
        if(possibleUsersForAllocation!=null && possibleUsersForAllocation.size()>0) {
            for (int k = 0; k < possibleUsersForAllocation.size(); k++) {
                RadioButton rb=new RadioButton(getContext());
                rb.setText(possibleUsersForAllocation.get(k).getName());
                rb.setTag(possibleUsersForAllocation.get(k).getId());

                radioGroupUserAllocate.addView(rb);
                if(possibleUsersForAllocation.get(k).getAllocated()){
                    rb.setChecked(true);
                }
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
                if(possibleUsersForAllocation.get(k).getAllocated()){
                    cb.setChecked(true);
                }
            }
        }


//        int count =scrollView.getChildCount();
//        //int count2=0;
//        for (int i = 0; i < count; i++) {
//            View v = scrollView.getChildAt(i);
//            if (v instanceof CheckBox) {
//
//                if(allocatedUserForATicket.get(i)){
//
//                }
//                if (((CheckBox) v).isChecked()) {
//                    int id = v.getId();
//                    String userid = Integer.toString(id);
//                    listofUserId.add(userid);
////                    strArray1[count2]=userid;
////                    count2++;
//                    Toast.makeText(getContext(), "useridadded=" + userid, Toast.LENGTH_SHORT).show();
//                }
//            }
//        }

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
                    Toast.makeText(getContext(), "useridaddedchan=" + userid, Toast.LENGTH_SHORT).show();
                }
            }
        }
        return listofAllocatedUserIds;
    }

    private void resetData(){
        radioGroupUserAllocate.removeAllViews();
        setUserAllocationRadioButton();

        if(this.ticketData==null){
            Toast.makeText(getContext(),"No ticket available with this id", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] projectName = new String[this.projectList.size()];
        for(int i=0;i<this.projectList.size();i++){
            projectName[i]=this.projectList.get(i).getName();
        }
        ArrayAdapter projectAdapter =  new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,projectName);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(projectAdapter);
        spinner.setSelection(getIndex(spinner, this.ticketData.getProject_name()));
        System.out.println("ticketdata+"+this.ticketData.toString());
        name.setText(this.ticketData.getName());
        description.setText(this.ticketData.getDesc());

        ArrayAdapter priorityAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,this.priorityList);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priority.setAdapter(priorityAdapter);
        priority.setSelection(getIndex(priority, this.ticketData.getPriority()));
    }
    private void setData(TicketEditResponse response){
        this.projectList=response.getProjectList();
        this.ticketData = response.getTicketModel();
        this.priorityList=response.getPrioritylist();
        this.possibleUsersForAllocation=response.getPossibleUsers();
        if(this.ticketData==null){
            Toast.makeText(getContext(),"No ticket available with this id", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] projectName = new String[this.projectList.size()];
        for(int i=0;i<this.projectList.size();i++){
            projectName[i]=this.projectList.get(i).getName();
        }
        ArrayAdapter projectAdapter =  new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,projectName);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(projectAdapter);
        spinner.setSelection(getIndex(spinner, this.ticketData.getProject_name()));
        System.out.println("ticketdata+"+this.ticketData.toString());
        name.setText(this.ticketData.getName());
        description.setText(this.ticketData.getDesc());

        ArrayAdapter priorityAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,response.getPrioritylist());
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priority.setAdapter(priorityAdapter);
        priority.setSelection(getIndex(priority, this.ticketData.getPriority()));
        allocatedUserForATicket=response.getAllocateUsersForATicket();
        //setUserAllocationCheckboxes();
        setUserAllocationRadioButton();

    }
    private void getTicketEditData(String id){
        //GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<TicketEditResponse> call = service.getTicketEdit(id);
        Toast.makeText(getContext(),"getTicketEditData id="+id, Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<TicketEditResponse>() {
            @Override
            public void onResponse(Call<TicketEditResponse> call, Response<TicketEditResponse> response) {
                if(response.isSuccessful()){
                    setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<TicketEditResponse> call, Throwable t) {
                Toast.makeText(getContext(),"getTicketEditData throwable="+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

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

    private void postTicketEdit(){
        Map<String, String> map=new HashMap<String, String>();
        String name_title=this.name.getText().toString();
        String desc = this.description.getText().toString();

        int project_position=this.spinner.getSelectedItemPosition();
        String projectid=projectList.get(project_position).getId();
        map.put("ticket_title",name_title);
        map.put("ticket_desc",desc);
        //map.put("project_id", this.ticketData.getProject_id());
        map.put("project_id",projectid);
        map.put("id", this.ticketData.getId());
        map.put("priority", priority.getSelectedItem().toString());
        UserAuth userAuth=new UserAuth(getContext());
        map.put("last_updated_by",userAuth.getId());

//        List<String> allocateduserids=getAllocatedUsersForTicket();
        List<String> allocateduserids=getAllocatedUsersForTicketRadio();
        if(allocateduserids==null || allocateduserids.isEmpty() || allocateduserids.size()==0){
            Toast.makeText(getContext(),"You must allocate atleast one user", Toast.LENGTH_LONG).show();
            return;
        }

        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        //Call<TicketEditPostResponse> call= service.postTicketEdit(name_title,desc,this.ticketData.getProject_id(),this.ticketData.getId(),priority.getSelectedItem().toString(),49); //49 Satyendra//commented on dec26


//        Call<TicketEditPostResponse> call = service.postTicketEdit(name_title, desc, this.ticketData.getProject_id(), this.ticketData.getId(), priority.getSelectedItem().toString(), Integer.parseInt(userAuth.getId())); //49 Satyendra
//
        //Call<TicketEditPostResponse> call = service.postTicketEditMap(map);
        Call<TicketEditPostResponse> call = service.postTicketEditMapUser(map,allocateduserids);

        Toast.makeText(getContext(),"postTicketEdit clicked", Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<TicketEditPostResponse>() {
            @Override
            public void onResponse(Call<TicketEditPostResponse> call, Response<TicketEditPostResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(), response.body().getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.popBackStack();
                }else{
                    Toast.makeText(getContext(), "Not successful", Toast.LENGTH_SHORT).show();

                }
                }

            @Override
            public void onFailure(Call<TicketEditPostResponse> call, Throwable t) {
                Toast.makeText(getContext(),"failed ra "+t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
