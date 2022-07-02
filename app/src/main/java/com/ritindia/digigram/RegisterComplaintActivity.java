package com.ritindia.digigram;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ritindia.digigram.adapter.ComplaintAdapter;
import com.ritindia.digigram.adapter.DataAdapter;
import com.ritindia.digigram.ui.home.FecthDetailsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterComplaintActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    DataAdapter adapter;
    private ArrayList<ComplaintData> dataArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

//        ComplaintData[] data = new ComplaintData[]{
//                new ComplaintData("1", "New Connection", "12/01/2022", "New Water Connection", "ONGOING", "1"),
//                new ComplaintData("2", "Maintenance", "12/02/2021", "New Water Connection", "ONGOING", "2"),
//                new ComplaintData("3", "New Connection", "13/03/2022", "New Water Connection", "ONGOING", "3"),
//                new ComplaintData("4", "New Connection", "14/04/2021", "New Water Connection", "ONGOING", "4"),
//                new ComplaintData("5", "Maintenance", "15/05/2022", "New Water Connection", "ONGOING", "5"),
//                new ComplaintData("6", "Water", "16/06/2021", "New Water Connection", "ONGOING", "6"),
//                new ComplaintData("7", "Other", "17/07/2022", "New Water Connection", "ONGOING", "7"),
//                new ComplaintData("8", "New Connection", "18/08/2021", "New Water Connection", "ONGOING", "8"),
//                new ComplaintData("9", "Water", "19/09/2021", "New Water Connection", "ONGOING", "9"),
//                new ComplaintData("10", "New Connection", "10/10/2022", "New Water Connection", "ONGOING", "10"),
//                new ComplaintData("11", "Water", "1/12/2021", "New Water Connection", "ONGOING", "11"),
//                new ComplaintData("12", "New Connection", "2/12/2022", "New Water Connection", "ONGOING", "12"),
//        };

        db=FirebaseFirestore.getInstance();
        db.collection("Complaint").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are
                            // hiding our progress bar and adding
                            // our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                ComplaintData c = d.toObject(ComplaintData.class);

                                // and we will pass this object class
                                // inside our arraylist which we have
                                // created for recycler view.
                                dataArrayList.add(c);
                            }
                            // after adding the data to recycler view.
                            // we are calling recycler view notifuDataSetChanged
                            // method to notify that data has been changed in recycler view.
                            adapter.notifyDataSetChanged();
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(getApplicationContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        Toast.makeText(getApplicationContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });

        dataArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        adapter = new DataAdapter(dataArrayList, this);

        // setting adapter to our recycler view.
        recyclerView.setAdapter(adapter);

//        recyclerView = (RecyclerView) findViewById(R.id.view_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        List<ComplaintData> list=Arrays.asList(data);
//
//        recyclerView.setAdapter(new FecthDetailsAdapter(list));


//        DocumentReference docRef = db.collection("Complaint").document("FA");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d("str", "DocumentSnapshot data: " + document.getData());
//                    } else {
//                        Log.d("str", "No such document");
//                    }
//                } else {
//                    Log.d("str", "get failed with ", task.getException());
//                }
//            }
//        });
//        db.collection("Complaint")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                                for (DocumentSnapshot d : list) {
//                                    // after getting this list we are passing
//                                    // that list to our object class.
//                                    ComplaintData c = d.toObject(ComplaintData.class);
//
//                                    // and we will pass this object class
//                                    // inside our arraylist which we have
//                                    // created for recycler view.
//                                    dataArrayList.add(c);
//                                }
////                                Log.d("str", document.getId() + " => " + document.get("Department"));
//                            }
//                        } else {
//                            Log.d("str", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });



    }
}
