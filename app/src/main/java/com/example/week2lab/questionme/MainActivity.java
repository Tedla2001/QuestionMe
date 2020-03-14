package com.example.week2lab.questionme;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

        final TextView tv_answer = findViewById(R.id.flashcard_answer);
        final TextView tv_question = findViewById(R.id.flashcard_question);
        tv_answer.setVisibility(View.INVISIBLE);
        tv_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            tv_question.setVisibility(View.INVISIBLE);
            tv_answer.setVisibility(View.VISIBLE);
            }
        });
        tv_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            tv_answer.setVisibility(View.INVISIBLE);
            tv_question.setVisibility(View.VISIBLE);
            }
        });
        findViewById((R.id.plus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent,100);
            }
        });

        findViewById(R.id.arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        allFlashcards = flashcardDatabase.getAllCards();

        if (resultCode== RESULT_OK) {
            String string1=data.getStringExtra("string1");
            String string2=data.getStringExtra("string2");
            ((TextView) findViewById(R.id.flashcard_question)).setText(string1);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(string2);
            flashcardDatabase.insertCard(new Flashcard(string1, string2));

        }
    }

}

