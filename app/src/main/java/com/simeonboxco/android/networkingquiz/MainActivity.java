package com.simeonboxco.android.networkingquiz;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String questions[][][] = {
            {
                    {"Question"},
                    {"QuestionType"},
                    {"Answer"},
                    {"Option", "Option", "Option", "Option",}
            },
            {
                    {"Question"},
                    {"QuestionType"},
                    {"Answer"},
                    {"Option", "Option", "Option", "Option",}
            },
            {
                    {"Question"},
                    {"QuestionType"},
                    {"Answer"},
                    {"Option", "Option", "Option", "Option",}
            }
    };
}