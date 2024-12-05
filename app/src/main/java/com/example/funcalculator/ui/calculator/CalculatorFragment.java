package com.example.funcalculator.ui.calculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.databinding.DataBindingUtil;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.example.funcalculator.R;
import com.example.funcalculator.data.remote.apis.evaluator.VolleyClient;
import com.example.funcalculator.databinding.FragmentCalculatorBinding;

public class CalculatorFragment extends Fragment {

    private FragmentCalculatorBinding binding;
    private CalculatorViewModel viewModel;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalculatorViewModel calculatorViewModel =
                new ViewModelProvider(this).get(CalculatorViewModel.class);

        calculatorViewModel.getScreen().observe(getViewLifecycleOwner(), value -> {
            binding.screen.setText(value);
        });
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false);
        View view = binding.getRoot();
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        VolleyClient volleyClient = new VolleyClient("evaluate", requestQueue);
        viewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);
        try {
            viewModel.setVolleyClient(volleyClient);
        } catch (RuntimeException e) {
            Log.e("CalculatorFragment", "VolleyClient cannot be null", e);
        }
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}