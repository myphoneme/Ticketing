package com.phoneme.ticketing.ui.company.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.helper.VerticalSpaceItemDecoration;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.company.adapter.CompanyMainAdapter;
import com.phoneme.ticketing.ui.company.model.CompanyModel;
import com.phoneme.ticketing.ui.company.model.CompanyViewModel;
import com.phoneme.ticketing.ui.company.sorting.CompanyNameCompare;
import com.phoneme.ticketing.ui.company.sorting.CompanySNCompare;
import com.phoneme.ticketing.ui.company.sorting.CompanyStatusCompare;
//import com.phoneme.ticketing.ui.ticketing.fragments.TicketListFragmentDirections;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyListFragment extends Fragment implements CompanyMainAdapter.OnItemClickListener{

    private CompanyViewModel toolsViewModel;
    private RecyclerView recyclerView;
    private List<CompanyModel> companyModelList;
    private TextView projectname,companystatus,companySN,companyCreate;
    private Boolean companyNameSort=true,companySNSort=true,companyStatusSort=true;
    CompanyMainAdapter adapter;
    private RelativeLayout progressbarlayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(CompanyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_company, container, false);
        //final TextView textView = root.findViewById(R.id.text_tools);
//        toolsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressbarlayout=(RelativeLayout)view.findViewById(R.id.progressbar_relativelayout);
        progressbarlayout.setVisibility(View.VISIBLE);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview_company_main);
        projectname=(TextView)view.findViewById(R.id.fragment_project_name);
        companystatus=(TextView)view.findViewById(R.id.fragment_company_status);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(10);
        recyclerView.addItemDecoration(itemDecoration);
        companySN=(TextView)view.findViewById(R.id.fragment_company_sn);
        companyCreate=(TextView)view.findViewById(R.id.fragment_company_create);
        //to be uncommented post notification
//        companyCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
//                navController.navigate(R.id.nav_company_add);
//            }
//        });
        companystatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(companyStatusSort){
                    CompanyStatusCompare companyStatusCompare=new CompanyStatusCompare();
                    Collections.sort(companyModelList,companyStatusCompare);
                    adapter.notifyDataSetChanged();
                    companyStatusSort=false;
                }else{
                    CompanyStatusCompare companyStatusCompare=new CompanyStatusCompare();
                    Collections.sort(companyModelList, Collections.reverseOrder(companyStatusCompare));
                    adapter.notifyDataSetChanged();
                    companyStatusSort=true;
                }
            }
        });
        companySN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(companySNSort){
                    CompanySNCompare snCompare=new CompanySNCompare();
                    Collections.sort(companyModelList,snCompare);
                    adapter.notifyDataSetChanged();
                    companySNSort=false;
                }else{
                    CompanySNCompare snCompare=new CompanySNCompare();
                    Collections.sort(companyModelList, Collections.reverseOrder(snCompare));
                    adapter.notifyDataSetChanged();
                    companySNSort=true;
                }
            }
        });
        projectname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(companyNameSort){

                    CompanyNameCompare companyNameCompare=new CompanyNameCompare();
                    Collections.sort(companyModelList,companyNameCompare);
                    adapter.notifyDataSetChanged();

                    companyNameSort=false;
                }else{
                    CompanyNameCompare companyNameCompare=new CompanyNameCompare();
                    Collections.sort(companyModelList, Collections.reverseOrder(companyNameCompare));
                    adapter.notifyDataSetChanged();
                    companyNameSort=true;
                }
            }
        });
        getCompanyData();
    }

    private void setRecyclerView(List<CompanyModel> companyModelList){
        adapter=new CompanyMainAdapter(getContext(),companyModelList,this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);
    }
    private void getCompanyData(){
        //GetDataService service= RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<List<CompanyModel>> call = service.getCompanies();
        call.enqueue(new Callback<List<CompanyModel>>() {
            @Override
            public void onResponse(Call<List<CompanyModel>> call, Response<List<CompanyModel>> response) {
                if(response.isSuccessful()){
                    System.out.println("Response successfull ra\n"+response.body());
                    companyModelList=response.body();
                    progressbarlayout.setVisibility(View.GONE);
                    setRecyclerView(response.body());
                }
                progressbarlayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<CompanyModel>> call, Throwable t) {
                progressbarlayout.setVisibility(View.GONE);
            }
        });
    }

    public void onCompanyClick(int position){
//        Bundle args2= TicketListFragmentDirections.navTicketingAction().getArguments();
//        args2.putString("company_id",companyModelList.get(position).getId());
//        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
//        navController.navigate(R.id.nav_company_action_edit,args2);
    }
}