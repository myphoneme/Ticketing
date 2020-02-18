package com.phoneme.ticketing.ui.user.fragments;

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
import com.phoneme.ticketing.ui.user.UserListAdapter;
import com.phoneme.ticketing.ui.user.UserModel;
import com.phoneme.ticketing.ui.user.network.UserListResponse;
import com.phoneme.ticketing.ui.user.sorting.UserEmailCompare;
import com.phoneme.ticketing.ui.user.sorting.UserMobileNumberCompare;
import com.phoneme.ticketing.ui.user.sorting.UserNameCompare;
import com.phoneme.ticketing.ui.user.sorting.UserSNCompare;
import com.phoneme.ticketing.ui.user.sorting.UserStatusCompare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListFragment extends Fragment implements UserListAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private List<UserModel> savedData=new ArrayList<>();
    private TextView userSN,userName,userMobileNumber,userEmail,userStatus,userCreate;
    private Boolean userSNSort=true,userNameSort=true,userMobileNumberSort=true, userEmailSort=true,userStatusSort=true;
    UserListAdapter adapter;
    private RelativeLayout progressbarlayout;
    //private TextView detailText;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_user_list, container, false);

        return root;
    }

    private void getUserList(){
        System.out.println("getUserList");
        //GetDataService service =  RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<UserListResponse> call=service.getUserList();
        System.out.println("getUserList2");
        call.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                System.out.println("userlistdataworking"+response.body().getUserModelList());
                savedData=response.body().getUserModelList();
                progressbarlayout.setVisibility(View.GONE);
                setAdapter(response.body().getUserModelList());
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                System.out.println("userlistdataworking onfailure"+t.getMessage());
                progressbarlayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressbarlayout=(RelativeLayout)view.findViewById(R.id.progressbar_relativelayout);
        progressbarlayout.setVisibility(View.VISIBLE);
        userSN=(TextView)view.findViewById(R.id.fragment_user_sn);
        userName=(TextView)view.findViewById(R.id.fragment_user_name);
        userMobileNumber=(TextView)view.findViewById(R.id.fragment_user_mobile_number);
        userEmail=(TextView)view.findViewById(R.id.fragment_user_email);
        userStatus=(TextView)view.findViewById(R.id.fragment_user_status);

        userCreate=(TextView)view.findViewById(R.id.fragment_user_create);

        userCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_user_add);
            }
        });
        userSN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userSNSort){
                    UserSNCompare userSNCompare=new UserSNCompare();
                    Collections.sort(savedData,userSNCompare);
                    adapter.notifyDataSetChanged();
                    userSNSort=false;
                }else{
                    UserSNCompare userSNCompare=new UserSNCompare();
                    Collections.sort(savedData, Collections.reverseOrder(userSNCompare));
                    adapter.notifyDataSetChanged();
                    userSNSort=true;
                }
            }
        });
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userNameSort){
                    UserNameCompare userNameCompare=new UserNameCompare();
                    Collections.sort(savedData,userNameCompare);
                    adapter.notifyDataSetChanged();
                    userNameSort=false;
                }else{
                    UserNameCompare userNameCompare=new UserNameCompare();
                    Collections.sort(savedData, Collections.reverseOrder(userNameCompare));
                    adapter.notifyDataSetChanged();
                    userNameSort=true;
                }
            }
        });

        userMobileNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userMobileNumberSort){
                    UserMobileNumberCompare userMobileNumberCompare=new UserMobileNumberCompare();
                    Collections.sort(savedData,userMobileNumberCompare);
                    adapter.notifyDataSetChanged();
                    userMobileNumberSort=false;
                }else{
                    UserMobileNumberCompare userMobileNumberCompare=new UserMobileNumberCompare();
                    Collections.sort(savedData, Collections.reverseOrder(userMobileNumberCompare));
                    adapter.notifyDataSetChanged();
                    userMobileNumberSort=true;
                }
            }
        });

        userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userEmailSort){
                    UserEmailCompare userEmailCompare=new UserEmailCompare();
                    Collections.sort(savedData,userEmailCompare);
                    adapter.notifyDataSetChanged();
                    userEmailSort=false;}
                else{
                    UserEmailCompare userEmailCompare=new UserEmailCompare();
                    Collections.sort(savedData, Collections.reverseOrder(userEmailCompare));
                    adapter.notifyDataSetChanged();
                    userEmailSort=true;
                }
            }
        });
        userStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userStatusSort){
                    UserStatusCompare userStatusCompare=new UserStatusCompare();
                    Collections.sort(savedData,userStatusCompare);
                    adapter.notifyDataSetChanged();
                    userStatusSort=false;
                }else{
                    UserStatusCompare userStatusCompare=new UserStatusCompare();
                    Collections.sort(savedData, Collections.reverseOrder(userStatusCompare));
                    adapter.notifyDataSetChanged();
                    userStatusSort=true;
                }
            }
        });

//        detailText = (TextView)view.findViewById(R.id.detailclick);
//        detailText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
//                navController.navigate(R.id.detailclick);
//            }
//        });
        getUserList();

    }
    public void setAdapter(List<UserModel> userModelList){
        adapter=new UserListAdapter(getContext(),userModelList,this);
        LinearLayoutManager manager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }
    public void onItemClick(int position){
        Bundle args = new Bundle();
        args.putString("user_id",savedData.get(position).getId());
        Toast.makeText(getContext(),"on click"+savedData.get(position).getId(), Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.detailclick,args);
    }
}
