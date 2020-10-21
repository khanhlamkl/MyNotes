package com.khanhlam.mynotes.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.khanhlam.mynotes.DAO.NotesDAO;
import com.khanhlam.mynotes.Model.Notes;
import com.khanhlam.mynotes.R;

import java.text.SimpleDateFormat;

public class CreateNotesActivity extends AppCompatActivity {

    Toolbar toolBarCreate;
    EditText edtTitleCreate, edtContentCreate;
    NotesDAO notesDAO;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);
        init();
        CreateToolbar();
    }

    private void init() {
        toolBarCreate = findViewById(R.id.toolBarCreate);
        edtTitleCreate = findViewById(R.id.edtTitleCreate);
        edtContentCreate = findViewById(R.id.edtContentCreate);

        notesDAO = new NotesDAO(this);
    }

    private void CreateToolbar() {
        setSupportActionBar(toolBarCreate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.effect_activity_in, R.anim.effect_activity_out);
                break;
            case R.id.itemCheckCreate:
                CreateCheck();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CreateCheck() {
        String sTitle = edtTitleCreate.getText().toString().trim();
        String sContent = edtContentCreate.getText().toString().trim();

        if (TextUtils.isEmpty(sTitle)) {
            Toast.makeText(this, getResources().getString(R.string.NhapTieuDe), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(sContent)) {
            Toast.makeText(this, getResources().getString(R.string.NhapNoiDung), Toast.LENGTH_SHORT).show();
        } else {

            s = System.currentTimeMillis() + "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String sDateTime = simpleDateFormat.format(System.currentTimeMillis());

            if (sTitle != null && sContent != null) {
                boolean kiemtra = notesDAO.AddNotes(sTitle, sContent, sDateTime);
                Intent intent = new Intent();
                intent.putExtra("ketqua", kiemtra);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        }
    }
}
