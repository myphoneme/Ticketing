package com.phoneme.ticketing.ui.productivity.fragments;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.productivity.adapter.TechsupportUserProductivityDetailsAdapter;
import com.phoneme.ticketing.ui.productivity.model.TechsupportUserDataDashboardModel;
import com.phoneme.ticketing.ui.productivity.network.TechsupportDashboardResponse;
import com.phoneme.ticketing.ui.productivity.network.TechsupportDashboardResponseList;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;
import com.phoneme.ticketing.ui.user.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.phoneme.ticketing.ui.techsupportdashboard.fragments.TechsupportUserProductivityDetailsFragmentDirections;

public class TechsupportUserProductivityDetailsFragment extends Fragment implements TechsupportUserProductivityDetailsAdapter.OnItemClickListener {
    private TextView name;
    private List<TicketModel> ticketModelList=new ArrayList<>();
    private List<UserModel> userModelList;
    private List<TechsupportUserDataDashboardModel> techsupportUserDataDashboardModelList;
    private RecyclerView recyclerView;
    private RelativeLayout progressbarlayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_techsupport_user_productivity_detail, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        name = (TextView) view.findViewById(R.id.user_name);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_user_productivity_list);
        progressbarlayout=(RelativeLayout)view.findViewById(R.id.progressbar_relativelayout);
        progressbarlayout.setVisibility(View.VISIBLE);
        String userid = getArguments().getString("user_id");

        //getUserProductivityData(userid);
        getUserProductivityDataList(userid);
    }


    private void getUserProductivityDataList(String uid) {
        GetDataService service = RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<TechsupportDashboardResponseList> call = service.getUserProductivityList(uid);
        call.enqueue(new Callback<TechsupportDashboardResponseList>() {
            @Override
            public void onResponse(Call<TechsupportDashboardResponseList> call, Response<TechsupportDashboardResponseList> response) {
                if (response != null && response.body() != null) {
                    progressbarlayout.setVisibility(View.GONE);
                    //Toast.makeText(getContext(), "size=" + response.body().getTicketModelListList().get(0).size(), Toast.LENGTH_LONG).show();
                    if (response.body().getUsers() != null) {
                        userModelList = response.body().getUsers();
                    }
                    if (response.body().getTechUsersData() != null) {
                        techsupportUserDataDashboardModelList = response.body().getTechUsersData();
                    }
                    setAdapterList( userModelList,response.body().getTicketModelListList(),techsupportUserDataDashboardModelList);
                }
                progressbarlayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TechsupportDashboardResponseList> call, Throwable t) {
                progressbarlayout.setVisibility(View.GONE);
            }
        });
    }
    private void getUserProductivityData(String uid) {
        GetDataService service = RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<TechsupportDashboardResponse> call = service.getUserProductivity(uid);
        call.enqueue(new Callback<TechsupportDashboardResponse>() {
            @Override
            public void onResponse(Call<TechsupportDashboardResponse> call, Response<TechsupportDashboardResponse> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getTicketModelList() != null) {
                        Toast.makeText(getContext(), "size=" + response.body().getTicketModelList().size(), Toast.LENGTH_LONG).show();
                        ticketModelList = response.body().getTicketModelList();
                    }

                    if (response.body().getUsers() != null) {
                        userModelList = response.body().getUsers();
                    }

                    if (response.body().getTechUsersData() != null) {
                        techsupportUserDataDashboardModelList = response.body().getTechUsersData();
                    }
                    setAdapter(userModelList, ticketModelList);
                }

            }

            @Override
            public void onFailure(Call<TechsupportDashboardResponse> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure=" + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    public void setAdapterList(List<UserModel> userModelList, List<List<TicketModel>> ticketModelsList, List<TechsupportUserDataDashboardModel> techUsersList){
        List<TicketModel> ticketModels=new ArrayList<>();
        //ticketModelList
        if(ticketModelsList!=null && !ticketModelsList.isEmpty() && ticketModelsList.size()>0){
            for(int i=0;i<ticketModelsList.size();i++){
                if(ticketModelsList.get(i)!=null && !ticketModelsList.get(i).isEmpty() && ticketModelsList.get(i).size()>0) {
                    for (int j = 0; j < ticketModelsList.get(i).size(); j++) {
                        //String userid=ticketModelsList.get(i).get(j).getCreated_by();
                        //String userimageurl=getImageforTechSupportUser(userid);
                        //ticketModelsList.get(i).get(j).setImage_of_user_creating_thread(userimageurl);

                        // ticketModels.add(ticketModelsList.get(i).get(j));
                        ticketModelList.add(ticketModelsList.get(i).get(j));
                    }
                }
            }
        }
        if (userModelList != null) {
            name.setText(userModelList.get(0).getName());
        }

        TechsupportUserProductivityDetailsAdapter adapter = new TechsupportUserProductivityDetailsAdapter(getContext(), this.ticketModelList, userModelList,this) ;
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);
    }
    public void setAdapter(List<UserModel> userModelList, List<TicketModel> ticketModels) {
        if (userModelList != null) {
            name.setText(userModelList.get(0).getName());
            TechsupportUserProductivityDetailsAdapter adapter = new TechsupportUserProductivityDetailsAdapter(getContext(), ticketModels, userModelList,this) ;
            recyclerView.setAdapter(adapter);

            LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(linearVertical);
        }
    }

    private String getImageforTechSupportUser(String userid){
        for(int i=0;i< techsupportUserDataDashboardModelList.size();i++){
            if(userid.equals(techsupportUserDataDashboardModelList.get(i).getId())){
                return techsupportUserDataDashboardModelList.get(i).getImage();
            }
        }
        return "";
    }

    public void onItemClick(int position){
        //Bundle args2=TicketListFragmentDirections.navTicketingAction().getArguments();

        //Bundle args2= TechsupportUserProductivityDetailsFragmentDirections.NavTechsupportUserProductivityDetail.getArguments();
        Bundle args2 = new Bundle();
        args2.putString("ticket_id",ticketModelList.get(position).getId());
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_techsupport_user_productivity_detail,args2);//to be uncommented in final post notification code
    }
}