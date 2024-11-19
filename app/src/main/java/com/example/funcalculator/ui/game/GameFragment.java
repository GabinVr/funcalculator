package com.example.funcalculator.ui.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.funcalculator.R;
import com.example.funcalculator.databinding.FragmentGameBinding;
import com.example.funcalculator.model.game.LiteralState;

import java.lang.reflect.Field;
import java.nio.channels.ScatteringByteChannel;
import java.util.Arrays;
import java.util.LinkedList;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private GameViewModel gameViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        binding = FragmentGameBinding.inflate(inflater, container, false);

        setNumberButtons();
        setOperationButtons();
        setControlButtons();

        View root = binding.getRoot();

        return root;
    }

    private void colorState(LinkedList<Integer> state, int stateId){
        int colorId = 1;
        for (Integer color : state){
            String textId = "editText" + ((stateId*5) + colorId);
            try {
                Field field = binding.getClass().getDeclaredField(textId);
                field.setAccessible(true);
                EditText editText = (EditText) field.get(binding);
                if (editText != null){
                    switch (color) {
                        case 0:
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
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            colorId++;
        }
    }


    private void updateUI(){

        int indice = gameViewModel.getTryCount();
        LinkedList<Integer>[] states = gameViewModel.getStates();

        int stateId = 0;
        for (LinkedList<Integer> state : states){
            if (state != null){
                colorState(state, stateId);
            }
            stateId++;
        }

        if (gameViewModel.gameFinished()){
            return;
        }

        Log.d("Game Fragment", "updateUi win "+ gameViewModel.winGame());
        switch (gameViewModel.winGame()){ 
            case -1:
                Toast.makeText(getContext(), "You lost", Toast.LENGTH_SHORT).show();
                return;
            case 1:
                Toast.makeText(getContext(), "You won", Toast.LENGTH_SHORT).show();
                return;
            default:
                break;
        }

        Log.d("Game Fragment", "updateUi states: "+ Arrays.toString(states));
        Log.d("Game Fragment", "updateUi count "+ gameViewModel.getTryCount());


        Character[] expression = gameViewModel.getExpression();
        Log.d("Game Fragment", "updateUi with"+ Arrays.toString(expression));

        for (int i = 0; i < expression.length; i++){
            try {
                String editTextName = "editText" + (((indice-1)*5) + i+1);
                Field field = binding.getClass().getDeclaredField(editTextName);
                field.setAccessible(true);
                EditText editText = (EditText) field.get(binding);
                if (editText != null) {
                    editText.setText(expression[i].toString());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void setNumberButtons(){
        binding.button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.numberButtonHandler(0);
                updateUI();
            }
        });
        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            gameViewModel.numberButtonHandler(1);
                updateUI();
        }
        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            gameViewModel.numberButtonHandler(2);
                updateUI();
        }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            gameViewModel.numberButtonHandler(3);
                updateUI();
        }
        });
        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            gameViewModel.numberButtonHandler(4);
                updateUI();
        }
        });
        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            gameViewModel.numberButtonHandler(5);
                updateUI();
        }
        });
        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            gameViewModel.numberButtonHandler(6);
                updateUI();
        }
        });
        binding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            gameViewModel.numberButtonHandler(7);
                updateUI();
        }
        });
        binding.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            gameViewModel.numberButtonHandler(8);
                updateUI();
        }
        });
        binding.button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            gameViewModel.numberButtonHandler(9);
                updateUI();
        }
        });
    }

    private void setOperationButtons(){
        binding.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.operationButtonHandler('+');
                updateUI();
            }
        });
        binding.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.operationButtonHandler('-');
                updateUI();
            }
        });
        binding.buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.operationButtonHandler('*');
                updateUI();
            }
        });
        binding.buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.operationButtonHandler('/');
                updateUI();
            }
        });
        binding.buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.operationButtonHandler('=');
                updateUI();
            }
        });
        binding.buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.operationButtonHandler('.');
                updateUI();
            }
        });
    }

    private void setControlButtons(){
        binding.buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.validateButtonHandler();
                if (!gameViewModel.gameFinished()){
                    updateUI();
                }
            }
        });
        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.deleteButtonHandler();
                updateUI();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}