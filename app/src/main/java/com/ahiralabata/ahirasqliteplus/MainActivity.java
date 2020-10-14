package com.ahiralabata.ahirasqliteplus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.ahiralabata.ahirasqliteplus.adapter.Adapter;
import com.ahiralabata.ahirasqliteplus.helper.DbHelper;
import com.ahiralabata.ahirasqliteplus.model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    DbHelper SQLite = new DbHelper(this);

    public static final String KEY_ID = "id";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_ALAMAT = "alamat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLite = new DbHelper(getApplicationContext());
        listView = (ListView) findViewById(R.id.list_view);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                startActivity(intent);
            }
        });
        adapter = new Adapter(MainActivity.this, itemList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String idx = itemList.get(position).getId();
                final String nama = itemList.get(position).getNama();
                final String alamat = itemList.get(position).getAlamat();

                final CharSequence[] dialogItem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Switch (which){
                            case 0:
                                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                                intent.putExtra(KEY_ID, idx);
                                intent.putExtra(KEY_NAMA, nama);
                                intent.putExtra(KEY_ALAMAT, alamat);
                                startActivity(intent);
                                break;
                            case 1:
                                SQLite.delete(Integer.parseInt(idx));
                                itemList.clear();
                                getAllData();
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
        getAllData();
    }

    private void getAllData(){
        ArrayList<HashMap<String, String>> row = SQLite.getAll();

        for(int i = 0; i < row.size(); i++){
            String id = row.get(i).get(KEY_ID);
            String poster = row.get(i).get(KEY_NAMA);
            String title = row.get(i).get(KEY_ALAMAT);

            Data data = new Data();

            data.setId(id);
            data.setNama(poster);
            data.setAlamat(title);
            itemList.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemList.clear();
        getAllData();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}