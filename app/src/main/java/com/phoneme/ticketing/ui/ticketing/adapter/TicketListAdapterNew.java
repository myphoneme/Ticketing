package com.phoneme.ticketing.ui.ticketing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.TooltipCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        View view = LayoutInflater.from(mcontext).inflate(R.layout.single_item_ticket_main_new_yash_design, parent, false);
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
        public TextView ticketTitle, description, status, ticketNumber, priority, serialNumber, createdBy, lastupdatedby, projectName, edit,ticketImagePdf,downloadFileName,time;
        private CardView cardView;
        private SimpleDraweeView downloadicon,editicon,userImage;
        private LinearLayout ticketViewLinearlayout;
        private RelativeLayout colorIndicatorForTicketStatus;
        TicketModel ticketModel;
        private RelativeLayout relativeLayoutView;


        public ViewHolder(View v) {
            super(v);
            ticketTitle=(TextView)v.findViewById(R.id.ticket_title);
            projectName=(TextView)v.findViewById(R.id.project_name);
            relativeLayoutView=(RelativeLayout)v.findViewById(R.id.relativelayoutview);
            edit=(TextView)v.findViewById(R.id.ticket_edit);
            ticketNumber=(TextView)v.findViewById(R.id.ticket_number);
            time=(TextView)v.findViewById(R.id.time);
            userImage=(SimpleDraweeView)v.findViewById(R.id.assigned_user_image);
            //userImage.setBackgroundColor(Color.parseColor("#fff"));
            colorIndicatorForTicketStatus=(RelativeLayout)v.findViewById(R.id.top_section);
//            relativeLayoutView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onTicketNumberClick(getAdapterPosition());
//                }
//            });
            ticketNumber.setOnClickListener(new View.OnClickListener() {
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
            ticketNumber.setText("#"+this.ticketModel.getTicket_no());
//            if(this.ticketModel.getClosed_at()!=null){
//                time.setText("Closed at "+getTime_ago(this.ticketModel.getClosed_at()));
//            }else{
//                time.setText("Opened at "+getTime_ago(this.ticketModel.getCreated_at()));
//            }

            if(this.ticketModel.getUser_assigned_image()!=null && this.ticketModel.getUser_assigned_image().length()>0){
           Uri uri= Uri.parse(this.ticketModel.getUser_assigned_image());
            userImage.setImageURI(uri);}else{
                Uri uri=Uri.parse("https://i.stack.imgur.com/srHnc.png");
                userImage.setImageURI(uri);
            }
            if(Build.VERSION.SDK_INT < 26) {
                TooltipCompat.setTooltipText(ticketTitle, this.ticketModel.getDesc());
            }else{
                ticketTitle.setTooltipText(this.ticketModel.getDesc());
            }
            //colorIndicatorForTicketStatus.setBackgroundResource(R.drawable.bg_text_header);
            if(this.ticketModel.getStatus().equalsIgnoreCase("0")) {
                time.setText("Closed at "+getTime_ago(this.ticketModel.getClosed_at()));
                colorIndicatorForTicketStatus.setBackgroundColor(Color.parseColor("#4caf50"));
                //colorIndicatorForTicketStatus.getBackground().setColorFilter();
            }else if(this.ticketModel.getStatus().equalsIgnoreCase("1")){
                time.setText("Opened at "+getTime_ago(this.ticketModel.getCreated_at()));
                colorIndicatorForTicketStatus.setBackgroundColor(Color.parseColor("#f44336"));
            }else if(this.ticketModel.getStatus().equalsIgnoreCase("2")){
                time.setText("Waiting for close "+getTime_ago(this.ticketModel.getClosed_at()));
                colorIndicatorForTicketStatus.setBackgroundColor(Color.parseColor("#FF8800"));//earlier #ffeb3b
            }
            //this.relativeLayoutView.setBackgroundColor(Color.parseColor(color));
        }
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
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onTicketNumberClick(int position);
    }
}
