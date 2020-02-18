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
import com.phoneme.ticketing.ui.company.model.CompanyModel;
import com.phoneme.ticketing.ui.project.network.ProjectAddGetResponse;
import com.phoneme.ticketing.ui.project.network.ProjectAddPostResponse;
import com.phoneme.ticketing.ui.user.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//AdapterView.OnItemSelectedListener
public class ProjectAddFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private EditText title, description;
    private LinearLayout checkboxRoot;
    private Spinner companySpinner, statusSpinner;
    private Button reset, submit;
    private TextView possibleAllocatedUser;
    private int ACTIVE = 1;
    private int INACTIVE = 0;
    private List<UserModel> userModelList;
    private List<CompanyModel> companyModelList;

    private HashSet allocated_userd_ids = new HashSet();
    private HashMap<String, String> Company_name_company_id = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project_add, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        title = (EditText) view.findViewById(R.id.title_text);
        description = (EditText) view.findViewById(R.id.description_text);
        possibleAllocatedUser = (TextView) view.findViewById(R.id.possible_allocated_users);
        checkboxRoot = (LinearLayout) view.findViewById(R.id.rootcheckbox);
        companySpinner = (Spinner) view.findViewById(R.id.company_spinner);
        statusSpinner = (Spinner) view.findViewById(R.id.spinner_status);
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFullAlertDialog(view);
            }
        });
        title.setOnClickListener(new View.OnClickListener() {
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
                //resetData();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentData();
            }
        });
        getData();


    }

    private void getCurrentData() {

        String title_text = title.getText().toString();
        String description_text = description.getText().toString();
        int company_selected_index = companySpinner.getSelectedItemPosition();
        int status_selected_index = statusSpinner.getSelectedItemPosition();

        String company_id_value = Company_name_company_id.get(companyModelList.get(company_selected_index).getName());

        String status_selected_value = new String();
        if (status_selected_index == ACTIVE) {
            status_selected_value = "1";

        } else {
            status_selected_value = "0";
        }


        HashMap<String, String> map = new HashMap<>();
        // map.put("id", originalData.getId());//I think project id never changes
        map.put("status", status_selected_value);
        map.put("company_id", company_id_value);
        map.put("description", description_text);
        map.put("title", title_text);

        List<String> listofUserId = new ArrayList<String>();

        int count = checkboxRoot.getChildCount();

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
        postProjectAdd(map, listofUserId);
    }

    private void setData() {

        String[] companyName = new String[companyModelList.size()];
        for (int i = 0; i < companyModelList.size(); i++) {
            companyName[i] = companyModelList.get(i).getName();
            Company_name_company_id.put(companyModelList.get(i).getName(), companyModelList.get(i).getId());
        }
        ArrayAdapter projectAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, companyName);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companySpinner.setAdapter(projectAdapter);
        //companySpinner.setSelection(getIndex(companySpinner, response.getCompay_name()));


        String[] statusName = new String[2];
        statusName[INACTIVE] = "InActive";
        statusName[ACTIVE] = "Active";
        ArrayAdapter statusAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, statusName);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);
//        if (response.getStatus().equals("1")) {
//            statusSpinner.setSelection(ACTIVE);
//        } else {
//            statusSpinner.setSelection(INACTIVE);
//        }


        for (int i = 0; i < userModelList.size(); i++) {
            LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkParams.setMargins(10, 10, 10, 10);
            checkParams.gravity = Gravity.LEFT;

            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setId(Integer.parseInt(userModelList.get(i).getId()));
            checkBox.setText(userModelList.get(i).getName());

            if (allocated_userd_ids.contains(userModelList.get(i).getId())) {
                checkBox.setChecked(true);
            }
            checkboxRoot.addView(checkBox, checkParams);

        }

    }

    private void postProjectAdd(HashMap<String, String> map, List<String> userids) {
        Toast.makeText(getContext(), "postProjectAdd", Toast.LENGTH_SHORT).show();
        GetDataService service = RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<ProjectAddPostResponse> call = service.postProjectAdd(map, userids);
        call.enqueue(new Callback<ProjectAddPostResponse>() {
            @Override
            public void onResponse(Call<ProjectAddPostResponse> call, Response<ProjectAddPostResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "postProjectAdd" + response.body().getResponsesuccess(), Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<ProjectAddPostResponse> call, Throwable t) {
                Toast.makeText(getContext(), "postProjectAdd" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    //Perhaps not need in Project Add only needed in Project Edit fragment
    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

    public void getData() {
        GetDataService service = RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<ProjectAddGetResponse> call = service.getDataForProjectAdd();
        call.enqueue(new Callback<ProjectAddGetResponse>() {
            @Override
            public void onResponse(Call<ProjectAddGetResponse> call, Response<ProjectAddGetResponse> response) {
                if (response != null && response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().isAllowed()!=null && response.body().isAllowed()) {
                            if(response.body().getUserModelList()!=null && response.body().getCompanyModelList()!=null) {
                                userModelList = response.body().getUserModelList();
                                companyModelList = response.body().getCompanyModelList();
                                setData();
                            }
                        }else{
                            Toast.makeText(getContext(), "You are not allowed to post Project", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProjectAddGetResponse> call, Throwable t) {
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
        //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
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
