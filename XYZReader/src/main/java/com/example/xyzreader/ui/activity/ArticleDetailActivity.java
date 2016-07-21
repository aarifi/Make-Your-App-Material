package com.example.xyzreader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.xyzreader.R;
import com.example.xyzreader.adapters.ArticlePageAdapter;
import com.example.xyzreader.model.ListResponsData;
import com.example.xyzreader.utils.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleDetailActivity extends AppCompatActivity {

    private ArticlePageAdapter articlePageAdapter;
    @Bind(R.id.viewpager_articledetails)
    ViewPager viewpager_articledetails;

    public static List<ListResponsData> articleList;

    @Bind(R.id.toolbar_article_detail)
    Toolbar toolbar;
    @Bind(R.id.appbar_activity_article_detail)
    AppBarLayout appbar_activity_article_detail;
    @Bind(R.id.fab_share)
    FloatingActionButton fab_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setUpViewPager();
    }


    private void setUpViewPager() {

        articleList = getIntent().getParcelableArrayListExtra("ListResponsData");
        articlePageAdapter = new ArticlePageAdapter(this.getSupportFragmentManager(), articleList);
        viewpager_articledetails.setAdapter(articlePageAdapter);
        viewpager_articledetails.setCurrentItem(Integer.parseInt(getIntent().getStringExtra(Constants.ARG_ITEM_ID_SELECTED)));
        articlePageAdapter.notifyDataSetChanged();
        articlePageAdapter.finishUpdate(viewpager_articledetails);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_d, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    private void createShareIntent(ListResponsData listResponsData) {
        final int SHARED_RESULT = 1;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, listResponsData.getBody());
        shareIntent.setType("image/*");
        shareIntent.putExtra(android.content.Intent.EXTRA_STREAM, listResponsData.getPhoto());

        startActivityForResult(Intent.createChooser(shareIntent, listResponsData.getTitle()), SHARED_RESULT);
    }

    @OnClick(R.id.fab_share)
    public void setOnClick_fab_share() {
        int position = viewpager_articledetails.getCurrentItem();
        createShareIntent(articleList.get(position));
    }
}
