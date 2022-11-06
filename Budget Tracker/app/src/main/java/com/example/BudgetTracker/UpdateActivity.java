package com.example.BudgetTracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    Spinner category_spinner;
    EditText amount_input;
    Button update_button, delete_button;
    DatePickerDialog datePickerDialog;
    Button date_input;

    String id;
    String category;
    String date;
    String amount;
    String category_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //date
        initDatePicker();
        date_input = findViewById(R.id.datePickerButton2);
        date_input.setText(getSetDate());

        //spinner
        category_spinner = (Spinner) findViewById(R.id.category_input2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_dropdown_item_1line);
        category_spinner.setAdapter(adapter);

        //input
        date_input = findViewById(R.id.datePickerButton2);
        amount_input = findViewById(R.id.amount_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(category);
        }

        //update
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_input = String.valueOf(category_spinner.getSelectedItem());
                Database myDB = new Database(UpdateActivity.this);
                category = category_input.trim();
                date = date_input.getText().toString().trim();
                amount = amount_input.getText().toString().trim();
                myDB.updateData(id, category, date, amount);
            }
        });

        //delete
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("category") &&
                getIntent().hasExtra("date") && getIntent().hasExtra("amount")){
            //Getting Data
            id = getIntent().getStringExtra("id");
            category = getIntent().getStringExtra("category");
            date = getIntent().getStringExtra("date");
            amount = getIntent().getStringExtra("amount");

            //Setting Data
            if (category.equals("Income"))
                category_spinner.setSelection(0);
            else {
                if (category.equals("Food and Drinks"))
                    category_spinner.setSelection(1);
                else{
                    if (category.equals("Home Essentials"))
                        category_spinner.setSelection(2);
                    else{
                        if (category.equals("Transportation"))
                            category_spinner.setSelection(3);
                        else{
                            if (category.equals("Leisure Activities"))
                                category_spinner.setSelection(4);
                            else{
                                if (category.equals("Bills and Taxes"))
                                    category_spinner.setSelection(5);
                                else{
                                    if (category.equals("Investments"))
                                        category_spinner.setSelection(6);
                                    else
                                        category_spinner.setSelection(7);
                                }

                            }

                        }

                    }

                }

            }




            date_input.setText(date);
            amount_input.setText(amount);

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    //delete confirmation
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + category + " ?");
        builder.setMessage("Are you sure you want to delete " + category + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Database myDB = new Database(UpdateActivity.this);
                myDB.deleteOneRow(id);
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

    //date picker
    private String getSetDate() {
        return date = getIntent().getStringExtra("date");

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date= day + " " + getMonthFormat(month) + " "+year;
                date_input.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }
    private String getMonthFormat(int month) {
        switch (month){
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            default:
                return "DEC";
        }
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }
}
