package com.ritindia.digigram;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewComplaintFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String complaintdes,date, category;
    Spinner spinner;
    EditText etcomplaintdes;
    Button btnrnewcomplaint;
    private ProgressDialog progressDialog;

    public NewComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewComplaintFragment newInstance(String param1, String param2) {
        NewComplaintFragment fragment = new NewComplaintFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_register_new_complaint, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        spinner = view.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.Departments,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
        etcomplaintdes = view.findViewById(R.id.etcomplaintdes);
        btnrnewcomplaint = view.findViewById(R.id.btnnewcomplaint);
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Please wait...");
        btnrnewcomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                complaintdes=etcomplaintdes.getText().toString();
                category=spinner.getSelectedItem().toString();
                newComplaint();


            }
        });
    }
    private void newComplaint(){
        progressDialog.show();
        String id = SharedPrefManager.getInstance(getActivity()).getUserId();
        String ward = SharedPrefManager.getInstance(getActivity()).getUserWard();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_TRIAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Complaint Registered")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // START THE GAME!
                                        startActivity(new Intent(getActivity(), HomePage.class));
                                        requireActivity().finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
//                        startActivity(new Intent(getActivity(),HomePage.class));
                        try {
                            JSONObject jsonbject= new JSONObject(response);
                            if(!jsonbject.getBoolean("error")){
                                Toast.makeText(getActivity(),"Complaint Registered",Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(
                                        getActivity(),"Unsuccessful",
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getActivity(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> complaint=new HashMap<>();
                complaint.put("uid",id);
                complaint.put("wid",ward);
                complaint.put("details",complaintdes);
                complaint.put("ctype",category);
                return complaint;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }
}