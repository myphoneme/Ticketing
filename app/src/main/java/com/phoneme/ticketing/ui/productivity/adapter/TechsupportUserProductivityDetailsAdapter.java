package com.phoneme.ticketing.ui.productivity.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.ui.productivity.model.TechsupportUserDataDashboardModel;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;
import com.phoneme.ticketing.ui.user.UserModel;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//import org.ocpsoft.prettytime.PrettyTime;

public class TechsupportUserProductivityDetailsAdapter extends RecyclerView.Adapter<TechsupportUserProductivityDetailsAdapter.ViewHolder> {
    private Context mcontext;
    private OnItemClickListener listener;
    private List<TicketModel> ticketModels;
    private List<UserModel> userModelList;
    private List<TechsupportUserDataDashboardModel> techsupportUserDataDashboardModelList;

    public TechsupportUserProductivityDetailsAdapter(Context context, List<TicketModel> ticketModels, OnItemClickListener listener) {
        this.mcontext = context;
        this.listener = listener;
        this.ticketModels = ticketModels;
    }

    public TechsupportUserProductivityDetailsAdapter(Context context, List<TicketModel> ticketModels, List<UserModel> userModelList, OnItemClickListener listener) {
        this.mcontext = context;
        this.listener = listener;
        this.ticketModels = ticketModels;
    }

    public TechsupportUserProductivityDetailsAdapter(Context context, List<TicketModel> ticketModels, List<UserModel> userModelList, List<TechsupportUserDataDashboardModel> techsupportUserDataDashboardModelList, OnItemClickListener listener) {
        this.mcontext = context;
        this.listener = listener;
        this.ticketModels = ticketModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SimpleDraweeView userimage;
        private TextView title, time;

        public ViewHolder(View v) {
            super(v);
            userimage = (SimpleDraweeView) v.findViewById(R.id.user_image);
            title = (TextView) v.findViewById(R.id.title);
            time = (TextView) v.findViewById(R.id.time);

        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, final int position) {
        //Vholder.setData(this.techsupportUserDataDashboardModelList.get(position));
        //Vholder.setData(dashboardModel1List.get(position));
        //Uri uri = Uri.parse("http://www.professorio.com/livebook/chapters/run-from-boring-class/run-from-boring-class-001.jpg");
        Uri uri1= Uri.parse(this.ticketModels.get(position).getImage_of_user_creating_thread());
        Vholder.userimage.setImageURI(uri1);

        Vholder.title.setText(this.ticketModels.get(position).getName());
        if(true){
            Vholder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position);
                }
            });
        }
        if(this.ticketModels.get(position).getClosed_at()!=null) {
            if (this.ticketModels.get(position).getStatus().equals("0")) {
                Vholder.time.setText("Closed at \n" + getTime_ago(this.ticketModels.get(position).getClosed_at()));
                //Vholder.title.setTextColor(mcontext.getResources().getColor(R.color.grey));
                Vholder.title.setTextColor(mcontext.getResources().getColor(R.color.primary));
            } else if (this.ticketModels.get(position).getStatus().equals("2")) {
                Vholder.time.setText("Waiting for close \n" + getTime_ago(this.ticketModels.get(position).getClosed_at()));
                //Vholder.title.setTextColor(mcontext.getResources().getColor(R.color.grey));
                Vholder.title.setTextColor(mcontext.getResources().getColor(R.color.primary));
            } else {
                Vholder.time.setText(getTime_ago(this.ticketModels.get(position).getCreated_at()));
                Vholder.title.setTextColor(mcontext.getResources().getColor(R.color.primary));
            }
        }else{
            //Vholder.time.setText("NA");
            if(!this.ticketModels.get(position).getStatus().equals("0") && !this.ticketModels.get(position).getStatus().equals("2"))
            {
                Vholder.time.setText(getTime_ago(this.ticketModels.get(position).getCreated_at()));
                Vholder.title.setTextColor(mcontext.getResources().getColor(R.color.primary));
            }else{
                Vholder.time.setText("Closed at \n" + getTime_ago("2019-12-21 12:05:31"));
                //Vholder.title.setTextColor(mcontext.getResources().getColor(R.color.grey));
                Vholder.title.setTextColor(mcontext.getResources().getColor(R.color.primary));
            }
        }


    }

    @Override
    public TechsupportUserProductivityDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.adapter_item_techsupport_user_productivity, parent, false);
        ViewGroup.LayoutParams p = view.getLayoutParams();

        //p.height = parent.getHeight() / 2;
//        p.height=parent.getWidth()/2;
        //view.setLayoutParams(p);
        return new TechsupportUserProductivityDetailsAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return this.ticketModels.size();
        //return 2;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private String getTime_ago(String date){
        //String sDate1="31/12/1998";
        //"2019-12-04 12:18:45"
        //HH:mm:ss
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            //Date currentDate=new Date();

            PrettyTime p = new PrettyTime();
            return p.format(date1);
//            System.out.println(p.format(date1));
        }catch(ParseException e){

        }
        return "";
    }
}
