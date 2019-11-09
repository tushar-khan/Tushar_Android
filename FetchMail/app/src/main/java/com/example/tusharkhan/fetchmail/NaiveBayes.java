package com.example.tusharkhan.fetchmail;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.*;
import java.lang.Math;

import static android.content.ContentValues.TAG;

public class NaiveBayes {
    int [] a=new int[100];
    Context context;
    AssetManager assManager;
    DatabaseHelper myDB;
    DatabaseHelper2 DB;
    public NaiveBayes(Context context)
    {

        this.context=context;
        assManager = context.getAssets();
    }

    void naive_classifier(int counter,String s,Map<String,Integer> non_imp_words,Map<String,Integer> imp_words,Map<String,Integer>vocab,int tot_imp,int tot_non_imp) throws IOException
    {
        Double probImp,probNonImp,probWordImp=1.0,probWordNonImp=1.0,finalprobImp,finalprobNonImp;


        s=s.replaceAll("[.|,|;]", " ");

        String raw="";
        String [] word =s.split("\\s+");


        for(Integer k=0;k<word.length;k++)
        {
            String s1="";
            raw=word[k];

            raw=raw.toUpperCase();

            char i;
            for(Integer j=0;j<raw.length();j++){
                for(i = 'A'; i <= 'Z';i++)
                {
                    if(raw.charAt(j)==i)
                    {
                        s1+=i;
                    }
                }
            }
            if(s1.equals("IF")||s1.equals("WE")||s1.equals("OUR")||s1.equals("OURS")||s1.equals("TO")||s1.equals("THE")||s1.equals("OF")||s1.equals("YOU")||s1.equals("YOUR")||s1.equals("YOURS")||s1.equals("IN")||s1.equals("A")||s1.equals("AT")||s1.equals("AND")||s1.equals("FROM")||s1.equals("FOR")||s1.equals("ON")||s1.equals("IS")||s1.equals("ARE")||s1.equals("WERE")||s1.equals("WAS")||s1.equals("WITH")||s1.equals("HAVE")||s1.equals("THIS")||s1.equals("THAT")||s1.equals("THESE")||s1.equals("THOSE")||s1.equals("OR")||s1.equals("CAN")||s1.equals("I")||s1.equals("HAS")||s1.equals("IT")||s1.equals("BE")||s1.equals("AS")||s1.equals("WILL")||s1.equals("AN")||s1.equals("BY")||s1.equals("HI")||s1.equals("HELLO")||s1.equals("HERE")||s1.equals("NOT")||s1.equals("NO")||s1.equals("ABOUT")||s1.equals("SO")||s1.equals("BEEN")||s1.equals("HAD")||s1.equals("BEING")||s1.equals("WOULD")||s1.equals("COULD")||s1.equals("MIGHT")||s1.equals("MAY")||s1.equals("AM")||s1.equals("THEY")||s1.equals("THEM")||s1.equals("THEMSELVES")||s1.equals("OURSELVES"))
            {
                continue;
            }
            if(s1.length()>1&&s1.length()<15)
            {
                Integer x,y;
              //  System.out.println(s1);
               // System.out.println(imp_words.get(s1));
               // System.out.println(non_imp_words.get(s1));
                x=imp_words.get(s1);
                y=non_imp_words.get(s1);
                if(imp_words.get(s1)==null)
                {
                    x=0;
                }
                if(non_imp_words.get(s1)==null)
                {
                    y=0;
                }
                if(!(x==0&&y==0))
                {
                    probImp=Math.log(((x+1)*1.0)/(tot_imp+vocab.size()));
                    probWordImp+=probImp;
                    probNonImp=Math.log(((y+1)*1.0)/(tot_non_imp+vocab.size()));
                    probWordNonImp+=probNonImp;
                    if( probWordImp==0.0)
                    {
                        probWordNonImp+=0.2;
                        break;
                    }
                    if( probWordNonImp==0.0)
                    {
                        probWordImp+=0.2;
                        break;
                    }
                    System.out.println("IMP:"+probWordImp+" Non:"+probWordNonImp);
                }

            }
        }

        finalprobImp=probWordImp;
        finalprobNonImp=probWordNonImp;
        System.out.println(finalprobImp+" "+finalprobNonImp);
        if(finalprobImp>=finalprobNonImp)
        {
            a[counter]=1;
           // System.out.println("Important");
        }
        else
        {
            a[counter]=2;
           // System.out.println("Not Important");
        }

    }
    public int[] naive(ArrayList<GmailList>list) throws IOException
    {
        Map<String,Integer> imp_words= new HashMap<String,Integer>();
        Map<String,Integer> non_imp_words= new HashMap<String,Integer>();
        Map<String,Integer> vocab= new HashMap<String,Integer>();

        myDB=new DatabaseHelper(context);
        DB=new DatabaseHelper2(context);

      Cursor data=myDB.getListContents2();

      Cursor data2=DB.getlistContent();
       if(data.getCount() == 0){

            for(int i=0;i<list.size();i++)
            {
                if(list.get(i).getValue()==1)
                {
                    String[] word=tokenize(list.get(i).getMailbody());
                    for(Integer k=0;k<word.length;k++) {
                        String s1 = "";
                        String raw = word[k];

                        raw = raw.toUpperCase();

                        char ii;
                        for (Integer j = 0; j < raw.length(); j++) {
                            for (ii = 'A'; ii <= 'Z'; i++) {
                                if (raw.charAt(j) == ii) {
                                    s1 += ii;
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
                }
            }
        }
        if(data.getCount()!=0){
            int k=0;
            while(data.moveToNext())
            {

                String s=data.getString(1);
                if(s.equals("TO")||s.equals("THE")||s.equals("OF")||s.equals("YOU")||s.equals("YOUR")||s.equals("IN")||s.equals("A")||s.equals("AT")||s.equals("AND")||s.equals("FROM")||s.equals("FOR")||s.equals("ON")||s.equals("IS")||s.equals("ARE")||s.equals("WERE")||s.equals("WAS")||s.equals("WITH")||s.equals("HAVE")||s.equals("THIS")||s.equals("THAT")||s.equals("THESE")||s.equals("THOSE")||s.equals("OR")||s.equals("CAN")||s.equals("I")||s.equals("HAS")||s.equals("IT")||s.equals("BE")||s.equals("AS")||s.equals("WILL")||s.equals("AN")||s.equals("BY")||s.equals("HI")||s.equals("HELLO")||s.equals("HERE")||s.equals("NOT")||s.equals("NO")||s.equals("ABOUT")||s.equals("SO")||s.equals("BEEN")||s.equals("HAD")||s.equals("BEING")||s.equals("WOULD")||s.equals("COULD")||s.equals("MIGHT")||s.equals("MAY")||s.equals("AM"))
                {
                    continue;
                }
                Integer count= imp_words.get(s);
                if(count!=null)
                {
                    count++;
                }
                else
                {
                    count=1;
                }

                if(s.length()>1&&s.length()<15)
                {
                    imp_words.put(s, count);
                    vocab.put(s,count);
                }


            }
        }

        if(data2.getCount() == 0){

            for(int i=0;i<list.size();i++)
            {
                if(list.get(i).getValue()==2)
                {
                    String[] word=tokenize(list.get(i).getMailbody());
                    for(Integer k=0;k<word.length;k++) {
                        String s1 = "";
                        String raw = word[k];

                        raw = raw.toUpperCase();

                        char ii;
                        for (Integer j = 0; j < raw.length(); j++) {
                            for (ii = 'A'; ii <= 'Z'; i++) {
                                if (raw.charAt(j) == ii) {
                                    s1 += ii;
                                }
                            }
                        }
                        if (s1.equals("IF") || s1.equals("WE") || s1.equals("OUR") || s1.equals("OURS") || s1.equals("TO") || s1.equals("THE") || s1.equals("OF") || s1.equals("YOU") || s1.equals("YOUR") || s1.equals("YOURS") || s1.equals("IN") || s1.equals("A") || s1.equals("AT") || s1.equals("AND") || s1.equals("FROM") || s1.equals("FOR") || s1.equals("ON") || s1.equals("IS") || s1.equals("ARE") || s1.equals("WERE") || s1.equals("WAS") || s1.equals("WITH") || s1.equals("HAVE") || s1.equals("THIS") || s1.equals("THAT") || s1.equals("THESE") || s1.equals("THOSE") || s1.equals("OR") || s1.equals("CAN") || s1.equals("I") || s1.equals("HAS") || s1.equals("IT") || s1.equals("BE") || s1.equals("AS") || s1.equals("WILL") || s1.equals("AN") || s1.equals("BY") || s1.equals("HI") || s1.equals("HELLO") || s1.equals("HERE") || s1.equals("NOT") || s1.equals("NO") || s1.equals("ABOUT") || s1.equals("SO") || s1.equals("BEEN") || s1.equals("HAD") || s1.equals("BEING") || s1.equals("WOULD") || s1.equals("COULD") || s1.equals("MIGHT") || s1.equals("MAY") || s1.equals("AM") || s1.equals("THEY") || s1.equals("THEM") || s1.equals("THEMSELVES") || s1.equals("OURSELVES")) {
                            continue;
                        }
                        if(s1.length()>1 && s1.length()<15)
                        {
                            DB.DataAdd(s1);
                        }
                    }
                }
            }
        }
        if(data2.getCount()!=0){
            int k=0;
            while(data2.moveToNext())
            {

                String s=data2.getString(1);
                if(s.equals("TO")||s.equals("THE")||s.equals("OF")||s.equals("YOU")||s.equals("YOUR")||s.equals("IN")||s.equals("A")||s.equals("AT")||s.equals("AND")||s.equals("FROM")||s.equals("FOR")||s.equals("ON")||s.equals("IS")||s.equals("ARE")||s.equals("WERE")||s.equals("WAS")||s.equals("WITH")||s.equals("HAVE")||s.equals("THIS")||s.equals("THAT")||s.equals("THESE")||s.equals("THOSE")||s.equals("OR")||s.equals("CAN")||s.equals("I")||s.equals("HAS")||s.equals("IT")||s.equals("BE")||s.equals("AS")||s.equals("WILL")||s.equals("AN")||s.equals("BY")||s.equals("HI")||s.equals("HELLO")||s.equals("HERE")||s.equals("NOT")||s.equals("NO")||s.equals("ABOUT")||s.equals("SO")||s.equals("BEEN")||s.equals("HAD")||s.equals("BEING")||s.equals("WOULD")||s.equals("COULD")||s.equals("MIGHT")||s.equals("MAY")||s.equals("AM"))
                {
                    continue;
                }
                Integer count= non_imp_words.get(s);
                if(count!=null)
                {
                    count++;
                }
                else
                {
                    count=1;
                }
                if(s.length()>1&&s.length()<15)
                {
                    non_imp_words.put(s, count);
                    vocab.put(s,count);
                }

            }
        }

        System.out.println(imp_words.size()+" "+non_imp_words.size()+" "+vocab.size()+" "+ data.getCount()+" "+data2.getCount());

      for(Integer i=0;i<list.size();i++)
       {
            naive_classifier(i,list.get(i).getMailbody(),non_imp_words,imp_words,vocab,data.getCount(),data2.getCount());

      }

        return  a;
    }
    public String[] tokenize(String s)
    {
        s=s.replaceAll("[.|,|;]", " ");

        String [] word =s.split("\\s+");
        return  word;
    }
}
