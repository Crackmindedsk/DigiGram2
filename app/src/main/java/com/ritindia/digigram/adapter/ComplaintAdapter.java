package com.ritindia.digigram.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ritindia.digigram.R;
import com.ritindia.digigram.model.ComplaintModel;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {
    private Context mCtx;
    private List<ComplaintModel> complaintModelList;
    public ComplaintAdapter(List<ComplaintModel> productList,Context mCtx) {
        this.complaintModelList = productList;
        this.mCtx=mCtx;
    }
    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.activity_registered_complaints,null);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        ComplaintModel cm = complaintModelList.get(position);
        holder.complainttext.setText(""+cm.getCdid());
        holder.categorytext.setText(""+cm.getCtype());
        holder.complaintdetails.setText(""+cm.getDetails());
        holder.complaintdate.setText(""+cm.getRegistereddate());
        holder.complaintstatus.setText(""+cm.getStatus());
        holder.complaintward.setText(""+cm.getWard());

    }

    @Override
    public int getItemCount() {
        return complaintModelList.size();
    }

    public static class ComplaintViewHolder extends RecyclerView.ViewHolder{
        TextView complainttext,categorytext, complaintdetails, complaintdate, complaintstatus, complaintward;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            complainttext=itemView.findViewById(R.id.complaint_id_edit_text);
            categorytext=itemView.findViewById(R.id.complaint_category_edit);
            complaintdetails=itemView.findViewById(R.id.complaint_edit);
            complaintdate = itemView.findViewById(R.id.Complaint_date_edit);
            complaintstatus=itemView.findViewById(R.id.transfer_text_edit);
            complaintward=itemView.findViewById(R.id.ward_edit);

        }
    }
}

