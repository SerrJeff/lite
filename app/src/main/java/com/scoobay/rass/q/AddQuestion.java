package com.scoobay.rass.q;


import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


public class AddQuestion extends AppCompatActivity {

    private  AutoCompleteTextView ques;
    private  AutoCompleteTextView ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ques= (AutoCompleteTextView) findViewById(R.id.ques);
        ans= (AutoCompleteTextView) findViewById(R.id.ans);
        Button save=(Button) findViewById(R.id.submit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myStatic mys=new myStatic();
        mys.resetSta(0);
        Intent i = new Intent(AddQuestion.this, MainActivity.class);
        AddQuestion.this.startActivity(i);
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void attemptLogin( ) {


        // Reset errors.
        ans.setError(null);
        ques.setError(null);

        // Store values at the time of the login attempt.
        String answer = ans.getText().toString();
        String question = ques.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(answer)){
            ans.setError(getString(R.string.error_field_required));
            focusView = ans;
            cancel = true;
        }


        if (TextUtils.isEmpty(question)) {
            ques.setError(getString(R.string.error_field_required));
            focusView = ques;
            cancel = true;
        }



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            rassdb rass=new rassdb(this);
            rass.insertQuiz(question,answer);

            Intent i = new Intent(AddQuestion.this, AddQuestion.class);
            AddQuestion.this.startActivity(i);
            finish();


            Toast.makeText(this,
                    "Item Has Been Added", Toast.LENGTH_LONG).show();


        }
    }



}
