package com.example.wheeloffortune.secondary_display;

import android.annotation.SuppressLint;
import android.app.Presentation;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wheeloffortune.R;
import com.example.wheeloffortune.adapter.PuzzleAdapter;
import com.example.wheeloffortune.util.UiUtil;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondaryDisplay extends Presentation implements SecondaryDisplayContract.View, Animation.AnimationListener {

    private static final String TAG = "WHEEL_OF_FORTUNE_TAG";

    private SecondaryDisplayPresenter presenter;

    private Context context;
    private PuzzleAdapter puzzleAdapter;
    private MediaPlayer backgroundMusicMediaPlayer;

    private long degrees = 0;
    private String[] wheelOfFortuneValues = {"Bankrupt", "900", "500", "650", "500", "800", "Lose Turn",
            "700", "Free Play", "650", "Bankrupt", "600", "500", "550", "600", "Million", "700",
            "500", "650", "600", "700", "600", "Wild", "2500"};

    @BindView(R.id.puzzle_grid_view)
    GridView puzzleGridView;

    @BindView(R.id.puzzle_category)
    TextView puzzleCategoryTV;

    @BindView(R.id.letters_guessed)
    TextView lettersGuessed;

    @BindView(R.id.player_one_name)
    TextView playerOneName;

    @BindView(R.id.player_one_score)
    TextView playerOneScore;

    @BindView(R.id.player_two_name)
    TextView playerTwoName;

    @BindView(R.id.player_two_score)
    TextView playerTwoScore;

    @BindView(R.id.player_three_name)
    TextView playerThreeName;

    @BindView(R.id.player_three_score)
    TextView playerThreeScore;

    @BindView(R.id.wheel_layout)
    RelativeLayout wheelLayout;

    @BindView(R.id.wheel)
    public ImageView wheel;

    @BindView(R.id.wheel_arrow)
    public ImageView arrow;

    @BindView(R.id.wheel_textview)
    public TextView wheelTextView;


    public SecondaryDisplay(Context outerContext, Display display) {
        super(outerContext, display);
        this.context = outerContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_display_layout);
        ButterKnife.bind(this);
    }

    public void passPresenter(SecondaryDisplayPresenter displayPresenter) {
        presenter = displayPresenter;
    }

    @Override
    public void updatePuzzleGridView(char[] puzzle) {
        puzzleAdapter = new PuzzleAdapter(puzzle, getContext());
        puzzleAdapter.notifyDataSetChanged();
        puzzleGridView.setAdapter(puzzleAdapter);
    }

    @Override
    public void setPuzzleCategory(String puzzleCategory) {
        puzzleCategoryTV.setText(String.format("%s", puzzleCategory.toUpperCase()));
    }

    @Override
    public void highlightPlayer(int playerTurn) {
        if (playerTurn == 0) {
            playerOneName.setTextColor(UiUtil.setColorHelper(context, R.color.wof_red));
        } else {
            playerOneName.setTextColor(UiUtil.setColorHelper(context, R.color.light_gray));
        }
        if (playerTurn == 1) {
            playerTwoName.setTextColor(UiUtil.setColorHelper(context, R.color.wof_yellow));
        } else {
            playerTwoName.setTextColor(UiUtil.setColorHelper(context, R.color.light_gray));
        }
        if (playerTurn == 2) {
            playerThreeName.setTextColor(UiUtil.setColorHelper(context, R.color.wof_blue));
        } else {
            playerThreeName.setTextColor(UiUtil.setColorHelper(context, R.color.light_gray));
        }
    }

    @Override
    public void setLettersGuessed(ArrayList<String> lettersGuessedArrayList) {
        lettersGuessed.setText(String.format("LETTERS GUESSED\n%s", lettersGuessedArrayList.toString()));
    }

    @Override
    public void setPlayerOneName(String name) {
        playerOneName.setText(name);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void setPlayerOneScore(int score) {
        playerOneScore.setText(String.format("$%d", score));
    }

    @Override
    public void setPlayerTwoName(String name) {
        playerTwoName.setText(name);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void setPlayerTwoScore(int score) {
        playerTwoScore.setText(String.format("$%d", score));
    }

    @Override
    public void setPlayerThreeName(String name) {
        playerThreeName.setText(name);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void setPlayerThreeScore(int score) {
        playerThreeScore.setText(String.format("$%d", score));
    }

    @Override
    public void spinWheel() {
        wheelLayout.setVisibility(View.VISIBLE);
        int ran = new Random().nextInt(360) + 6120;
        RotateAnimation rotateAnimation = new RotateAnimation((float) this.degrees, (float)
                (this.degrees + ((long) ran)), 1, 0.5f, 1, 0.5f);
        this.degrees = (this.degrees + ((long) ran)) % 360;
        rotateAnimation.setDuration((long) ran);
        rotateAnimation.setInterpolator(new DecelerateInterpolator(2));
        rotateAnimation.setAnimationListener(this);
        rotateAnimation.setFillAfter(true);
        wheel.setAnimation(rotateAnimation);
        wheel.startAnimation(rotateAnimation);
    }

    @Override
    public void toggleBackgroundMusic(boolean turnMusicOn) {
        if (turnMusicOn) {
            backgroundMusicMediaPlayer = MediaPlayer.create(getContext(), R.raw.background_music);
            backgroundMusicMediaPlayer.setLooping(true);
            backgroundMusicMediaPlayer.start();
        } else {
            if (backgroundMusicMediaPlayer != null) {
                backgroundMusicMediaPlayer.stop();
            }
        }
    }

    @Override
    public void playSound(int audioFile) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), audioFile);
        mediaPlayer.start();
    }


    @Override
    public void onAnimationStart(Animation animation) {
        wheelTextView.setText("");
        playSound(R.raw.wheel_spin_sound);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Handler handler = new Handler();
        int wheelIndex;
        String wheelValue;

        wheelIndex = (int) (degrees / 15);
        wheelValue = wheelOfFortuneValues[wheelIndex];

        if (wheelOfFortuneValues[wheelIndex].equalsIgnoreCase("Million")) {
            if (degrees >= 230 && degrees < 235)
                wheelValue = "Million";
            else
                wheelValue = "Bankrupt";
        }

        wheelTextView.setText(wheelValue);
        if (wheelValue.equalsIgnoreCase("Bankrupt") || wheelValue.equalsIgnoreCase("Lose Turn")) {
            playSound(R.raw.bankrupt_sound);
        }

        String finalWheelValue = wheelValue;
        handler.postDelayed(() -> {
            presenter.onWheelFinishedSpining(finalWheelValue);
            wheelLayout.setVisibility(View.INVISIBLE);
        }, 1500);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
