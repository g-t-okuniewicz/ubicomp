package com.example.gokuniewicz.simpleform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

public class FormActivity extends AppCompatActivity {

    private static final String TAG = "FormActivity";

    private EditText nameEdit, passwordEdit, phoneEdit, emailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        nameEdit = (EditText)findViewById(R.id.nameEdit);
        passwordEdit = (EditText)findViewById(R.id.passwordEdit);
        phoneEdit = (EditText)findViewById(R.id.phoneEdit);
        emailEdit = (EditText)findViewById(R.id.emailEdit);

    }

    public void submitForm(View view) {

        CharSequence text;

        if(Validation.hasText(nameEdit)
                && Validation.hasText(passwordEdit)
                && Validation.isPhoneNumber(phoneEdit, true)
                && Validation.isEmailAddress(emailEdit, true)) {
            text = "Thank you, your request is being processed.";

            Intent intent = new Intent(this, FormConfirmationActivity.class);
            String key = "name";
            String message = nameEdit.getText().toString();
            intent.putExtra(key, message);

            // Verify that the intent will resolve to an activity
            if(intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }
        else
            text = "Please review the form.";

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();


    }

}
