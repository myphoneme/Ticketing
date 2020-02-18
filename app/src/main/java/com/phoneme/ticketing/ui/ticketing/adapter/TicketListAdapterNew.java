package com.phoneme.ticketing.ui.ticketing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketListAdapterNew extends RecyclerView.Adapter<TicketListAdapterNew.ViewHolder> {
    private Context mcontext;
    private List<TicketModel> ticketModelList;
    private OnItemClickListener listener;
    private String[] fcolor;
    private Map<Integer, String> backgroundcolor=new HashMap<>();
    public TicketListAdapterNew(Context context, List<TicketModel> ticketModelList, OnItemClickListener listener) {
        this.mcontext = context;
        this.ticketModelList = ticketModelList;
        this.listener = listener;
        backgroundcolor.put(0,"#EFB41D");
        backgroundcolor.put(1,"#2BBAF2");
        backgroundcolor.put(2,"#3ABD42");
        backgroundcolor.put(3,"#ED544A");
    }
    public TicketListAdapterNew(Context context, List<TicketModel> ticketModelList, OnItemClickListener listener, String[] color) {
        this.mcontext = context;
        this.ticketModelList = ticketModelList;
        this.listener = listener;

    }
    @Override
    public TicketListAdapterNew.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.single_item_ticket_main_new, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return this.ticketModelList.size();
    }
    @Override
    public void onBindViewHolder(TicketListAdapterNew.ViewHolder holder, int position) {
        if (this.ticketModelList.get(position) != null) {
            holder.setData(this.ticketModelList.get(position),position);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView ticketTitle, description, status, ticketNumber, priority, serialNumber, createdBy, lastupdatedby, projectName, edit,ticketImagePdf,downloadFileName;
        private CardView cardView;
        private SimpleDraweeView downloadicon,editicon;
        private LinearLayout ticketViewLinearlayout;
        TicketModel ticketModel;
        private RelativeLayout relativeLayoutView;


        public ViewHolder(View v) {
            super(v);
            ticketTitle=(TextView)v.findViewById(R.id.ticket_title);
            projectName=(TextView)v.findViewById(R.id.project_name);
            relativeLayoutView=(RelativeLayout)v.findViewById(R.id.relativelayoutview);
            edit=(TextView)v.findViewById(R.id.ticket_edit);
            relativeLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTicketNumberClick(getAdapterPosition());
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(position);
                }
            });
        }
        @Override
        public void onClick(View v) {

        }
        public void setData(TicketModel item, int position) {
            this.ticketModel = item;
            if(this.ticketModel.getName()!=null && this.ticketModel.getName().length()>0){
                if(this.ticketModel.getName().length()>20){
                    ticketTitle.setText(this.ticketModel.getName().substring(0,20));
                }else{
                    ticketTitle.setText(this.ticketModel.getName());
                }

            }

            projectName.setText(this.ticketModel.getProject_name());
            String color=backgroundcolor.get(position%4);
            this.relativeLayoutView.setBackgroundColor(Color.parseColor(color));
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onTicketNumberClick(int position);
    }
}
