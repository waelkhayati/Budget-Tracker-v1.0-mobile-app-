package com.example.BudgetTracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;
    Database myDB;
    ArrayList<String> id, category, date, amount;
    CustomAdapter customAdapter;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new Database(MainActivity.this);
        id = new ArrayList<>();
        category = new ArrayList<>();
        date = new ArrayList<>();
        amount = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this,this, id, category, date, amount);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //PieChart
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        if (myDB.totalIncome() != 0){
            pieEntries.add(new PieEntry(myDB.totalIncome(), (String.format("%sTND", myDB.totalIncome()))));
        }
        if (myDB.totalExpenses() !=0){
            pieEntries.add(new PieEntry(myDB.totalExpenses(), (String.format("%sTND", myDB.totalExpenses()))));
        }
        pieChart = findViewById(R.id.pieChart);
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieChart.setData(new PieData(pieDataSet));
        pieChart.setNoDataTextColor(Color.WHITE);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(0);
        pieDataSet.setColors(Color.parseColor("#2ac924"), Color.parseColor("#dd0000"));
        pieChart.setEntryLabelTextSize(16);
        pieChart.setNoDataTextColor(Color.WHITE);
        pieChart.animateXY(1500, 1500);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterTextSize(20);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setHoleColor(Color.parseColor("#1c1b1f"));
        pieChart.setTransparentCircleRadius(0);
        pieChart.setHoleRadius(60);
        pieChart.getLegend().setEnabled(false);
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() != 0){
            pieChart.setCenterText((String.format("Balance:\n%s TND", myDB.totalAmount())));
        } else{
            pieChart.setCenterText(null);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                category.add(cursor.getString(1));
                date.add(cursor.getString(2));
                amount.add(cursor.getString(3));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }
//delete all menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Database myDB = new Database(MainActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


}
