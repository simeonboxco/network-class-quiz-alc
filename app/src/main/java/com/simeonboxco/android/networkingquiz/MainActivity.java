package com.simeonboxco.android.networkingquiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private String questionType;
    private int questionNo = 0;
    private int maxQuestions = 5;
    private String questions[][][] = {
            {
                    {"Select two addresses that are not assignable to hosts"},   //question
                    {"check"},      // Question type: available types ['radio','check','edit']
                    {"Broadcast Address", "Network Address", "", ""}, // answer
                    {"Broadcast Address", "Default gateway", "Network Address", "Host Address"}, // options
                    {"", "", "", "",} // responses
            },
            {
                    {"At what layer of the OSI model does routing occur?"},   //question
                    {"radio"},      // Question type: available types ['radio','check','edit']
                    {"Network", "", "", ""}, // answer
                    {"Network", "Transport", "Session", "Physical",}, // options
                    {"", "", "", "",} // responses
            },
            {
                    {"Which of these addresses is not within 192.168.0.0/25 subnet?"},   //question
                    {"radio"},      // Question type: available types ['radio','check','edit']
                    {"192.168.0.179", "", "", ""}, // answer
                    {"192.168.0.1", "192.168.0.179", "192.168.0.22", "192.168.0.4"}, // options
                    {"", "", "", "",} // responses
            },
            {
                    {"What is a device that splits broadcast domain?"},   //question
                    {"edit"},      // Question type: available types ['radio','check','edit']
                    {"Router", "", "", ""}, // answer
                    {"Option 1", "Router", "Option 3 - ans", "Option 4",}, // options
                    {"", "", "", "",} // responses
            },
            {
                    {"What service resolves domain names IP addresses?"},   //question
                    {"radio"},   // Question type: available types ['radio','check','edit']
                    {"DNS", "", "", ""}, // answer
                    {"ARP", "CALEA", "DHCP", "DNS"}, // options
                    {"", "", "", "",} // responses
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_question(questionNo);

        // for some reason android studio added this final stuff here
        final Button nextButton = (Button) findViewById(R.id.next_question_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeResponse(questionNo);
                String buttonLabel = (String) nextButton.getText();
                if (buttonLabel != "Start Over")
                    questionNo++;
                if (questionNo >= maxQuestions) {
                    // we have come to the end of the quiz
                    RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.answer_radiogroup);
                    LinearLayout edit_container = (LinearLayout) findViewById(R.id.answer_edit_container);
                    LinearLayout check_container = (LinearLayout) findViewById(R.id.answer_check_container);
                    LinearLayout summary_con = (LinearLayout) findViewById(R.id.summary_container);
                    TextView question_con = (TextView) findViewById(R.id.question);

                    summary_con.setVisibility(View.VISIBLE);
                    rbtnGrp.setVisibility(View.GONE);
                    edit_container.setVisibility(View.GONE);
                    check_container.setVisibility(View.GONE);
                    question_con.setVisibility(View.GONE);

                    evaluateScore();
                    nextButton.setText("Start Over");
                    questionNo = 0;
                } else {
                    init_question(questionNo);
                }
            }
        });
    }

    public void displayQuestion(int que_no) {
        TextView questionView = (TextView) findViewById(R.id.question);
        String que = questions[que_no][0][0];
        questionView.setText(que);
    }

    public void setQuestionType(int que_no) {
        questionType = questions[que_no][1][0];
    }

    public void displayOptions(int question_no) {
        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.answer_radiogroup);
        // reset logic credit: https://stackoverflow.com/a/10060455/2106841
        rbtnGrp.clearCheck();

        if (questionType != "edit") {
            // set the options
            for (int i = 0; i < 4; i++) {
                String option = questions[question_no][3][i];
                // find out the type of question we have to determine what view to update
                if (questionType == "radio") {
                    ((RadioButton) rbtnGrp.getChildAt(i)).setText(option);
                } else {  // checkbox reset credit: https://stackoverflow.com/a/13133373/2106841
                    if (i == 0) { // option 1
                        CheckBox option1 = (CheckBox) findViewById(R.id.check_item_1);
                        option1.setText(option);
                        option1.setChecked(false);
                    } else if (i == 1) {  // option 2
                        CheckBox option2 = (CheckBox) findViewById(R.id.check_item_2);
                        option2.setText(option);
                        option2.setChecked(false);
                    } else if (i == 2) {  // option 3
                        CheckBox option3 = (CheckBox) findViewById(R.id.check_item_3);
                        option3.setText(option);
                        option3.setChecked(false);
                    } else {  // option 4
                        CheckBox option4 = (CheckBox) findViewById(R.id.check_item_4);
                        option4.setText(option);
                        option4.setChecked(false);
                    }
                }
            }
        } else {
            EditText answerEditText = (EditText) findViewById(R.id.answer_edit_text);
            answerEditText.setText("");
        }
    }

    private void init_question(int que_no) {
        Button nextButton = (Button) findViewById(R.id.next_question_button);
        nextButton.setText("Next Question");
        TextView question_con = (TextView) findViewById(R.id.question);
        question_con.setVisibility(View.VISIBLE);

        LinearLayout summary_con = (LinearLayout) findViewById(R.id.summary_container);
        summary_con.setVisibility(View.GONE);

        setQuestionType(que_no);

        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.answer_radiogroup);
        LinearLayout edit_container = (LinearLayout) findViewById(R.id.answer_edit_container);
        LinearLayout check_container = (LinearLayout) findViewById(R.id.answer_check_container);

        // which option/answer view should we show or hide
        if (questionType == "radio") {
            rbtnGrp.setVisibility(View.VISIBLE);
            edit_container.setVisibility(View.GONE);
            check_container.setVisibility(View.GONE);
        } else if (questionType == "check") {
            check_container.setVisibility(View.VISIBLE);
            edit_container.setVisibility(View.GONE);
            rbtnGrp.setVisibility(View.GONE);
        } else if (questionType == "edit") {
            edit_container.setVisibility(View.VISIBLE);
            check_container.setVisibility(View.GONE);
            rbtnGrp.setVisibility(View.GONE);
        }

        displayQuestion(que_no);
        displayOptions(que_no);

        // update question number
        TextView questionNoText = (TextView) findViewById(R.id.question_number);
        questionNoText.setText(String.valueOf(questionNo + 1));  // casting credit to https://stackoverflow.com/a/15836869/2106841
    }

    private void storeResponse(int que_no) {
        String response = "";

        if (questionType == "edit") {
            EditText answerEditText = (EditText) findViewById(R.id.answer_edit_text);
            response = answerEditText.getText().toString();
        } else if (questionType == "radio") {
            RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.answer_radiogroup);
            if (rbtnGrp.getCheckedRadioButtonId() != -1) {
                response = ((RadioButton) findViewById(rbtnGrp.getCheckedRadioButtonId())).getText().toString();
            }
        } else {
            CheckBox check_item_1 = (CheckBox) findViewById(R.id.check_item_1);
            if (check_item_1.isChecked()) {  // thanks to stackoverflow https://stackoverflow.com/a/9411880/2106841
                questions[que_no][4][0] = check_item_1.getText().toString();
            }

            CheckBox check_item_2 = (CheckBox) findViewById(R.id.check_item_2);
            if (check_item_2.isChecked()) {
                questions[que_no][4][1] = check_item_1.getText().toString();
            }

            CheckBox check_item_3 = (CheckBox) findViewById(R.id.check_item_3);
            if (check_item_3.isChecked()) {
                questions[que_no][4][2] = check_item_1.getText().toString();
            }

            CheckBox check_item_4 = (CheckBox) findViewById(R.id.check_item_4);
            if (check_item_4.isChecked()) {
                questions[que_no][4][3] = check_item_1.getText().toString();
            }
        }

        if (questionType != "check")
            questions[que_no][4][0] = response;

        // show a toast for the selected response

    }

    private void evaluateScore() {
        int totalScore = 0;
        int correctResponses = 0;

        String testVar = "";

        for (int que_no = 0; que_no <= 4; que_no++) {

            setQuestionType(que_no);

            // checkboxes can have multiple answers
            if (questionType.equals("check")) {

                // count the responses
                int responseCount = 0;
                boolean wrongResponseFound = false;
                for (int x = 0; x < 4; x++) {
                    String thisResponse = questions[que_no][4][x].trim();
                    if (!thisResponse.equals("")) {
                        responseCount++;

                        // test whether it's a correct response
                        boolean noMatch = true;
                        for (int y = 0; y < 4; y++) {
                            if (thisResponse.equals(questions[que_no][2][y])) {
                                noMatch = false;
                                break;
                            }
                        }

                        if (noMatch) {
                            wrongResponseFound = true;
                            break;
                        }
                    }
                }

                if (!wrongResponseFound) {
                    // count the number of answers available
                    int answerCount = 0;
                    for (int x = 0; x < 4; x++) {
                        if (!questions[que_no][2][x].equals(""))
                            answerCount++;
                    }

                    // if they don't match, response is wrong
                    if (answerCount == responseCount) {
                        // now we got it right
                        totalScore += 1;
                        correctResponses += 1;
                    }
                }
            } else {
                String answer = questions[que_no][2][0].trim().toUpperCase();
                String response = questions[que_no][4][0].trim().toUpperCase();
                if (response.equals(answer)) {   // correct
                    totalScore += 1;
                    correctResponses += 1;
                }
            }
        }

        // write out and display results
        String displayText = "";
        displayText = "You have gotten " + String.valueOf(correctResponses);
        displayText += " out of " + String.valueOf(maxQuestions) + " questions correctly!\n\r";
        displayText += "Your total Score is " + String.valueOf(totalScore);


        TextView summaryScore = (TextView) findViewById(R.id.summary_score);
        summaryScore.setText(displayText);

        String toastMessage = "You scored "+String.valueOf(correctResponses)+" out of ";
        toastMessage += String.valueOf(maxQuestions);
        Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();

    }
}