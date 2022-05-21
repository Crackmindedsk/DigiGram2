package com.ritindia.digigram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.ritindia.digigram.databinding.ActivityComplaintStatusBinding;

public class ComplaintStatusActivity extends AppCompatActivity {
    ActivityComplaintStatusBinding binding;
    String[] description = {"Registered", "Received", "Ongoing", "Complete"};
    int current_state = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComplaintStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.stp.setLabels(description).setBarColorIndicator(Color.TRANSPARENT)
                .setProgressColorIndicator(getResources().getColor(R.color.primaryColor))
                .setLabelColorIndicator(getResources().getColor(R.color.black))
                .setCompletedPosition(0)
                .drawView();

        binding.stp.setCompletedPosition(current_state);

    }
}
