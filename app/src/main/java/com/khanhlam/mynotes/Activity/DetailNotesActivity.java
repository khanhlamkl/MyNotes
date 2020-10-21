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

public class DetailNotesActivity extends AppCompatActivity {

    EditText edtTitleDetail, edtContentDetail;
    Toolbar toolBarDetail;
    NotesDAO notesDAO;
    Notes notes;
    String s;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notes);
        init();
        CreateToolbar();
    }

    private void init() {
        edtTitleDetail = findViewById(R.id.edtTitleDetail);
        edtContentDetail = findViewById(R.id.edtContentDetail);
        toolBarDetail = findViewById(R.id.toolBarDetail);

        notesDAO = new NotesDAO(this);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id", 0);
        }

        notes = notesDAO.GetListId(id);
        edtTitleDetail.setText(notes.getTitle());
        edtContentDetail.setText(notes.getContent());
    }

    private void CreateToolbar() {
        setSupportActionBar(toolBarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.effect_activity_in, R.anim.effect_activity_out);
                break;
            case R.id.itemSaveDetail:
                DetailNotes();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void DetailNotes() {
        String sTitle = edtTitleDetail.getText().toString().trim();
        String sContent = edtContentDetail.getText().toString().trim();

        if (TextUtils.isEmpty(sTitle)) {
            Toast.makeText(this, getResources().getString(R.string.NhapTieuDe), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(sContent)) {
            Toast.makeText(this, getResources().getString(R.string.NhapNoiDung), Toast.LENGTH_SHORT).show();
        } else {

            s = System.currentTimeMillis() + "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String sDateTime = simpleDateFormat.format(System.currentTimeMillis());

            if (sTitle != null && sContent != null) {
                boolean kiemtracapnhat = notesDAO.UpdateNotes(sTitle, sContent, sDateTime, id);
                Intent intent = new Intent();
                intent.putExtra("capnhat", kiemtracapnhat);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }
}
