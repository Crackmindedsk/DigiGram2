package com.ritindia.digigram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class RegisterComplaintActivity extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        String[] source={"1","2","3","4","5","6","7","8","9","10","11"};

        recyclerView = (RecyclerView) findViewById(R.id.view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ComplaintAdapter(source);
        recyclerView.setAdapter(adapter);
    }
}
