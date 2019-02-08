package com.gp.nfctagmanager;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tag_table")
public class Tag {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String tagCode;

    public Tag(String name, String tagCode) {
        this.name = name;
        this.tagCode = tagCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTagCode() {
        return tagCode;
    }
}
