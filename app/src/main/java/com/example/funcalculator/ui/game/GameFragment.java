package com.example.funcalculator.ui.game;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.funcalculator.R;
import com.example.funcalculator.databinding.FragmentGameBinding;

import com.example.funcalculator.model.pair.Pair;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.example.funcalculator.ui.main.MainActivity;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private GameViewModel gameViewModel;

    public GameFragment(){
        // Required empty public constructor
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GameViewModel gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false);
        binding.setViewModel(gameViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        gameViewModel.getStatesLiveData().observe(getViewLifecycleOwner(), states -> {
            Log.d("Game Fragment", "states: "+ states);
            updateMatrixUI(states);
        });

        gameViewModel.getGameStatusLiveData().observe(getViewLifecycleOwner(), gameStatus -> {
            Log.d("Game Fragment", "gameStatus: "+ gameStatus);
            if (gameStatus != 0) {
                gameStatusToast(gameStatus);
            }
        });


        View view = binding.getRoot();

        return view;
    }

    public void changeColorState(String textId, int color){
        try{
            Field field = binding.getClass().getDeclaredField(textId);
            field.setAccessible(true);
            EditText editText = (EditText) field.get(binding);
            if (editText != null) {
                switch (color) {
                    case -1:
                        editText.setBackgroundColor(getResources().getColor(R.color.grey));
                        break;
                    case 1:
                        editText.setBackgroundColor(getResources().getColor(R.color.light_green));
                        break;
                    case 2:
                        editText.setBackgroundColor(getResources().getColor(R.color.orange));
                        break;
                    default:
                        break;
                }
            }
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void gameStatusToast(int gameStatus){
        switch (gameStatus){
            case -1:
                Toast.makeText(getContext(), "You lost", Toast.LENGTH_SHORT).show();
                return;
            case 1:
                Toast.makeText(getContext(), "You won", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    public void modifyEditTextContent(String editTextName, String content){
        try{
            Field field = binding.getClass().getDeclaredField(editTextName);
            field.setAccessible(true);
            EditText editText = (EditText) field.get(binding);
            if (editText != null) {
                editText.setText(content);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateMatrixUI(List<List<Pair>> states){
        for (int i = 0; i < states.size(); i++) {
            List<Pair> row = states.get(i);
            for (int j = 0; j < row.size(); j++) {
                Pair pair = row.get(j);
                String editTextName = "editText" + (i*5) + j + 1;
                changeColorState(editTextName, pair.getColor());
                modifyEditTextContent(editTextName, pair.getContent().toString());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).lockOrientationPortrait();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).unlockOrientation();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}