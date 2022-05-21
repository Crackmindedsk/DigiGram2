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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.TimeUnit;

public class Verification extends AppCompatActivity {
    Button btnverifyotp, btnsendotp;
    EditText etphonenoverify, etotp;
    String phonenumber = "";
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    FirebaseFirestore db;
    String otp = "";
    boolean result=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        auth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        btnsendotp = findViewById(R.id.btnsendotp);
        btnverifyotp = findViewById(R.id.btnverifyotp);

        etphonenoverify = findViewById(R.id.etphonenoverify);
        etotp = findViewById(R.id.etotp);

        //----code starts for firebase phone authentication
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                otp = s;
                Toast.makeText(Verification.this, "OTP Sent", Toast.LENGTH_SHORT).show();
            }
        };
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
                    verify(phonenumber);
                }
            }
        });
        //Checking otp is valid or not
        btnverifyotp.setOnClickListener(view -> {
            String temp = etotp.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otp, temp);
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Verification.this, "Successfull", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(), RegisterUser.class);
                                i.putExtra("phonenumber",phonenumber);
                                startActivity(i);
                            }else {
                                etotp.setError("enter correct otp...");
                            }
                        }
                    });
        });
    }
    //Method for getting otp
    private void verify(String phonenumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    //----code ends for firebase phone authentication

    //----Method for checking phone number is already registered or not
    private boolean checkPhoneRegistration(String phonenum){
        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot snapshot:task.getResult()){
                                if(phonenum.equals(snapshot.getString("Phonenumber"))){
                                    result=true;
                                }
                            }
                        }
                    }
                });
        return result;
    }
}