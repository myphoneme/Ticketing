package com.phoneme.ticketing.ui.company.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.ui.company.model.CompanyModel;

import java.util.List;

public class CompanyMainAdapter extends RecyclerView.Adapter<CompanyMainAdapter.ViewHolder> {
    private List<CompanyModel> companyModelList;
    private Context mcontext;
    private OnItemClickListener listener;

    public CompanyMainAdapter(Context context, List<CompanyModel> companyModelList) {
        this.mcontext = context;
        this.companyModelList = companyModelList;
    }

    public CompanyMainAdapter(Context context, List<CompanyModel> companyModelList, OnItemClickListener listener) {
        this.mcontext = context;
        this.companyModelList = companyModelList;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView companyName, status, priority, serialNumber, createdBy, lastupdatedby, id, action;
        CompanyModel companyModel;

        public ViewHolder(View v) {
            super(v);
            status = v.findViewById(R.id.status);
            id = v.findViewById(R.id.serial_number);
            companyName = v.findViewById(R.id.company_name);
            companyName.setOnClickListener(this);
            action = v.findViewById(R.id.action);
            action.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == companyName|| v==action) {
                int position = getAdapterPosition();
                listener.onCompanyClick(position);
            }
        }

        public void setData(CompanyModel item) {
            this.companyModel = item;
            if (this.companyModel != null ) {
                if (this.companyModel.getStatus() != null && this.companyModel.getStatus().equals("1")) {
                    status.setText("Status:Active");
                } else {
                    status.setText("Status:Inactive");
                }

                id.setText("SN:" + this.companyModel.getId());
                companyName.setText("" + this.companyModel.getName());
            }
        }
    }

    @Override
    public CompanyMainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.single_item_company_main, parent, false);
        return new CompanyMainAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyMainAdapter.ViewHolder holder, int position) {
        holder.setData(this.companyModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.companyModelList.size();
    }

    public interface OnItemClickListener {
        //void onItemClick(int position);

        void onCompanyClick(int position);
    }
}
