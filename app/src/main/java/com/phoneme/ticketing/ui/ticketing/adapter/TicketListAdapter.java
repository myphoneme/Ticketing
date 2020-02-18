package com.phoneme.ticketing.ui.ticketing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.ViewHolder> {
    private Context mcontext;
    private List<TicketModel> ticketModelList;
    private OnItemClickListener listener;

    public TicketListAdapter(Context context, List<TicketModel> ticketModelList) {
        this.mcontext = context;
        this.ticketModelList = ticketModelList;
    }

    public TicketListAdapter(Context context, List<TicketModel> ticketModelList, OnItemClickListener listener) {
        this.mcontext = context;
        this.ticketModelList = ticketModelList;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView ticketTitle, description, status, ticketNumber, priority, serialNumber, createdBy, lastupdatedby, projectName, edit,ticketImagePdf,downloadFileName;
        private CardView cardView;
        private SimpleDraweeView downloadicon,editicon;
        private LinearLayout ticketViewLinearlayout;
        TicketModel ticketModel;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.cardid);
            projectName = (TextView) v.findViewById(R.id.project);
            ticketTitle = (TextView) v.findViewById(R.id.ticket_title);
            description = (TextView) v.findViewById(R.id.description);
            status = (TextView) v.findViewById(R.id.status);
            ticketNumber = (TextView) v.findViewById(R.id.ticket_number);
            priority = (TextView) v.findViewById(R.id.priority);
            serialNumber = (TextView) v.findViewById(R.id.serial_number);
            createdBy = (TextView) v.findViewById(R.id.created_by);
            lastupdatedby = (TextView) v.findViewById(R.id.last_updated_by);//not set
            edit = (TextView) v.findViewById(R.id.action);
            ticketImagePdf=(TextView)v.findViewById(R.id.imagespdf);
            downloadicon=(SimpleDraweeView)v.findViewById(R.id.downloadicon);
            editicon=(SimpleDraweeView)v.findViewById(R.id.editicon);
            ticketViewLinearlayout = (LinearLayout)v.findViewById(R.id.ticket_view_linearlayout);
            downloadFileName=(TextView)v.findViewById(R.id.download_filename);
//            ticketViewLinearlayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (ticketModelList.get(position).getId() != null) {
//                        listener.onTicketNumberClick(position);
//                    } else {
//                        Toast.makeText(mcontext, "Not available", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

            ticketTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (ticketModelList.get(position).getId() != null) {
                        listener.onTicketNumberClick(position);
                    } else {
                        Toast.makeText(mcontext, "Not available", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            downloadicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onDownloadLinkClick(position);
                }
            });
            ticketImagePdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onDownloadLinkClick(position);
                    //listener.onItemClick(position);
                }
            });
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position=getAdapterPosition();
//                    listener.onItemClick(position);
//                }
//            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(position);
                }
            });

            editicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(position);
                }
            });
            ticketNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Linearlayout is made clickable
//                    int position = getAdapterPosition();
//                    if (ticketModelList.get(position).getId() != null) {
//                        listener.onTicketNumberClick(position);
//                    } else {
//                        Toast.makeText(mcontext, "Not available", Toast.LENGTH_SHORT).show();
//                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }

        public void setData(TicketModel item) {
            this.ticketModel = item;
            if (this.ticketModel != null) {
                if(this.ticketModel.getImage()!=null && this.ticketModel.getImage().length()>0 ){
                   // if(true){
                    downloadicon.setVisibility(View.VISIBLE);
                    downloadFileName.setText("this.ticketModel.getImage()");
                }else{
                    downloadicon.setVisibility(View.GONE);
                    downloadFileName.setVisibility(View.GONE);
                    //downloadicon.setVisibility(View.GONE);
                }
                if (this.ticketModel.getName() != null) {
                    ticketTitle.setText("Ticket title:" + this.ticketModel.getName());//ticket title
                } else {
                    ticketTitle.setText("Ticket title:NA");//ticket title
                }
                if (this.ticketModel.getDesc() != null && this.ticketModel.getDesc().length() > 0) {
                    if (this.ticketModel.getDesc().length() >= 100) {
                        description.setText("Desc:\n" + this.ticketModel.getDesc().substring(0, 100));
                    } else {
                        description.setText("Desc:\n" + this.ticketModel.getDesc());
                    }
                } else {
                    description.setText("Desc:NA");
                }
                if (this.ticketModel.getStatus() != null && this.ticketModel.getStatus().length() > 0) {
                    if (this.ticketModel.getStatus().equals("1"))
                        status.setText("Status:Opened");
                    else if(this.ticketModel.getStatus().equals("0")){
                        status.setText("Status:Closed");
                    }else{
                        status.setText("Status:Waiting for close");
                    }
                } else {
                    status.setText("Status:NA");
                }
                if (this.ticketModel.getTicket_no() != null && this.ticketModel.getTicket_no().length() > 0) {
                    ticketNumber.setText("Ticket No:#" + this.ticketModel.getTicket_no());
                } else {
                    ticketNumber.setText("Ticket No:NA");
                }
                if (this.ticketModel.getPriority() != null && this.ticketModel.getPriority().length() > 0) {
                    priority.setText("Priority:" + this.ticketModel.getPriority());
                } else {
                    priority.setText("Priority:NA");
                }

                serialNumber.setText("SN:" + (getAdapterPosition() + 1));
                if (this.ticketModel.getCreated_by_name() != null && this.ticketModel.getCreated_by_name().length() > 0) {
                    createdBy.setText("Created by:" + this.ticketModel.getCreated_by_name());
                } else {
                    createdBy.setText("Created by:NA");
                }
                if (this.ticketModel.getProject_name() != null && this.ticketModel.getProject_name().length() > 0) {
                    projectName.setText("Project:" + this.ticketModel.getProject_name());
                } else {
                    projectName.setText("Project:NA");
                }
                if(this.ticketModel.getLast_updated_by_name()!=null && this.ticketModel.getLast_updated_by_name().length()>0){
                    lastupdatedby.setText("Last updated by:"+this.ticketModel.getLast_updated_by_name());
                }
            }
        }


    }

    @Override
    public TicketListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.single_item_ticket_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (this.ticketModelList.get(position) != null) {
            holder.setData(this.ticketModelList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return this.ticketModelList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onTicketNumberClick(int position);
        void onDownloadLinkClick(int position);
    }
}
