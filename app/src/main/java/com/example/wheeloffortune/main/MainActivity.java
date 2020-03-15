package com.example.wheeloffortune.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wheeloffortune.R;

import java.io.IOException;

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

    @BindView(R.id.guess_consonant)
    Button guessConsonantButton;

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
        guessConsonantButton.setOnClickListener(v -> guessConsonantDialog());
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

    private void guessConsonantDialog() {
        AlertDialog.Builder guessConsonantDialog = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText consonantET = new EditText(getApplicationContext());
        consonantET.setHint("Please enter a consonant");
        consonantET.setPadding(32, 32, 32, 32);
        consonantET.setTextSize(24f);
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

        guessConsonantDialog.setPositiveButton("GUESS", (dialog, which) -> {
            String consonant = consonantET.getText().toString();
            presenter.onGuessConsonant(consonant);
        });

        guessConsonantDialog.setNegativeButton("CANCEL", (dialog, which) -> {
        });

        guessConsonantDialog.show();
    }

    private void buyVowelDialog() {
        AlertDialog.Builder guessConsonantDialog = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText vowelET = new EditText(getApplicationContext());
        vowelET.setHint("Please enter a vowel");
        vowelET.setPadding(32, 32, 32, 32);
        vowelET.setTextSize(24f);
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

        guessConsonantDialog.setView(layout);

        guessConsonantDialog.setPositiveButton("BUY", (dialog, which) -> {
            String vowel = vowelET.getText().toString();
            presenter.onBuyVowel(vowel);
        });

        guessConsonantDialog.setNegativeButton("CANCEL", (dialog, which) -> {
        });

        guessConsonantDialog.show();
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
    public void displayToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
