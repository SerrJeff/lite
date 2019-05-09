package com.scoobay.rass.q;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.scoobay.rass.q.myStatic.counter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Cursor monitor;
    private int score;
    private int tot;
    String sd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rassdb rass=new rassdb(this);
         monitor=rass.getQuizItems();
        controlLoop(monitor);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void onCheckedChanged( boolean isChecked) {
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isChecked", isChecked);
        editor.commit();
    }

    public void controlLoop(Cursor valu) {


        int n=valu.getCount();
        tot=n;

        Button sub = (Button) findViewById(R.id.submit);
        EditText ans= (EditText) findViewById(R.id.ans);
        TextView  pullText = (TextView) findViewById(R.id.textView1);
        TextView textView=(TextView) findViewById(R.id.textView);


        if (n==0){

           // pullText.setVisibility(View.GONE);
            sub.setVisibility(View.GONE);
            ans.setVisibility(View.GONE);

            textView.setText("No Data Found,Add Questions And Answers");
        }else{

            if(n==counter){
                alertFun();
                //textView.setText("Well Done");
               // textView.setVisibility(View.GONE);
               // pullText.setVisibility(View.GONE);
                sub.setVisibility(View.GONE);
               // ans.setVisibility(View.GONE);
                return;

            }
            myStatic mys=new myStatic();
            sd=mys.getAns();
            int counter=mys.increment();
            if (counter==1){
                valu.moveToFirst();
            }else{
                valu.moveToPosition(counter-1);
            }

            String question = valu.getString(valu.getColumnIndex("question"));
            String answer = valu.getString(valu.getColumnIndex("answer"));
            mys.setAns(answer);
               /* if (counter>1){
                  if(answer.equals(sd)){

                  }

                }*/


            ans.setText("");
            pullText.setText("Question "+counter);
            textView.setText(question);


            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText ans= (EditText) findViewById(R.id.ans);
                    TextView textView=(TextView) findViewById(R.id.textView);

                    String cd=textView.getText().toString();
                    String newans=ans.getText().toString();

                   rassdb rass=new rassdb(MainActivity.this);
                    Cursor valu=rass.valQuiz(cd);
                    valu.moveToFirst();
                    String answers = valu.getString(valu.getColumnIndex("answer"));


                    myStatic mys=new myStatic();
                    if (newans.equals(answers)){
                        score= mys.addScore();
                       // Toast.makeText(MainActivity.this,
                       //         newans+" "+answers+" "+score, Toast.LENGTH_LONG).show();
                    }



                    controlLoop(monitor);
                }
            });


        }

    }

    private void alertFun(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Scoreboard")
                .setMessage("You answered "+score+" out of "+tot)

                .setNegativeButton(android.R.string.no, null).show();
    }


    @Override
    public void onBackPressed() {
        myStatic mys=new myStatic();
        mys.resetSta(0);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.quiz) {
            //  Handle the camera action
            myStatic my=new myStatic();
            my.resetSta(0);

            Intent i = new Intent(MainActivity.this, MainActivity.class);
            MainActivity.this.startActivity(i);
            finish();


        } else
        if (id == R.id.add_que) {
            Intent i = new Intent(MainActivity.this, AddQuestion.class);
            MainActivity.this.startActivity(i);

        } else if (id == R.id.up_que) {

            Intent i = new Intent(MainActivity.this, newlist.class);
            MainActivity.this.startActivity(i);

        } else if (id == R.id.logout) {
            onCheckedChanged(false);
            Intent i = new Intent(MainActivity.this, Login.class);
            MainActivity.this.startActivity(i);
            finish();
        }
        /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
