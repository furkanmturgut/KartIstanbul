package com.kunai.istanbulkart.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kunai.istanbulkart.R;


public class HomeFragment extends Fragment {
    TextView textPrice;
    Button buttonHistoryProcess,buttonAddMoney,buttoneveningProcess;
    String fragmentTC;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();
        fragmentTC = bundle.getString("myTelNo");
        Log.e("Home Fragment Tel : ",""+fragmentTC);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textPrice = view.findViewById(R.id.textPrice);
        buttonHistoryProcess = view.findViewById(R.id.buttonHistoryProcess);
        buttonAddMoney = view.findViewById(R.id.buttonAddMoney);
        buttoneveningProcess = view.findViewById(R.id.buttoneveningProcess);

        buttonAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddMoneyActivity.class);
                startActivity(intent);
            }
        });
    }
}