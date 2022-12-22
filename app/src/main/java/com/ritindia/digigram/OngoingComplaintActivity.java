package com.ritindia.digigram;


import static com.ritindia.digigram.Constants.URL_ONGOING_COMPLAINTS;

import android.os.Bundle;
import android.widget.Toast;

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
import com.ritindia.digigram.adapter.ComplaintAdapter;
import com.ritindia.digigram.model.ComplaintModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OngoingComplaintActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ComplaintModel> complaintModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        recyclerView = (RecyclerView) findViewById(R.id.view_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintModelList = new ArrayList<>();
        loadComplaints();
//        adapter.notifyDataSetChanged();
    }
    private void loadComplaints(){
        String id = SharedPrefManager.getInstance(getApplicationContext()).getUserId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ONGOING_COMPLAINTS,
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
                            ComplaintAdapter adapter = new ComplaintAdapter(complaintModelList,OngoingComplaintActivity.this);
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
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", id);
                return params;
            }
        };

        //adding our stringrequest to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
