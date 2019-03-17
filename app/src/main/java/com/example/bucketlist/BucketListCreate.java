package com.example.bucketlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BucketListCreate extends AppCompatActivity {
    EditText inputTitle;
    EditText inputDescription;
    Button addButton;
    Intent intent;
    static String nTitle;
    static String nDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_list_create);
        inputTitle = findViewById(R.id.inputTitle);
        inputDescription = findViewById(R.id.inputDescription);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(BucketListCreate.this, MainActivity.class);
                String title = inputTitle.getText().toString();
                String description = inputDescription.getText().toString();
                intent.putExtra(nTitle,title);
                intent.putExtra(nDescription,description);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
