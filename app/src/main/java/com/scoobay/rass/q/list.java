package com.scoobay.rass.q;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class list extends AppCompatActivity {

  private  String mobileArray[]= {"Android","IPhone","WindowsMobile","Blackberry",
          "WebOS","Ubuntu","Windows7","Max OS X"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView=(TextView) findViewById(R.id.textView);
        rassdb rass=new rassdb(this);
        Cursor ty=rass.getQuizItems();
        if (ty.getCount()==0){
            textView.setText("No Data Found");
        }else{

            for (int i=0;i<ty.getCount();i++){
                if (i==0){
                    ty.moveToFirst();
                }else{
                    ty.moveToPosition(i);
                }

                String question = ty.getString(ty.getColumnIndex("question"));
                int id =Integer.parseInt(ty.getString(ty.getColumnIndex("id"))) ;
                // mobileArray[id]= question;

            }

            textView.setVisibility(View.GONE);
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.activity_newlist, mobileArray);

            ListView listView = (ListView) findViewById(R.id.mylist);
            listView.setAdapter(adapter);
        }




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myStatic mys=new myStatic();
        mys.resetSta(0);
        Intent i = new Intent(list.this, MainActivity.class);
        list.this.startActivity(i);
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
