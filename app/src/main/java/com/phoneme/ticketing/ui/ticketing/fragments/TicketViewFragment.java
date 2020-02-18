package com.phoneme.ticketing.ui.ticketing.fragments;

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
import com.phoneme.ticketing.helper.VerticalSpaceItemDecoration;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.ticketing.adapter.TicketThreadAdapter;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;
import com.phoneme.ticketing.ui.ticketing.network.TicketGetViewResponse;
import com.phoneme.ticketing.ui.ticketing.network.TicketPostViewResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketViewFragment extends Fragment implements TicketThreadAdapter.OnItemClickListener{
    private TextView ticketName,Status,Priority,ticketNumber,project_name;
    private RecyclerView recyclerView;
    private List<TicketModel> ThreadList;
    private TicketModel ticket;
    private RelativeLayout progressbarlayout;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root=inflater.inflate(R.layout.fragment_ticket_view,container,false);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceStat){
        String ticketid=getArguments().getString("ticket_id");
//        Toast.makeText(getContext(),"TicketEditFragment ticketid="+ticketid,Toast.LENGTH_SHORT).show();
        String id=new String();
        if(ticketid!=null && ticketid.length()>0){
            id=ticketid;
        }else{ //else part not there in final code
            id="136";// "167" "301" "237" "274" "292" //This part need to go
        }
        progressbarlayout=(RelativeLayout)view.findViewById(R.id.progressbar_relativelayout);
        project_name=(TextView)view.findViewById(R.id.project_name);
        ticketName=(TextView)view.findViewById(R.id.tickt_name);
        Status=(TextView)view.findViewById(R.id.status);
        Priority=(TextView)view.findViewById(R.id.priority);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        ticketNumber=(TextView)view.findViewById(R.id.tickt_number);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(10);
        recyclerView.addItemDecoration(itemDecoration);
        progressbarlayout.setVisibility(View.VISIBLE);

        getData(id);
    }
    private void setData(TicketGetViewResponse response){
        if(response.getThreads()!=null){
            ThreadList=response.getThreads();
        }
        ticket=response.getTicketModel();
        String ticket_name=response.getTicketModel().getName();
        String ticket_number=response.getTicketModel().getTicket_no();
        String s=response.getTicketModel().getStatus();
        String priority=response.getTicketModel().getPriority();
        String projectName=response.getProjectModel().getName();

        ticketName.setText(ticket_name);
        ticketNumber.setText("#"+ticket_number);

        if(s.equals("0")){
            Status.setText("Status \n Closed");
        }else if(s.equals("1")){
            Status.setText("Status \n Open");
        }
        Priority.setText("Priority \n"+priority);
        project_name.setText("Project \n"+projectName);


        TicketThreadAdapter adapter=new TicketThreadAdapter(getContext(),ThreadList,this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);

    }
    private void getData(String id){
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<TicketGetViewResponse> call=service.getTicketView(id);
        call.enqueue(new Callback<TicketGetViewResponse>() {
            @Override
            public void onResponse(Call<TicketGetViewResponse> call, Response<TicketGetViewResponse> response) {
                if(response!=null && response.isSuccessful() && response.body()!=null ){
                    setData(response.body());
                    progressbarlayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"getData=onresponse", Toast.LENGTH_LONG).show();
                }else{
                    progressbarlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TicketGetViewResponse> call, Throwable t) {
                progressbarlayout.setVisibility(View.GONE);
                Toast.makeText(getContext(),"getData=onFailure", Toast.LENGTH_LONG).show();

            }
        });

    }
    public void onItemClick(int position){

    }

    public void onRespond(String response){
        HashMap<String, String> map=new HashMap<String, String>();
        map.put("id",ticket.getId());
        map.put("thread_id",ticket.getThread_id());
        map.put("introtext",response);
        map.put("respond","respond");
        postRespond(map);
    }

    private void postRespond(HashMap<String, String> data){
        Toast.makeText(getContext()," postRespond=", Toast.LENGTH_LONG).show();
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<TicketPostViewResponse> call=service.postTicketView(data);
        call.enqueue(new Callback<TicketPostViewResponse>() {
            @Override
            public void onResponse(Call<TicketPostViewResponse> call, Response<TicketPostViewResponse> response) {
                if(response.isSuccessful() && response.body()!=null && response.body().getMessage()!=null) {
                    Toast.makeText(getContext(), "Message="+response.body().getMessage(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Message=Some erro", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<TicketPostViewResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Message="+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void onClose(String response){
        HashMap<String, String> map=new HashMap<String, String>();
        map.put("id",ticket.getId());
        map.put("thread_id",ticket.getThread_id());
        map.put("introtext",response);
        map.put("close","close");
        postClose(map);
        Toast.makeText(getContext(),"threadID="+ticket.getThread_id(), Toast.LENGTH_LONG).show();

    }

    private void postClose(HashMap<String, String> data){
        Toast.makeText(getContext()," postClose=", Toast.LENGTH_LONG).show();
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<TicketPostViewResponse> call=service.postTicketView(data);
        call.enqueue(new Callback<TicketPostViewResponse>() {
            @Override
            public void onResponse(Call<TicketPostViewResponse> call, Response<TicketPostViewResponse> response) {
                Toast.makeText(getContext(),"postReopen=onResponse", Toast.LENGTH_LONG).show();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.popBackStack();
            }

            @Override
            public void onFailure(Call<TicketPostViewResponse> call, Throwable t) {
                Toast.makeText(getContext(),"postReopen=onFailure"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onDone(String response){
        HashMap<String, String> map=new HashMap<String, String>();
        map.put("id",ticket.getId());
        map.put("thread_id",ticket.getThread_id());
        map.put("introtext",response);
        map.put("done","done");
        postDone(map);

    }

    private void postDone(HashMap<String, String> data){
        Toast.makeText(getContext()," postDone=", Toast.LENGTH_LONG).show();
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<TicketPostViewResponse> call=service.postTicketView(data);
        call.enqueue(new Callback<TicketPostViewResponse>() {
            @Override
            public void onResponse(Call<TicketPostViewResponse> call, Response<TicketPostViewResponse> response) {
                Toast.makeText(getContext(),"postReopen=onResponse", Toast.LENGTH_LONG).show();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.popBackStack();
            }

            @Override
            public void onFailure(Call<TicketPostViewResponse> call, Throwable t) {
                Toast.makeText(getContext(),"postReopen=onFailure", Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public  void onReopenClick(){
        HashMap<String, String> map=new HashMap<String, String>();
        map.put("id",ticket.getId());
        map.put("reopen","reopen");
        map.put("thread_id",ticket.getThread_id());
        Toast.makeText(getContext(),"threadidreopen="+ticket.getThread_id(), Toast.LENGTH_LONG).show();
        postReopen(map);
    }
    private void postReopen(HashMap<String, String> data){

        Toast.makeText(getContext()," postReopen=", Toast.LENGTH_LONG).show();
        GetDataService service= RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);
        Call<TicketPostViewResponse> call=service.postTicketView(data);
        call.enqueue(new Callback<TicketPostViewResponse>() {
            @Override
            public void onResponse(Call<TicketPostViewResponse> call, Response<TicketPostViewResponse> response) {
                Toast.makeText(getContext(),"postReopen=onResponse", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<TicketPostViewResponse> call, Throwable t) {
                Toast.makeText(getContext(),"postReopen=onFailure"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
