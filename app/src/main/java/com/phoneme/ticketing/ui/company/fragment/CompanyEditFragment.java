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
import com.phoneme.ticketing.ui.company.model.CompanyModel;
import com.phoneme.ticketing.ui.company.network.CompanyEditGetResponse;
import com.phoneme.ticketing.ui.company.network.CompanyEditPostResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyEditFragment extends Fragment {
    private EditText editCompay;
    private Spinner spinner;
    private Button companySubmit;
    private String ACTIVE = "Active";
    private String INACTIVE = "Inactive";
    private String STATUS_ACTIVE = "1";
    private String STATUS_INACTIVE = "0";
    private String[] statusList = new String[2];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_company_edit, container, false);

        return root;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String companyid=getArguments().getString("company_id");
//        Toast.makeText(getContext(),"TicketEditFragment ticketid="+ticketid,Toast.LENGTH_SHORT).show();
        final String id = "3";
//        if(companyid!=null && companyid.length()>0){
//            id=companyid;
//        }else{ //else part not there in final code
//            id="3";// "167" "301" "237" "274" "292" //This part need to go
//        }
        Toast.makeText(getContext(),"CompanyEditFragment companyid="+companyid+" id="+id, Toast.LENGTH_SHORT).show();

        statusList[0] = ACTIVE;
        statusList[1] = INACTIVE;

        editCompay = (EditText) view.findViewById(R.id.edit_name);
        spinner = (Spinner) view.findViewById(R.id.company_status_spinner);
        companySubmit = (Button) view.findViewById(R.id.submit);
//        companySubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        ArrayAdapter statusAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(statusAdapter);
        spinner.setSelection(0);

        companySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(companyid!=null && !companyid.isEmpty() && companyid.toString().length()>0){
                    getEditedDataAndPost(companyid);
                }else {
                    getEditedDataAndPost(id);
                }
            }
        });

        if(companyid!=null && !companyid.isEmpty() && companyid.toString().length()>0){
            getCompanyData(companyid);
        }else{
            getCompanyData(id);
        }
    }

    private void getEditedDataAndPost(String id) {
        String companyName = editCompay.getText().toString();
        int selectedStatusPosition = spinner.getSelectedItemPosition();
        int status;
        if (statusList[selectedStatusPosition].equals(ACTIVE)) {
            status = 1;
        } else {
            status = 0;
        }
        postEditedCoompanyData(id,companyName, status);
    }

    private void getCompanyData(String id) {
        GetDataService service = RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<CompanyEditGetResponse> call = service.getCompanyData(id);
        call.enqueue(new Callback<CompanyEditGetResponse>() {
            @Override
            public void onResponse(Call<CompanyEditGetResponse> call, Response<CompanyEditGetResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null && response.body().getCompany() != null) {
                    setCompanyData(response.body().getCompany());
                }
            }

            @Override
            public void onFailure(Call<CompanyEditGetResponse> call, Throwable t) {

            }
        });
    }

    private void postEditedCoompanyData(String id, String companyName, int status) {
        GetDataService service = RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<CompanyEditPostResponse> call = service.postCompanyEditFinal(id,companyName, status);
        call.enqueue(new Callback<CompanyEditPostResponse>() {
            @Override
            public void onResponse(Call<CompanyEditPostResponse> call, Response<CompanyEditPostResponse> response) {

                if (response != null && response.isSuccessful() && response.body() != null) {
                    //Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<CompanyEditPostResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setCompanyData(CompanyModel company) {
        if (company != null) {
            editCompay.setText(company.getName());
            if (company.getStatus().equals(STATUS_ACTIVE)) {
                int position = 0;
                spinner.setSelection(position);
            } else {
                int position = 1;
                spinner.setSelection(position);
            }
        }
    }

}
