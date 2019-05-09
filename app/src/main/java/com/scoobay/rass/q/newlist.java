package com.scoobay.rass.q;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class newlist extends AppCompatActivity {

    String[] mobileArray;
    String[] storeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rassdb rass=new rassdb(this);
        Cursor ty=rass.getQuizItems();
        if (ty.getCount()==0){
            mobileArray[0]="No Data Found";
            //textView.setText("No Data Found");
        }else {
            mobileArray = new String[ty.getCount()];
            storeid=new String[ty.getCount()];
            for (int r = 0; r < ty.getCount(); r++) {
                if (r == 0) {
                    ty.moveToFirst();
                } else {
                    ty.moveToPosition(r);
                }

                String question = ty.getString(ty.getColumnIndex("question"));
                //System.out.print(question);
                String answer = ty.getString(ty.getColumnIndex("answer"));
                String id = ty.getString(ty.getColumnIndex("id"));
                storeid[r]=id+"_"+question;
                mobileArray[r] = question;

            }

        }






        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, mobileArray);

        ListView listView = (ListView) findViewById(R.id.mylist);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                String main = mobileArray[position];

                myStatic mys=new myStatic();
                mys.setStore(main);
                Intent i = new Intent(newlist.this, up.class);
                newlist.this.startActivity(i);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myStatic mys=new myStatic();
        mys.resetSta(0);
        Intent i = new Intent(newlist.this, MainActivity.class);
        newlist.this.startActivity(i);
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
