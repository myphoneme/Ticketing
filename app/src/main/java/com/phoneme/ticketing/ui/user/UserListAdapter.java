package com.phoneme.ticketing.ui.user;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.phoneme.ticketing.R;
import com.phoneme.ticketing.user.UserAuth;

import java.util.List;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private Context mcontext;
    private List<UserModel> userModelList;
    private OnItemClickListener listener;
    private UserAuth userAuth;

    public UserListAdapter(Context context, List<UserModel> userModelList) {
        this.mcontext = context;
        this.userModelList = userModelList;
    }

    public UserListAdapter(Context context, List<UserModel> userModelList, OnItemClickListener listener) {
        this.mcontext = context;
        this.userModelList = userModelList;
        this.listener = listener;
        userAuth = new UserAuth(this.mcontext);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, mobile_number, email, status, designation;
        private UserModel userModel;
        private SimpleDraweeView usericonsimple;
        private CardView card;
        private Button button;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            mobile_number = (TextView) v.findViewById(R.id.mobile_number);
            email = (TextView) v.findViewById(R.id.email);
            status = (TextView) v.findViewById(R.id.status);
            usericonsimple = (SimpleDraweeView) v.findViewById(R.id.user_icon_simpledrawee);
            card = (CardView) v.findViewById(R.id.cardcontainer);
            button = (Button) v.findViewById(R.id.edit_profile);
            designation = (TextView) v.findViewById(R.id.designation);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

//            name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    listener.onItemClick(getAdapterPosition());
//                }
//            });
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {

        }

        public void setData(UserModel item) {
            if (item != null && item.getName() != null) {
                this.userModel = item;
                name.setText(this.userModel.getName());
                mobile_number.setText(this.userModel.getMobile_no());
                email.setText(this.userModel.getEmail());
                if (this.userModel.getStatus().equals("1")) {
                    status.setText("Status:active");
                } else {
                    status.setText("Status:inactive");
                }
                if (this.userModel.getImage() != null && this.userModel.getImage().length() > 0) {
                    Uri imgURI = Uri.parse(this.userModel.getImage());
                    usericonsimple.setImageURI(imgURI);
                }
                if (userAuth.getId().equalsIgnoreCase(this.userModel.getId())) {
                    button.setText("Edit");
                } else if (userAuth.getRole().equalsIgnoreCase("0")) {
                    button.setText("Edit");
                } else {
                    button.setText("View Profile");
                }

                designation.setText(this.userModel.getDesignation());
            }
        }
    }

    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.adapter_item_userlist, parent, false);
        return new UserListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserListAdapter.ViewHolder Vholder, int position) {
        Vholder.setData(this.userModelList.get(position));
        //Vholder.setData(this.techsupportDashboardModelList.get(position));
        //Vholder.setData(dashboardModel1List.get(position));

    }

    @Override
    public int getItemCount() {
        return this.userModelList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
