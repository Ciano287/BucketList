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
     static final String EXTRA_TITLE = "com.example.bucketlist.EXTRA_TITLE";
     static final String EXTRA_DESCRIPTION = "com.example.bucketlist.EXTRA_DESCRIPTION";

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
                intent.putExtra(EXTRA_TITLE,title);
                intent.putExtra(EXTRA_DESCRIPTION,description);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
