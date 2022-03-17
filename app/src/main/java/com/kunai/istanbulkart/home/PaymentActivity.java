package com.kunai.istanbulkart.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.kunai.istanbulkart.HomeActivity;
import com.kunai.istanbulkart.R;
import com.kunai.istanbulkart.model.BoolModel;
import com.kunai.istanbulkart.retrofit.APIUtils;
import com.kunai.istanbulkart.retrofit.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    EditText editTextCardNumber,editTextDate,editTextCCV;
    Button buttonPayment;
    String myPrice;
    int price;
    RetrofitInterface retrofitInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        editTextCardNumber = findViewById(R.id.editTextCardNumber);
        editTextCCV = findViewById(R.id.editTextCCV);
        editTextDate = findViewById(R.id.editTextDate);
        buttonPayment = findViewById(R.id.buttonPayment);

        retrofitInterface = APIUtils.getIstanbulKartInterface();

        Bundle bundle = getIntent().getExtras();
        myPrice = bundle.getString("tutar");
        Log.e("Tutarım : ",myPrice);
        price = Integer.parseInt(myPrice);

        buttonPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber = editTextCardNumber.getText().toString();
                String ccv = editTextCCV.getText().toString();
                String date = editTextDate.getText().toString();


                int myCCV = Integer.parseInt(ccv);

                retrofitInterface.odemeAl(cardNumber,price,myCCV,date).enqueue(new Callback<BoolModel>() {
                    @Override
                    public void onResponse(Call<BoolModel> call, Response<BoolModel> response) {
                        boolean message = response.body().isResponse();
                        if (message == true){
                            Snackbar snackbar = Snackbar.make(view,"İşleminiz gerçekleşti.",BaseTransientBottomBar.LENGTH_LONG);
                            snackbar.show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                }
                            },2000);

                        }else {
                            Snackbar snackbar = Snackbar.make(view,"İşlemlerinizi kontrol ediniz.",BaseTransientBottomBar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BoolModel> call, Throwable t) {
                        Snackbar snackbar = Snackbar.make(view,"Beklenmedik hata oluştu!",BaseTransientBottomBar.LENGTH_LONG);
                        snackbar.show();
                        Log.e("Hata Odeme : ",t.getLocalizedMessage());
                    }
                });


            }
        });
    }
}