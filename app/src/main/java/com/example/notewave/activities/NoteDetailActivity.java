package com.example.notewave.activities;

// NoteDetailActivity.java
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notewave.utils.DatabaseHelper;
import com.example.notewave.R;

public class NoteDetailActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView dateTextView;
    private TextView bodyTextView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        titleTextView = findViewById(R.id.titleTextView);
        dateTextView = findViewById(R.id.dateTextView);
        bodyTextView = findViewById(R.id.bodyTextView);
        databaseHelper = new DatabaseHelper(this);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            String selectedTitle = intent.getStringExtra("selectedTitle");
            if (selectedTitle != null) {
                loadNoteDetails(selectedTitle);
            }
        }
    }

    private void loadNoteDetails(String selectedTitle) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_TITLE,
                DatabaseHelper.COLUMN_DATE,
                DatabaseHelper.COLUMN_BODY
        };
        String selection = DatabaseHelper.COLUMN_TITLE + " = ?";
        String[] selectionArgs = {selectedTitle};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NOTES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
            String body = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BODY));

            System.out.println(title);
            titleTextView.setText(title);
            dateTextView.setText(date);
            bodyTextView.setText(body);
        }

        cursor.close();
        db.close();
    }
}
