package com.example.tusharkhan.fetchmail;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.io.File;
import java.io.PrintWriter;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class GMail {
    String emailHost;
    String fromEmail;
    String fromPassword;
    String from;
    int type=0;
    String To;
    String subj;
    String mailbody;
    GmailList gmailList;
    ArrayList<GmailList> ArrayGmailList= new ArrayList<GmailList>();
    ArrayList<GmailList> ArrayGList= new ArrayList<GmailList>();
    private Context context;
    DatabaseHelper2 myDB;
    DatabaseHelper DB;

    public GMail(Context context)
    {
        this.context=context;

    }
    public ArrayList<GmailList> fetching(String pop3Host, String user,
                                String password) {

       try {

            // create properties field
            Properties properties = new Properties();
            properties.put("mail.store.protocol","imap");
            // properties.put("mail.store.protocol", "pop3");
            properties.put("mail.imap.host",pop3Host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);
            // emailSession.setDebug(true);

            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("imaps");

            store.connect(pop3Host,user,password);

            // create the folder object and open it
            Folder emailFolder = store.getFolder("Inbox");
            emailFolder.open(Folder.READ_ONLY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            int msgLength=messages.length;

            int prevLength=0;

            myDB=new DatabaseHelper2(context);
      //  DB=new DatabaseHelper(context);

            Cursor data=myDB.getlistContent();

           if(data.getCount()==0)
           {
               System.out.println("No data");
               prevLength=messages.length-100;
           }
           else if(data.getCount()!=0)
            {
                int x1=0;
                while(data.moveToLast())
                {
                    String s=data.getString(1);
                  //  System.out.println(s);
                    x1=data.getInt(0);
                    prevLength=Integer.parseInt(s);

                    break;

                }
               // System.out.println(x1+" "+prevLength);
                myDB.delete(x1);
            }
           myDB.DataAdd(String.valueOf(msgLength));

           System.out.println(prevLength+" "+msgLength+" "+data.getCount());
            for (int b = prevLength; b <messages.length; b++) {
                Message message = messages[b];
                if(message.isSet(Flags.Flag.SEEN)){
                    type=1;
                    System.out.println("This message is not unread "+b);}
                else{
                    type=2;
                    System.out.println("This message is unread "+b);}
                System.out.println("---------------------------------start");
                 writePart(message);

            }

            // close the store and folder objects

            emailFolder.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
           System.out.println("From 1");
            e.printStackTrace();
        } catch (MessagingException e) {
           System.out.println("From 2");
          // Toast.makeText(context,"Authentication Failed! Invalid Credentials. Restart Application",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (Exception e) {
           System.out.println("From 3");
            e.printStackTrace();
        }
        return ArrayGmailList;

    }
    public ArrayList<GmailList> Gmail(String emailHost,String fromEmail, String fromPassword)
    {
        this.fromEmail=fromEmail;
        this.fromPassword=fromPassword;
        this.emailHost=emailHost;
       ArrayGList= fetching(emailHost,fromEmail,fromPassword);
       return ArrayGList;
    }
    public void writePart(Part p) throws Exception {
        if (p instanceof Message)
            //Call method writeEnvelope
            writeEnvelope((Message) p);

        //check if the content is plain text
        if (p.isMimeType("text/plain")) {
            mailbody ="";
            System.out.println("This is plain text");
            System.out.println("---------------------------");
            mailbody+=(String)p.getContent();
            gmailList=new GmailList(from,To,subj,mailbody,type);
            ArrayGmailList.add(gmailList);
        }
        //check if the content has attachment
        else if (p.isMimeType("multipart/*")) {
             System.out.println("This is a Multipart");
             System.out.println("---------------------------");
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++)
               writePart(mp.getBodyPart(i));
        }
        //check if the content is a nested message
        else if (p.isMimeType("message/rfc822")) {
             System.out.println("This is a Nested Message");
             System.out.println("---------------------------");
            writePart((Part) p.getContent());
        }
    }
    public void writeEnvelope(Message m) throws Exception {
         System.out.println("This is the message envelope");
         System.out.println("---------------------------");
        Address[] a;
        from="";
        To="";
        subj="";
        // FROM
        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++) {
                System.out.println("FROM: " + a[j].toString());
                from+=a[j].toString();
            }
        }

        // TO
        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++) {
                System.out.println("TO: " + a[j].toString());
                To+=a[j].toString();
            }
        }

        // SUBJECT
        if (m.getSubject() != null) {
            System.out.println("SUBJECT: " + m.getSubject());
            subj+=m.getSubject();

        }

    }
}
