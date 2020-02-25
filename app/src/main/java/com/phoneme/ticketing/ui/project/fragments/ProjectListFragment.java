package com.phoneme.ticketing.ui.project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.UserAuth;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.project.adapter.ProjectListAdapterNew;
import com.phoneme.ticketing.ui.project.model.ProjectModel;
import com.phoneme.ticketing.ui.project.model.ProjectViewModel;
import com.phoneme.ticketing.ui.project.network.ProjectListResponse;
import com.phoneme.ticketing.ui.project.sorting.CompanyNameCompare;
import com.phoneme.ticketing.ui.project.sorting.DescriptionCompare;
import com.phoneme.ticketing.ui.project.sorting.StatusCompare;
import com.phoneme.ticketing.ui.project.sorting.TitleCompare;
//import com.phoneme.ticketing.ui.ticketing.fragments.TicketListFragmentDirections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Collections.reverseOrder;

public class ProjectListFragment extends Fragment implements ProjectListAdapterNew.OnItemClickListener {
    private ProjectViewModel projectViewModel;
    private RecyclerView recyclerView;
    private List<ProjectModel> projectModelList=new ArrayList<ProjectModel>();
    private TextView companyName,Title,Description,Status,projectCreate;
    private Boolean companyNameSort=true,TitleSort=true,DescriptionSort=true,StatusSort=true;
    //private ProjectListAdapter adapter;
    private ProjectListAdapterNew adapter;
    private RelativeLayout progressbarlayout;
    private String[] item_color;
    ;public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        projectViewModel =
                ViewModelProviders.of(this).get(ProjectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_project_list, container, false);
//        final TextView textView = root.findViewById(R.id.text_project);
//        projectViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s+"modified");
//            }
//        });
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState){
        super.onViewCreated(v, savedInstanceState);
        progressbarlayout=(RelativeLayout)v.findViewById(R.id.progressbar_relativelayout);
        progressbarlayout.setVisibility(View.VISIBLE);
        recyclerView =(RecyclerView)v.findViewById(R.id.recyclerview_project_list);
        companyName=(TextView)v.findViewById(R.id.company);
        Title=(TextView)v.findViewById(R.id.title);
        Description=(TextView)v.findViewById(R.id.description);
        Status=(TextView)v.findViewById(R.id.status);
        projectCreate=(TextView)v.findViewById(R.id.fragment_project_add);
        item_color = getContext().getResources().getStringArray(R.array.dashboard_color);
        UserAuth userAuth=new UserAuth(getContext());

        if(userAuth.getRole().equalsIgnoreCase("0")){
            projectCreate.setVisibility(View.VISIBLE);
        }else{
            projectCreate.setVisibility(View.GONE);
        }
        //to be uncommented post notification
        projectCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_project_add);
            }
        });
        companyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(companyNameSort){
                    CompanyNameCompare companyNameCompare = new CompanyNameCompare();
                    Collections.sort(projectModelList, companyNameCompare);
                    adapter.notifyDataSetChanged();
                    companyNameSort=false;
                }else{
                    CompanyNameCompare companyNameCompare = new CompanyNameCompare();
                    Collections.sort(projectModelList, reverseOrder(companyNameCompare));
                    adapter.notifyDataSetChanged();
                    companyNameSort=true;
                }
            }
        });

        Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Titleclickced=", Toast.LENGTH_SHORT).show();
                if(TitleSort){
                    TitleCompare titleCompare=new TitleCompare();
                    Collections.sort(projectModelList,titleCompare);
                    adapter.notifyDataSetChanged();
                    TitleSort=false;
                }else{
                    TitleCompare titleCompare=new TitleCompare();
                    Collections.sort(projectModelList, Collections.reverseOrder(titleCompare));
                    adapter.notifyDataSetChanged();
                    TitleSort=true;
                }
            }
        });

        Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "descriptionclickced=", Toast.LENGTH_SHORT).show();
                if(DescriptionSort){
                    DescriptionCompare descriptionCompare=new DescriptionCompare();
                    Collections.sort(projectModelList,descriptionCompare);
                    adapter.notifyDataSetChanged();
                    DescriptionSort=false;
                }else{
                    DescriptionCompare descriptionCompare=new DescriptionCompare();
                    Collections.sort(projectModelList, Collections.reverseOrder(descriptionCompare));
                    adapter.notifyDataSetChanged();
                    DescriptionSort=true;
                }
            }
        });

        Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "statusclickced=", Toast.LENGTH_SHORT).show();
                if(StatusSort){
                    StatusCompare statusCompare=new StatusCompare();
                    Collections.sort(projectModelList,statusCompare);
                    adapter.notifyDataSetChanged();
                    StatusSort=false;
                }else{
                    StatusCompare statusCompare=new StatusCompare();
                    Collections.sort(projectModelList, Collections.<ProjectModel>reverseOrder(statusCompare));
                    adapter.notifyDataSetChanged();
                    StatusSort=true;
                }
            }
        });
        getProjectListData();

    }
    private void setData(List<ProjectModel> projectModelList){
       // adapter=new ProjectListAdapter(getContext(),projectModelList,this);
        //adapter=new ProjectListAdapterNew(getContext(),projectModelList,this);
        adapter=new ProjectListAdapterNew(getContext(),projectModelList,this,item_color);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);
    }
    private void getProjectListData(){
        System.out.println("getProjectListData");
        //GetDataService service= RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<ProjectListResponse> call=service.getProjectList();
        System.out.println("getProjectListData2");
        call.enqueue(new Callback<ProjectListResponse>() {
            @Override
            public void onResponse(Call<ProjectListResponse> call, Response<ProjectListResponse> response) {
                if(response.isSuccessful()){
                    System.out.println("getProjectListData3");
                    projectModelList=response.body().getProjectModelList();
                    progressbarlayout.setVisibility(View.GONE);
                    if(projectModelList!=null && !projectModelList.isEmpty() && projectModelList.size()>0){
                        setData(response.body().getProjectModelList());
                    }else{
                        Toast.makeText(getContext(),"No projects for you.", Toast.LENGTH_SHORT).show();
                    }

                    //Toast.makeText(getContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
                }else{
                    //Possibility no internet
                    progressbarlayout.setVisibility(View.GONE);
                    System.out.println("getProjectListData3 not successfull"+response.message());
                }
            }

            @Override
            public void onFailure(Call<ProjectListResponse> call, Throwable t) {
                progressbarlayout.setVisibility(View.GONE);
                System.out.println("getProjectListData3"+t.getMessage());
            }
        });

    }

    public void onItemClick(int position){
        //to be uncommented post notification
        Bundle args2 = new Bundle();
//        Bundle args2= TicketListFragmentDirections.navTicketingAction().getArguments();
        args2.putString("project_id",this.projectModelList.get(position).getId());
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_project_action,args2);
    }
    public void onItemClick2(int position){
//        to be uncommmented post notification
        Bundle args2 = new Bundle();
        //args2.putString("user_id",savedData.get(position).getId());
//        Bundle args2= TicketListFragmentDirections.navTicketingAction().getArguments();
        args2.putString("project_id",this.projectModelList.get(position).getId());
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_project_detail_ticket_list_action,args2);
    }


}
