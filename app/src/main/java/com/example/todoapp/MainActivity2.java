package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.List;

public class MainActivity2 extends AppCompatActivity{

    private EditText topLabel2, createList;
    private Button save;
    MySQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        topLabel2=findViewById(R.id.editLabel2);
        createList=findViewById(R.id.insertList);
        save=(Button)findViewById(R.id.saveButton);



        Intent intent = getIntent();
        String b = intent.getStringExtra("id");
        String s = intent.getStringExtra("editListText");
        String d = intent.getStringExtra("list item");
        topLabel2.setText(s);
        createList.setText(d);


    }
    public void onClickSave(View view){

//        topLabel2=findViewById(R.id.editLabel2);
//        createList=findViewById(R.id.insertList);
        Intent intent = getIntent();
        String s = intent.getStringExtra("editListText");
        String a = intent.getStringExtra("id");

        if(s.equals("editListText")){
//            topLabel2.getText().toString();

            dbHelper.updateList(new MyList(a, topLabel2.getText().toString(), createList.getText().toString()));
            Intent intent2 = new Intent();
            setResult(RESULT_OK, intent2);
            finish();

        }
        else {


            String name = topLabel2.getText().toString();
            String newList = createList.getText().toString();
            Intent intent1 = new Intent();
            intent.putExtra("the_name", name);
            intent.putExtra("new_list", newList);
            setResult(RESULT_OK, intent1);
            finish();
        }

    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}