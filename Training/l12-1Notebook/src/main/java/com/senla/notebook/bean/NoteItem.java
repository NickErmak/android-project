package com.senla.notebook.bean;

import java.util.Date;

public class NoteItem {

    private String title;
    private Long date;

    public NoteItem(String title, Long date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public Long getDate() {
        return date;
    }

}
