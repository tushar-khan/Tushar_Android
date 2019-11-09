package com.example.tusharkhan.fetchmail;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ViewListContents extends AppCompatActivity {

    DatabaseHelper myDB;
    int count=0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("I am destroyed");
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewlistcontents_layout);
        setTitle("Inbox");
       viewContent();
       refresh();

    }
    public void viewContent() {
      //  if (count >= 1) {
            count++;
            System.out.println("Refreshed " + "Important");
            ListView listView = (ListView) findViewById(R.id.listView);
            myDB = new DatabaseHelper(this);
            final Cursor data = myDB.getListContents();
            final String[] item1 = new String[data.getCount()];
           final String[] item2 = new String[data.getCount()];
            final String[] first_item = new String[data.getCount()];
            final String[] second_item = new String[data.getCount()];
            final String[] item3 = new String[data.getCount()];
            final String[] third_item = new String[data.getCount()];
        final String[] item4 = new String[data.getCount()];
        final String[] fourth_item = new String[data.getCount()];
            if (data.getCount() == 0) {
                Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
            } else {
                int k = 0;

                while (data.moveToNext()) {

                        item1[k] = (data.getString(1));
                        item2[k] = (data.getString(2));
                        item3[k] = (data.getString(3));
                        item4[k]=(data.getString(0));


                    k++;
                    //  ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                    //  listView.setAdapter(listAdapter);
                }

                k = 0;
                System.out.println(item1.length + " " + data.getCount());
                for (int i = item1.length - 1; i >= 0; i--) {
                    first_item[k] = item1[i];
                    second_item[k] = item2[i];
                    third_item[k] = item3[i];
                    fourth_item[k]= item4[i];
                    k++;
                }

            }

            adapter Adapter = new adapter(this, first_item, second_item);
            listView.setAdapter(Adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //  String s=data.getString(1);
                    Intent intent = new Intent(ViewListContents.this, GmailActivity.class);
                    intent.putExtra("from", first_item[i]);
                    intent.putExtra("subject", second_item[i]);
                    intent.putExtra("Body", third_item[i]);
                    intent.putExtra("ID", fourth_item[i]);
                    intent.putExtra("type", "1");
                    startActivity(intent);
                    // Toast.makeText(ViewListContents.this,"Its working "+first_item[i],Toast.LENGTH_LONG).show();
                }
            });
      //  }
    }
    public void refresh()
    {
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewContent();
                handler.postDelayed(this,30000);
            }
        },30000);

    }

}
class adapter extends ArrayAdapter<String>
{
    Context context;
    String [] item1;
    String [] item2;
    adapter(Context c,String [] item1,String [] item2)
    {
        super(c,R.layout.single_row,R.id.textView,item1);
        this.context=c;
        this.item1=item1;
        this.item2=item2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.single_row,parent,false);
        TextView tv1=row.findViewById(R.id.textView);
        TextView tv2=row.findViewById(R.id.textView3);

        tv1.setText(item1[position]);
        tv2.setText(item2[position]);
        return row;
    }
}
