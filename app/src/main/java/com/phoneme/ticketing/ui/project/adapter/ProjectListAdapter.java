package com.phoneme.ticketing.ui.project.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.ui.project.model.ProjectModel;

import java.util.List;
//commented just to check if this code is being used anywhere. will be deleted if it is so
//public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
//    private Context mcontext;
//    private List<ProjectModel> projectModelList;
//    private OnItemClickListener listener;
//    public ProjectListAdapter(Context context, List<ProjectModel> projectModelList){
//        this.mcontext=context;
//        this.projectModelList=projectModelList;
//    }
//    public ProjectListAdapter(Context context, List<ProjectModel> projectModelList, OnItemClickListener listener){
//        this.mcontext=context;
//        this.projectModelList=projectModelList;
//        this.listener=listener;
//    }
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        private TextView title,description,company_name,allocated_users,status,createdat;
//        private ProjectModel projectModel;
//        private TextView edit;
//        public ViewHolder(View v){
//            super(v);
//            edit=(TextView)v.findViewById(R.id.edit);
//            title=(TextView)v.findViewById(R.id.name);
//            description=(TextView)v.findViewById(R.id.description);
//            company_name=(TextView)v.findViewById(R.id.company_name);
//            allocated_users=(TextView)v.findViewById(R.id.allocated_users);
//            status = (TextView)v.findViewById(R.id.status);
//            createdat=(TextView)v.findViewById(R.id.created_at);
//            edit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position=getAdapterPosition();
//                    listener.onItemClick(position);
//                }
//            });
//        }
//
//        public void setData(ProjectModel item){
//            this.projectModel=item;
//            title.setText(Html.fromHtml("<b>Title:</b>"+this.projectModel.getName()));
//            description.setText(Html.fromHtml("<b>Descrption:</b>"+this.projectModel.getDescription()));
//            company_name.setText(Html.fromHtml("<b>Company:</b>"+this.projectModel.getCompany_name()));
//
//            String allocateusers=new String();
//            if(this.projectModel.getAllcatedusers()!=null && this.projectModel.getAllcatedusers().size()>0){
//                allocateusers=this.projectModel.getAllcatedusers().get(0).getUserName();
//                for(int i=1;i<this.projectModel.getAllcatedusers().size();i++){
//                    allocateusers=allocateusers+","+this.projectModel.getAllcatedusers().get(i).getUserName();
//                }
//            }else{
//                allocateusers="";
//
//            }
//
//            allocated_users.setText(Html.fromHtml("<b>Allocated users:</b>"+allocateusers));
//            if(this.projectModel.getStatus().equals("1")){
//                status.setText(Html.fromHtml("<b>Status:</b>Active"));
//            }else{
//                status.setText(Html.fromHtml("<b>Status:</b>Inactive"));
//            }
//            createdat.setText(Html.fromHtml("<b>Created:</b>"+this.projectModel.getCreated_at()));
//        }
//
//    }
//    @Override
//    public ProjectListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
//        View view= LayoutInflater.from(mcontext).inflate(R.layout.adapter_item_projectlist,viewGroup,false);
//        return new ViewHolder(view);
//    }
//
//    public void onBindViewHolder(ViewHolder vh,int position){
//        vh.setData(this.projectModelList.get(position));
//
//    }
//    @Override
//    public int getItemCount(){
//        return this.projectModelList.size();
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//}
