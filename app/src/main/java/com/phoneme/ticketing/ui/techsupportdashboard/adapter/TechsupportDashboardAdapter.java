package com.phoneme.ticketing.ui.techsupportdashboard.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.ui.techsupportdashboard.model.TechsupportUserDataDashboardModel;

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
        private TextView textView,name,opentickets,closetickets,heading,total,todaysolvedtickets,totalticketsassigned,solvedtickets;
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
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if(v==cardView){
                int position=getAdapterPosition();
                listener.onUserClicked(position);
            }
        }

        public void setData(TechsupportUserDataDashboardModel item){
            this.techsupportUserDataDashboardModel = item;
            
            cardView.setCardBackgroundColor(Color.parseColor(this.techsupportUserDataDashboardModel.getColor()));
            Uri uri= Uri.parse(this.techsupportUserDataDashboardModel.getImage());
            userimage.setImageURI(uri);

            name.setText(this.techsupportUserDataDashboardModel.getName());
            todaysolvedtickets.setText("Today solved tickets:"+this.techsupportUserDataDashboardModel.getTotalavgcount());
            totalticketsassigned.setText("Tickets assigned:"+this.techsupportUserDataDashboardModel.getTotalopenticketcount());
            solvedtickets.setText("Solved tickets:"+this.techsupportUserDataDashboardModel.getTotalcloseticketcount());
        }

    }

    @Override
    public TechsupportDashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent , int i){

        View view = LayoutInflater.from(mcontext).inflate(R.layout.adapter_item_techsupportdashboard, parent, false);
        ViewGroup.LayoutParams p = view.getLayoutParams();
        //p.height=parent.getHeight()/2;
        p.height=parent.getWidth()/2;
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
    }
}
