package com.phoneme.ticketing.ui.project.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.ui.project.model.ProjectModel;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProjectDetailTicketListAdapter extends RecyclerView.Adapter<ProjectDetailTicketListAdapter.ViewHolder>{
    private List<TicketModel> tickets;
    private Context mcontext;
    private SimpleDateFormat formatter;
    private OnItemClickListener listener;

    public  ProjectDetailTicketListAdapter(Context context, List<TicketModel> ticketModelList, OnItemClickListener listener){
        this.mcontext=context;
        this.tickets=ticketModelList;
        this.listener=listener;

        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description, company_name, allocated_users, status, createdat,ticketTitle;
        private ProjectModel projectModel;
        private TextView edit;
        private CardView cardView;
        private ImageView imageView;
        private RelativeLayout relativeLayoutView;
        private TicketModel ticketModel;
        private SimpleDraweeView simpleDraweeView;

        public ViewHolder(View v) {
            super(v);
            ticketTitle=(TextView)v.findViewById(R.id.immuneText);
            createdat=(TextView)v.findViewById(R.id.immuneText1);
            relativeLayoutView=(RelativeLayout)v.findViewById(R.id.relativelayoutview);
            simpleDraweeView=(SimpleDraweeView)v.findViewById(R.id.datetxt);
            relativeLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTicketNumberClick(getAdapterPosition());
                }
            });
        }
        public void setData(TicketModel item,int position) {
            this.ticketModel = item;
            ticketTitle.setText(this.ticketModel.getName());
            Uri uri= Uri.parse(this.ticketModel.getUser().getImage());
            simpleDraweeView.setImageURI(uri);
            try {
                Date date = formatter.parse(this.ticketModel.getCreated_at());
                SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
                createdat.setText(dt1.format(date));
            }catch(ParseException e){

            }
        }
    }


    @Override
    public ProjectDetailTicketListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
//            View view= LayoutInflater.from(mcontext).inflate(R.layout.adapter_item_projectlist_newscreen,viewGroup,false);
//            return new ViewHolder(view);

        View view= LayoutInflater.from(mcontext).inflate(R.layout.adapter_item_project_detail_ticket_lists_indradhanush,viewGroup,false);

        //view.setBackgroundColor(Color.parseColor(fcolor[]));
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ProjectDetailTicketListAdapter.ViewHolder vh, int position){
        vh.setData(this.tickets.get(position),position);
    }
    @Override
    public int getItemCount(){
        return this.tickets.size();
    }

    public interface OnItemClickListener{
        void onTicketNumberClick(int position);
    }

}
