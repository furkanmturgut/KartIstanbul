package com.kunai.istanbulkart.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kunai.istanbulkart.R;

public class AddMoneyActivity extends AppCompatActivity {
    EditText editTextAddMoney;
    Button buttonNextPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        editTextAddMoney = findViewById(R.id.editTextAddMoney);
        buttonNextPrice = findViewById(R.id.buttonNextPrice);

        buttonNextPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price = editTextAddMoney.getText().toString();
                Intent intent = new Intent(getApplicationContext(),PaymentActivity.class);
                intent.putExtra("tutar",price);
                startActivity(intent);
            }
        });

    }
}