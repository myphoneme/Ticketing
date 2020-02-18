package com.phoneme.ticketing.ui.ticketing.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.UserAuth;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;


import java.util.List;

public class TicketThreadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mcontext;
    private List<TicketModel> ticketModelList;
    private OnItemClickListener listener;
    private int TYPE_THREAD = 0, TYPE_RESPOND = 1,TYPE_REOPEN=2;
    private String status;
    private String userRole=new String();

    public TicketThreadAdapter(Context context, List<TicketModel> ticketModelList) {
        this.mcontext = context;
        this.ticketModelList = ticketModelList;
    }

    public TicketThreadAdapter(Context context, List<TicketModel> ticketModelList, OnItemClickListener listener) {
        this.mcontext = context;
        this.ticketModelList = ticketModelList;
        this.listener = listener;
        this.status=this.ticketModelList.get(0).getStatus();
        UserAuth auth=new UserAuth(this.mcontext);
        this.userRole=auth.getRole();
    }
    public class RespondViewHolder extends RecyclerView.ViewHolder {
        private Button Respond,Close;
        private EditText introtext;
        public RespondViewHolder(View v){
            super(v);
            Respond=(Button)v.findViewById(R.id.respond);
            Close = (Button)v.findViewById(R.id.close_ticket);
            if(userRole.equals("2")){
                Close.setText("Close Ticket");
            }else{
                Close.setText("Done");
            }
            introtext=(EditText)v.findViewById(R.id.edit_respond);
            Respond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String respondText=introtext.getText().toString();
                    respond(respondText);
                }
            });
            Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String respondText=introtext.getText().toString();
                    String doneorclose=Close.getText().toString();
                    if(doneorclose.equalsIgnoreCase("Done")){
                        Done(respondText);
                    }else{
                        Close(respondText);
                    }

                }
            });
        }

    }
    public class ReopenViewHolder extends RecyclerView.ViewHolder{
        private Button Reopen;
        public ReopenViewHolder(View v){
            super(v);
            Reopen =(Button)v.findViewById(R.id.reopen);
            Reopen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Reopen();
                }
            });
        }
    }
    private void Done(String response){
        this.listener.onDone(response);
    }
    private void Close(String response){
        this.listener.onClose(response);
    }
    private void Reopen(){
        this.listener.onReopenClick();
    }
    private void respond(String response){
        this.listener.onRespond(response);
    }
    public class ThreadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView ticketTitle, description, status, ticketNumber, priority, serialNumber, createdBy, lastupdatedby, projectName;
        private CardView cardView;
        private SimpleDraweeView userImage;
        TicketModel ticketModel;

        public ThreadViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.cardid);
            description = (TextView) v.findViewById(R.id.description);
            lastupdatedby = (TextView) v.findViewById(R.id.created_at);
            createdBy = (TextView) v.findViewById(R.id.user_name);
            userImage = (SimpleDraweeView) v.findViewById(R.id.user_image);
        }

        @Override
        public void onClick(View v) {

        }

        public void setData(TicketModel item) {
            this.ticketModel = item;
            UserAuth auth=new UserAuth(mcontext);


            if(ticketModel!=null ) {
                createdBy.setText(this.ticketModel.getCreated_by_name());
                lastupdatedby.setText(this.ticketModel.getCreated_at());
                Uri uri = Uri.parse(this.ticketModel.getImage_of_user_creating_thread());
                userImage.setImageURI(uri);
                description.setText(Html.fromHtml(this.ticketModel.getDesc()));
            }
        }


    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_THREAD) {
            ((ThreadViewHolder) viewHolder).setData(this.ticketModelList.get(position));
        }
//        else {
//            ((EmailViewHolder) viewHolder).setEmailDetails(employees.get(position));
//        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        //View view = LayoutInflater.from(mcontext).inflate(R.layout.single_item_ticket_main,parent,false);
        if (i == TYPE_THREAD) {
            //Toast.makeText( this.mcontext, "postProjectAdd1", Toast.LENGTH_SHORT).show();
            View view = LayoutInflater.from(mcontext).inflate(R.layout.single_item_ticket_thread, parent, false);
            return new ThreadViewHolder(view);
        } else if(i==TYPE_RESPOND){
            //Toast.makeText( this.mcontext, "postProjectAdd2", Toast.LENGTH_SHORT).show();
            View view = LayoutInflater.from(mcontext).inflate(R.layout.single_item_ticket_thread_respond, parent, false);
            return new RespondViewHolder(view);
        }else{
            View view = LayoutInflater.from(mcontext).inflate(R.layout.single_item_ticket_thread_reopen, parent, false);
            return new ReopenViewHolder(view);
        }
    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        if (position < this.ticketModelList.size()) {
//            holder.setData(this.ticketModelList.get(position));
//        }
//
//    }

    @Override
    public int getItemCount() {
        //return 3;
        return this.ticketModelList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < this.ticketModelList.size()) {
            return TYPE_THREAD;
        } else if(status.equals("0")){
            return TYPE_REOPEN;
        }else{
            return TYPE_RESPOND;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onReopenClick();
        void onRespond(String response);
        void onClose(String response);
        void onDone(String response);
    }
}
