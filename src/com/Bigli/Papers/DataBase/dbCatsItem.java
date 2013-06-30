package com.Bigli.Papers.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDiskIOException;
import com.Bigli.Papers.Data.DataCats;
import com.Bigli.Papers.Data.DataPapers;

import java.util.ArrayList;

/**
 * By Bigli
 */
public class dbCatsItem {

    private static final String DATABASE_NAME = "papers_db.sqlite";//Имя базы, в нашем случаи papers_db.sqlite
    private static final String TABLE = "Papers";//Таблица, у нас их 2, это papers - записи и cats - категории.

    private final String _id = "_id";
    private final String Paper_name = "name";
    private final String Paper_Text = "text";
    private final String Paper_Time = "time";
    private final String Paper_image = "image";
    private final String Cats_id = "cats_id";
    private final String Recall_time = "recall_time";


    private final int Column_id = 0;
    private final int Column_name = 1;
    private final int Column_text = 2;
    private final int Column_time = 3;
    private final int Column_image = 4;
    private final int Column_catsId = 5;
    private final int Column_recallTime = 6;

    SQLiteDatabase db;
    public dbCatsItem(Context context)
    {
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(context, DATABASE_NAME);
        db = dbOpenHelper.openDataBase();
    }

    public ArrayList<DataPapers> getPapers(long _CatsId)
    {
        ArrayList<DataPapers> arrayPapers = new ArrayList<DataPapers>();
        Cursor p_Cursor = db.query(TABLE, null,Cats_id + " = ?", new String[] {String.valueOf(_CatsId)},null,null,_id);
        p_Cursor.moveToFirst();
        if(!p_Cursor.isAfterLast())
        {
            do {
                long _id = p_Cursor.getLong(Column_id);
                String name = p_Cursor.getString(Column_name);
                String text = p_Cursor.getString(Column_text);
                String time = p_Cursor.getString(Column_time);
                String image = p_Cursor.getString(Column_image);
                long cats_id = p_Cursor.getLong(Column_catsId);
                int recall_time = p_Cursor.getInt(Column_recallTime);
                arrayPapers.add(new DataPapers(_id,name,text,time,image,cats_id,recall_time));
            }
            while (p_Cursor.moveToNext());
        }
        p_Cursor.close();
        return arrayPapers;
    }

    public long insertPapers(DataPapers dataPapers)
    {
        //Добавляем категорию.
        ContentValues papersValue =new ContentValues();
        papersValue.put("name", dataPapers.get_name());
        papersValue.put("text", dataPapers.get_text());
        papersValue.put("time", dataPapers.get_time());
        papersValue.put("cats_id", dataPapers.get_catsId());
        papersValue.put("recall_time", dataPapers.get_recallTime());
        return db.insert(TABLE, null, papersValue);
    }

    public int updatePapers(DataPapers dataPapers)
    {
        //Редактируем категорию.
        ContentValues papersValue =new ContentValues();
        papersValue.put("name", dataPapers.get_name());
        papersValue.put("text", dataPapers.get_text());
        papersValue.put("time", dataPapers.get_time());
        papersValue.put("cats_id", dataPapers.get_catsId());
        papersValue.put("recall_time", dataPapers.get_recallTime());
        return db.update(TABLE, papersValue, _id + " = ?", new String[]{String.valueOf(dataPapers.get_id())});
    }


    public void delete(long _id)
    {
        db.delete(TABLE, _id + " = ?", new String[]{String.valueOf(_id)});
    }

    public void close()
    {
        db.close();
    }

}
