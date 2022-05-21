package com.ritindia.digigram;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ritindia.digigram.adapter.AdminComplaintAdapter;

public class AdminComplaintActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaint);
        String[] source={"1","2","3","4","5","6","7","8","9","10","11"};

        recyclerView = (RecyclerView) findViewById(R.id.admin_complaint_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminComplaintAdapter(source);
        recyclerView.setAdapter(adapter);
    }
}