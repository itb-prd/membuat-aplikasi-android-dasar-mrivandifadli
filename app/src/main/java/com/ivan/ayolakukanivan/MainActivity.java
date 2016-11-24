package com.ivan.ayolakukanivan;

import android.app.Activity;

/*Referensi :   https://guides.codepath.com/android/Basic-Todo-App-Tutorial#creating-list-of-items*/

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends Activity{

    public ArrayList<String> ToDoListContent = new ArrayList<>();

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.task_view);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();


    }

    // Menghapus Item
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(

                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Menghapus item dan digantikan posisi item di bawahnya
                        items.remove(pos);
                        // Refresh adapter
                        itemsAdapter.notifyDataSetChanged();
                        //menulis ke file
                        writeItems();
                        // Mengembalikan nilai
                        return true;
                    }

                });
    }

    //Menambah Item
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    //Membaca item untuk dipersiapkan dimasukkan ke file
    private void readItems() {

        String filename = "todolistdatabase.txt";

        try {
            FileInputStream ins = new FileInputStream(new File(getFilesDir(), filename));
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));

            String todo;

            while((todo = br.readLine()) != null) {
                ToDoListContent.add(todo);
            }
            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Menuliskan Item ke file
    private void writeItems() {

        String filename = "todolistdatabase.txt";

        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(new File(getFilesDir(), filename)));
            for (String todo: ToDoListContent) {
                pw.println(todo);
            }
            pw.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}