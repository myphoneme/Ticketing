package com.phoneme.ticketing.ui.project.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.project.model.ProjectEditGetModel;
import com.phoneme.ticketing.ui.project.network.ProjectEditGetResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectDetailOnlyFragment extends Fragment {

    private SimpleDraweeView projecticon;
    private TextView projectTitle,projectDescription,projectDate,projectCompany,team,projectManager,status;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project_detial_only, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState){
        super.onViewCreated(v, savedInstanceState);
        String projectid = getArguments().getString("project_id",null);
        projectTitle=(TextView)v.findViewById(R.id.project_name);
        projectDescription=(TextView)v.findViewById(R.id.project_description);
        projectDate=(TextView)v.findViewById(R.id.project_date);
        projectCompany=(TextView)v.findViewById(R.id.project_company);
        projecticon=(SimpleDraweeView) v.findViewById(R.id.child_image);
        team=(TextView)v.findViewById(R.id.project_team);
        projectManager=(TextView)v.findViewById(R.id.project_manager);
        status=(TextView)v.findViewById(R.id.project_status);
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
        Uri uri= Uri.parse(response.getImages());
        projecticon.setImageURI(uri);
        team.setText(response.getTeam());
        projectManager.setText(response.getProjectEditAllocatedUserModelList().get(0).getUser_name());
        if(response.getStatus().equalsIgnoreCase("1")){
            status.setText("Active");
        }
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
}
