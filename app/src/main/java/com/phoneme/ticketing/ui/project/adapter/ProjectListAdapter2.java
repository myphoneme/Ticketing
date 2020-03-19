package com.phoneme.ticketing.ui.project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectListAdapter2 extends RecyclerView.Adapter<ProjectListAdapter2.ViewHolder> {
    private Context mcontext;
    private List<ProjectModel> projectModelList;
    private Map<Integer, String> backgroundcolor=new HashMap<>();
    private Map<Integer, String> textcolor=new HashMap<>();
    private ProjectListAdapter2.OnItemClickListener listener;
    private String[] fcolor;
    public ProjectListAdapter2(Context context, List<ProjectModel> projectModelList, OnItemClickListener listener){
        this.mcontext=context;
        this.projectModelList=projectModelList;
        this.listener=listener;
        backgroundcolor.put(0,"#EFB41D");
        backgroundcolor.put(1,"#2BBAF2");
        backgroundcolor.put(2,"#3ABD42");
        backgroundcolor.put(3,"#ED544A");
        textcolor.put(0,"#684E0C");
        textcolor.put(1,"#14546E");
        textcolor.put(2,"#1A531D");
        textcolor.put(3,"#642420");
    }
    public ProjectListAdapter2(Context context, List<ProjectModel> projectModelList, OnItemClickListener listener, String[] fcolor){
        this.mcontext=context;
        this.projectModelList=projectModelList;
        this.listener=listener;
        backgroundcolor.put(0,"#EFB41D");
        backgroundcolor.put(1,"#2BBAF2");
        backgroundcolor.put(2,"#3ABD42");
        backgroundcolor.put(3,"#ED544A");
        textcolor.put(0,"#684E0C");
        textcolor.put(1,"#14546E");
        textcolor.put(2,"#1A531D");
        textcolor.put(3,"#642420");
        this.fcolor=fcolor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description, company_name, allocated_users, status, createdat;
        private ProjectModel projectModel;
        private TextView edit;
        private CardView cardView;
        private ImageView imageView;
        private SimpleDraweeView projectLogo;
        private RelativeLayout relativeLayoutView;

        public ViewHolder(View v) {
            super(v);
            title=(TextView)v.findViewById(R.id.name);
            company_name=(TextView)v.findViewById(R.id.company_name);
            imageView=(ImageView)v.findViewById(R.id.image);
            relativeLayoutView=(RelativeLayout)v.findViewById(R.id.relativelayoutview);
            projectLogo=(SimpleDraweeView)v.findViewById(R.id.project_logo_image);
            relativeLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick2(getAdapterPosition());
                }
            });
            //cardView=(CardView)v.findViewById(R.id.cardid);
        }

        public void setData(ProjectModel item, int position) {
            this.projectModel = item;
            this.title.setText(Html.fromHtml(this.projectModel.getName()));

            this.company_name.setText(Html.fromHtml(this.projectModel.getCompany_name()));
            String colorText=textcolor.get(position%4);
            this.company_name.setTextColor(Color.parseColor(colorText));
            String color=backgroundcolor.get(position%4);
//            this.cardView.setBackgroundColor(Color.parseColor(color));
            //this.relativeLayoutView.setBackgroundColor(Color.parseColor("#ffffff"));
            this.relativeLayoutView.setBackgroundColor(Color.parseColor(color));
            if(item.getImage()!=null && item.getImage().length()>0) {
                System.out.println("imageurl=" + item.getImage());
                Uri uri = Uri.parse(item.getImage());
                projectLogo.setImageURI(uri);
            }else{
                System.out.println("no image imageurl=" + item.getImage());
                Uri uri = Uri.parse("android.resource://com.phoneme.ticketing/drawable/icon1.png");
                projectLogo.setImageURI(uri);
            }
        }
    }
        @Override
        public ProjectListAdapter2.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
//            View view= LayoutInflater.from(mcontext).inflate(R.layout.adapter_item_projectlist_newscreen,viewGroup,false);
//            return new ViewHolder(view);

            View view= LayoutInflater.from(mcontext).inflate(R.layout.app_bar1,viewGroup,false);

            //view.setBackgroundColor(Color.parseColor(fcolor[]));
            return new ViewHolder(view);
        }

    public void onBindViewHolder(ProjectListAdapter2.ViewHolder vh, int position){
        vh.setData(this.projectModelList.get(position),position);



    }
    @Override
    public int getItemCount(){
        return this.projectModelList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemClick2(int position);
    }
}
