package com.moon.content_proviber_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name;
    EditText age;
    EditText phone;

    Button insertButton;
    Button updateButton;
    Button deleteButton;
    Button selectButton;

    TextView result;

    private final String CP_AUTHORITY = "com.ttt.database";
    private final Uri CONTENT_URI =
            Uri.parse("content://"+ CP_AUTHORITY + "/People");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        phone = findViewById(R.id.phone);
        insertButton = findViewById(R.id.insertButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        selectButton = findViewById(R.id.selectButton);
        result = findViewById(R.id.resultText);

        insertButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        selectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ContentValues values = new ContentValues();
        switch (view.getId()){
            case R.id.insertButton : // 삽입
                values.put("name", name.getText().toString());
                values.put("age", Integer.parseInt(age.getText().toString()));
                values.put("phone", phone.getText().toString());

                Uri uri = getContentResolver().insert(CONTENT_URI, values);

                Toast.makeText(this, "uri: " + uri, Toast.LENGTH_SHORT).show();
                break;
            case R.id.updateButton : // 수정
                values.put("name", name.getText().toString());
                values.put("age", age.getText().toString());
                values.put("phone", phone.getText().toString());

                int update = getContentResolver().update(CONTENT_URI, values, name.getText().toString(), null);

                Toast.makeText(this, "수정 수: " + update, Toast.LENGTH_SHORT).show();
                break;
            case R.id.deleteButton : // 삭제
                int delete = getContentResolver().delete(CONTENT_URI, name.getText().toString(), null);

                Toast.makeText(this, "삭제 수: " + delete, Toast.LENGTH_SHORT).show();
                break;
            case R.id.selectButton : // select
                String query = "";
                Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
                if (cursor.moveToFirst())
                {
                    do
                    {
                        System.out.println(cursor.getColumnCount());
                        query = query + cursor.getString(cursor.getColumnIndex("NAME")) + ", " +
                                cursor.getString(cursor.getColumnIndex("AGE")) + ", " +
                                cursor.getString(cursor.getColumnIndex("PHONE")) +
                                "\n";
                    }
                    while (cursor.moveToNext());
                }

                result.setText(query);
                break;
        }
    }
}