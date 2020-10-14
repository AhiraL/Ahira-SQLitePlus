package com.ahiralabata.ahirasqliteplus;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ahiralabata.ahirasqliteplus.helper.DbHelper;

public class AddEdit extends AppCompatActivity {
        EditText txt_id, txt_nama, txt_alamat;
        Button simpan, batal;
        DbHelper SQLite = new DbHelper(this);
        String id, nama, alamat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id = (EditText) findViewById(R.id.inp_id);
        txt_nama = (EditText) findViewById(R.id.inp_nama);
        txt_alamat = (EditText) findViewById(R.id.inp_alamat);
        simpan = (Button) findViewById(R.id.simpan);
        batal = (Button) findViewById(R.id.batal);

        id = getIntent().getStringExtra(MainActivity.KEY_ID);
        nama = getIntent().getStringExtra(MainActivity.KEY_NAMA);
        alamat = getIntent().getStringExtra(MainActivity.KEY_ALAMAT);

        if (id == null || id == "") {
            setTitle("Tambah Data");
        } else {
            setTitle("Ubah Data");
            txt_id.setText(id);
            txt_nama.setText(nama);
            txt_alamat.setText(alamat);
        }

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(txt_id.getText().toString().equals("")){
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception e){
                    Log.e("simpan", e.toString());
                }
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void blank(){
        txt_nama.requestFocus();
        txt_id.setText(null);
        txt_nama.setText(null);
        txt_alamat.setText(null);
    }

    private void save(){
        if(txt_nama.getText().toString().equals(null) || txt_nama.getText().toString().equals("") || txt_alamat.getText().toString().equals(null) || txt_alamat.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Tolong isikan nama dan alamat anda", Toast.LENGTH_LONG).show();
        } else {
            SQLite.insert(txt_nama.getText().toString().trim(), txt_alamat.getText().toString().trim());
            blank();
            finish();
        }
    }

    private void edit(){
        if(txt_nama.getText().toString().equals(null) || txt_nama.getText().toString().equals("") || txt_alamat.getText().toString().equals(null) || txt_alamat.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Tolong isikan nama dan alamat anda", Toast.LENGTH_LONG).show();
        } else {
            SQLite.update(Integer.parseInt(txt_id.getText().toString().trim()), txt_nama.getText().toString().trim(), txt_alamat.getText().toString().trim());
            blank();
            finish();
        }
    }
}
