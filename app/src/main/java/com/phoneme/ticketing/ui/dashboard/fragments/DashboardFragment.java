package com.phoneme.ticketing.ui.dashboard.fragments;

import android.content.res.Configuration;
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
import com.phoneme.ticketing.user.UserAuth;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.dashboard.DashboardApi;
import com.phoneme.ticketing.ui.dashboard.adapter.DashboardAdapter;
import com.phoneme.ticketing.ui.dashboard.model.DashboardModel;
import com.phoneme.ticketing.ui.dashboard.model.DashboardModel1;
import com.phoneme.ticketing.ui.dashboard.model.DashboardSubheading;
import com.phoneme.ticketing.ui.dashboard.model.DashboardViewModel;
import com.phoneme.ticketing.ui.dashboard.network.DashboardMainModel;
import com.phoneme.ticketing.ui.dashboard.network.DashboardMainResponse;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment implements DashboardAdapter.OnItemClickListener {

    private DashboardViewModel homeViewModel;//learn how to use it
    private RecyclerView recyclerView;
    private RelativeLayout progressbarlayout;

    List<DashboardModel1> dashboardModel1List = new ArrayList<DashboardModel1>();
    List<DashboardModel> dashboardModelList = new ArrayList<DashboardModel>();

    private List<DashboardMainModel> dashboardMainModelList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //Toast.makeText(getContext(), "dashboardfragmentonCreateView", Toast.LENGTH_LONG).show();
        getActivity().getRequestedOrientation();

        return root;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Toast.makeText(getContext(), "Dashboardfragment orientation changed=", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(), "Dashboardfragment orientation changed=", Toast.LENGTH_LONG).show();//NOt working
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            getDashboardData();
            getDashboardLiveData();
        //Toast.makeText(getContext(), "Dashboardfragment onViewCreated width="+view.getWidth(), Toast.LENGTH_LONG).show();

         dashboardModelList = new ArrayList<DashboardModel>();
            datasetNew(dashboardModelList);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressbarlayout=(RelativeLayout)view.findViewById(R.id.progressbar_relativelayout);
        progressbarlayout.setVisibility(View.VISIBLE);

//        DashboardAdapter adapter = new DashboardAdapter(getContext(), dashboardModelList,dashboardMainModelList,this);
//            recyclerView.setAdapter(adapter);
//            GridLayoutManager manager;
//            manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
//
//            recyclerView.setLayoutManager(manager);

    }
    private void setAdapter(List<DashboardMainModel> dashboardMainModelList){
        DashboardAdapter adapter = new DashboardAdapter(getContext(), dashboardModelList,dashboardMainModelList,this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager;
        manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(manager);
    }

    private void datasetNew(List<DashboardModel> dashboardModelList) {
        DashboardModel item1 = new DashboardModel();
        item1.setTotal("69");
        item1.setColorHex("#00c0ef");
        item1.setHeading("Tickets");
        item1.setIconurl("https://img.icons8.com/material/24/000000/cloud-network.png");
        item1.setType("TICKET");

        DashboardSubheading subheading1 = new DashboardSubheading();
        subheading1.setSubheading("Total1");
        subheading1.setSubheading_value("7");
        DashboardSubheading subheading2 = new DashboardSubheading();
        subheading2.setSubheading("Total2");
        subheading2.setSubheading_value("8");
        DashboardSubheading subheading3 = new DashboardSubheading();
        subheading3.setSubheading("Total3");
        subheading3.setSubheading_value("9");

        List<DashboardSubheading> dashboardSubheadingList = new ArrayList<DashboardSubheading>();
        dashboardSubheadingList.add(subheading1);
        dashboardSubheadingList.add(subheading2);
        dashboardSubheadingList.add(subheading3);

        item1.setDashboardSubheadings(dashboardSubheadingList);

        DashboardModel item2 = new DashboardModel();
        item2.setTotal("8");
        item2.setColorHex("#00a65a");
        item2.setHeading("Projects");
        item2.setIconurl("https://img.icons8.com/material/24/000000/chamfer.png");
        item2.setDashboardSubheadings(dashboardSubheadingList);
        item2.setType("PROJECTS");

        DashboardModel item3 = new DashboardModel();
        item3.setTotal("8");
        item3.setColorHex("#f39c12");
        item3.setHeading("Users");
        item3.setIconurl("https://img.icons8.com/material/24/000000/invert-selection--v1.png");
        item3.setDashboardSubheadings(dashboardSubheadingList);
        item3.setType("USERS");

        DashboardModel item4 = new DashboardModel();
        item4.setTotal("65");
        item4.setColorHex("#dd4b39");
        item4.setHeading("Unique visitors");
        item4.setIconurl("https://img.icons8.com/material/24/000000/3d-printer.png");
        item4.setDashboardSubheadings(dashboardSubheadingList);
        item4.setType("VISITORS");

        dashboardModelList.add(item1);
        dashboardModelList.add(item2);
        dashboardModelList.add(item3);
        dashboardModelList.add(item4);
    }

    private void dataset() {
        DashboardModel1 item1 = new DashboardModel1("1", "#00c0ef", "Tickets", "14", "54");
        DashboardModel1 item2 = new DashboardModel1("2", "#00a65a", "Projects");

        DashboardModel1 item3 = new DashboardModel1("3", "#f39c12", "Users");

        DashboardModel1 item4 = new DashboardModel1("4", "#dd4b39", "Unique Visitos");

//        DashboardModel1 item5=new DashboardModel1("5","#00c0ef","Tickets","18","34");
//
//        DashboardModel1 item6=new DashboardModel1("6","#00a65a","Projects new");
//        DashboardModel1 item7=new DashboardModel1("7","#f39c12","Users Old");
//        DashboardModel1 item8=new DashboardModel1("8","#dd4b39","Visitors special");

        dashboardModel1List.add(item1);
        dashboardModel1List.add(item2);
        dashboardModel1List.add(item3);
        dashboardModel1List.add(item4);
//        dashboardModel1List.add(item5);
//        dashboardModel1List.add(item6);
//        dashboardModel1List.add(item7);
//        dashboardModel1List.add(item8);
    }

    private void getDashboardData() {
        System.out.println("getDashboardData");
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DashboardApi> call = service.getDashboardData();
        System.out.println("getDashboardData2");
        call.enqueue(new Callback<DashboardApi>() {
            @Override
            public void onResponse(Call<DashboardApi> call, Response<DashboardApi> response) {
                System.out.println("getDashboardData3" + response.toString());
                if (response.isSuccessful()) {
                    System.out.println("data got from server");
                    System.out.println("response dashdata" + response.body().dashdata.closeticketcount);
                    progressbarlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DashboardApi> call, Throwable t) {
                progressbarlayout.setVisibility(View.GONE);
            }
        });
    }

    private void getDashboardLiveData(){
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<DashboardMainResponse> call=service.getDashboardMainData();//Might have to pass userid in future
        call.enqueue(new Callback<DashboardMainResponse>() {
            @Override
            public void onResponse(Call<DashboardMainResponse> call, Response<DashboardMainResponse> response) {
                if(response.isSuccessful() && response.body()!=null ){
                    progressbarlayout.setVisibility(View.GONE);
                    dashboardMainModelList=response.body().getDashboardMainModelList();
                    System.out.println("dashboardlivedata"+dashboardMainModelList);
                    //Toast.makeText(getContext(), "Dashboardfragment live data"+ dashboardMainModelList, Toast.LENGTH_LONG).show();
                    setAdapter(dashboardMainModelList);
                }
                progressbarlayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DashboardMainResponse> call, Throwable t) {
                progressbarlayout.setVisibility(View.GONE);
            }
        });
    }

    public void onItemClick(int position){
        String type=dashboardModelList.get(position).getType();
        if(type.equals("TICKET")){
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_ticketing);
        }else if(type.equals("PROJECTS")){
            UserAuth userAuth=new UserAuth(getContext());
            String role=userAuth.getRole();
            if(!role.equals("2")) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_project_engagement);
            }else{
                Toast.makeText(getContext(), "Not allowed for you", Toast.LENGTH_LONG).show();

            }
        }else if(type.equals("USERS")){
//            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
//            navController.navigate(R.id.nav_user_list);
//
//            navController.navigate(R.id.nav_dashboard_user_action);

            UserAuth userAuth=new UserAuth(getContext());
            String userid=userAuth.getId();
            Bundle args = new Bundle();

            args.putString("user_id",userid);
            Toast.makeText(getContext(),"on click"+userid, Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_dashboard_user_action,args);//uncomment in final code post notificaiton
        }
    }

   public void onTicketNumberClick(int position){

   }

}