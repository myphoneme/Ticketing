package com.phoneme.ticketing.ui.company.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.company.network.CompanyCreatePostResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyCreateFragment extends Fragment {
    private Button button;
    private Spinner spinner;
    private EditText editCompanyName;
    private int STATUS_ACTIVE=1;
    private int STATUS_INACTIVE=0;
    private final String ACTIVE="Active";
    private final String INACTIVE="Inactive";
    private String[] statusList=new String[2];
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_company_create, container, false);

        return root;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button=(Button)view.findViewById(R.id.submit);
        spinner=(Spinner)view.findViewById(R.id.company_status_spinner);
        editCompanyName=(EditText)view.findViewById(R.id.edit_name);
        statusList[0]=ACTIVE;
        statusList[1]=INACTIVE;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyName=editCompanyName.getText().toString();
                int statusPosition=spinner.getSelectedItemPosition();
                int status;
                if(statusList[statusPosition].equals(ACTIVE)){
                    status=1;
                }else{
                    status=0;
                }
                if(companyName!=null && !companyName.isEmpty() && companyName.toString().length()>0){

                    createCompanyPostData(companyName,status);
                }else{
                    Toast.makeText(getContext(),"Please enter proper data", Toast.LENGTH_LONG).show();
                }
            }
        });


        ArrayAdapter statusAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(statusAdapter);
        spinner.setSelection(0);
    }

    public void createCompanyPostData(String companyName, int status){
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<CompanyCreatePostResponse> call=service.postCompanyCreate(companyName,status);
        call.enqueue(new Callback<CompanyCreatePostResponse>() {
            @Override
            public void onResponse(Call<CompanyCreatePostResponse> call, Response<CompanyCreatePostResponse> response) {
                if(response!=null && response.isSuccessful() && response.body()!=null && response.body().getMessage()!=null){
                    //Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<CompanyCreatePostResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
