package com.example.todoapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.util.List;

public class MainActivity3 extends AppCompatActivity{

    private EditText id, topLabel3, editList;
    private Button save1;
    MySQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        topLabel3=findViewById(R.id.editLabel3);
        editList=findViewById(R.id.insertList2);
        save1=(Button)findViewById(R.id.saveButton1);


        //save.setOnClickListener((View.OnClickListener) this);
        Intent intent = getIntent();
           String result = intent.getStringExtra("editListText");


    showMessage("mesage", "HEy");
            MyList list = dbHelper.findList(result);
            String id = list.get_id();
            topLabel3.setText(list.getName());
            editList.setText(list.getLists());



        }



    public void onClickSave1(View view){


            dbHelper.updateList(new MyList(id.getText().toString(), topLabel3.getText().toString(), editList.getText().toString()));



//            Intent intent = new Intent();
//            //intent.putExtra("id", id);
//            intent.putExtra("the_name", name);
//            intent.putExtra("new_list", newList);
            setResult(RESULT_OK);
            finish();

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