package edu.udacity.android.contentfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import edu.udacity.android.contentfinder.model.Keyword;
import edu.udacity.android.contentfinder.task.db.SaveKeywordTask;
import edu.udacity.android.contentfinder.util.StringUtils;

public class AddKeywordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keyword);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView keywordView = (TextView) findViewById(R.id.new_keyword);
                String str = keywordView.getText().toString();
                
                if (StringUtils.isBlank(str)) {
                    Toast.makeText(AddKeywordActivity.this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
                    return;
                }

                Keyword keyword = new Keyword();
                keyword.setWord(str);
                keyword.setCreatedDate(new Date());
                SaveKeywordTask task = new SaveKeywordTask(AddKeywordActivity.this, keyword);
                task.execute();
            }
        });
    }
}
