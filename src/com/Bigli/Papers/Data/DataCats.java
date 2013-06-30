package com.Bigli.Papers.Data;

import java.io.Serializable;

/**
 * By Bigli
 */
public class DataCats implements Serializable {
    long _id;
    String name;
    int papers_count;
    String image;

    public DataCats(long _id, String name, int papers_count, String image)
    {
        this._id = _id;
        this.name = name;
        this.papers_count = papers_count;
        this.image = image;
    }

    public long get_id(){return _id;}
    public String get_name(){return name;}
    public int get_papers_count(){return papers_count;}
    public String get_image(){return image;}
}

