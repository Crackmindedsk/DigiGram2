package com.ritindia.digigram.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ritindia.digigram.R;

import java.util.ArrayList;
import java.util.Map;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {
    //    private String[] data;
    private ArrayList<Map<String,String>> data;
    public ComplaintAdapter(ArrayList<Map<String,String>> data){
        this.data=data;
    }
    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_registered_complaints,parent,false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        Log.d("Data", data.size()+"");
        String title = data.get(position).get("ID");
        holder.text.setText(title);
        holder.categorytext.setText(data.get(position).get("Category"));
        holder.complaint.setText(data.get(position).get("Complaint"));
        holder.date.setText(data.get(position).get("Date"));
        holder.complaintStatus.setText(data.get(position).get("Status"));
        holder.wardNo.setText(data.get(position).get("Ward"));
    }

    @Override
    public int getItemCount() {
        Log.d("Data", data.size()+"");
        return data.size();
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder{
        TextView text,categorytext,complaint, date, complaintStatus, wardNo;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.complaint_id_edit);
            categorytext = itemView.findViewById(R.id.complaint_category_edit);
            complaint = itemView.findViewById(R.id.complaint_edit);
            date = itemView.findViewById(R.id.Complaint_date_edit);
            complaintStatus = itemView.findViewById(R.id.transfer_text_edit);
            wardNo = itemView.findViewById(R.id.ward_edit);

        }
    }
}

