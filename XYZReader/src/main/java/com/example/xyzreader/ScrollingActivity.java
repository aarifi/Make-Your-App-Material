package com.example.xyzreader;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.xyzreader.api.ApiClient;
import com.example.xyzreader.model.ListResponsData;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ScrollingActivity extends AppCompatActivity {

    @Bind(R.id.anim_toolbar)
    Toolbar toolbar;

    @Bind(R.id.content_fragment_article_details)
    NestedScrollView content_fragment_article_details;

    @Bind(R.id.imageView_details)
    ImageView imageView_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
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
        ApiClient apiClient = new ApiClient();
        ApiClient.getApiClientInstance(this).getBooksApiInterfaceMethod().getData(new Callback<List<ListResponsData>>() {
            @Override
            public void success(List<ListResponsData> listResponsDatas, Response response) {
                for (ListResponsData l :
                        listResponsDatas) {
                    Log.d("TEST TEST", l.getThumb());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Retrofit Erro", error.getMessage());
            }
        });

        Glide.with(this).load("https://dl.dropboxusercontent.com/u/231329/xyzreader_data/images/p014.jpg")
                .into(imageView_details);
        //imageView_details.setAspectRatio(Float.parseFloat("0.66667"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

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

    private void hideViews() {
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

       /* FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
 */
    }

    private void showViews() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        // mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }
}
