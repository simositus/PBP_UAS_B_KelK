package com.tubes.sandangin.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tubes.sandangin.R;
import com.tubes.sandangin.SplashActivity;
import com.tubes.sandangin.activities.MyOrders;
import com.tubes.sandangin.activities.ShoppingCart;
import com.tubes.sandangin.database.DB_Handler;
import com.tubes.sandangin.database.SessionManager;
import com.tubes.sandangin.interfaces.FinishActivity;
import com.tubes.sandangin.pojo.User;
import com.tubes.sandangin.utils.Constants;

public class Account extends Fragment {

    DB_Handler db_handler;
    TextView name, email, mobile;
    RelativeLayout orders, logoutLay;
    FinishActivity finishActivityCallback;
    Button btnUpdet;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        finishActivityCallback = (FinishActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.account, container, false);

        // Get User
        db_handler = new DB_Handler(getActivity());
        SessionManager sessionManager = new SessionManager(getActivity());
        User user = db_handler.getUser(sessionManager.getSessionData(Constants.SESSION_EMAIL));

        // Set Values
        setIds(view);
        setValues(user);
        setClickListeners();

        return view;
    }

    // Set Ids
    private void setIds(View view) {
        logoutLay = view.findViewById(R.id.logoutLay);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        mobile = view.findViewById(R.id.mobile);
        orders = view.findViewById(R.id.myOrdersLay);
        btnUpdet = view.findViewById(R.id.btnUpdet);
    }

    // Set Values
    private void setValues(User user) {
        // Name
        name.setText(user.getName());

        // Email
        email.setText(user.getEmail());

        // Mobile
        mobile.setText(user.getMobile());
    }

    // Set Click Listeners
    private void setClickListeners() {
        // Update
        btnUpdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProfilEdit.class);
                startActivity(i);
//                startActivity(new Intent(getApplicationContext(), ShoppingCart.class))
//                Intent i = new Intent(com.tubes.sandangin.fragments.Account.this, ProfilEdit.class);
//                i.putExtra("name",name);
//                startActivity(i);
            }
        });
//        btnUpdet.setOnClickListener(new View.OnClickListener() {
//            @SuppressWarnings("ConstantConditions")
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ProfilEdit.class);
//                startActivity(intent);
//                getActivity().overridePendingTransition(0,0);
//            }
//        });

        // My Orders
        orders.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyOrders.class);
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);
            }
        });


        // Logout
        logoutLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(getActivity());
                sessionManager.clearPreferences();
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishActivityCallback.finishActivity();
            }
        });
    }
}
