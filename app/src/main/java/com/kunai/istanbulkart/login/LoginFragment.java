package com.kunai.istanbulkart.login;

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
import android.widget.EditText;
import android.widget.TextView;

import com.kunai.istanbulkart.HomeActivity;
import com.kunai.istanbulkart.R;
import com.kunai.istanbulkart.model.BoolModel;
import com.kunai.istanbulkart.model.PhoneLoginModel;
import com.kunai.istanbulkart.retrofit.APIUtils;
import com.kunai.istanbulkart.retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
    EditText editTextMail,editTextPassword;
    Button buttonLogin;

    RetrofitInterface retrofitInterface;
    TextView textForgotPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        editTextMail = view.findViewById(R.id.editTextMail);
        editTextPassword = view.findViewById(R.id.editTextPass);
        textForgotPassword = view.findViewById(R.id.textForgotPassword);
        textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HomeActivity.class));

            }
        });


        retrofitInterface = APIUtils.getIstanbulKartInterface();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = editTextMail.getText().toString();
                String pass = editTextPassword.getText().toString();


              retrofitInterface.girisYap(mail,pass).enqueue(new Callback<BoolModel>() {
                  @Override
                  public void onResponse(Call<BoolModel> call, Response<BoolModel> response) {
                      boolean message = response.body().isResponse();
                      if (message == true) {
                          Intent intent = new Intent(getActivity(),PinActivity.class);
                          intent.putExtra("phoneLogin","+90"+mail);
                          startActivity(intent);
                      }else {

                      }
                  }

                  @Override
                  public void onFailure(Call<BoolModel> call, Throwable t) {
                      Log.e("Login Error :",t.getLocalizedMessage());
                  }
              });
            }
        });
    }
}