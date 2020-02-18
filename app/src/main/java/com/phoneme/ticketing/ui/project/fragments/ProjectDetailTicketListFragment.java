package com.phoneme.ticketing.ui.project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.project.adapter.ProjectDetailTicketListAdapter;
import com.phoneme.ticketing.ui.project.model.ProjectEditGetModel;
import com.phoneme.ticketing.ui.project.network.ProjectEditGetResponse;
//import com.phoneme.ticketing.ui.ticketing.fragments.TicketListFragmentDirections;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;
import com.phoneme.ticketing.ui.ticketing.network.TicketResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectDetailTicketListFragment extends Fragment implements ProjectDetailTicketListAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private List<TicketModel> ticketModelList;
    private ProjectDetailTicketListAdapter adapter;
    private TextView projectTitle,projectDescription,projectDate,projectCompany;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project_detial_ticket_list, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState){
        super.onViewCreated(v, savedInstanceState);
        String projectid = getArguments().getString("project_id");
        Toast.makeText(getContext(),"projectid="+projectid, Toast.LENGTH_SHORT).show();

        recyclerView =(RecyclerView)v.findViewById(R.id.recyclerview_project_list);
        projectTitle=(TextView)v.findViewById(R.id.project_name);
        projectDescription=(TextView)v.findViewById(R.id.project_description);
        projectDate=(TextView)v.findViewById(R.id.project_date);
        projectCompany=(TextView)v.findViewById(R.id.project_company);
        getTicketsForAProject(projectid);
        getProjectDetails(projectid);
    }
    private void getProjectDetails(String id){
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<ProjectEditGetResponse> call = service.getProjectEdit(id);
        call.enqueue(new Callback<ProjectEditGetResponse>() {
            @Override
            public void onResponse(Call<ProjectEditGetResponse> call, Response<ProjectEditGetResponse> response) {
                if (response.isSuccessful()) {
                    //System.out.println("getProjectEditData successful" + response.body().getProjectEditGetModel().getCompay_name());
                    //setData(response.body().getProjectEditGetModel());
                    setProjectDetailsData(response.body().getProjectEditGetModel());
                }
            }

            @Override
            public void onFailure(Call<ProjectEditGetResponse> call, Throwable t) {
                System.out.println("getProjectEditData onFailure" + t.getMessage());
            }
        });
    }

    public void setProjectDetailsData(ProjectEditGetModel response){

        projectTitle.setText(response.getName());
        projectDescription.setText(response.getDesc());

        if(response.getCreated_at()!=null) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = formatter.parse(response.getCreated_at());
                SimpleDateFormat dt1 = new SimpleDateFormat("MMM dd yyyy");
                projectDate.setText(dt1.format(date));
            } catch (ParseException e) {

            }
        }
        projectCompany.setText(response.getCompay_name());

    }
    private void getTicketsForAProject(String projectid){
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<TicketResponse> call=service.getTicketsForaGivenProject(projectid);
        call.enqueue(new Callback<TicketResponse>() {
            @Override
            public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                Toast.makeText(getContext(),"ticketlist of project on response", Toast.LENGTH_SHORT).show();
                if(response!=null && response.body()!=null && response.body().getListOfTickets()!=null && response.body().getListOfTickets().size()>0){
                    ticketModelList=response.body().getListOfTickets();
                    setAdapter(ticketModelList);
                }
            }

            @Override
            public void onFailure(Call<TicketResponse> call, Throwable t) {
                Toast.makeText(getContext(),"ticketlist of project throwable", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void setAdapter(List<TicketModel> ticketModelList){
        adapter=new ProjectDetailTicketListAdapter(getContext(),ticketModelList,this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);
    }

    public void onTicketNumberClick(int position) {
        //to be uncommented post notification
//        Bundle args2 = TicketListFragmentDirections.navTicketingAction().getArguments();
//
//        args2.putString("ticket_id", ticketModelList.get(position).getId());
//        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
//        navController.navigate(R.id.nav_project_detail_ticket_view_action, args2);
    }
}
