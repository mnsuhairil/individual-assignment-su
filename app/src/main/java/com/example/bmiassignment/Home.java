package com.example.bmiassignment;

import static java.lang.String.format;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Home extends AppCompatActivity {

    private static final String FILE_NAME1 = "reuseDataWeight.txt";
    private static final String FILE_NAME2 = "reuseDataHeight.txt";

    EditText weight, height;
    TextView resulttext, category, healthrisk,u2;
    String calculation, BMIresult, healthtext,S1,S2, text;
    boolean cancel = false;
    float heightValue, weightValue;
    String getWeight,getHeight;
    Button calcuBtn,cm,m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        weight = findViewById(R.id.editWeight);
        height = findViewById(R.id.editHeight);
        resulttext = findViewById(R.id.textViewBMI);
        category = findViewById(R.id.textViewCategory);
        healthrisk = findViewById(R.id.healthrisk);
        u2 = findViewById(R.id.units2);
        calcuBtn=findViewById(R.id.btnCalcu);
        cm=findViewById(R.id.Centi);
        m=findViewById(R.id.Meter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.Exit:
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext()
                                ,About.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        FileInputStream fis1 = null;

        try {
            fis1 = openFileInput(FILE_NAME1);
            InputStreamReader isr = new InputStreamReader(fis1);
            BufferedReader br = new BufferedReader(isr);
            String txt;

            while((txt = br.readLine()) != null){
                weight.setText(txt);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis1 != null){
                try {
                    fis1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        FileInputStream fis2 = null;

        try {
            fis2 = openFileInput(FILE_NAME2);
            InputStreamReader isr = new InputStreamReader(fis2);
            BufferedReader br = new BufferedReader(isr);
            String txt;

            while((txt = br.readLine()) != null){
                height.setText(txt);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis2 != null){
                try {
                    fis2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        calcuBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getHeight=height.getText().toString();
                getWeight=weight.getText().toString();

                if(getWeight.equals(""))
                {
                    Toast.makeText(Home.this, "PLEASE ENTER WEIGHT", Toast.LENGTH_SHORT).show();
                    //this.weight.setText("0");
                    return;
                }else
                if(getHeight.equals(""))
                {
                    Toast.makeText(Home.this, "PLEASE ENTER HEIGHT", Toast.LENGTH_SHORT).show();
                    //this.height.setText("0");
                    return;
                }
                if (cancel == false)
                {
                    S1 = weight.getText().toString();
                    S2 = height.getText().toString();
                    weightValue = Float.parseFloat(S1);
                    heightValue = Float.parseFloat(S2);
                }

                float bmi = weightValue / (heightValue * heightValue);
                if(bmi <= 18.4){
                    BMIresult = "Under Weight";
                    healthtext = "Malnutrition Risk";
                }else if(bmi > 18.4 && bmi <= 24.9){
                    BMIresult = "Normal Weight";
                    healthtext = "Low Risk";
                }else if (bmi > 24.9 && bmi <= 29.9){
                    BMIresult = "Overweight";
                    healthtext = "Enchanced Risk";
                }else if (bmi > 29.9 && bmi <= 34.9){
                    BMIresult = "Moderately Obese";
                    healthtext = "Medium Risk";
                }else if (bmi > 34.9 && bmi <= 39.9){
                    BMIresult = "Severely Obese";
                    healthtext = "High Risk";
                }else if (bmi > 39.9){
                    BMIresult = "Very Severely Obese";
                    healthtext = "Very High Risk";
                }
                DecimalFormat df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.CEILING);
                calculation = df.format(bmi) + " kg/m²";
                resulttext.setText(calculation);
                category.setText(BMIresult);
                healthrisk.setText(healthtext);

                String txt1 = weight.getText().toString();
                String txt2 = height.getText().toString();
                FileOutputStream fos1 = null;
                try {
                    fos1 = openFileOutput(FILE_NAME1, MODE_PRIVATE);
                    fos1.write(txt1.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fos1!=null){
                        try {
                            fos1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                FileOutputStream fos2 = null;
                try {
                    fos2 = openFileOutput(FILE_NAME2, MODE_PRIVATE);
                    fos2.write(txt2.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fos2!=null){
                        try {
                            fos2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHeight=height.getText().toString();
                getWeight=weight.getText().toString();
                if(getWeight.equals(""))
                {
                    Toast.makeText(Home.this, "PLEASE ENTER WEIGHT", Toast.LENGTH_SHORT).show();
                    //this.weight.setText("0");
                    return;
                }else
                if(getHeight.equals(""))
                {
                    Toast.makeText(Home.this, "PLEASE ENTER HEIGHT", Toast.LENGTH_SHORT).show();
                    //this.height.setText("0");
                    return;
                }
                text = "cm";
                u2.setText(text);
                S1 = weight.getText().toString();
                S2 = height.getText().toString();
                weightValue = Float.parseFloat(S1);
                heightValue = Float.parseFloat(S2) / 100;
                cancel = true;
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHeight=height.getText().toString();
                getWeight=weight.getText().toString();
                if(getWeight.equals(""))
                {
                    Toast.makeText(Home.this, "PLEASE ENTER WEIGHT", Toast.LENGTH_SHORT).show();
                    //this.weight.setText("0");
                    return;
                }else
                if(getHeight.equals(""))
                {
                    Toast.makeText(Home.this, "PLEASE ENTER HEIGHT", Toast.LENGTH_SHORT).show();
                    //this.height.setText("0");
                    return;
                }
                text = "m";
                u2.setText(text);
                S1 = weight.getText().toString();
                S2 = height.getText().toString();
                weightValue = Float.parseFloat(S1);
                heightValue = Float.parseFloat(S2);
                cancel = true;
            }
        });
    }



    public void converterCM(View view){

    }
    public void converterM(View view){

    }

    public void calcuResult(View view) {


    }


}