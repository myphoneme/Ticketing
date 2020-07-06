package com.phoneme.ticketing.ui.productivity.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.productivity.adapter.TechsupportDashboardAdapter;
import com.phoneme.ticketing.ui.productivity.model.TechSupportDashboardViewModel;
import com.phoneme.ticketing.ui.productivity.model.TechsupportUserDataDashboardModel;
import com.phoneme.ticketing.ui.productivity.network.TechsupportDashboardResponse;
import com.phoneme.ticketing.ui.productivity.network.TechsupportDashboardResponse2;
import com.phoneme.ticketing.ui.productivity.network.TechsupportDashboardResponseCheck;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TechSupportDashboardFragment extends Fragment implements TechsupportDashboardAdapter.OnItemClickListener {

    private TechSupportDashboardViewModel slideshowViewModel;
    private RecyclerView recyclerView;
    private List<TechsupportUserDataDashboardModel> techsupportUserDataDashboardModelList;
    private RelativeLayout progressbarlayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(TechSupportDashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_techsupportdashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        slideshowViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressbarlayout=(RelativeLayout)view.findViewById(R.id.progressbar_relativelayout);
        progressbarlayout.setVisibility(View.VISIBLE);
        getTotalDashboardDaata();
//        TechsupportDashboardAdapter adapter = new TechsupportDashboardAdapter(getContext());
//        recyclerView.setAdapter(adapter);
//        GridLayoutManager manager= new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
    }

    //Below function just to check. It was added on 6th july 2020
//    public void getCheckingDashboardController(){
//        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
//        Call<TechsupportDashboardResponseCheck> call = service.getProductivityCheck();
//        System.out.println("getCheckingDashboardController1");
//        call.enqueue(new Callback<TechsupportDashboardResponseCheck>() {
//            @Override
//            public void onResponse(Call<TechsupportDashboardResponseCheck> call, Response<TechsupportDashboardResponseCheck> response) {
//                Toast.makeText(getContext(),"check data"+response.body(), Toast.LENGTH_SHORT).show();
//                System.out.println("getCheckingDashboardController2");
//
//            }
//
//            @Override
//            public void onFailure(Call<TechsupportDashboardResponseCheck> call, Throwable t) {
//                System.out.println("getCheckingDashboardController3");
//
//            }
//        });
//    }
    public void getTotalDashboardDaata(){
        System.out.println("getTotalDashboardDaata");
        //getCheckingDashboardController();
        //GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        //Call<TechsupportDashboardResponse> call = service.getProductivity();
        Call<TechsupportDashboardResponse> call = service.getProductivity();
        //Call<DashboardApi> call2 = service.getDashboardData();
        System.out.println("getTotalDashboardDaata2");

//        call2.enqueue(new Callback<DashboardApi>() {
//            @Override
//            public void onResponse(Call<DashboardApi> call, Response<DashboardApi> response) {
//                System.out.println("getTotalDashboardDaata3");
//            }
//
//            @Override
//            public void onFailure(Call<DashboardApi> call, Throwable t) {
//
//            }
//        });


        call.enqueue(new Callback<TechsupportDashboardResponse>() {
            @Override
            public void onResponse(Call<TechsupportDashboardResponse> call, Response<TechsupportDashboardResponse> response) {
                System.out.println("getTotalDashboardDaata3");
                //System.out.println("techsupportresponsesize0="+response.body());
                if(response.isSuccessful()){
                    System.out.println("techsupportresponsesize="+response.body());
                    techsupportUserDataDashboardModelList=response.body().getTechUsersData();
                    progressbarlayout.setVisibility(View.GONE);
                    setLiveData(techsupportUserDataDashboardModelList);
                }
                progressbarlayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TechsupportDashboardResponse> call, Throwable t) {
                System.out.println("getTotalDashboardDaata failure "+call.toString()+" "+t.getMessage());
                progressbarlayout.setVisibility(View.GONE);
            }
        });
    }
    public void setLiveData(List<TechsupportUserDataDashboardModel> techsupportUserDataDashboardModelList){
        TechsupportDashboardAdapter adapter = new TechsupportDashboardAdapter(getContext(), techsupportUserDataDashboardModelList,this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager= new GridLayoutManager(getContext(),1, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
    }

    public void onUserClicked(int position){
        String userid=this.techsupportUserDataDashboardModelList.get(position).getId();

        Bundle args = new Bundle();

        args.putString("user_id",userid);
        Toast.makeText(getContext(),"userid="+userid, Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_techsupport_user_productivity_detail,args);//to be uncommented in post notification
    }
    public  void onTicketAssignedClicked(int position){
        String userid=this.techsupportUserDataDashboardModelList.get(position).getId();

        Bundle args = new Bundle();

        args.putString("user_id",userid);
        Toast.makeText(getContext(),"userid="+userid, Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_techsupport_user_productivity_detail,args);//to be uncommented in post notification

    }
    public void onTicketsOpenClicked(int position){
        String userid=this.techsupportUserDataDashboardModelList.get(position).getId();
        Toast.makeText(getContext(),"tickets open clicked",Toast.LENGTH_SHORT).show();

        Bundle args = new Bundle();

        args.putString("user_id",userid);
        args.putString("solved","1");
        Toast.makeText(getContext(),"userid="+userid, Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_techsupport_user_productivity_detail,args);//to be uncommented in post notification

    }
    public void onTicketsSolvedClicked(int position){
        String userid=this.techsupportUserDataDashboardModelList.get(position).getId();

        Bundle args = new Bundle();

        args.putString("user_id",userid);
        args.putString("solved","0");
        Toast.makeText(getContext(),"userid="+userid, Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_techsupport_user_productivity_detail,args);//to be uncommented in post notification

    }
    public void onTicketsSolvedTodayClicked(int position){
        String userid=this.techsupportUserDataDashboardModelList.get(position).getId();

        Bundle args = new Bundle();

        args.putString("user_id",userid);
        args.putString("solved","3");
        Toast.makeText(getContext(),"userid="+userid, Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_techsupport_user_productivity_detail,args);//to be uncommented in post notification
    }
}