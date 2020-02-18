package com.phoneme.ticketing.ui.inframonitoring;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.helper.VerticalSpaceItemDecoration;
import com.phoneme.ticketing.interfaces.GetDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfraMonitoringFragment extends Fragment implements InfraMonitoringAdapter.OnItemClickListener{
    private EditText commandEdit;
    private Button button;
    private RelativeLayout progressbarlayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<InfraMonitoringModel> infraMonitoringModelList=new ArrayList<InfraMonitoringModel>();
    private InfraMonitoringAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_infra_monitoring, container, false);
        return root;
    }

    private void postData(String command) {
        HashMap<String, String> map = new HashMap<>();
        map.put("command", command);
        GetDataService service = RetrofitClientInstance.APIInfraMonitoringCommand(getActivity()).create(GetDataService.class);
        Call<String> call = service.postInframonitoringDataCommand(map);
        Toast.makeText(getContext(),"postData function", Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response!=null && response.body()!=null) {
                    Toast.makeText(getContext(),"postData onResponse function"+response.body().toString(), Toast.LENGTH_LONG).show();
                    infraMonitoringModelList.get(0).setOutput(response.body().toString());
                    //adapter.setCommandOutput(response.body().toString());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(),"postData onFailure function", Toast.LENGTH_LONG).show();
            }
        });
//        call.enqueue(new Callback<InfraMonitoringResponse>() {
//            @Override
//            public void onResponse(Call<InfraMonitoringResponse> call, Response<InfraMonitoringResponse> response) {
//                if (response != null && response.isSuccessful()) {
////                    if(){
////
////                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<InfraMonitoringResponse> call, Throwable t) {
//
//            }
//        });

    }

    private void getData() {
        GetDataService service = RetrofitClientInstance.APIInfraMonitoring(getActivity()).create(GetDataService.class);
        Call<InfraMonitoringResponse> call = service.getInframonitoringData();
        call.enqueue(new Callback<InfraMonitoringResponse>() {
            @Override
            public void onResponse(Call<InfraMonitoringResponse> call, Response<InfraMonitoringResponse> response) {
                if (response != null && response.isSuccessful()) {
                    Toast.makeText(getContext(),"infraMonitoring get onResponse function", Toast.LENGTH_LONG).show();
                    if (response.body() != null) {
                        Toast.makeText(getContext(),"infraMonitoring get onResponse!=null  function", Toast.LENGTH_LONG).show();
                        if (response.body().getExecutedoutput() != null) {
                            InfraMonitoringModel model3=new InfraMonitoringModel();
                            model3.setName("executedoutput");
                            model3.setOutput(response.body().getExecutedoutput());
                            infraMonitoringModelList.add(model3);
                        }

                        if (response.body().getAccesslogresult() != null) {
                            InfraMonitoringModel model1=new InfraMonitoringModel();
                            model1.setName("accesslogresult");
                            model1.setOutput(response.body().getAccesslogresult());
                            infraMonitoringModelList.add(model1);
                        }

                        if (response.body().getErrorlogresult() != null) {
                            InfraMonitoringModel model2=new InfraMonitoringModel();
                            model2.setName("errorlogresult");
                            model2.setOutput(response.body().getErrorlogresult());
                            infraMonitoringModelList.add(model2);
                        }



                        if (response.body().getMysqllogresult() != null) {
                            InfraMonitoringModel model4=new InfraMonitoringModel();
                            model4.setName("mysqllogresult");
                            model4.setOutput(response.body().getMysqllogresult());
                            infraMonitoringModelList.add(model4);
                        }

                        if (response.body().getPingresult() != null) {
                            InfraMonitoringModel model5=new InfraMonitoringModel();
                            model5.setName("pingresult");
                            model5.setOutput(response.body().getPingresult());
                            infraMonitoringModelList.add(model5);
                        }
                        setAdapter(infraMonitoringModelList);
                    }else{
                        //progressBar.setVisibility(View.GONE);
                        progressbarlayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<InfraMonitoringResponse> call, Throwable t) {
                Toast.makeText(getContext(),"infraMonitoring get onFailure function "+t.getMessage(), Toast.LENGTH_LONG).show();
                progressbarlayout.setVisibility(View.GONE);
            }
        });
    }
    private void setAdapter(List<InfraMonitoringModel> infraMonitoringModelList){
//        InfraMonitoringAdapter adapter=new InfraMonitoringAdapter(getContext(),infraMonitoringModelList);
        adapter=new InfraMonitoringAdapter(getContext(),infraMonitoringModelList,this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearVertical);
        //progressBar.setVisibility(View.GONE);
        progressbarlayout.setVisibility(View.GONE);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        commandEdit = (EditText) view.findViewById(R.id.command);
        button = (Button) view.findViewById(R.id.submit);
        //progressBar=(ProgressBar)view.findViewById(R.id.simpleProgressBar);
        progressbarlayout=(RelativeLayout)view.findViewById(R.id.progressbar_relativelayout);
        //progressBar.setVisibility(View.VISIBLE);
        progressbarlayout.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_inframonitoring);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(10);
        recyclerView.addItemDecoration(itemDecoration);

        getData();
    }
    @Override
    public void onItemClick(String command){
        Toast.makeText(getContext(),"onItemClick function", Toast.LENGTH_LONG).show();
        postData(command);
    }
}
