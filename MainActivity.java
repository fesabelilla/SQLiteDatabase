package com.oneiro.mysqlitedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private  EditText nameEditeText, genderEditeText, ageEditeText,idEditeText;
    private Button  addButton,displayAllDataButton,updateButton,deleteButton;
    MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hiding the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //hiding the action bar
        getSupportActionBar().hide();

        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();

        nameEditeText = findViewById(R.id.nameEditTextID);
        genderEditeText = findViewById(R.id.genderEditTextID);
        ageEditeText = findViewById(R.id.ageEditTextID);
        idEditeText = findViewById(R.id.idEditTextID);
        addButton = findViewById(R.id.addButtonID);
        displayAllDataButton = findViewById(R.id.displayAllDataButtonID);
        updateButton = findViewById(R.id.updateButtonID);
        deleteButton = findViewById(R.id.deleteButtonID);


        addButton.setOnClickListener(this);
        displayAllDataButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = nameEditeText.getText().toString();
        String age = ageEditeText.getText().toString();
        String gender = genderEditeText.getText().toString();
        String id  = idEditeText.getText().toString();

        if(view.getId()==R.id.addButtonID){
           long rowID =  myDatabaseHelper.insertData(name,age,gender);

           if(rowID==-1){
               Toast.makeText(getApplicationContext(),"Unsucessfull ",Toast.LENGTH_LONG).show();
           }
           else {
               Toast.makeText(getApplicationContext(),"Row is "+rowID+" added.",Toast.LENGTH_LONG).show();
           }

        }
        if(view.getId()==R.id.displayAllDataButtonID){
          Cursor cursor = myDatabaseHelper .displayAllData();

          if(cursor.getCount()==0){
              //Toast.makeText(getApplicationContext(),"The table is empty",Toast.LENGTH_LONG).show();
              showData("Error ","No Data Found");
              return;
          }
          StringBuffer stringBuffer = new StringBuffer();
          while (cursor.moveToNext()){
              stringBuffer.append("ID : "+cursor.getString(0)+"\n");
              stringBuffer.append("Name : "+cursor.getString(1)+"\n");
              stringBuffer.append("Age : "+cursor.getString(2)+"\n");
              stringBuffer.append("Gender : "+cursor.getString(3)+"\n\n\n");

                        }
                        showData("ResultSet",stringBuffer.toString());
        }
        if(view.getId()==R.id.updateButtonID){
        Boolean isUpdated = myDatabaseHelper.updateData(id,name,age,gender);

        if(isUpdated == true){
            Toast.makeText(getApplicationContext(),"Update Completed ",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Not Updated ",Toast.LENGTH_LONG).show();
        }

        }

        if (view.getId()==R.id.deleteButtonID){
            int value = myDatabaseHelper.deleteData(id);

            if(value>0){
                Toast.makeText(getApplicationContext(),"Data is Deleted ",Toast.LENGTH_LONG).show();

            }else {

                Toast.makeText(getApplicationContext(),"Data is not Deleted ",Toast.LENGTH_LONG).show();

            }

        }

    }
  public void  showData(String title,String message){
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(title);
      builder.setMessage(message);
      builder.setCancelable(true);
      builder.show();

    }
}
