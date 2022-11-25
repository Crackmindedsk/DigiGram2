package com.ritindia.digigram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.TimeUnit;

public class Verification extends AppCompatActivity {
    Button btnverifyotp, btnsendotp;
    EditText etphonenoverify, etotp;
    String phonenumber = "";


    String otp = "";
    boolean result=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);



        btnsendotp = findViewById(R.id.btnsendotp);
        btnverifyotp = findViewById(R.id.btnverifyotp);

        etphonenoverify = findViewById(R.id.etphonenoverify);
        etotp = findViewById(R.id.etotp);

        //----code starts for firebase phone authentication

        //validation of phone number and calling verify()
        btnsendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonenumber = "+91" + etphonenoverify.getText().toString().trim();
                if (phonenumber.length() != 13) {
                    etphonenoverify.setError("Enter 10 digit valid phone number");
                    etphonenoverify.requestFocus();
                }else if(checkPhoneRegistration(phonenumber)){
                    Toast.makeText(Verification.this, "Phone number is already registered", Toast.LENGTH_SHORT).show();
                }else {
//                    verify(phonenumber);
                }
            }
        });
        //Checking otp is valid or not
        btnverifyotp.setOnClickListener(view -> {
            String temp = etotp.getText().toString();

        });
    }
    //Method for getting otp

    //----code ends for firebase phone authentication

    //----Method for checking phone number is already registered or not
    private boolean checkPhoneRegistration(String phonenum){
        return true;
    }
}