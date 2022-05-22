package com.ritindia.digigram;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ritindia.digigram.adapter.ComplaintAdapter;
import com.ritindia.digigram.ui.home.FecthDetailsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterComplaintActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        ComplaintData[] data = new ComplaintData[]{
                new ComplaintData("1", "New Connection", "12/01/2022", "New Water Connection", "ONGOING", "1"),
                new ComplaintData("2", "Maintenance", "12/02/2021", "New Water Connection", "ONGOING", "2"),
                new ComplaintData("3", "New Connection", "13/03/2022", "New Water Connection", "ONGOING", "3"),
                new ComplaintData("4", "New Connection", "14/04/2021", "New Water Connection", "ONGOING", "4"),
                new ComplaintData("5", "Maintenance", "15/05/2022", "New Water Connection", "ONGOING", "5"),
                new ComplaintData("6", "Water", "16/06/2021", "New Water Connection", "ONGOING", "6"),
                new ComplaintData("7", "Other", "17/07/2022", "New Water Connection", "ONGOING", "7"),
                new ComplaintData("8", "New Connection", "18/08/2021", "New Water Connection", "ONGOING", "8"),
                new ComplaintData("9", "Water", "19/09/2021", "New Water Connection", "ONGOING", "9"),
                new ComplaintData("10", "New Connection", "10/10/2022", "New Water Connection", "ONGOING", "10"),
                new ComplaintData("11", "Water", "1/12/2021", "New Water Connection", "ONGOING", "11"),
                new ComplaintData("12", "New Connection", "2/12/2022", "New Water Connection", "ONGOING", "12"),
        };

        recyclerView = (RecyclerView) findViewById(R.id.view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ComplaintData> list=Arrays.asList(data);

        recyclerView.setAdapter(new FecthDetailsAdapter(list));
    }
}
