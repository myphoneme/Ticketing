package com.phoneme.ticketing.ui.project.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
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
import com.phoneme.ticketing.ui.project.model.ProjectEditGetModel;
import com.phoneme.ticketing.ui.project.network.ProjectEditGetResponse;
import com.phoneme.ticketing.ui.project.network.ProjectEditPostResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectEditFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private EditText title, description;
    private TextView possibleAllocatedUser;
    private LinearLayout checkboxRoot;
    private HashSet allocated_userd_ids = new HashSet();
    private HashMap<String, String> Company_name_company_id = new HashMap<>();
    private Spinner companySpinner, statusSpinner;
    private ProjectEditGetModel originalData;
    private Button reset, submit;
    private int ACTIVE = 1;
    private int INACTIVE = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project_edit, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String projectid = getArguments().getString("project_id");
        if (projectid != null) {
            //String projectid = "28";
            title = (EditText) view.findViewById(R.id.title_text);
            description = (EditText) view.findViewById(R.id.description_text);
            possibleAllocatedUser = (TextView) view.findViewById(R.id.possible_allocated_users);
            checkboxRoot = (LinearLayout) view.findViewById(R.id.rootcheckbox);
            companySpinner = (Spinner) view.findViewById(R.id.company_spinner);
            statusSpinner = (Spinner) view.findViewById(R.id.spinner_status);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showFullAlertDialog(view);
                }
            });
            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showFullAlertDialog(view);
                }
            });
            //companySpinner.setOnItemSelectedListener(this);//working even after commenting
            //statusSpinner.setOnItemSelectedListener(this);//working even after commenting
            reset = (Button) view.findViewById(R.id.reset);
            submit = (Button) view.findViewById(R.id.submit);

            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetData();
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getCurrentData();
                }
            });
            getProjectEditData(projectid);
        }
    }


    private void getCurrentData() {
        String title_text = title.getText().toString();
        String description_text = description.getText().toString();
        int company_selected_index = companySpinner.getSelectedItemPosition();
        int status_selected_index = statusSpinner.getSelectedItemPosition();
        //Toast.makeText(getContext(), "company slected index="+company_selected_index, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(),"status selected"+status_selected_index,Toast.LENGTH_SHORT).show();
        String company_id_value = Company_name_company_id.get(originalData.getCompanyList().get(company_selected_index).getName());
        //Toast.makeText(getContext(),"company id value="+company_id_value,Toast.LENGTH_SHORT).show();
        String status_selected_value = new String();
        if (status_selected_index == ACTIVE) {
            status_selected_value = "1";

        } else {
            status_selected_value = "0";
        }
        String project_id = originalData.getId();
        //Toast.makeText(getContext(),"status selected value="+status_selected_value,Toast.LENGTH_SHORT).show();
        HashMap<String, String> map = new HashMap<>();
        map.put("id", originalData.getId());//I think project id never changes
        map.put("status", status_selected_value);
        map.put("company_id", company_id_value);
        map.put("description", description_text);
        map.put("title", title_text);
        HashMap<String, List<String>> map2 = new HashMap<>();
        List<String> listofUserId = new ArrayList<String>();
        String[] strArray1 = new String[3];
        HashMap<String, String[]> map3 = new HashMap<>();
        int count = checkboxRoot.getChildCount();
        //int count2=0;
        for (int i = 0; i < count; i++) {
            View v = checkboxRoot.getChildAt(i);
            if (v instanceof CheckBox) {
                if (((CheckBox) v).isChecked()) {
                    int id = v.getId();
                    String userid = Integer.toString(id);
                    listofUserId.add(userid);
//                    strArray1[count2]=userid;
//                    count2++;
                    Toast.makeText(getContext(), "useridadded=" + userid, Toast.LENGTH_SHORT).show();
                }
            }
        }
        //map2.put("allocated_users",listofUserId);
        //map3.put("allocated_users[]",strArray1);

        postCompanyData(map, listofUserId);
    }

    private void postCompanyData(HashMap<String, String> map, List<String> userids) {

        //GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<ProjectEditPostResponse> call = service.postCompanyEdit(map, userids);
        call.enqueue(new Callback<ProjectEditPostResponse>() {
            @Override
            public void onResponse(Call<ProjectEditPostResponse> call, Response<ProjectEditPostResponse> response) {
                if(response!=null && response.body()!=null && response.body().getResponsesuccess()!=null){
                    Toast.makeText(getContext(), "response===" + response.body().getResponsesuccess(), Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.popBackStack();
                }

            }

            @Override
            public void onFailure(Call<ProjectEditPostResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable===" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetData() {
        title.setText(originalData.getName());
        description.setText(originalData.getDesc());
        companySpinner.setSelection(getIndex(companySpinner, originalData.getCompay_name()));
        if (originalData.getStatus().equals("1")) {
            statusSpinner.setSelection(ACTIVE);
        } else {
            statusSpinner.setSelection(INACTIVE);
        }

        int count = checkboxRoot.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = checkboxRoot.getChildAt(i);
            if (v instanceof CheckBox) {
                int id = v.getId();
                String userid = Integer.toString(id);
                if (allocated_userd_ids.contains(userid)) {
                    ((CheckBox) v).setChecked(true);
                } else {
                    ((CheckBox) v).setChecked(false);
                }
            }
        }
    }

    private void setData(ProjectEditGetModel response) {
        originalData = response;

        title.setText(response.getName());
        description.setText(response.getDesc());


        if (response.getProjectEditAllocatedUserModelList() != null && !response.getProjectEditAllocatedUserModelList().isEmpty() && response.getProjectEditAllocatedUserModelList().size() > 0) {
            for (int i = 0; i < response.getProjectEditAllocatedUserModelList().size(); i++) {
                allocated_userd_ids.add(response.getProjectEditAllocatedUserModelList().get(i).getUser_id());//id in string format
            }
        }

        //Creating checkboxes dynamically
        for (int i = 0; i < response.getPossibleAllocatedProjectUserModelList().size(); i++) {
            LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkParams.setMargins(10, 10, 10, 10);
            checkParams.gravity = Gravity.LEFT;

            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setId(Integer.parseInt(response.getPossibleAllocatedProjectUserModelList().get(i).getId()));
            checkBox.setText(response.getPossibleAllocatedProjectUserModelList().get(i).getUserName());

            if (allocated_userd_ids.contains(response.getPossibleAllocatedProjectUserModelList().get(i).getId())) {
                checkBox.setChecked(true);
            }
            checkboxRoot.addView(checkBox, checkParams);

        }

        String[] companyName = new String[response.getCompanyList().size()];
        for (int i = 0; i < response.getCompanyList().size(); i++) {
            companyName[i] = response.getCompanyList().get(i).getName();
            Company_name_company_id.put(response.getCompanyList().get(i).getName(), response.getCompanyList().get(i).getId());
        }
        ArrayAdapter projectAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, companyName);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companySpinner.setAdapter(projectAdapter);
        companySpinner.setSelection(getIndex(companySpinner, response.getCompay_name()));


        String[] statusName = new String[2];
        statusName[INACTIVE] = "InActive";
        statusName[ACTIVE] = "Active";
        ArrayAdapter statusAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, statusName);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);
        if (response.getStatus().equals("1")) {
            statusSpinner.setSelection(ACTIVE);
        } else {
            statusSpinner.setSelection(INACTIVE);
        }
    }

    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

    private void getProjectEditData(String id) {
        //GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<ProjectEditGetResponse> call = service.getProjectEdit(id);
        call.enqueue(new Callback<ProjectEditGetResponse>() {
            @Override
            public void onResponse(Call<ProjectEditGetResponse> call, Response<ProjectEditGetResponse> response) {
                if (response.isSuccessful()) {
                    //System.out.println("getProjectEditData successful" + response.body().getProjectEditGetModel().getCompay_name());
                    setData(response.body().getProjectEditGetModel());
                }
            }

            @Override
            public void onFailure(Call<ProjectEditGetResponse> call, Throwable t) {
                System.out.println("getProjectEditData onFailure" + t.getMessage());
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
    private void showFullAlertDialog(final View view){
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Animation_Design_BottomSheetDialog);
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.custom_edit_view, viewGroup, false);
        final EditText userInput = (EditText) dialogView.findViewById(R.id.edittext);
        if(view==description){
            userInput.setText(description.getText());
        }else if(view==title){
            userInput.setText(title.getText());
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
                }else if(view==title){
                    title.setText(userInput.getText().toString());
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
