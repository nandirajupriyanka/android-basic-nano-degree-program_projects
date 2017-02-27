package com.priyankanandiraju.scorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int THREE_POINT = 3;
    private static final int TWO_POINT = 2;
    private static final int ONE_POINT = 1;
    private int scoreTeamA;
    private int scoreTeamB;
    private int foulTeamA;
    private int foulTeamB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Team A
     */
    public void addOneForTeamA(View v) {
        scoreTeamA = scoreTeamA + ONE_POINT;
        displayForTeamA(scoreTeamA);
    }

    public void addTwoForTeamA(View v) {
        scoreTeamA = scoreTeamA + TWO_POINT;
        displayForTeamA(scoreTeamA);
    }

    public void addThreeForTeamA(View v) {
        scoreTeamA = scoreTeamA + THREE_POINT;
        displayForTeamA(scoreTeamA);
    }

    public void foulForTeamA(View view) {
        foulTeamA = foulTeamA + ONE_POINT;
        displayFoulForTeamA(foulTeamA);
    }

    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    private void displayFoulForTeamA(int foul) {
        TextView foulScoreView = (TextView) findViewById(R.id.team_a_foul);
        foulScoreView.setText(getString(R.string.foul_count) + String.valueOf(foul));
    }


    /**
     * Team B.
     */
    public void addOneForTeamB(View v) {
        scoreTeamB = scoreTeamB + ONE_POINT;
        displayForTeamB(scoreTeamB);
    }

    public void addTwoForTeamB(View v) {
        scoreTeamB = scoreTeamB + TWO_POINT;
        displayForTeamB(scoreTeamB);
    }

    public void addThreeForTeamB(View v) {
        scoreTeamB = scoreTeamB + THREE_POINT;
        displayForTeamB(scoreTeamB);
    }

    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    public void foulForTeamB(View view) {
        foulTeamB = foulTeamB + ONE_POINT;
        displayFoulForTeamB(foulTeamB);
    }

    private void displayFoulForTeamB(int foul) {
        TextView foulScoreView = (TextView) findViewById(R.id.team_b_foul);
        foulScoreView.setText(getString(R.string.foul_count) + String.valueOf(foul));
    }


    /**
     * Reset scores.
     */

    public void reset(View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        foulTeamA = 0;
        foulTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displayFoulForTeamA(foulTeamA);
        displayFoulForTeamB(foulTeamB);
    }
}
