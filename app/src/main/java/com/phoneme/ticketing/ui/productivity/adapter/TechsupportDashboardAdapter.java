package com.phoneme.ticketing.ui.productivity.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.ui.productivity.model.TechsupportUserDataDashboardModel;

import java.util.List;

public class TechsupportDashboardAdapter extends RecyclerView.Adapter<TechsupportDashboardAdapter.ViewHolder>  {
    private Context mcontext;
    private List<TechsupportUserDataDashboardModel> techsupportUserDataDashboardModelList;
    private OnItemClickListener listener;
    public TechsupportDashboardAdapter(Context context){
        this.mcontext=context;
    }
    public TechsupportDashboardAdapter(Context context, List<TechsupportUserDataDashboardModel> techsupportUserDataDashboardModelList){
        this.mcontext=context;
        this.techsupportUserDataDashboardModelList = techsupportUserDataDashboardModelList;
    }

    public TechsupportDashboardAdapter(Context context, List<TechsupportUserDataDashboardModel> techsupportUserDataDashboardModelList, OnItemClickListener listener){
        this.mcontext=context;
        this.techsupportUserDataDashboardModelList = techsupportUserDataDashboardModelList;
        this.listener=listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView,name,opentickets,closetickets,heading,total,todaysolvedtickets,totalticketsassigned,solvedtickets,designation,tickets_open_right_now;
        private RelativeLayout tickets_Open_layout,tickets_assigned_layout,tickets_closed_layout,tickets_closed_today_layout;
        private CardView cardView;
        private SimpleDraweeView userimage;
        //        DashboardModel1 dashboardModel1;
        //DashboardModel dashboardModel;
        private TechsupportUserDataDashboardModel techsupportUserDataDashboardModel;
        private Resources res;
        public ViewHolder(View v){
            super(v);
            cardView=(CardView)v.findViewById(R.id.cardid);
            name=(TextView)v.findViewById(R.id.name);
            res=v.getContext().getResources();
            todaysolvedtickets=(TextView)v.findViewById(R.id.todaysolvedtickets);
            totalticketsassigned =(TextView)v.findViewById(R.id.totalticketsassigned);
            solvedtickets = (TextView)v.findViewById(R.id.solvedtickets);
            userimage = (SimpleDraweeView)v.findViewById(R.id.user_image);
            designation=(TextView)v.findViewById(R.id.id_designation);
            tickets_open_right_now=(TextView)v.findViewById(R.id.tickets_open);
            tickets_Open_layout=(RelativeLayout)v.findViewById(R.id.tickets_open_layout);
            tickets_assigned_layout=(RelativeLayout)v.findViewById(R.id.tickets_assigned_layout);
            tickets_closed_layout=(RelativeLayout)v.findViewById(R.id.tickets_closed_layout);
            tickets_closed_today_layout=(RelativeLayout)v.findViewById(R.id.tickets_closed_today_layout);
            //cardView.setOnClickListener(this);
            totalticketsassigned.setOnClickListener(this);
            solvedtickets.setOnClickListener(this);
            todaysolvedtickets.setOnClickListener(this);
            tickets_open_right_now.setOnClickListener(this);
            tickets_Open_layout.setOnClickListener(this);
            tickets_assigned_layout.setOnClickListener(this);
            tickets_closed_layout.setOnClickListener(this);
            tickets_closed_today_layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if(v==cardView){
                int position=getAdapterPosition();
                listener.onUserClicked(position);
            }else if(v==totalticketsassigned){
                int position=getAdapterPosition();
                listener.onTicketAssignedClicked(position);
            }else if(v==solvedtickets){
                int position=getAdapterPosition();
                listener.onTicketsSolvedClicked(position);
            }else if(v==todaysolvedtickets){
                int position=getAdapterPosition();
                listener.onTicketsSolvedTodayClicked(position);
            }else if(v==tickets_open_right_now || v==tickets_Open_layout){
                int position=getAdapterPosition();
                listener.onTicketsOpenClicked(position);
            }else if(v== tickets_assigned_layout){
                int position=getAdapterPosition();
                listener.onUserClicked(position);
            }else if(v==tickets_closed_layout){
                int position=getAdapterPosition();
                listener.onTicketsSolvedClicked(position);
            }else if(v==tickets_closed_today_layout){
                int position=getAdapterPosition();
                listener.onTicketsSolvedTodayClicked(position);
            }
        }

        public void setData(TechsupportUserDataDashboardModel item){
            this.techsupportUserDataDashboardModel = item;
            
            //cardView.setCardBackgroundColor(Color.parseColor(this.techsupportUserDataDashboardModel.getColor()));
            Uri uri= Uri.parse(this.techsupportUserDataDashboardModel.getImage());
            userimage.setImageURI(uri);
            designation.setText(this.techsupportUserDataDashboardModel.getDesignation());
            name.setText(this.techsupportUserDataDashboardModel.getName());
            totalticketsassigned.setText(""+this.techsupportUserDataDashboardModel.getTotalopenticketcount());
            solvedtickets.setText(""+this.techsupportUserDataDashboardModel.getTotalcloseticketcount());
            todaysolvedtickets.setText(""+this.techsupportUserDataDashboardModel.getTotalavgcount());
            tickets_open_right_now.setText(this.techsupportUserDataDashboardModel.getTotalopenticketsrightnow());
        }

    }

    @Override
    public TechsupportDashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent , int i){

        View view = LayoutInflater.from(mcontext).inflate(R.layout.adapter_item_techsupportdashboard_new_ui_sir, parent, false);
        ViewGroup.LayoutParams p = view.getLayoutParams();
        //p.height=parent.getHeight()/2;
        //p.height=parent.getWidth()/2;
        view.setLayoutParams(p);
        return new TechsupportDashboardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TechsupportDashboardAdapter.ViewHolder Vholder, int position) {
        Vholder.setData(this.techsupportUserDataDashboardModelList.get(position));
        //Vholder.setData(dashboardModel1List.get(position));

    }

    @Override
    public int getItemCount(){

        //return this.dashboardModelList.size();
        //return 4;
        return this.techsupportUserDataDashboardModelList.size();
    }

    public interface OnItemClickListener {
        void onUserClicked(int position);
        void onTicketAssignedClicked(int position);
        void onTicketsOpenClicked(int position);
        void onTicketsSolvedClicked(int position);
        void onTicketsSolvedTodayClicked(int position);
    }
}
