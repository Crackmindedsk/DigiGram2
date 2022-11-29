package com.ritindia.digigram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {
    Button btnregister;
    EditText etphoneno,etname,etwardno,etlocation,etenterpassword,etenteraadhaaar;
    TextView tvwarning;
    String phoneno;
    String name;
    String wardno;
    String location;
    String password;
    String aadhaarno;
    Integer id=1;
    private ProgressDialog progressDialog;


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
        etenteraadhaaar= findViewById(R.id.etenteraadhaar);
        progressDialog = new ProgressDialog(this);


        Intent intent=getIntent();
        phoneno=intent.getStringExtra("phonenumber");
        etphoneno.setText(phoneno);

        //----Saving user details to User collection----
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
    private void registerUser(){
        name=etname.getText().toString().trim();
        wardno=etwardno.getText().toString().trim();
        location=etlocation.getText().toString().trim();
        password=etenterpassword.getText().toString().trim();
        aadhaarno=etenteraadhaaar.getText().toString().trim();
        phoneno=etphoneno.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        id++;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonbject= new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonbject.getString("message"),Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),ComplaintStatusActivity.class));
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("uid", id.toString());
            params.put("uname", name);
            params.put("phone", phoneno);
            params.put("address", location);
            params.put("addharno", aadhaarno);
            params.put("wid", wardno);
            params.put("password", password);
            return params;
        }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}