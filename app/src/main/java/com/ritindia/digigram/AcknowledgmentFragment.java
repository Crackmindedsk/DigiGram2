package com.ritindia.digigram;

import static com.ritindia.digigram.Constants.URL_ACKNOWLEDGEMENT_LIST;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ritindia.digigram.adapter.AcknowledgeAdapter;
import com.ritindia.digigram.adapter.AdminComplaintAdapter;
import com.ritindia.digigram.adapter.ComplaintAdapter;
import com.ritindia.digigram.model.ComplaintModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcknowledgmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcknowledgmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    List<ComplaintModel> complaintModelList;

    public AcknowledgmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcknowledgmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcknowledgmentFragment newInstance(String param1, String param2) {
        AcknowledgmentFragment fragment = new AcknowledgmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_acknowledgment, container, false);
        recyclerView = view.findViewById(R.id.acknowledge_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        complaintModelList = new ArrayList<>();
        loadComplaints();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
    private void loadComplaints(){
        String id = SharedPrefManager.getInstance(getActivity()).getUserId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ACKNOWLEDGEMENT_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                complaintModelList.add(new ComplaintModel(
                                        product.getInt("cdid"),
                                        product.getString("ctype"),
                                        product.getString("details"),
                                        product.getString("registereddate"),
                                        product.getString("status"),
                                        product.getString("ward")
                                ));
                                Toast.makeText(getActivity(),""+product.getString("registereddate"),Toast.LENGTH_SHORT).show();

                            }
                            AcknowledgeAdapter adapter = new AcknowledgeAdapter(complaintModelList,requireContext());
                            recyclerView.setAdapter(adapter);

                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", id);
                return params;
            }
        };

        //adding our stringrequest to queue
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }
}