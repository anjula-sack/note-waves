package com.example.notewave.activities;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notewave.utils.DatabaseHelper;
import com.example.notewave.MainActivity;
import com.example.notewave.R;
import com.example.notewave.models.Note;

import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private EditText titleEditText;
    private EditText bodyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        databaseHelper = new DatabaseHelper(this);
        titleEditText = findViewById(R.id.titleEditText);
        bodyEditText = findViewById(R.id.bodyEditText);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String body = bodyEditText.getText().toString();

                if (!title.isEmpty() && !body.isEmpty()) {
                    Note newNote = new Note(title, getCurrentDate(), body);
                    long newRowId = databaseHelper.insertNote(newNote);

                    if (newRowId != -1) {
                        showSaveSuccessNotification();
                        navigateToMainActivity();
                    } else {
                        Toast.makeText(AddNoteActivity.this, "Note couldn't be saved.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddNoteActivity.this, "Title and body are required.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showSaveSuccessNotification() {
        Toast.makeText(this, "Note saved successfully!", Toast.LENGTH_SHORT).show();
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
