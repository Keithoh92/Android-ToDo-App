package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements onClickListener{

    private static final int REQ_CODE = 1234;
    private static final int REQ_CODE_EDIT = 4321;

    TextView topLabel;
    Button create, edit;
    MySQLiteHelper dbHelper;
    ArrayList<String> listArray;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MySQLiteHelper(this);
        listArray = new ArrayList<>();

        topLabel = (TextView) findViewById(R.id.label);
        create = (Button) findViewById(R.id.createButton);
        edit = (Button) findViewById(R.id.editButton);
        listView = (ListView) findViewById(R.id.todo_list);
        //edit.setOnClickListener((View.OnClickListener) this);

//        dbHelper.addList(new MyList(newName, newList));
//        dbHelper.deleteList();


        viewData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        String text = listView.getItemAtPosition(position).toString();
                        viewList(text);

                        edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //********This is where I left off
                                //********Need to get the values of the list selected and pass them to activity 2 when the edit is clicked
                                //********At the moment its getting the name of the list and then the name of the second list.
                                        String s = topLabel.getText().toString();
                                        String a = listArray.get(0);
                                        String d = listArray.get(2);
                                        Intent editIntent = new Intent(MainActivity.this, MainActivity2.class);
                                editIntent.putExtra("id", a);

                                editIntent.putExtra("editListText", s);
                                editIntent.putExtra("list item", d);
                                editIntent.putExtra("requestCodeEdit", REQ_CODE_EDIT);


                                startActivityForResult(editIntent, REQ_CODE_EDIT);

                            }
                        });

                    }
        });

    }

    public void viewData(){
        Cursor cursor = dbHelper.viewData();
        if(cursor.getCount()==0){
            showMessage("Error", "No records");
        }else{
            while(cursor.moveToNext()){
                listArray.add(cursor.getString(1));
                //listArray.add(cursor.getString(2));
            }
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listArray);
            listView.setAdapter(myAdapter);

        }
    }
//    public void viewEditList(String name){
//        ArrayList<String> listArray3 = new ArrayList<>();
//        Cursor cursor = dbHelper.viewList(name);
//        if(cursor.getCount()==0){
//            showMessage("Error", "No records");
//        }else{
//            while(cursor.moveToNext()){
//                String a = cursor.getString(1);
//                String b = cursor.getString(2);
//            }
//            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listArray2);
//            listView.setAdapter(myAdapter);
//        }
//    }

    public void viewList(String name){
        ArrayList<String> listArray2 = new ArrayList<>();
        Cursor cursor = dbHelper.viewList(name);
        if(cursor.getCount()==0){
            showMessage("Error", "No records");
        }else{
            while(cursor.moveToNext()){
                topLabel.setText(cursor.getString(1));
                listArray2.add(cursor.getString(2));
            }
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listArray2);
                listView.setAdapter(myAdapter);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        viewData();

    }

    public void onPause() {
        super.onPause();
        Log.d("testing", "onPause got called");
    }

    public void onResume() {
        super.onResume();
        Log.d("testing", "onResume got called");

    }

    public void onStart() {
        super.onStart();
        Log.d("testing", "onStart got called");
    }

    public void onStop() {
        super.onStop();
        Log.d("testing", "onStop got called");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d("testing", "onDestroy got called");
    }

    //Create Button clicked activate second activity
    public void onClickCreate(View view){
        Intent createIntent = new Intent(this, MainActivity2.class);
        createIntent.putExtra("requestCode", REQ_CODE);

        if(createIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(createIntent, REQ_CODE);
        }
    }

//    public void editClick(View view){
//        String s = topLabel.getText().toString();
//        Intent editIntent = new Intent(this, MainActivity3.class);
//        editIntent.putExtra("editListText", s);
//        if(editIntent.resolveActivity(getPackageManager()) != null) {
//
//            startActivityForResult(editIntent, REQ_CODE_EDIT);
//        }
//        else{
//            showMessage("Error" , "No activity");
//        }
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            String newName = intent.getStringExtra("the_name");
            String newList = intent.getStringExtra("new_list");

            dbHelper.addList(new MyList(newName, newList));
            listArray.clear();

            viewData();
        } else if (requestCode == REQ_CODE_EDIT && resultCode == RESULT_OK) {
            listArray.clear();

            viewData();
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