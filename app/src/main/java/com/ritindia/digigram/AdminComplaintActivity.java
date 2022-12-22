package com.ritindia.digigram;

import static com.ritindia.digigram.Constants.URL_ADMIN_COMPLAINT;
import static com.ritindia.digigram.Constants.URL_PRODUCTS;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ritindia.digigram.adapter.AdminComplaintAdapter;
import com.ritindia.digigram.adapter.ComplaintAdapter;
import com.ritindia.digigram.model.ComplaintModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminComplaintActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ComplaintModel> complaintModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaint);

        recyclerView = (RecyclerView) findViewById(R.id.admin_complaint_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintModelList = new ArrayList<>();
        loadComplaints();


    }
    private void loadComplaints(){
        String alevel = AdminSharedPrefManager.getInstance(getApplicationContext()).getAdminLevel();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADMIN_COMPLAINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                complaintModelList.add(new ComplaintModel(
                                        product.getInt("cdid"),
                                        product.getString("ctype"),
                                        product.getString("details"),
                                        product.getString("registereddate"),
                                        product.getString("status"),
                                        product.getString("ward")
                                ));
                                Toast.makeText(getApplicationContext(),""+product.getString("registereddate"),Toast.LENGTH_SHORT).show();

                            }
                            AdminComplaintAdapter adapter = new AdminComplaintAdapter(complaintModelList,AdminComplaintActivity.this);
                            recyclerView.setAdapter(adapter);

                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("alevel", alevel);
                return params;
            }
        };

        //adding our stringrequest to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}