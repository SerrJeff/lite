package com.scoobay.rass.q;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class up extends AppCompatActivity {

    private  AutoCompleteTextView ques;
    private  AutoCompleteTextView ans;
    rassdb rass;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);
        myStatic mys=new myStatic();
        String cd=mys.getStore();
         rass=new rassdb(this);
        Cursor valu=rass.valQuiz(cd);
        valu.moveToFirst();
         id = valu.getString(valu.getColumnIndex("id"));
        String question = valu.getString(valu.getColumnIndex("question"));
        String answer = valu.getString(valu.getColumnIndex("answer"));



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ques= (AutoCompleteTextView) findViewById(R.id.ques);
        ques.setText(question);
        ans= (AutoCompleteTextView) findViewById(R.id.ans);
        ans.setText(answer);
        Button del=(Button) findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertFun();
            }
        });
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
        Intent i = new Intent(up.this, newlist.class);
        up.this.startActivity(i);
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void rkt(){
        Toast.makeText(this,
                "Item Has Been Deleted", Toast.LENGTH_LONG).show();
    }

    private void alertFun(){
        new AlertDialog.Builder(up.this)
                .setTitle("Delete Item")
                .setMessage("Do you want to Delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        rass.delQuiz(id);
                        rkt();
                        Intent i = new Intent(up.this, newlist.class);
                        up.this.startActivity(i);
                        finish();


                    }})
                .setNegativeButton(android.R.string.no, null).show();
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

            rass.updateQuiz(question,answer,id);


            Toast.makeText(this,
                    "Item Has Been Updated", Toast.LENGTH_LONG).show();


        }
    }
}
