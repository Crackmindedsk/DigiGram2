package com.ritindia.digigram;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ritindia.digigram.adapter.ComplaintAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisteredComplaintActivity extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        Toast.makeText(this, "Hello ", Toast.LENGTH_SHORT).show();
        db = FirebaseFirestore.getInstance();
        String[] source={"1","2","3","4","5","6","7","8","9","10","11"};
        ArrayList<Map<String,String>> data = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.collection("Complaints").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    int id = 1;
                    List<DocumentSnapshot> arr = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot db : arr){
                        Map<String,String> map = new HashMap<>();
                        map.put("ID", ""+id);
                        id++;
                        map.put("Category", db.getData().get("Category").toString());
                        map.put("Complaint", db.getData().get("Description").toString());
                        map.put("Date", db.getData().get("Date").toString());
                        map.put("Status", db.getData().get("Status").toString());
                        map.put("Ward", db.getData().get("Address").toString());
                        Log.d("Print", db.getData().get("Status").toString());
                        Log.d("Print", db.getData().get("Department").toString());
//                        Log.d("Print", db.getData().get("Complaint_Category").toString());
//                        Log.d("Print", db.getData().get("Complaint_Address").toString());
//                        Log.d("Print", db.getData().get("Status").toString());
                        Log.d("Print", db.getData().toString());
                        data.add(map);

                    }

                    Log.d("Data",data.toString());

                    adapter = new ComplaintAdapter(data);
                    recyclerView.setAdapter(adapter);
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Print Fail", e.getMessage());
            }
        });

    }
}
