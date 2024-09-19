package com.example.vehidocs.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vehidocs.R;
import com.google.android.material.textfield.TextInputEditText;

public class RequestBluebookActivity extends AppCompatActivity {

    public static final String mobileNoParameter = "mobileNo";
    public static final String fullNameParameter = "fullName";
    Button btnupload;
    TextInputEditText vehicleNo, vehicleLotNo;
    String[] items = {"BA (Bagmati)", "GA (Gandaki)", "LU (Lumbini)", "JA (Janakpur)"};
    String[] items1 = {"Motorbike", "Car", "Bus", "Van", "Jeep"};
    String[] items2 = {"KA", "KHA", "JA", "PA", "CA", "HA", "MA"};

    AutoCompleteTextView autoCompleteTxt, autoCompleteTxt1, autoCompleteTxt2;
    ArrayAdapter<String> adapterItems, adapterItems1, adapterItems2;
    private ProgressBar loadingProgress;
    String zone = "";
    String vehicleType = "";
    String symbol = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_bluebook);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        autoCompleteTxt1 = findViewById(R.id.auto_complete_txt1);
        autoCompleteTxt2 = findViewById(R.id.auto_complete_txt2);
        vehicleNo = findViewById(R.id.txtVehicleNo);
        vehicleLotNo = findViewById(R.id.txtVehicleLotNo);
        loadingProgress = findViewById(R.id.regProgressBar);
        loadingProgress.setVisibility(View.INVISIBLE);
        btnupload = findViewById(R.id.btnRequestBluebook);


        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
        autoCompleteTxt.setAdapter(adapterItems);
        adapterItems1 = new ArrayAdapter<String>(this, R.layout.list_item, items1);
        autoCompleteTxt1.setAdapter(adapterItems1);
        adapterItems2 = new ArrayAdapter<String>(this, R.layout.list_item, items2);
        autoCompleteTxt2.setAdapter(adapterItems2);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                zone = parent.getItemAtPosition(position).toString();
                return;
            }
        });

        autoCompleteTxt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vehicleType = parent.getItemAtPosition(position).toString();
                return;
            }
        });

        autoCompleteTxt2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                symbol = parent.getItemAtPosition(position).toString();
                return;
            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber = getIntent().getStringExtra(mobileNoParameter);
                Intent intent = new Intent(getApplicationContext(), AddBluebookImageActivity.class);
                intent.putExtra(AddBluebookImageActivity.zoneParameter, zone);
                intent.putExtra(AddBluebookImageActivity.vehicleTypeParameter, vehicleType);
                intent.putExtra(AddBluebookImageActivity.symbolParameter, symbol);
                intent.putExtra(AddBluebookImageActivity.mobileNoParameter, mobileNumber);
                intent.putExtra(AddBluebookImageActivity.vehicleNoParameter, vehicleNo.getText().toString());
                intent.putExtra(AddBluebookImageActivity.vehicleLotNoParameter, vehicleLotNo.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show();
        Intent i = getIntent();
        String fullName = i.getStringExtra(fullNameParameter);
        String mobileNumber = i.getStringExtra(mobileNoParameter);
        AlertDialog.Builder alert = new AlertDialog.Builder(RequestBluebookActivity.this);
        alert.setTitle("Digital Vehicle");
        alert.setMessage("Are you sure you want to go back?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                intent.putExtra(MainPageActivity.fullNameParameter, fullName);
                intent.putExtra(MainPageActivity.mobileNoParameter, mobileNumber);
                startActivity(intent);
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}




