package com.example.xyzreader.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyzreader.R;
import com.example.xyzreader.data.FirebaseDatabaseXyzReader;
import com.example.xyzreader.model.ListResponsDataAbout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    private List<ListResponsDataAbout> responsDataList = new ArrayList<>();
    @Bind(R.id.imageView_about)
    ImageView imageView_about;
    @Bind(R.id.textView_author_about)
    TextView textView_author_about;
    @Bind(R.id.textView_body_about)
    TextView textView_body_about;

    @Bind(R.id.textView_githublink_about)
    TextView textView_githublink_about;

    @Bind(R.id.toolbar_about)
    Toolbar toolbar_about;

    @Bind(R.id.collapsing_toolbar_about)
    CollapsingToolbarLayout collapsing_toolbar_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_about);
        ActionBar actionBar = getSupportActionBar();
        collapsing_toolbar_about.setTitle("");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar_about.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().abouteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (responsDataList != null) {

                    responsDataList.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ListResponsDataAbout post = postSnapshot.getValue(ListResponsDataAbout.class);
                    responsDataList.add(post);

                }

                updateView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("Error Firebase", databaseError.getMessage());
            }
        });

    }


    public void updateView() {

        Glide.with(this).load(responsDataList.get(0).getPhoto()).into(imageView_about);
        textView_author_about.setText(responsDataList.get(0).getDeveloper_name());
        textView_body_about.setText(responsDataList.get(0).getDescription());
        collapsing_toolbar_about.setTitle(responsDataList.get(0).getName_of_project());
        textView_githublink_about.setText(responsDataList.get(0).getGithub_link());
    }
}
