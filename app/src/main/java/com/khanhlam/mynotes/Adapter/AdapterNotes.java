package com.khanhlam.mynotes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.khanhlam.mynotes.Model.Notes;
import com.khanhlam.mynotes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterNotes extends BaseAdapter {

    private Context context;
    int layout;
    private List<Notes> notesList;
    private ArrayList<Notes> arrayList;
    ViewHolderNotes viewHolderNotes;

    public AdapterNotes(Context context, int layout, List<Notes> notesList) {
        this.context = context;
        this.layout = layout;
        this.notesList = notesList;
        this.arrayList = new ArrayList<Notes>();
        this.arrayList.addAll(notesList);
    }

    @Override
    public int getCount() {
        return notesList.size();
    }

    @Override
    public Object getItem(int i) {
        return notesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return notesList.get(i).getIdNotes();
    }


    public class ViewHolderNotes {
        TextView tvTitle, tvContent, tvDateTime;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_layout_row_notes, viewGroup, false);

            viewHolderNotes = new ViewHolderNotes();
            viewHolderNotes.tvTitle = view.findViewById(R.id.tvTitle);
            viewHolderNotes.tvContent = view.findViewById(R.id.tvContent);
            viewHolderNotes.tvDateTime = view.findViewById(R.id.tvDateTime);

            view.setTag(viewHolderNotes);
        } else {
            viewHolderNotes = (ViewHolderNotes) view.getTag();
        }

        Notes notes = notesList.get(i);
        viewHolderNotes.tvTitle.setText(notes.getTitle());
        viewHolderNotes.tvContent.setText(notes.getContent());
        viewHolderNotes.tvDateTime.setText(notes.getDateTime());

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.effect_listview_notes);
        view.startAnimation(animation);

        return view;
    }


    public void Filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        notesList.clear();
        if (charText.length() == 0) {
            notesList.addAll(arrayList);
        } else {
            for (Notes notes : arrayList) {
                if (notes.getTitle().toLowerCase(Locale.getDefault()).contains(charText)
                        || notes.getContent().toLowerCase(Locale.getDefault()).contains(charText)) {
                    notesList.add(notes);
                }
            }
        }
        notifyDataSetChanged();
    }
}
