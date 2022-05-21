package com.ritindia.digigram.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ritindia.digigram.R;
<<<<<<< HEAD
=======
import com.ritindia.digigram.RegisterNewComplaint;
>>>>>>> 3ec5a0efcf7846004f097961e015a4d4c9064c7e

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);
        Button btncomplaint=(Button) view.findViewById(R.id.btncomplaint);
        btncomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), RegisterNewComplaint.class);
                startActivity(i);
            }
        });
        return view;

    }
}

