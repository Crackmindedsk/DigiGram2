package com.ritindia.digigram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterNewComplaint extends AppCompatActivity {
    ArrayAdapter arrayAdapter;
    Button btnrnewcomplaint;
    String complaintadd,complaintdes,date;
    EditText etcomplaintadd,etcomplaintdes,etdate;
    FirebaseFirestore db;
    Spinner spinner,spinner2;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_complaint);
        spinner= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.water_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner2= (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Derpartments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        etcomplaintadd=findViewById(R.id.etcomplaintadd);
        etcomplaintdes=findViewById(R.id.etcomplaintdes);
        etdate=findViewById(R.id.etdate);
        db=FirebaseFirestore.getInstance();


        btnrnewcomplaint=findViewById(R.id.btnnewcomplaint);

        btnrnewcomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                complaintdes=etcomplaintdes.getText().toString();
                complaintadd=etcomplaintadd.getText().toString();
                date=etdate.getText().toString();

                Map<String,Object> complaint=new HashMap<>();
                LocalVariables localVariables=new LocalVariables();
                complaint.put("Username",localVariables.user_id);
                //complaint.put("ComplaintID","1");
                complaint.put("Complaint_Description",complaintdes);
                complaint.put("Complaint_Address",complaintadd);
                complaint.put("Date",date);
                complaint.put("Department",spinner2.getSelectedItem().toString());
                complaint.put("Status","Ongoing");
                complaint.put("Complaint_Category",spinner.getSelectedItem().toString());

                db.collection("Complaints")
                        .add(complaint)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(RegisterNewComplaint.this, "Complaint added...", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterNewComplaint.this, "Failed to add complaint...", Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog.Builder notify=new AlertDialog.Builder(RegisterNewComplaint.this);
                notify.setMessage("Complaint registerd successfully..");
                notify.setIcon(R.drawable.ic_launcher_background);
                notify.setCancelable(false);
                notify.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                notify.show();

            }
        });
    }
}