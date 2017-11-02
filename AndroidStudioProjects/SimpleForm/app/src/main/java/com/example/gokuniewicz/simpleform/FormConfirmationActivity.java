package com.example.gokuniewicz.simpleform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FormConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_confirmation);

        // Get the intent that started this activity and extract the string
        String name = getIntent().getExtras().getString("name");
        String message = "Thank you, "
                + name
                + ".\nYour request is being processed.";

        TextView textView = (TextView)findViewById(R.id.confirmationTextView);
        textView.setText(message);
    }
}
