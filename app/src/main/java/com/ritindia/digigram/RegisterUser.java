package com.ritindia.digigram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {
    Button btnregister;
    EditText etphoneno,etname,etwardno,etlocation,etenterpassword;
    TextView tvwarning;
    String phoneno;
    String name;
    String wardno;
    String location;
    String password;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        btnregister=findViewById(R.id.btnregister);
        etphoneno=findViewById(R.id.etphoneno);
        etname=findViewById(R.id.etname);
        etwardno=findViewById(R.id.etwardno);
        etlocation=findViewById(R.id.etlocation);
        etenterpassword=findViewById(R.id.etenterpassword);
        tvwarning=findViewById(R.id.tvwaring);

        db=FirebaseFirestore.getInstance();

        Intent intent=getIntent();
        phoneno=intent.getStringExtra("phonenumber");
        etphoneno.setText(phoneno);

        //----Saving user details to User collection----
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=etname.getText().toString().trim();
                wardno=etwardno.getText().toString().trim();
                location=etlocation.getText().toString().trim();
                password=etenterpassword.getText().toString().trim();

                if(name.isEmpty()||wardno.isEmpty()||location.isEmpty()||password.isEmpty()){
                    tvwarning.setText("All fields are required..");
                }else{
                    Map<String,Object> user=new HashMap<>();
                    user.put("Name",name);
                    user.put("Phonenumber",phoneno);
                    user.put("Ward",wardno);
                    user.put("Location",location);
                    user.put("Password",password);

                    db.collection("User")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(RegisterUser.this, "User added successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent1);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterUser.this, "Failed to add user", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}