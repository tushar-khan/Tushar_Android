package com.example.tusharkhan.fetchmail;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    SendMailAsynTask obj;
    Button button,button1;
    DatabaseUser myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Button send = (Button) this.findViewById(R.id.newbutton1);
        obj=new SendMailAsynTask(this);
        myDB=new DatabaseUser(this);

        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                final String fromEmail = ((TextView) findViewById(R.id.neweditText1))
                        .getText().toString();
                final String fromPassword = ((TextView) findViewById(R.id.neweditText2))
                        .getText().toString();
                System.out.println("Yoo..."+fromEmail+" "+fromPassword);
                Cursor data=myDB.getListContents();
                if(data.getCount()>0)
                {
                    String user="",pass="";
                    while(data.moveToLast())
                    {
                        user=data.getString(1);
                        pass=data.getString(2);
                        System.out.println("From inside "+user+" "+pass);
                        break;
                    }
                    if(fromEmail.equals(user)&& fromPassword.equals(pass))
                    {
                        fetch(fromEmail,fromPassword);
                        finish();
                        startActivity(new Intent(Main2Activity.this, LoginActivity.class));
                    }
                    else
                    {
                        Toast.makeText(Main2Activity.this,"Email or password doesn't match. Try again",Toast.LENGTH_LONG).show();
                    }
                }
                else {

                    fetch(fromEmail, fromPassword);
                    finish();
                    startActivity(new Intent(Main2Activity.this, LoginActivity.class));
                }
                Log.i("SendMailActivity", "Send Button Clicked.");
                // myDB.addData(fromEmail, fromPassword);
            }
        });

    }
    public void fetch(String fromEmail,String fromPassword)
    {
        System.out.println("Fething mails..");
        Cursor data=myDB.getListContents();
        System.out.println("user "+fromEmail);
        String user="",pass="";
        if(data.getCount() == 0){
            obj.execute(fromEmail,
                    fromPassword);
            System.out.println("No data");
        }
        else
        {
            while(data.moveToLast())
            {
                user=data.getString(1);
                pass=data.getString(2);
                System.out.println("From inside "+user+" "+pass);
                break;
            }
            if(fromEmail.equals(user)&& fromPassword.equals(pass))
            {
                obj.execute(fromEmail,
                        fromPassword);

            }
        }


    }
}
