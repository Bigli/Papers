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
import com.Bigli.Papers.Adapter.adapterGallery;
import com.Bigli.Papers.Adapter.adapterPapers;
import com.Bigli.Papers.Data.DataCats;
import com.Bigli.Papers.Data.DataPapers;
import com.Bigli.Papers.DataBase.dbCatsItem;

/**
 * By Bigli
 */
public class PapersActivity extends Activity {

    Corrector corrector = new Corrector();
    long _idCats;
    dbCatsItem dbCatsItem;
    adapterPapers adapterPapers;
    Context context;
    ListView papers_list;
    ImageView add_papers;
    TextView title_papers;
    DataCats dataCats;
    Resources res; //Ресурсы

    //Запрос note_papers, редактирование текста.
    int Activity_code_result = 0;

    //Диалог.
    TextView papers_title;
    TextView dialog_title;
    Gallery dialog_gallery;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Удаляем шапку, она как тян, не нужна.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Подключаем из res/layout papers.xml - это наш дизайн, там хроняться все наши элементы.
        setContentView(R.layout.papers);
        res = getResources();
        context = this;
        papers_list = (ListView) findViewById(R.id.papers_list);
        add_papers = (ImageView) findViewById(R.id.add_papers);
        title_papers = (TextView) findViewById(R.id.Title_papers);

        dataCats = (DataCats) getIntent().getSerializableExtra("DataCats");
        title_papers.setText(dataCats.get_name());
        _idCats = dataCats.get_id();


        dbCatsItem = new dbCatsItem(context);
        adapterPapers = new adapterPapers(context, dbCatsItem.getPapers(dataCats.get_id()));
        papers_list.setAdapter(adapterPapers);

        add_papers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_papers();
            }
        });

        papers_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                //Меню.
                AlertDialog.Builder menu_dialog = new AlertDialog.Builder(context);
                menu_dialog.setTitle(res.getString(R.string.select_action));
                menu_dialog.setItems(res.getStringArray(R.array.menu_Papers), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        switch (item)
                        {
                            case 0:
                                //Удалить.
                                delete_papers(position);
                                break;
                            case 1:
                                //Редактировать.
                                DataPapers _dataPapers = adapterPapers.getItem(position);
                                edite_cats(_dataPapers);
                                break;
                        }
                    }
                });
                menu_dialog.show();
                return false;
            }
        });

        papers_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int position, long l) {
                //note_papers activity open
                DataPapers _dataPapers = adapterPapers.getItem(position);
                Intent note_papers = new Intent(context, NotePapers.class);
                note_papers.putExtra("DataPapers", _dataPapers);
                startActivityForResult(note_papers, Activity_code_result);
            }
        });


    }

    private void add_papers()
    {
        //Добавление
        final int[] gallery_icon = {R.drawable.star_64, R.drawable.folder_64, R.drawable.info_64};
        final String[] gallery_string = new String[] {"star_64", "folder_64", "info_64"};
        adapterGallery adapterGallery = new adapterGallery(context,gallery_icon);
        final Dialog dialog_cats = new Dialog(context);
        dialog_cats.setContentView(R.layout.dialog_add);
        dialog_cats.setTitle(res.getString(R.string.add_papers));

        papers_title = (TextView) dialog_cats.findViewById(R.id.add_dialog_title);
        dialog_title = (TextView) dialog_cats.findViewById(R.id.lable_add_dialog);
        dialog_gallery =(Gallery) dialog_cats.findViewById(R.id.dialog_gallery);
        Button ok = (Button) dialog_cats.findViewById(R.id.okdialog_button);
        Button cancel = (Button) dialog_cats.findViewById(R.id.canceldialog_button);

        dialog_gallery.setAdapter(adapterGallery);
        dialog_title.setText(res.getString(R.string.papers_add_label));
        papers_title.setText(res.getString(R.string.papers_add_title));

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name = String.valueOf(papers_title.getText());
                String icon = gallery_string[dialog_gallery.getSelectedItemPosition()];
                String text = "Ваш текст.";
                String todaytime = corrector.getDate();
                //0 - будильник, напоминание.
                DataPapers dp = new DataPapers(-1,name,text,todaytime,icon,_idCats,0);
                dbCatsItem.insertPapers(dp);
                update_papersList();
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

    private void edite_cats(final DataPapers _dataPapers)
    {
        //Редактирование
        final int[] gallery_icon = {R.drawable.star_64, R.drawable.folder_64, R.drawable.info_64};
        final String[] gallery_string = new String[] {"star_64", "folder_64", "info_64"};
        adapterGallery adapterGallery = new adapterGallery(context,gallery_icon);
        final Dialog dialog_cats = new Dialog(context);
        dialog_cats.setContentView(R.layout.dialog_add);
        dialog_cats.setTitle(res.getString(R.string.add_papers));

        papers_title = (TextView) dialog_cats.findViewById(R.id.add_dialog_title);
        dialog_title = (TextView) dialog_cats.findViewById(R.id.lable_add_dialog);
        dialog_gallery =(Gallery) dialog_cats.findViewById(R.id.dialog_gallery);
        Button ok = (Button) dialog_cats.findViewById(R.id.okdialog_button);
        Button cancel = (Button) dialog_cats.findViewById(R.id.canceldialog_button);

        dialog_gallery.setAdapter(adapterGallery);
        dialog_title.setText(res.getString(R.string.papers_add_label));
        papers_title.setText(_dataPapers.get_name());

        //Выбираем позицию иконки
        int position_image = 0;
        for (int i=0;i<gallery_string.length;i++)
            if(_dataPapers.get_image() == gallery_string[i])
            {
                position_image = i;
                break;
            }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name = String.valueOf(papers_title.getText());
                String icon = gallery_string[dialog_gallery.getSelectedItemPosition()];
                String todaytime = corrector.getDate();
                //0 - будильник, напоминание.
                DataPapers dp = new DataPapers(_dataPapers.get_id(),name,_dataPapers.get_text(),todaytime,icon,_idCats,0);
                dbCatsItem.updatePapers(dp);
                update_papersList();
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


    private void delete_papers(int position)
    {
        //Удаление
        final long _idCats = adapterPapers.getItemId(position);
        String selectPapers = adapterPapers.getItem(position).get_name();
        AlertDialog.Builder delete_dialog = new AlertDialog.Builder(context);
        delete_dialog.setTitle(String.format(res.getString(R.string.delete_msg), selectPapers));
        delete_dialog.setPositiveButton(res.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //delete
                dbCatsItem.delete(_idCats);
                update_papersList();
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

    private void update_papersList()
    {
        adapterPapers.notifyDataSetChanged();
        adapterPapers = new adapterPapers(context,dbCatsItem.getPapers(_idCats));
        papers_list.setAdapter(adapterPapers);
    }

    @Override
    public void onBackPressed()
    {
        dbCatsItem.close();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity_code_result) {
            if (resultCode == RESULT_OK) {
                update_papersList();
            }
        }
    }

}
