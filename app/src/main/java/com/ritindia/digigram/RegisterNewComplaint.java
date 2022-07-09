package com.ritindia.digigram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterNewComplaint extends AppCompatActivity {
    ArrayAdapter arrayAdapter;
    Button btnrnewcomplaint;
    String complaintadd,complaintdes,pickdate;
    EditText etcomplaintadd,etcomplaintdes,etdate;
    FirebaseFirestore db;
    Spinner spinner,spinner2;
    final Calendar myCalendar= Calendar.getInstance();

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
                R.array.Departments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        etcomplaintadd=findViewById(R.id.etcomplaintadd);
        etcomplaintdes=findViewById(R.id.etcomplaintdes);
        etdate=findViewById(R.id.etdate);
        db=FirebaseFirestore.getInstance();


        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterNewComplaint.this , date , myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnrnewcomplaint=findViewById(R.id.btnnewcomplaint);

        btnrnewcomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                complaintdes=etcomplaintdes.getText().toString();
                complaintadd=etcomplaintadd.getText().toString();
                pickdate=etdate.getText().toString();

                Map<String,Object> complaint=new HashMap<>();
                LocalVariables localVariables=new LocalVariables();
                complaint.put("Username",localVariables.user_id);
                //complaint.put("ComplaintID","1");
                complaint.put("Description",complaintdes);
                complaint.put("Address",complaintadd);
                complaint.put("Date",pickdate);
                complaint.put("Department",spinner2.getSelectedItem().toString());
                complaint.put("Status","Ongoing");
                complaint.put("Category",spinner.getSelectedItem().toString());

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

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        etdate.setText(dateFormat.format(myCalendar.getTime()));
    }
}