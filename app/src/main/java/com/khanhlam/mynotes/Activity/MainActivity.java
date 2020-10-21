package com.khanhlam.mynotes.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.khanhlam.mynotes.Adapter.AdapterNotes;
import com.khanhlam.mynotes.DAO.NotesDAO;
import com.khanhlam.mynotes.Model.Notes;
import com.khanhlam.mynotes.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int RESQUEST_CODE = 111;
    private static int RESQUEST_CODE_DETAIL = 123;

    Toolbar toolBarMain;
    ListView listView;
    FloatingActionButton fabButton;
    List<Notes> notesList;
    NotesDAO notesDAO;
    AdapterNotes adapterNotes;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        toolBarMain = findViewById(R.id.toolBarMain);
        listView = findViewById(R.id.listView);
        fabButton = findViewById(R.id.fabButton);

        setSupportActionBar(toolBarMain);
        notesDAO = new NotesDAO(this);

        ShowNotesList();

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCreate = new Intent(MainActivity.this, CreateNotesActivity.class);
                startActivityForResult(intentCreate, RESQUEST_CODE);
                overridePendingTransition(R.anim.effect_activity_in, R.anim.effect_activity_out);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentDetail = new Intent(MainActivity.this, DetailNotesActivity.class);
                intentDetail.putExtra("id", notesList.get(i).getIdNotes());
                startActivityForResult(intentDetail, RESQUEST_CODE_DETAIL);
                overridePendingTransition(R.anim.effect_activity_in, R.anim.effect_activity_out);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                id = notesList.get(i).getIdNotes();
                ShowDialog();
                return true;
            }

            private void ShowDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_alertdialog, viewGroup, false);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button btnOk, btnNo;
                btnOk = alertDialog.findViewById(R.id.btnOk);
                btnNo = alertDialog.findViewById(R.id.btnNo);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        boolean kiemtra = notesDAO.DeleteNotes(id);
                        if (kiemtra) {
                            ShowNotesList();
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.XoaThanhCong), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.XoaThatBai), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.ChuaXoa), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void ShowNotesList() {
        notesList = notesDAO.AllNotes();
        adapterNotes = new AdapterNotes(this, R.layout.custom_layout_row_notes, notesList);
        listView.setAdapter(adapterNotes);
        adapterNotes.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.itemSearchMain);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.TimKiem));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adapterNotes.Filter("");
                    listView.clearTextFilter();
                } else {
                    adapterNotes.Filter(newText);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemSearchMain) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("ketqua", false);
                if (kiemtra) {
                    overridePendingTransition(R.anim.effect_activity_in, R.anim.effect_activity_out);
                    ShowNotesList();
                    Toast.makeText(this, getResources().getString(R.string.ThemThanhCong), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.ThemThatBai), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == RESQUEST_CODE_DETAIL) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent1 = data;
                boolean kiemtracapnhat = intent1.getBooleanExtra("capnhat", false);
                if (kiemtracapnhat) {
                    overridePendingTransition(R.anim.effect_activity_in, R.anim.effect_activity_out);
                    ShowNotesList();
                    Toast.makeText(this, getResources().getString(R.string.CapNhatThanhCong), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.CapNhatThatBai), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
