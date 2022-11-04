package com.mhike.m_hike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.Helper;
import com.mhike.m_hike.classes.Hike;

import java.util.ArrayList;

public class EditHikeActivity extends AppCompatActivity {
    int hikeID;
    DatabaseHelper db;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);
        context = getApplicationContext();
        db = DatabaseHelper.getInstance(context);
        getIntentAndSetData();
    }

    private void getIntentAndSetData() {
        // check if book id exists
        if (getIntent().hasExtra("hikeID")) {
            hikeID = Integer.parseInt(getIntent().getStringExtra("hikeID"));

            Helper.longToastMessage(context, String.valueOf(hikeID));
//            ArrayList<Hike> hikeList = db.getOneBook(hikeID);
//            if (books.size() > 0) {
//                for (Book book : books) {
//                    title = book.getBookTitle();
//                    author = book.getAuthor();
//                    pages = String.valueOf(book.getPages());
//                }
//
//                txtBookTitle.setText(title);
//                txtAuthor.setText(author);
//                txtPages.setText(pages);
//                return;
//            }
//            Helper.toastMessage(context, "Error retrieving selected book");

        }
    }
}