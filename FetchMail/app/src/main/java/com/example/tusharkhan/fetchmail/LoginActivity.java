package com.example.tusharkhan.fetchmail;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class LoginActivity extends AppCompatActivity {


    Button Inbox,non_imp,del_non_imp,logoutbtn;
    DatabaseHelper3 db;
    DatabaseUser myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Inbox=findViewById(R.id.Inboxbutton);
       non_imp= findViewById(R.id.non_importantbutton);
       del_non_imp=findViewById(R.id.delete_non_imp_button);
       logoutbtn=findViewById(R.id.logout_button);
       db=new DatabaseHelper3(this);
       myDB=new DatabaseUser(this);
        final Cursor data=db.getListContents();
        Inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ViewListContents.class);
                startActivity(intent);
            }
        });
        non_imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ViewNonImportant.class);
                startActivity(intent);
            }
        });
        del_non_imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while(data.moveToNext())
                {
                    String s=data.getString(0);
                    int id=Integer.parseInt(s);
                    db.deleteAll(id);
                }
                Toast.makeText(LoginActivity.this,"All Deleted Successfully",Toast.LENGTH_LONG).show();
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor data1=myDB.getListContents();
                if(data1.getCount()>0) {
                    while (data1.moveToNext()) {
                        String s = data1.getString(0);
                        int id = Integer.parseInt(s);
                        myDB.delete(id);
                    }
                }
                finish();
                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

    }




    }