package com.priyankanandiraju.learnandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    public static final String TEXT_PLAIN = "text/plain";
    public static final String INT = "int";
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText etName;
    private Button btnSubmit;
    private Button btnReset;
    private Switch swEmail;
    private RadioGroup rgAnswer1;
    private RadioButton rb1Answer1;
    private RadioButton rb2Answer1;
    private EditText etAnswer2;
    private CheckBox cb1Answer3;
    private CheckBox cb2Answer3;
    private CheckBox cb3Answer3;
    private CheckBox cb4Answer3;
    private EditText etAnswer4;
    private RadioGroup rgAnswer5;
    private RadioButton rb1Answer5;
    private RadioButton rb2Answer5;
    private int score;
    private TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.name);
        tvScore = (TextView) findViewById(R.id.tv_score);
        swEmail = (Switch) findViewById(R.id.switch_email);

        rgAnswer1 = (RadioGroup) findViewById(R.id.ans_1_rg);
        rb1Answer1 = (RadioButton) findViewById(R.id.ans_1_rb_1);
        rb2Answer1 = (RadioButton) findViewById(R.id.ans_1_rb_2);
        etAnswer2 = (EditText) findViewById(R.id.ans_2_et);

        cb1Answer3 = (CheckBox) findViewById(R.id.cb_1_ans_3);
        cb2Answer3 = (CheckBox) findViewById(R.id.cb_2_ans_3);
        cb3Answer3 = (CheckBox) findViewById(R.id.cb_3_ans_3);
        cb4Answer3 = (CheckBox) findViewById(R.id.cb_4_ans_3);


        etAnswer4 = (EditText) findViewById(R.id.ans_4_et);
        rgAnswer5 = (RadioGroup) findViewById(R.id.ans_rg_5);
        rb1Answer5 = (RadioButton) findViewById(R.id.ans_5_rb_1);
        rb2Answer5 = (RadioButton) findViewById(R.id.ans_5_rb_2);

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnReset = (Button) findViewById(R.id.btn_reset);

        btnSubmit.setOnClickListener(this);
        btnReset.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                submitClicked();
                break;
            case R.id.btn_reset:
                resetClicked();
                break;
            default:
                break;

        }
    }

    private void submitClicked() {
        btnReset.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
        evaluateAnswers();
        displayScore();
        if (swEmail.isChecked()) {
            Log.v(TAG, "Compose email");
            String subject = getString(R.string.app_name);
            String body = setEmailContent();
            composeEmail(subject, body);
        } else {
            Log.v(TAG, "Compose email not enabled");
        }
    }

    private void displayScore() {
        String scored = "Score : " + score;
        tvScore.setText(scored);
        String yourScore = "You scored " + score + "/5";
        Toast.makeText(this, yourScore, Toast.LENGTH_SHORT).show();
    }

    /**
     * Evaluate all answers
     */
    private void evaluateAnswers() {
        if (rgAnswer1.getCheckedRadioButtonId() == R.id.ans_1_rb_1) {
            score += 1;
        }
        if (etAnswer2.getText().toString().equals(getString(R.string.scroll_view))) {
            score += 1;
        }

        if (cb1Answer3.isChecked() && cb3Answer3.isChecked() && cb4Answer3.isChecked()) {
            score += 1;
        }

        if (etAnswer4.getText().toString().equals(INT)) {
            score += 1;
        }

        if (rgAnswer5.getCheckedRadioButtonId() == R.id.ans_5_rb_1) {
            score += 1;
        }
    }

    private void resetClicked() {
        btnReset.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.VISIBLE);
        resetAnswers();
    }

    /**
     * Reset all answer fields, etc
     */
    private void resetAnswers() {
        score = 0;
        etName.setText("");
        tvScore.setText("");
        swEmail.setChecked(false);
        rgAnswer1.clearCheck();
        etAnswer2.setText("");
        cb1Answer3.setChecked(false);
        cb2Answer3.setChecked(false);
        cb3Answer3.setChecked(false);
        cb4Answer3.setChecked(false);
        etAnswer4.setText("");
        rgAnswer5.clearCheck();
    }

    /**
     * @return String - body for the email
     */
    private String setEmailContent() {
        final String name = etName.getText().toString();
        String body = getString(R.string.hi) + name + "\n";
        body += getString(R.string.your_score) + score + "\n";
        body += getString(R.string.good_luck);
        return body;
    }

    /**
     * Compose email
     *
     * @param subject String - Contains subject of the email
     * @param body    String - Contains body of the email
     */
    private void composeEmail(final String subject, final String body) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(TEXT_PLAIN);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.v(TAG, "Start activity : email");
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.email_not_set_up, Toast.LENGTH_SHORT).show();
        }
    }
}
