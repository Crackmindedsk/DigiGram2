package com.ritindia.digigram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ritindia.digigram.databinding.ActivityComplaintStatusBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ComplaintStatusActivity extends AppCompatActivity {
    ActivityComplaintStatusBinding binding;
    String[] description = {"Registered", "Received", "Ongoing", "Complete"};
    int current_state = 0;
    private ProgressDialog progressDialog;
    EditText userid;
    String user, status;
    Button statusButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComplaintStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userid = findViewById(R.id.userid);
        statusButton = findViewById(R.id.getStatus);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        binding.stp.setLabels(description).setBarColorIndicator(Color.TRANSPARENT)
                .setProgressColorIndicator(getResources().getColor(R.color.primaryColor))
                .setLabelColorIndicator(getResources().getColor(R.color.black))
                .setCompletedPosition(0)
                .drawView();
        statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getComplaintStatus();
            }
        });




//        binding.stp.setCompletedPosition(current_state);

    }
    private void getComplaintStatus(){
        user = userid.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_COMPLAINT_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(),obj.getString("status"),Toast.LENGTH_LONG).show();
                                status = obj.getString("status");
                                if(description[2].equals(status)){
                                    current_state = 2;
                                    binding.stp.setCompletedPosition(current_state);
                                }else if(description[0].equals(status)){
                                    current_state=0;
                                    binding.stp.setCompletedPosition(current_state);
                                }else if(description[1].equals(status)){
                                    current_state=1;
                                    binding.stp.setCompletedPosition(current_state);
                                }else if(description[3].equals(status)){
                                    current_state=3;
                                    binding.stp.setCompletedPosition(current_state);
                                }else {
                                    Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_LONG).show();
                                }
//
                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
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
                Map<String, String> params = new HashMap<>();
                params.put("cdid", user);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
