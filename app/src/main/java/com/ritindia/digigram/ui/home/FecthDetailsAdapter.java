package com.ritindia.digigram.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ritindia.digigram.ComplaintData;
import com.ritindia.digigram.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FecthDetailsAdapter extends RecyclerView.Adapter<FecthDetailsAdapter.ViewHolder> {

    List<ComplaintData> list;

    public FecthDetailsAdapter(List<ComplaintData> data) {
        this.list = data;
    }//

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_registered_complaints, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ComplaintData data=list.get(position);

        holder.t[0].setText(data.getId());
        holder.t[1].setText(data.getCategory());
        holder.t[2].setText(data.getComplaint());
        holder.t[3].setText(data.getDate());
        holder.t[4].setText(data.getStatus());
        holder.t[5].setText(data.getWarNO());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView[] t;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t = new TextView[]{
//                    itemView.findViewById(R.id.complaint_id_edit),
                    itemView.findViewById(R.id.complaint_category_edit),
                    itemView.findViewById(R.id.complaint_edit),
                    itemView.findViewById(R.id.Complaint_date_edit),
                    itemView.findViewById(R.id.transfer_text_edit),
//                    itemView.findViewById(R.id.status_edit),
            };
        }
    }
}
