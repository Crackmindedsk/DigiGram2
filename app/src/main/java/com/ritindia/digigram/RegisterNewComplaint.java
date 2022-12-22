package com.ritindia.digigram;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterNewComplaint extends AppCompatActivity {
    ArrayAdapter arrayAdapter;
    Button btnrnewcomplaint;
    String complaintdes,date, category;
    EditText etcomplaintdes;
    Spinner spinner,spinner2;
    private ProgressDialog progressDialog;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_complaint);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.water_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        spinner2= (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Departments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        etcomplaintdes=findViewById(R.id.etcomplaintdes);
        btnrnewcomplaint=findViewById(R.id.btnnewcomplaint);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        btnrnewcomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                complaintdes=etcomplaintdes.getText().toString();
                category=spinner2.getSelectedItem().toString();
                newComplaint();


            }
        });
    }

    private void newComplaint(){
        progressDialog.show();
        String id = SharedPrefManager.getInstance(getApplicationContext()).getUserId();
        String ward = SharedPrefManager.getInstance(getApplicationContext()).getUserWard();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_TRIAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setMessage("Complaint Registered")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // START THE GAME!
                                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                                        finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                       // startActivity(new Intent(getApplicationContext(),HomeFragment.class));
                        try {
                            JSONObject jsonbject= new JSONObject(response);
                            if(!jsonbject.getBoolean("error")){
                                Toast.makeText(getApplicationContext(),"Complaint Registered",Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(
                                        getApplicationContext(),"Unsuccessful",
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> complaint=new HashMap<>();
                complaint.put("uid",id);
                complaint.put("wid",ward);
                complaint.put("details",complaintdes);
                complaint.put("ctype",category);
                return complaint;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}