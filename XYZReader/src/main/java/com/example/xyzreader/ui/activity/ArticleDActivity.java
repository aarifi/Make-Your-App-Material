package com.example.xyzreader.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.xyzreader.R;
import com.example.xyzreader.adapters.ArticlePageAdapter;
import com.example.xyzreader.model.ListResponsData;
import com.example.xyzreader.utils.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleDActivity extends AppCompatActivity {


    private ArticlePageAdapter articlePageAdapter;
    @Bind(R.id.viewpager_articledetails)
    ViewPager viewpager_articledetails;

    private List<ListResponsData> articleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_d);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        ButterKnife.bind(this);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }




/*
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_article_details, ArticleDFragment.newInstance("1")).commit();
*/

        setUpViewPager();
    }

    private void setUpViewPager() {

        articleList = getIntent().getParcelableArrayListExtra("ListResponsData");
        articlePageAdapter = new ArticlePageAdapter(getSupportFragmentManager(), articleList);
        viewpager_articledetails.setAdapter(articlePageAdapter);
        viewpager_articledetails.setCurrentItem(Integer.parseInt(getIntent().getStringExtra(Constants.ARG_ITEM_ID_SELECTED)));
        articlePageAdapter.notifyDataSetChanged();
        articlePageAdapter.finishUpdate(viewpager_articledetails);
    }


}
