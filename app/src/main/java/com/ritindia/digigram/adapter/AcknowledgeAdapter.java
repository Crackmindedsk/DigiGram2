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
import com.ritindia.digigram.HomePage;
import com.ritindia.digigram.R;
import com.ritindia.digigram.model.ComplaintModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcknowledgeAdapter extends RecyclerView.Adapter<AcknowledgeAdapter.AcknowledgeViewHolder>{
    private Context mCtx;
    private List<ComplaintModel> complaintModelList;
    public AcknowledgeAdapter(List<ComplaintModel> productList,Context mCtx){
        this.complaintModelList = productList;
        this.mCtx=mCtx;
    }
    @NonNull
    @Override
    public AcknowledgeAdapter.AcknowledgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.acknowledge_card,null);
        return new AcknowledgeAdapter.AcknowledgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcknowledgeAdapter.AcknowledgeViewHolder holder, int position) {
        ComplaintModel cm = complaintModelList.get(position);
        holder.text.setText(""+cm.getCdid());
        holder.categorytext.setText(""+cm.getCtype());
        holder.complaint.setText(""+cm.getDetails());
        holder.complaintDate.setText(""+cm.getRegistereddate());
        holder.complaintStatus.setText(""+cm.getStatus());
        holder.complaintward.setText(""+cm.getWard());
        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeComplaint(cm.getCdid());
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setMessage("Complaint Acknowledged Complete")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // START THE GAME!
                                mCtx.startActivity(new Intent(mCtx, HomePage.class));
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        holder.pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pendingComplaint(cm.getCdid());
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setMessage("Complaint acknowledged not yet complete")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // START THE GAME!
                                mCtx.startActivity(new Intent(mCtx, HomePage.class));
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

    public class AcknowledgeViewHolder extends RecyclerView.ViewHolder{
        TextView text,categorytext,complaint,complaintDate, complaintStatus, complaintward;
        Button pending, complete;

        public AcknowledgeViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.acknowledge_id);
            categorytext=itemView.findViewById(R.id.acknowledge_category);
            complaint=itemView.findViewById(R.id.acknowledge_complaint);
            complaintDate=itemView.findViewById(R.id.acknowledge_date);
            complaintStatus=itemView.findViewById(R.id.acknowledge_status);
            complaintward=itemView.findViewById(R.id.acknowledge_ward);
            complete=itemView.findViewById(R.id.acknowledge_complete);
            pending=itemView.findViewById(R.id.acknowledge_pending);

        }

    }
    private void completeComplaint(int cdid){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_COMPLETE_ACKNOWLEDGEMENT,
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
    private void pendingComplaint(int cdid){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_PENDING_ACKNOWLEDGEMENT,
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
