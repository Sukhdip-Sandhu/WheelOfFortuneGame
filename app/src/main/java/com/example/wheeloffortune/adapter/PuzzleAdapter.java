package com.example.wheeloffortune.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.constraintlayout.widget.Placeholder;
import androidx.core.content.res.ResourcesCompat;

import com.example.wheeloffortune.R;
import com.example.wheeloffortune.util.PuzzleUtil;
import com.example.wheeloffortune.util.UiUtil;

public class PuzzleAdapter extends BaseAdapter {

    private char[] answerCharArray;
    private Context context;

    public PuzzleAdapter(char[] answerCharArray, Context context) {
        this.answerCharArray = answerCharArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return answerCharArray.length;
    }

    @Override
    public Object getItem(int position) {
        return answerCharArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button letterButton;
        if (convertView == null) {
            letterButton = new Button(context);
            letterButton.setPadding(8, 8, 8, 8);


            if (answerCharArray[position] == PuzzleUtil.PLACEHOLDER) {
                letterButton.setBackgroundColor(UiUtil.setColorHelper(context, R.color.wof_green));
            } else if (answerCharArray[position] == PuzzleUtil.SPACE) {
                letterButton.setBackgroundColor(UiUtil.setColorHelper(context, R.color.wof_green));
            } else { // MISSING LETTER OR CHARACTER
                letterButton.setBackgroundColor(UiUtil.setColorHelper(context, R.color.white));
                if (answerCharArray[position] != PuzzleUtil.MISSING_LETTER) {
                    letterButton.setText(String.valueOf(answerCharArray[position]));
                    letterButton.setTextColor(UiUtil.setColorHelper(context, R.color.black));
                }
            }
            letterButton.setTextSize(50);
        } else {
            letterButton = (Button) convertView;
        }

        return letterButton;
    }

}
