package com.ritindia.digigram.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ritindia.digigram.R;


public class AdminComplaintAdapter extends RecyclerView.Adapter<AdminComplaintAdapter.AdminComplaintViewHolder>{
    private String[] data;
    public AdminComplaintAdapter(String[] data){
        this.data=data;
    }
    @NonNull
    @Override
    public AdminComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_complaint_card,parent,false);
        return new AdminComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminComplaintViewHolder holder, int position) {
        String title = data[position];
        holder.text.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class AdminComplaintViewHolder extends RecyclerView.ViewHolder{
        TextView text,categorytext;
        Button approve, reject;

        public AdminComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.admin_complaint_id_edit);
            categorytext=itemView.findViewById(R.id.admin_complaint_category_edit);
            approve=itemView.findViewById(R.id.button);
            reject=itemView.findViewById(R.id.button2);
        }
    }
}
