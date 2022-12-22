package com.ritindia.digigram.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ritindia.digigram.AdminHomeActivity;
import com.ritindia.digigram.Constants;
import com.ritindia.digigram.R;
import com.ritindia.digigram.model.ComplaintModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminComplaintAdapter extends RecyclerView.Adapter<AdminComplaintAdapter.AdminComplaintViewHolder>{
    private Context mCtx;
    private List<ComplaintModel> complaintModelList;
    public AdminComplaintAdapter(List<ComplaintModel> productList,Context mCtx){
        this.complaintModelList = productList;
        this.mCtx=mCtx;
    }
    @NonNull
    @Override
    public AdminComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.admin_complaint_card,null);
        return new AdminComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminComplaintViewHolder holder, int position) {
        ComplaintModel cm = complaintModelList.get(position);
        holder.text.setText(""+cm.getCdid());
        holder.categorytext.setText(""+cm.getCtype());
        holder.complaint.setText(""+cm.getDetails());
        holder.complaintDate.setText(""+cm.getRegistereddate());
        holder.complaintStatus.setText(""+cm.getStatus());
        holder.complaintward.setText(""+cm.getWard());
        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ongoing(cm.getCdid());
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setMessage("Complaint approved, set to Ongoing")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // START THE GAME!
                                mCtx.startActivity(new Intent(mCtx, AdminHomeActivity.class));
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete(cm.getCdid());
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setMessage("Complaint Completed")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // START THE GAME!
                                mCtx.startActivity(new Intent(mCtx, AdminHomeActivity.class));
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return complaintModelList.size();
    }

    public class AdminComplaintViewHolder extends RecyclerView.ViewHolder{
        TextView text,categorytext,complaint,complaintDate, complaintStatus, complaintward;
        Button approve, complete;

        public AdminComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.admin_complaint_id_edit);
            categorytext=itemView.findViewById(R.id.admin_complaint_category_edit);
            complaint=itemView.findViewById(R.id.admin_complaint_edit);
            complaintDate=itemView.findViewById(R.id.admin_Complaint_date_edit);
            complaintStatus=itemView.findViewById(R.id.admin_complaint_status);
            complaintward=itemView.findViewById(R.id.admin_ward_edit);
            approve=itemView.findViewById(R.id.button);
            complete=itemView.findViewById(R.id.button2);

        }

    }
    private void ongoing(int cdid){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ONGOING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                Toast.makeText(mCtx,obj.getString("cdid"),Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cdid", String.valueOf(cdid));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);
    }
    private void complete(int cdid){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_COMPLETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                Toast.makeText(mCtx,obj.getString("cdid"),Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cdid", String.valueOf(cdid));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);
    }
}