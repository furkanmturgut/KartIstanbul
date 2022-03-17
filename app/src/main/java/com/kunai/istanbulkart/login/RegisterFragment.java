package com.kunai.istanbulkart.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import com.kunai.istanbulkart.R;
import com.kunai.istanbulkart.model.BoolModel;
import com.kunai.istanbulkart.retrofit.APIUtils;
import com.kunai.istanbulkart.retrofit.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {
    EditText editTextTC,editTextName,editTextSoyad,editTextYear,editTextPhone,editTextRegMail,editTextRegPassword;
    Button buttonRegister;
    FirebaseAuth firebaseAuth;
    Boolean mailControl;
    RetrofitInterface retrofitInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextName= view.findViewById(R.id.editTextName);
        editTextTC = view.findViewById(R.id.editTextTC);
        editTextSoyad = view.findViewById(R.id.editTextSoyad);
        editTextYear = view.findViewById(R.id.editTextYear);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextRegMail = view.findViewById(R.id.editTextRegMail);
        editTextRegPassword = view.findViewById(R.id.editTextRegPassword);
        buttonRegister = view.findViewById(R.id.buttonRegister);
        firebaseAuth = FirebaseAuth.getInstance();
        retrofitInterface = APIUtils.getIstanbulKartInterface();


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClicked(view);
            }
        });
    }

    private void registerClicked(View view) {
        String tc = editTextTC.getText().toString();
        String name = editTextName.getText().toString();
        String surname = editTextSoyad.getText().toString();
        String mail = editTextRegMail.getText().toString();
        String pass = editTextRegPassword.getText().toString();
        String year = editTextYear.getText().toString();
        String phone = editTextPhone.getText().toString();
        mailControl = mail.contains("@");
        mailControl = mail.contains(".com");


        if (TextUtils.isEmpty(tc)|| TextUtils.isEmpty(name)||TextUtils.isEmpty(surname)||TextUtils.isEmpty(mail)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(year)||TextUtils.isEmpty(phone)){
            Snackbar snackbar = Snackbar.make(view,"Tüm alanları doldurunuz",BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else if (pass.length() < 6){
            Snackbar snackbar = Snackbar.make(view,"En az 6 karakter şifre belirleyin",BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else if(mailControl != true){
            Snackbar snackbar = Snackbar.make(view,"Geçerli mail adresi giriniz",BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else if(tc.length()>11 && tc.length()<11){
            Snackbar snackbar = Snackbar.make(view,"Geçerli TC giriniz",BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }
        else {
            retrofitInterface.uyeol(tc,name,surname,year,mail,pass,phone).enqueue(new Callback<BoolModel>() {
                @Override
                public void onResponse(Call<BoolModel> call, Response<BoolModel> response) {
                    boolean message = response.body().isResponse();
                    if (message == true){
                        Intent intent = new Intent(getContext(),PinActivity.class);
                        intent.putExtra("number","+90"+phone);
                        startActivity(intent);
                    }else {
                        Snackbar snackbar = Snackbar.make(view,"İşlemleri kontrol ediniz!1",BaseTransientBottomBar.LENGTH_LONG);
                        snackbar.show();
                        Log.e("Bool Message",""+message);

                    }
                }

                @Override
                public void onFailure(Call<BoolModel> call, Throwable t) {

                }
            });
                }

        }

    }
