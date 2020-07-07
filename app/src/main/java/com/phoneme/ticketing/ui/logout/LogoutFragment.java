package com.phoneme.ticketing.ui.logout;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.phoneme.ticketing.user.LoginActivity;
import com.phoneme.ticketing.user.LoginEmailActivity;
import com.phoneme.ticketing.user.UserAuth;

public class LogoutFragment extends Fragment {

//    private SendViewModel sendViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        sendViewModel =
//                ViewModelProviders.of(this).get(SendViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_send, container, false);
//        final TextView textView = root.findViewById(R.id.text_send);
//        sendViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserAuth userAuth=new UserAuth(getContext());
        userAuth.clearData();
        Intent intent=new Intent(getActivity(), LoginEmailActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}