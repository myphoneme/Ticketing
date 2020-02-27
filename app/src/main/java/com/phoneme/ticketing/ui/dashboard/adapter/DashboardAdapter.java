package com.phoneme.ticketing.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.ui.dashboard.model.DashboardModel;
import com.phoneme.ticketing.ui.dashboard.network.DashboardMainModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private Context mcontext;
    private List<DashboardModel> dashboardModelList;
    private OnItemClickListener listener;
    private List<DashboardMainModel> dashboardMainModelList;

    public DashboardAdapter(Context context){
        this.mcontext=context;
    }

    public DashboardAdapter(Context context, List<DashboardModel> dashboardModelList){
        this.mcontext=context;
        this.dashboardModelList = dashboardModelList;
        Toast.makeText(this.mcontext,"dashboardAdapter itemcoun="+this.dashboardModelList.size(), Toast.LENGTH_LONG).show();
    }
    public DashboardAdapter(Context context, List<DashboardModel> dashboardModelList, OnItemClickListener listerner){
        this.listener=listerner;
        this.mcontext=context;
        this.dashboardModelList = dashboardModelList;
        Toast.makeText(this.mcontext,"dashboardAdapter itemcoun="+this.dashboardModelList.size(), Toast.LENGTH_LONG).show();
    }//DashboardMainModel

    public DashboardAdapter(Context context, List<DashboardModel> dashboardModelList, List<DashboardMainModel> dashboardMainModelList, OnItemClickListener listerner){
        this.listener=listerner;
        this.mcontext=context;
        this.dashboardModelList = dashboardModelList;
        //Toast.makeText(this.mcontext,"dashboardAdapter itemcoun="+this.dashboardModelList.size(),Toast.LENGTH_LONG).show();
        this.dashboardMainModelList=dashboardMainModelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView,name,opentickets,closetickets,heading,total,openticketts,closetickets_heading,opentickets_heading;
        private CardView cardView;
        private FrameLayout frameLayout;
        private ImageView icon;
        private LinearLayout lowerLeft,lowerRight;
//        DashboardModel1 dashboardModel1;
        DashboardModel dashboardModel;
        private DashboardMainModel dashboardMainModel;
        public ViewHolder(View v){
            super(v);
            //cardView=(CardView)v.findViewById(R.id.cardid);
            frameLayout=(FrameLayout)v.findViewById(R.id.cardid);
            icon=(ImageView) v.findViewById(R.id.icon);
            heading = (TextView)v.findViewById(R.id.heading);
            total=(TextView)v.findViewById(R.id.num);
            opentickets=(TextView)v.findViewById(R.id.openticketscount);
            closetickets=(TextView)v.findViewById(R.id.closeticketscount) ;
            opentickets_heading=(TextView)v.findViewById(R.id.opentickets);
            closetickets_heading=(TextView)v.findViewById(R.id.closetickets);
            lowerRight=(LinearLayout)v.findViewById(R.id.right_bottom);
            lowerLeft=(LinearLayout)v.findViewById(R.id.left_bottom);
//            cardView.setOnClickListener(this);
            frameLayout.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            if(v==cardView){
                Toast.makeText(mcontext,"card clicked", Toast.LENGTH_LONG).show();
                listener.onItemClick(getAdapterPosition());

            }else if(v==frameLayout){
                Toast.makeText(mcontext,"fragme layout clicked", Toast.LENGTH_LONG).show();
                listener.onItemClick(getAdapterPosition());
            }

        }
        public void setData(DashboardModel item){
            this.dashboardModel = item;
            //cardView.setCardBackgroundColor(Color.parseColor(this.dashboardModel.getColorHex()));
            Picasso.get().load(this.dashboardModel.getIconurl()).into(icon);
            heading.setText(this.dashboardModel.getHeading());
            total.setText(this.dashboardModel.getTotal());
        }

        public void setDataLive(DashboardMainModel item){
            this.dashboardMainModel=item;
            //cardView.setCardBackgroundColor(Color.parseColor(this.dashboardMainModel.getBackgroundColor()));
            Picasso.get().load(this.dashboardMainModel.getIcon()).into(icon);
            heading.setText(this.dashboardMainModel.getDashboardSubheadingList().get(0).getSubheading());
            total.setText(this.dashboardMainModel.getDashboardSubheadingList().get(0).getSubheading_value());
            if(this.dashboardMainModel.getType().equals("ticket")){
                lowerLeft.setVisibility(View.VISIBLE);
                lowerRight.setVisibility(View.VISIBLE);
                opentickets.setText(this.dashboardMainModel.getDashboardSubheadingList().get(1).getSubheading_value());
                closetickets.setText(this.dashboardMainModel.getDashboardSubheadingList().get(2).getSubheading_value());
            }else if (this.dashboardMainModel.getType().equals("users")){
                lowerLeft.setVisibility(View.VISIBLE);
                lowerRight.setVisibility(View.VISIBLE);
                opentickets.setText(this.dashboardMainModel.getDashboardSubheadingList().get(1).getSubheading_value());
                closetickets.setText(this.dashboardMainModel.getDashboardSubheadingList().get(2).getSubheading_value());
                opentickets_heading.setText(this.dashboardMainModel.getDashboardSubheadingList().get(1).getSubheading());
                closetickets_heading.setText(this.dashboardMainModel.getDashboardSubheadingList().get(2).getSubheading());
                //lowerLeft.setVisibility(View.GONE);
                //lowerRight.setVisibility(View.GONE);
            }else{
                lowerLeft.setVisibility(View.GONE);
                lowerRight.setVisibility(View.GONE);
            }
        }


    }
    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent , int i){

        View view = LayoutInflater.from(mcontext).inflate(R.layout.adapter_item_dashboard1, parent, false);
        ViewGroup.LayoutParams p = view.getLayoutParams();
        //p.height=parent.getHeight()/2;
        p.height=parent.getWidth()/3+parent.getWidth()/5;
        view.setLayoutParams(p);
        return new DashboardAdapter.ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(DashboardAdapter.ViewHolder Vholder, int position) {
        //Vholder.setData(this.dashboardModelList.get(position));
        Vholder.setDataLive(this.dashboardMainModelList.get(position));


    }

    @Override
    public int getItemCount(){
        return this.dashboardMainModelList.size();
       // return 3;
//        return this.dashboardModelList.size();
        //return 5;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onTicketNumberClick(int position);
    }
}
