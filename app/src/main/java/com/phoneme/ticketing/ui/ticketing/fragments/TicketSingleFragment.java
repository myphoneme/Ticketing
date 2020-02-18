package com.phoneme.ticketing.ui.ticketing.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;
import com.phoneme.ticketing.ui.ticketing.network.TicketSingleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketSingleFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView name,description,ticket_number,project_fullname,priority,status;
    private TextView created_by,created_at;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_ticket_single, container, false);

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String id="286";
        name=(TextView) view.findViewById(R.id.project_name);
        description = (TextView) view.findViewById(R.id.description);
        ticket_number = (TextView)view.findViewById(R.id.ticket_number);
        project_fullname = (TextView)view.findViewById(R.id.project_fullname);
        priority = (TextView)view.findViewById(R.id.priority);
        status  =(TextView)view.findViewById(R.id.status);
        created_by = (TextView)view.findViewById(R.id.created_by);
        created_at = (TextView)view.findViewById(R.id.created_at);
        getSingleTicketData(id);
    }
    private void setData(TicketModel ticketModel, List<String> images){
        name.setText(ticketModel.getName());
        description.setText(ticketModel.getDesc());
        ticket_number.setText("#"+ticketModel.getTicket_no());
        project_fullname.setText(ticketModel.getProject_id());
        priority.setText("Priority "+ticketModel.getPriority());
        status.setText("Status "+ticketModel.getStatus());
        created_by.setText(ticketModel.getCreated_by());
        created_at.setText(ticketModel.getCreated_at());
    }
    private void getSingleTicketData(String id){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<TicketSingleResponse> call = service.getTicketView1(id);
        call.enqueue(new Callback<TicketSingleResponse>() {
            @Override
            public void onResponse(Call<TicketSingleResponse> call, Response<TicketSingleResponse> response) {
                if(response.isSuccessful()){
                    setData(response.body().getTicket(),response.body().getTicketimages());
                }
                //name.setText("ysysysysy");
            }

            @Override
            public void onFailure(Call<TicketSingleResponse> call, Throwable t) {

            }
        });


    }
}
