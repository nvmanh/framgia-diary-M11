package com.framgia.diary.model;

import java.io.Serializable;

import com.orm.SugarRecord;

public class Diary extends SugarRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    public long created_date;
    public long update_date;
    public String content;
    public boolean deleted;

    public Diary() {
        super();
    }

    public Diary(String content) {
        this.content = content;
    }
}
