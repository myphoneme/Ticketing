package com.phoneme.ticketing.ui.inframonitoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;

import java.util.List;

public class InfraMonitoringAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mcontext;
    private InfraMonitoringResponse data;
    private List<InfraMonitoringModel> infraMonitoringModelList;
    private int TYPE_COMMAND = 0, TYPE_OTHER = 1;
    private OnItemClickListener listener;

    //    public InfraMonitoringAdapter(Context context,InfraMonitoringResponse response){
//        this.mcontext=context;
//        this.data=response;
//    }
    public InfraMonitoringAdapter(Context context, List<InfraMonitoringModel> infraMonitoringModelList) {
        this.mcontext = context;
        this.infraMonitoringModelList = infraMonitoringModelList;
    }
    public InfraMonitoringAdapter(Context context, List<InfraMonitoringModel> infraMonitoringModelList, OnItemClickListener listener) {
        this.mcontext = context;
        this.infraMonitoringModelList = infraMonitoringModelList;
        this.listener=listener;
    }
    public class CommandViewHolder extends RecyclerView.ViewHolder{
        public TextView name, result;
        private Button button;
        private EditText editText;

        private InfraMonitoringModel infraMonitoringModel;
        public CommandViewHolder(View v){
            super(v);
            //name = (TextView) v.findViewById(R.id.name);
            result = (TextView) v.findViewById(R.id.command_output);
            button=(Button)v.findViewById(R.id.command_button);
            editText=(EditText)v.findViewById(R.id.command_edit);
            //result.setText("Checkra");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String command=editText.getText().toString();
                    Toast.makeText(mcontext,"inAdapter function", Toast.LENGTH_LONG).show();
                    listener.onItemClick(command);
                }
            });

        }
        public void setData(InfraMonitoringModel item) {
            this.infraMonitoringModel = item;
            //name.setText(this.infraMonitoringModel.getName());

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                result.setText(Html.fromHtml(this.infraMonitoringModel.getOutput(), Html.FROM_HTML_MODE_LEGACY));
//            } else {
//                result.setText(Html.fromHtml(this.infraMonitoringModel.getOutput()));
//            }
            String newline= System.getProperty("line.separator");
            String text=this.infraMonitoringModel.getOutput().replace('\n',newline.charAt(0));
            result.setText(text);
            //result.setText("yahoora");

        }

    }
    public class OtherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, result;
        private CardView cardView;
        private InfraMonitoringModel infraMonitoringModel;
        //TicketModel ticketModel;

        public OtherViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            result = (TextView) v.findViewById(R.id.result);
        }

        @Override
        public void onClick(View v) {

        }

        public void setData(InfraMonitoringModel item) {
            this.infraMonitoringModel = item;
            if(this.infraMonitoringModel.getName().equalsIgnoreCase("accesslogresult"))
            {
                name.setText("Access Log");
            }else if(this.infraMonitoringModel.getName().equalsIgnoreCase("mysqllogresult")){
                name.setText("MySQL Log");
            }else if(this.infraMonitoringModel.getName().equalsIgnoreCase("pingresult")){
                name.setText("Ping");
            }else if(this.infraMonitoringModel.getName().equalsIgnoreCase("errorlogresult")){
                name.setText("Error Log");
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                result.setText(Html.fromHtml(this.infraMonitoringModel.getOutput().toString(), Html.FROM_HTML_MODE_LEGACY));
//            } else {
//                result.setText(Html.fromHtml(this.infraMonitoringModel.getOutput().toString()));
//            }
            String newline= System.getProperty("line.separator");
            String text=this.infraMonitoringModel.getOutput().replace('\n',newline.charAt(0));
            result.setText(text);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        if (i == TYPE_OTHER) {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.single_item_infra_monitoring, parent, false);
            return new  OtherViewHolder(view);
        } else if(i==TYPE_COMMAND){
            View view = LayoutInflater.from(mcontext).inflate(R.layout.single_item_inframonitoring_command, parent, false);
            return new CommandViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (this.infraMonitoringModelList.get(position) != null) {
            if(getItemViewType(position)==TYPE_OTHER){
                ((OtherViewHolder) holder).setData(this.infraMonitoringModelList.get(position));
            }else{
                ((CommandViewHolder) holder).setData(this.infraMonitoringModelList.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (this.infraMonitoringModelList != null)
            return this.infraMonitoringModelList.size();
        else return 1;
//        return this.ticketModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(this.infraMonitoringModelList.get(position).getName().equalsIgnoreCase("executedoutput"))
            return TYPE_COMMAND;
        else{
            return TYPE_OTHER;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String command);
    }
    public void setCommandOutput(String resultra){
        Toast.makeText(mcontext,"setCommandOutput function output before"+ this.infraMonitoringModelList.get(0).getOutput(), Toast.LENGTH_LONG).show();

        this.infraMonitoringModelList.get(0).setOutput(resultra);
        Toast.makeText(mcontext,"setCommandOutput function "+resultra, Toast.LENGTH_LONG).show();
        Toast.makeText(mcontext,"setCommandOutput function output after"+ this.infraMonitoringModelList.get(0).getOutput(), Toast.LENGTH_LONG).show();
        notifyDataSetChanged();
    }
}
