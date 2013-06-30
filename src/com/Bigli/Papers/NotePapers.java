package com.Bigli.Papers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.Bigli.Papers.Data.DataPapers;
import com.Bigli.Papers.DataBase.dbCatsItem;

/**
 * By Bigli
 */
public class NotePapers extends Activity {


    TextView note_text, note_title;
    ImageView back_note;
    DataPapers dataPapers;
    dbCatsItem dbCatsItem;
    Context context;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.note_papers);
        context = this;
        note_text = (TextView) findViewById(R.id.note_text);
        note_title = (TextView) findViewById(R.id.note_title);
        back_note = (ImageView) findViewById(R.id.back_note);
        dataPapers = (DataPapers) getIntent().getSerializableExtra("DataPapers");

        dbCatsItem = new dbCatsItem(context);

        note_text.setText(dataPapers.get_text());
        note_title.setText(dataPapers.get_name());

        note_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //key
                Log.d("", "event = " + i);
                String text = String.valueOf(note_text.getText());
                dataPapers.set_text(text);
                dbCatsItem.updatePapers(dataPapers);
                return false;
            }
        });

        back_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //back
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        //close base
        dbCatsItem.close();
        setResult(Activity.RESULT_OK);
        finish();
    }
}
