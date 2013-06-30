package com.Bigli.Papers.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.Bigli.Papers.Data.DataCats;

import java.util.ArrayList;
import java.util.Currency;

/**
 *  By Bigli
 */
public class dbCats {

    private static final String DATABASE_NAME = "papers_db.sqlite";//Имя базы, в нашем случаи papers_db.sqlite
    private static final String TABLE = "Cats";//Таблица, у нас их 2, это papers - записи и cats - категории.

    //НАЗВАНИЕ из Cats
    private final String Cats_id = "_id"; //_id категории
    private final String Cats_name = "name"; //имя категории
    private final String Papers_count = "papers_count"; //кол-во записей в категории
    private final String Cats_Image = "image"; //Название иконки.
    //НОМЕРА СТОЛБЦОВ ОТ 0 ДО N
    private final int Column_id = 0;
    private final int Column_name = 1;
    private final int Column_papers_count = 2;
    private final int Column_image = 3;

    private SQLiteDatabase db; //Наша база.
    public dbCats(Context context)
    {
        //Открываем нашу базу.
        //С помощью нашего класса, указываем название базы.
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(context, DATABASE_NAME);
        db = dbOpenHelper.openDataBase();
    }

    public ArrayList<DataCats> getCats()
    {
        ArrayList<DataCats> arrayCats = new ArrayList<DataCats>();

        Cursor c_Cursor = db.query(TABLE, null,null,null,null,null,Cats_id);

        c_Cursor.moveToFirst(); //Встаем на 1 итем из нашего списка, если он есть.
        //Если нету итемов, то эта функция выполняться не будет.
        if(!c_Cursor.isAfterLast())
        {
            do {
                long _id = c_Cursor.getLong(Column_id);
                String _name = c_Cursor.getString(Column_name);
                int _papers_count = c_Cursor.getInt(Column_papers_count);
                String _image = c_Cursor.getString(Column_image);
                arrayCats.add(new DataCats(_id,_name,_papers_count, _image));
            }
            while (c_Cursor.moveToNext());
        }
        c_Cursor.close();
        return arrayCats;
    }

    public long insertCats(DataCats dataCats)
    {
        //Добавляем категорию.
        ContentValues catsValue =new ContentValues();
        catsValue.put(Cats_name, dataCats.get_name());
        catsValue.put(Papers_count, dataCats.get_papers_count());
        catsValue.put(Cats_Image, dataCats.get_image());
        return db.insert(TABLE, null, catsValue);
    }

    public int updateCats(DataCats dataCats)
    {
        //Обновляем категорию.
        ContentValues catsValue = new ContentValues();
        catsValue.put(Cats_name, dataCats.get_name());
        catsValue.put(Papers_count, dataCats.get_papers_count());
        catsValue.put(Cats_Image, dataCats.get_image());
        return db.update(TABLE, catsValue, Cats_id + " = ?", new String[] {String.valueOf(dataCats.get_id()) });
    }

    public void deleteCats(long _id)
    {
        db.delete(TABLE, Cats_id  + " = ?", new String[] { String.valueOf(_id)});
    }

    public void close()
    {
        db.close();
    }

}
