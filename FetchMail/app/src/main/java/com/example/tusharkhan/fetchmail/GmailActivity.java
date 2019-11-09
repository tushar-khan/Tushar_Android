package com.example.tusharkhan.fetchmail;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GmailActivity extends AppCompatActivity {

    TextView tv;
    Button button,deletebtn,addimportantbtn;
    DatabaseHelper3 db;
    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail);
        setTitle("Email");
        button=findViewById(R.id.replybutton);
        deletebtn=findViewById(R.id.deletebutton);
        addimportantbtn=findViewById(R.id.add_to_imp_button);
        db=new DatabaseHelper3(this);
        myDB=new DatabaseHelper(this);
        Intent intent=getIntent();
        final String from=intent.getStringExtra("from");
        final String subject=intent.getStringExtra("subject");
        final String s=intent.getStringExtra("Body");
        final String[] word=tokenize(s);
        final String s1=intent.getStringExtra("ID");
        final String s2=intent.getStringExtra("type");
        final int id=Integer.parseInt(s1);
        final int type=Integer.parseInt(s2);
        tv=findViewById(R.id.showmailbody);
        tv.setText(s);
        tv.setMovementMethod(new ScrollingMovementMethod());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Intent.ACTION_SEND);
                intent1.setData(Uri.parse("mailto:"));
                String [] to={s1};
                intent1.putExtra(Intent.EXTRA_EMAIL,to);

                intent1.setType("message/rfc822");
                Intent chooser=Intent.createChooser(intent1,"Send mail");
                startActivity(chooser);
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(type==2) {
                    db.delete(id);
                    finish();
                    Intent intent2 = new Intent(GmailActivity.this, ViewNonImportant.class);
                    startActivity(intent2);
                }
                if(type==1)
                {
                    myDB.delete(id);
                    finish();
                    Intent intent2 = new Intent(GmailActivity.this, ViewListContents.class);
                    startActivity(intent2);
                }
            }
        });
        addimportantbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(Integer k=0;k<word.length;k++) {
                    String s1 = "";
                    String raw = word[k];

                    raw = raw.toUpperCase();

                    char i;
                    for (Integer j = 0; j < raw.length(); j++) {
                        for (i = 'A'; i <= 'Z'; i++) {
                            if (raw.charAt(j) == i) {
                                s1 += i;
                            }
                        }
                    }
                    if (s1.equals("IF") || s1.equals("WE") || s1.equals("OUR") || s1.equals("OURS") || s1.equals("TO") || s1.equals("THE") || s1.equals("OF") || s1.equals("YOU") || s1.equals("YOUR") || s1.equals("YOURS") || s1.equals("IN") || s1.equals("A") || s1.equals("AT") || s1.equals("AND") || s1.equals("FROM") || s1.equals("FOR") || s1.equals("ON") || s1.equals("IS") || s1.equals("ARE") || s1.equals("WERE") || s1.equals("WAS") || s1.equals("WITH") || s1.equals("HAVE") || s1.equals("THIS") || s1.equals("THAT") || s1.equals("THESE") || s1.equals("THOSE") || s1.equals("OR") || s1.equals("CAN") || s1.equals("I") || s1.equals("HAS") || s1.equals("IT") || s1.equals("BE") || s1.equals("AS") || s1.equals("WILL") || s1.equals("AN") || s1.equals("BY") || s1.equals("HI") || s1.equals("HELLO") || s1.equals("HERE") || s1.equals("NOT") || s1.equals("NO") || s1.equals("ABOUT") || s1.equals("SO") || s1.equals("BEEN") || s1.equals("HAD") || s1.equals("BEING") || s1.equals("WOULD") || s1.equals("COULD") || s1.equals("MIGHT") || s1.equals("MAY") || s1.equals("AM") || s1.equals("THEY") || s1.equals("THEM") || s1.equals("THEMSELVES") || s1.equals("OURSELVES")) {
                        continue;
                    }
                    if(s1.length()>1 && s1.length()<15)
                    {
                        myDB.addData2(s1);
                    }
                }
                if(type==2)
                {
                   myDB.addData(from,subject,s);
                }
                Toast.makeText(GmailActivity.this,"Added To Important",Toast.LENGTH_LONG).show();
            }
        });
    }
    public String[] tokenize(String s)
    {
        s=s.replaceAll("[.|,|;]", " ");

        String [] word =s.split("\\s+");
        return  word;
    }
}
