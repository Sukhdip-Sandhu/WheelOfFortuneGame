package com.example.wheeloffortune.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wheeloffortune.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = "WHEEL_OF_FORTUNE_TAG";
    private String puzzleNameRef = "";
    private MainPresenter presenter;

    @BindView(R.id.new_game_button)
    Button newGameButton;

    @BindView(R.id.spin_wheel_button)
    Button spinWheelButton;

    @BindView(R.id.buy_vowel_button)
    Button buyVowelButton;

    @BindView(R.id.solve_puzzle_button)
    Button solvePuzzleButton;

    @BindView(R.id.switch_player_button)
    Button switchPlayerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
        presenter.connectToScreen(this);

        newGameButton.setOnClickListener(v -> createNewGameDialog());
        spinWheelButton.setOnClickListener(v -> presenter.onSpinWheel());
        buyVowelButton.setOnClickListener(v -> buyVowelDialog());
        solvePuzzleButton.setOnClickListener(v -> solvePuzzleDialog());
        switchPlayerButton.setOnClickListener(v -> presenter.onSwitchPlayer());
    }


    public void createNewGameDialog() {
        AlertDialog.Builder newGameDialog = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText puzzleNameET = new EditText(getApplicationContext());
        puzzleNameET.setHint("Puzzle Name");
        puzzleNameET.setPadding(32, 32, 32, 32);
        puzzleNameET.setTextSize(24f);
        layout.addView(puzzleNameET);

        final EditText puzzleCategoryET = new EditText(getApplicationContext());
        puzzleCategoryET.setHint("Puzzle Category");
        puzzleCategoryET.setPadding(32, 32, 32, 32);
        puzzleCategoryET.setTextSize(24f);
        layout.addView(puzzleCategoryET);

        final EditText playerOneNameET = new EditText(getApplicationContext());
        playerOneNameET.setHint("Player One Name");
        playerOneNameET.setTextSize(24f);
        playerOneNameET.setPadding(32, 32, 32, 32);
        layout.addView(playerOneNameET);

        final EditText playerTwoNameET = new EditText(getApplicationContext());
        playerTwoNameET.setHint("Player Two Name");
        playerTwoNameET.setTextSize(24f);
        playerTwoNameET.setPadding(32, 32, 32, 32);
        layout.addView(playerTwoNameET);

        final EditText playerThreeNameET = new EditText(getApplicationContext());
        playerThreeNameET.setHint("Player Three Name");
        playerThreeNameET.setTextSize(24f);
        playerThreeNameET.setPadding(32, 32, 32, 32);
        layout.addView(playerThreeNameET);

        newGameDialog.setView(layout);

        newGameDialog.setPositiveButton("BEGIN", (dialog, which) -> {
            String puzzleName = puzzleNameET.getText().toString();
            if (puzzleName.length() > 28) {
                displayToast("Puzzle name must be less than 28 characters long");
                return;
            }
            String puzzleCategory = puzzleCategoryET.getText().toString();
            String playerOneName = playerOneNameET.getText().toString();
            String playerTwoName = playerTwoNameET.getText().toString();
            String playerThreeName = playerThreeNameET.getText().toString();
            puzzleNameRef = puzzleName;
            presenter.onNewPuzzle(puzzleName, puzzleCategory, playerOneName, playerTwoName, playerThreeName);
        });

        newGameDialog.setNegativeButton("CANCEL", (dialog, which) -> {
        });

        newGameDialog.show();
    }

    private void buyVowelDialog() {
        AlertDialog.Builder buyVowelDialog = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText vowelET = new EditText(getApplicationContext());
        vowelET.setPadding(32, 32, 32, 32);
        vowelET.setTextSize(48f);
        vowelET.setGravity(Gravity.CENTER);
        vowelET.requestFocus();
        vowelET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        
        vowelET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 1) {
                    vowelET.setText(s.subSequence(1, s.length()));
                    vowelET.setSelection(1);
                }
            }
        });

        layout.addView(vowelET);

        buyVowelDialog.setView(layout);

        buyVowelDialog.setIcon(R.drawable.wheel);
        buyVowelDialog.setTitle("Buy Vowel");
        buyVowelDialog.setMessage("Please enter a vowel to buy");

        buyVowelDialog.setPositiveButton("BUY", (dialog, which) -> {
            String vowel = vowelET.getText().toString();
            presenter.onBuyVowel(vowel);
        });

        buyVowelDialog.setNegativeButton("CANCEL", (dialog, which) -> {
        });

        AlertDialog dialog = buyVowelDialog.create();
        Objects.requireNonNull(dialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    private void solvePuzzleDialog() {
        AlertDialog.Builder isPuzzleCorrectDialog = new AlertDialog.Builder(this);
        isPuzzleCorrectDialog.setTitle("ANSWER:");
        isPuzzleCorrectDialog.setMessage(puzzleNameRef);
        isPuzzleCorrectDialog.setPositiveButton("CORRECT", (dialog, which) -> presenter.onSolvePuzzle(true, puzzleNameRef));
        isPuzzleCorrectDialog.setNegativeButton("INCORRECT", (dialog, which) -> presenter.onSolvePuzzle(false, puzzleNameRef));
        isPuzzleCorrectDialog.show();
    }

    @Override
    public void toggleButtons(boolean startGame) {
        if (startGame) {
            newGameButton.setEnabled(false);
            spinWheelButton.setEnabled(true);
            solvePuzzleButton.setEnabled(true);
            switchPlayerButton.setEnabled(true);
        } else {
            newGameButton.setEnabled(true);
            spinWheelButton.setEnabled(false);
            buyVowelButton.setEnabled(false);
            solvePuzzleButton.setEnabled(false);
            switchPlayerButton.setEnabled(false);
        }
    }

    @Override
    public void showConsonantDialog() {
        AlertDialog.Builder guessConsonantDialog = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText consonantET = new EditText(getApplicationContext());
        consonantET.setPadding(32, 32, 32, 32);
        consonantET.setTextSize(48f);
        consonantET.setGravity(Gravity.CENTER);
        consonantET.requestFocus();
        consonantET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        consonantET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 1) {
                    consonantET.setText(s.subSequence(1, s.length()));
                    consonantET.setSelection(1);
                }
            }
        });

        layout.addView(consonantET);

        guessConsonantDialog.setView(layout);

        guessConsonantDialog.setIcon(R.drawable.wheel);
        guessConsonantDialog.setTitle("Guess Consonant");
        guessConsonantDialog.setMessage("Please enter a consonant to guess");

        guessConsonantDialog.setPositiveButton("GUESS", (dialog, which) -> {
            String consonant = consonantET.getText().toString();
            presenter.onGuessConsonant(consonant);
        });
        guessConsonantDialog.setCancelable(false);

        AlertDialog dialog = guessConsonantDialog.create();
        Objects.requireNonNull(dialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    @Override
    public void displayToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toggleVowelButton(boolean playerCanBuyVowel) {
        buyVowelButton.setEnabled(playerCanBuyVowel);
    }
}
