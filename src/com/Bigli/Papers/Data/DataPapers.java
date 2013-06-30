package com.Bigli.Papers.Data;

import java.io.Serializable;

/**
 * By Bigli
 */
public class DataPapers implements Serializable {
    long _id;
    String name;
    String text;
    String time;
    String image;
    long cats_id;
    int recall_time;

    public DataPapers(long _id, String name, String text, String time, String image, long cats_id, int recall_time)
    {
        this._id = _id;
        this.name = name;
        this.text = text;
        this.time = time;
        this.image = image;
        this.cats_id = cats_id;
        this.recall_time = recall_time;
    }

    public long get_id() {return _id;}
    public String get_name(){return name;}
    public String get_text(){return text;}
    public String get_time(){return time;}
    public String get_image(){return image;}
    public long get_catsId(){return cats_id;}
    public int get_recallTime(){return recall_time;}
    public void set_text(String text){this.text = text;}
}
