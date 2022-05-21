package com.ritindia.digigram.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ritindia.digigram.R;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {
    private String[] data;
    public ComplaintAdapter(String[] data){
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
        String title = data[position];
        holder.text.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder{
        TextView text,categorytext;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.complaint_id_edit);
            categorytext=itemView.findViewById(R.id.complaint_category_edit);
        }
    }
}

