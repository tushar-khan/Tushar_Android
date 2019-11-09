package com.example.tusharkhan.fetchmail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class SendMailAsynTask extends AsyncTask<String,String,Void> {
    private ProgressDialog statusDialog;
    private Activity sendMailActivity;
    Context context;
    DatabaseHelper myDB;
    DatabaseHelper3 DB;
    NaiveBayes object;
    DatabaseUser db;
    String user="",password="";
    ArrayList<GmailList> ArrayGList= new ArrayList<GmailList>();
    GmailList gmailList;
    int[] Array=new int[100];

    public SendMailAsynTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       /* statusDialog = new ProgressDialog(context);
        statusDialog.setMessage("Getting ready...");
       statusDialog.setIndeterminate(false);
       statusDialog.setCancelable(false);
        statusDialog.show();*/
    }
    @Override
    protected Void doInBackground(String... params) {
        String host = "imap.gmail.com";
        String username=params[0];
        String pass=params[1];
        user=username;
        password=pass;
        GMail obj=new GMail(context);
        object=new NaiveBayes(context);
        ArrayGList=obj.Gmail(host,username,pass);

        try {
            Array=object.naive(ArrayGList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<ArrayGList.size();i++)
        System.out.println(ArrayGList.get(i).getSubj()+" "+Array[i]);

        Log.d("mytag", "Fetching Successfull!");
        System.out.println("Total Instance: 101");
        return null;
    }
    @Override
    protected void onProgressUpdate(String... values) {
       super.onProgressUpdate(values);
      //  statusDialog.setMessage(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
      //  statusDialog.dismiss();
        myDB=new DatabaseHelper(context);
        DB=new DatabaseHelper3(context);
        db=new DatabaseUser(context);
        Cursor data=db.getListContents();
        if(data.getCount()==0)
            db.addData(user,password);
        int TP=0,TN=0,FP=0,FN=0;
       for(int i=0;i<ArrayGList.size();i++)
       {

           if(Array[i]==2)
           {

               DB.addData(ArrayGList.get(i).getFrom(),ArrayGList.get(i).getSubj(),ArrayGList.get(i).getMailbody());
               System.out.println(ArrayGList.get(i).getFrom()+" "+ArrayGList.get(i).getValue()+" "+Array[i]);
              /*if(ArrayGList.get(i).getValue()==Array[i])
               {
                   TN++;
               }
               else
                   FN++;*/

           }
           if(Array[i]==1)
           {
             myDB.addData(ArrayGList.get(i).getFrom(),ArrayGList.get(i).getSubj(),ArrayGList.get(i).getMailbody());
               System.out.println(ArrayGList.get(i).getFrom()+" "+ArrayGList.get(i).getValue()+" "+Array[i]);
               /*if(ArrayGList.get(i).getValue()==Array[i])
               {
                   TP++;
               }
               else
                   FP++;*/
           }

       }
        System.out.println("TP: "+TP+" TN: "+TN+" FP: "+FP+" FN: "+FN);
       double accuracy=(TP+TN)*1.0/(TP+TN+FP+FN);
        System.out.println("Accuracy: "+accuracy);
        if(ArrayGList.size()>0)
        Toast.makeText(context,"Successfully fetched",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context,"No New Email to Fetch",Toast.LENGTH_LONG).show();


    }

}

