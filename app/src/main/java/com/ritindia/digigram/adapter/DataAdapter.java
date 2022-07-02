package com.ritindia.digigram.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ritindia.digigram.ComplaintData;
import com.ritindia.digigram.R;
import com.ritindia.digigram.RegisterComplaintActivity;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<ComplaintData> dataArrayList;
    public Context context;

    // creating constructor for our adapter class
    public DataAdapter(ArrayList<ComplaintData> coursesArrayList, Context context) {
        this.dataArrayList= coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_registered_complaints, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        ComplaintData complaintData = dataArrayList.get(position);
        holder.complaintid.setText(complaintData.getId());
        holder.category.setText(complaintData.getCategory());
        holder.complaint.setText(complaintData.getComplaint());
        holder.date.setText(complaintData.getDate());
        holder.status.setText(complaintData.getStatus());
        holder.ward.setText(complaintData.getWarNO());
    }


    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return dataArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView complaintid;
        private final TextView category;
        private final TextView complaint;
        private final TextView date;
        private final TextView status;
        private final TextView ward;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            complaintid = itemView.findViewById(R.id.complaint_id_edit);
            category = itemView.findViewById(R.id.complaint_category_edit);
            complaint = itemView.findViewById(R.id.complaint_edit);
            date=itemView.findViewById(R.id.Complaint_date_edit);
            status = itemView.findViewById(R.id.transfer_text_edit);
            ward=itemView.findViewById(R.id.status_edit);
        }
    }
}
