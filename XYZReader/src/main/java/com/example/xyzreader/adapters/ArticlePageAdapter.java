package com.example.xyzreader.adapters;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.xyzreader.model.ListResponsData;
import com.example.xyzreader.ui.fragment.ArticleDFragment;

import java.util.List;

/**
 * Created by AdonisArifi on 21.6.2016 - 2016 . xyzreader
 */

public class ArticlePageAdapter extends FragmentStatePagerAdapter {
    private List<ListResponsData> listResponsDatas;
    private Fragment mCurrentFragment = null;

    public ArticlePageAdapter(FragmentManager fm, List<ListResponsData> list) {
        super(fm);
        listResponsDatas = list;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        ArticleDFragment fragment = (ArticleDFragment) object;

    }


    @Override
    public Fragment getItem(int position) {
        return ArticleDFragment.newInstance( listResponsDatas.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        super.destroyItem(container, position, object);
    }
    @Override
    public int getCount() {
        return (listResponsDatas != null) ? listResponsDatas.size() : 0;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }
}
