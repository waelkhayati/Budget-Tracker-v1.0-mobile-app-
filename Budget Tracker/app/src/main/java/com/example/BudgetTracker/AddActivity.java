package com.example.BudgetTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {


    Spinner category_spinner;
    EditText amount_input;
    String category_input;
    Button add_button;
    private DatePickerDialog datePickerDialog;
    private Button date_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //date
        initDatePicker();
        date_input = findViewById(R.id.datePickerButton);
        date_input.setText(getTodaysDate());

        //spinner category
        category_spinner = (Spinner) findViewById(R.id.category_input);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categories,
                android.R.layout.simple_dropdown_item_1line
        );
        category_spinner.setAdapter(adapter);

        //amount input
        amount_input = findViewById(R.id.amount_input);

        add_button = findViewById(R.id.add_button);


            add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    category_input = String.valueOf(category_spinner.getSelectedItem());
                    Database myDB = new Database(AddActivity.this);
                    myDB.addRecord(category_input.trim(),
                            date_input.getText().toString().trim(),
                            Integer.parseInt(amount_input.getText().toString().trim()));
                }
            });
        }




    //date picker
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day + " " + getMonthFormat(month) + " "+year;
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

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

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
