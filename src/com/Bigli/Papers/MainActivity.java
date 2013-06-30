package com.Bigli.Papers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.Bigli.Papers.Adapter.adapterCats;
import com.Bigli.Papers.Adapter.adapterGallery;
import com.Bigli.Papers.Data.DataCats;
import com.Bigli.Papers.DataBase.dbCats;

public class MainActivity extends Activity {
    /**
     * By Bigli
     */

    ListView cats_list; //Список, listview - это список, для наших записей.
    adapterCats adapterCats; //Наш адаптер.
    dbCats dbCats; //База.
    Context context;
    ImageView cats_add;
    Resources res;
    //Диалог.
    TextView cats_title;
    TextView dialog_title;
    Gallery dialog_gallery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Удаляем шапку, она как тян, не нужна.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Подключаем из res/layout main.xml - это наш дизайн, там хроняться все наши элементы.
        setContentView(R.layout.main);
        context = this;
        res = getResources();
        //Указываем что cats_list это Listview из main.xml, с id cats_list.
        cats_list = (ListView) findViewById(R.id.cats_list);
        cats_add = (ImageView) findViewById(R.id.add_cats);
        dbCats = new dbCats(context);
        adapterCats = new adapterCats(context, dbCats.getCats());
        cats_list.setAdapter(adapterCats);


        cats_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Событие при 1 нажатии на item из cats_list.
                Intent Papers = new Intent(context, PapersActivity.class);
                Papers.putExtra("DataCats", adapterCats.getItem(position));
                startActivity(Papers);
            }
        });

        cats_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                //Событие при долгом нажатии на item из cats_list.
                AlertDialog.Builder menu_dialog = new AlertDialog.Builder(context);
                menu_dialog.setTitle(res.getString(R.string.select_action));
                menu_dialog.setItems(res.getStringArray(R.array.menu_Cats), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        switch (item)
                        {
                            case 0:
                                //Удалить.
                                delete_cats(position);
                                break;
                            case 1:
                                //Редактировать.
                                DataCats _dataCats = adapterCats.getItem(position);
                                edite_cats(_dataCats);
                                break;
                        }
                    }
                });
                menu_dialog.show();
                return false;
            }
        });

        cats_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_cats();
            }
        });
    }

    private void delete_cats(int position)
    {
        //Удаление
        final long _idCats = adapterCats.getItemId(position);
        String selectCat = adapterCats.getItem(position).get_name();
        AlertDialog.Builder delete_dialog = new AlertDialog.Builder(context);
        delete_dialog.setTitle(String.format(res.getString(R.string.delete_msg), selectCat));
        delete_dialog.setPositiveButton(res.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //delete
                dbCats.deleteCats(_idCats);
                update_catsList();
            }
        });
        delete_dialog.setNegativeButton(res.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        delete_dialog.show();
    }

    private void edite_cats(final DataCats _dataCats)
    {
        //Редактирование
        final int[] gallery_icon = {R.drawable.star_64, R.drawable.folder_64, R.drawable.info_64};
        final String[] gallery_string = new String[] {"star_64", "folder_64", "info_64"};
        adapterGallery adapterGallery = new adapterGallery(context,gallery_icon);
        final Dialog dialog_cats = new Dialog(context);
        dialog_cats.setContentView(R.layout.dialog_add);
        dialog_cats.setTitle(res.getString(R.string.add_cats));

        cats_title = (TextView) dialog_cats.findViewById(R.id.add_dialog_title);
        dialog_title = (TextView) dialog_cats.findViewById(R.id.lable_add_dialog);
        dialog_gallery =(Gallery) dialog_cats.findViewById(R.id.dialog_gallery);
        Button ok = (Button) dialog_cats.findViewById(R.id.okdialog_button);
        Button cancel = (Button) dialog_cats.findViewById(R.id.canceldialog_button);

        //Выбираем позицию иконки
        int position_image = 0;
        for(int i=0; i<gallery_string.length;i++)
            if(_dataCats.get_image() == gallery_string[i])
            {
                position_image = i;
                break;
            }

        dialog_gallery.setAdapter(adapterGallery);
        dialog_title.setText(res.getString(R.string.cats_add_label));
        cats_title.setText(_dataCats.get_name());


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name = String.valueOf(cats_title.getText());
                String icon = gallery_string[dialog_gallery.getSelectedItemPosition()];
                DataCats dc = new DataCats(_dataCats.get_id(),name,_dataCats.get_papers_count(),icon);
                dbCats.updateCats(dc);
                update_catsList();
                dialog_cats.cancel();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_cats.cancel();
            }
        });

        dialog_cats.getWindow().setLayout(res.getInteger(R.integer.width_dialog),res.getInteger(R.integer.height_dialog));
        dialog_cats.show();
        dialog_gallery.setSelection(position_image);

    }

    private void add_cats()
    {
        //Добавление
        final int[] gallery_icon = {R.drawable.star_64, R.drawable.folder_64, R.drawable.info_64};
        final String[] gallery_string = new String[] {"star_64", "folder_64", "info_64"};
        adapterGallery adapterGallery = new adapterGallery(context,gallery_icon);
        final Dialog dialog_cats = new Dialog(context);
        dialog_cats.setContentView(R.layout.dialog_add);
        dialog_cats.setTitle(res.getString(R.string.add_cats));

        cats_title = (TextView) dialog_cats.findViewById(R.id.add_dialog_title);
        dialog_title = (TextView) dialog_cats.findViewById(R.id.lable_add_dialog);
        dialog_gallery =(Gallery) dialog_cats.findViewById(R.id.dialog_gallery);
        Button ok = (Button) dialog_cats.findViewById(R.id.okdialog_button);
        Button cancel = (Button) dialog_cats.findViewById(R.id.canceldialog_button);

        dialog_gallery.setAdapter(adapterGallery);
        dialog_title.setText(res.getString(R.string.cats_add_label));
        cats_title.setText(res.getString(R.string.cats_add_title));

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name = String.valueOf(cats_title.getText());
                String icon = gallery_string[dialog_gallery.getSelectedItemPosition()];
                DataCats dc = new DataCats(-1,name,0,icon);
                dbCats.insertCats(dc);
                update_catsList();
                dialog_cats.cancel();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_cats.cancel();
            }
        });

        dialog_cats.getWindow().setLayout(res.getInteger(R.integer.width_dialog),res.getInteger(R.integer.height_dialog));
        dialog_cats.show();
        dialog_gallery.setSelection(gallery_icon.length / 2);
    }

    private void update_catsList()
    {
        adapterCats.notifyDataSetChanged();
        adapterCats = new adapterCats(context, dbCats.getCats());
        cats_list.setAdapter(adapterCats);
    }
}
