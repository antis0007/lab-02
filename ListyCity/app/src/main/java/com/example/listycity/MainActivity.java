package com.example.listycity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    String selected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button addButton = (Button) findViewById(R.id.add_city_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog(1); //opt: 1 = add city
            }
        });
        Button deleteButton = (Button) findViewById(R.id.delete_city_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog(0); //opt: 0 = delete city
            }
        });

        cityList = findViewById(R.id.city_list); // Default static array of string city names
        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka"};

        dataList = new ArrayList<>(); //Dynamic list loads defaults
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this,R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Selected city: " + selected, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showInputDialog(int opt) {
        final EditText input = new EditText(this);
        if(opt == 0 && !Objects.equals(selected, "")){
            input.setText(selected);
        }
        input.setHint("Enter city name");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if(opt == 0){builder.setTitle("Delete City");}
            if(opt == 1){builder.setTitle("Add City");}
                builder.setMessage("Enter a city name:")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = input.getText().toString();
                        if(opt == 0){
                            Toast.makeText(MainActivity.this, "Removing city: " + value, Toast.LENGTH_SHORT).show();
                            cityAdapter.remove(value);
                        }
                        if(opt == 1){
                            Toast.makeText(MainActivity.this, "Adding city: " + value, Toast.LENGTH_SHORT).show();
                            cityAdapter.add(value);
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}