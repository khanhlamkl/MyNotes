package com.khanhlam.mynotes.Model;

public class Notes {

    private int IdNotes;
    private String Title, Content, DateTime;

    public int getIdNotes() {
        return IdNotes;
    }

    public void setIdNotes(int idNotes) {
        IdNotes = idNotes;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

}
