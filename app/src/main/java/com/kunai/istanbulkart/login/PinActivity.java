package com.kunai.istanbulkart.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kunai.istanbulkart.HomeActivity;
import com.kunai.istanbulkart.R;

import java.util.concurrent.TimeUnit;

public class PinActivity extends AppCompatActivity {
    Button buttonSucces;
    EditText editTextPin;
    String phoneNumber;
    String phoneLoginNumber;
    String phoneNumberData;
    String otpid;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        editTextPin = findViewById(R.id.editTextPin);
        buttonSucces = findViewById(R.id.buttonSucces);
        firebaseAuth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        phoneNumber = bundle.getString("number");

        Bundle bundle2 = getIntent().getExtras();
        phoneLoginNumber = bundle2.getString("phoneLogin");

        if (phoneLoginNumber == null) {
            phoneNumberData = phoneNumber;
            otpProcess();
        }else if(phoneNumber == null){
            phoneNumberData = phoneLoginNumber;
            otpProcess();
        }else {
            Toast.makeText(this,"Geçersiz İşlem!", Toast.LENGTH_SHORT).show();
        }

        Log.e("Phone :",""+phoneNumber);
        Log.e("Phone :",""+phoneLoginNumber);



        buttonSucces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextPin.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Bu alan boş bırakılamaz!",Toast.LENGTH_LONG).show();
                else if(editTextPin.getText().toString().length()!=6)
                    Toast.makeText(getApplicationContext(),"Geçerli kod gir!",Toast.LENGTH_LONG).show();
                else
                {
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(otpid,editTextPin.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });


    }

    private void otpProcess() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumberData,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        otpid=s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
                    {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.e("My Error",e.getMessage());
                    }
                });        // OnVerificationStateChangedCallbacks

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                            intent.putExtra("telNo",phoneLoginNumber);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(),"Kodunuzu kontrol ediniz.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    }

