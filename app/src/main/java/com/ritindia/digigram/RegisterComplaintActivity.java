package com.ritindia.digigram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ritindia.digigram.adapter.ComplaintAdapter;
import com.ritindia.digigram.adapter.DataAdapter;
import com.ritindia.digigram.ui.home.FecthDetailsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterComplaintActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    DataAdapter adapter;
    private ArrayList<ComplaintData> dataArrayList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        dataArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        adapter = new DataAdapter(dataArrayList, this);

        // setting adapter to our recycler view.
        recyclerView.setAdapter(adapter);



//        recyclerView = (RecyclerView) findViewById(R.id.view_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        List<ComplaintData> list=Arrays.asList(data);
//
//        recyclerView.setAdapter(new FecthDetailsAdapter(list));


    }
    private void listOfComplaint(){

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
//                                SharedPrefManager.getInstance(getApplicationContext())
//                                        .userLogin(
//                                                obj.getInt("id"),
//                                                obj.getString("username"),
//                                                obj.getString("email")
//                                        );
                                startActivity(new Intent(getApplicationContext(),HomePage.class));
//                                finish();
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
//                params.put("phone", username);
//                params.put("password", password);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
