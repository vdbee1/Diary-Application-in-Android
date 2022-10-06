package com.s4plabs.logbook.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.s4plabs.logbook.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Settings extends Activity {
    ListView lv;
    final ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    EditText passw,confirmpass;


ExpandableListAdapter obj1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listview();
    }
    public void listview()
    {
        final  HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("maintext", "Change Password");
        map1.put("subtext", "Set a new password here");
        data.add(map1);

        final HashMap<String,String> map2=new HashMap<>();
map2.put("maintext"," ");
        map2.put("subtext"," ");
        data.add(map2);

        lv=(ListView)findViewById(R.id.listView);
        SimpleAdapter adapter=new SimpleAdapter(this,data, R.layout.items,new String[]{"maintext","subtext"},new int[]{R.id.textView3, R.id.textView4});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map =(HashMap<String,String>)lv.getItemAtPosition(position);

                String value=map.get("maintext");

                if(value=="Change Password")
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this,R.style.MyDialogTheme);

                    // Get the layout inflater
                    final LayoutInflater inflater = Settings.this.getLayoutInflater();
                    View prompt = inflater.inflate(R.layout.pass, null);


                    // Inflate and set the layout for the dialog
                    // Pass null as the parent view because its going in the dialog layout
                    builder.setView(prompt);
                            // Add action buttons
                    passw=(EditText)prompt.findViewById(R.id.editText3);
                    confirmpass=(EditText)prompt.findViewById(R.id.editText4);
                            builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                        String s=passw.getText().toString();
                                    String s1=confirmpass.getText().toString();
                                    SharedPreferences sharedPref;
                                    sharedPref = getSharedPreferences("Joker", Context.MODE_PRIVATE);
                                    if(s.equals(s1))
                                    {

                                        SharedPreferences.Editor editor=sharedPref.edit();
                                        editor.putString("Password",passw.getText().toString());
                                        editor.commit();
                                        Toast.makeText(getApplicationContext(),"Password changed",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(getApplicationContext(),"Your passwords do not match.",Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert=builder.create();
                    alert.setTitle(Html.fromHtml("<font color='#5daf98'>Set Password</font>"));


                    alert.show();

                }




        }
    });

}}
