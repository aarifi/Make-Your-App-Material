package com.example.xyzreader.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyzreader.R;
import com.example.xyzreader.ScrollingActivity;
import com.example.xyzreader.model.ListResponsData;
import com.example.xyzreader.utils.ImageViewFit;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AdonisArifi on 2.6.2016 - 2016 . xyzreader
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context myContext;
    private List<ListResponsData> listResponsDatas;

    public ArticleAdapter(List<ListResponsData> listResponsDatas, Context myContext) {
        this.myContext = myContext;
        this.listResponsDatas = listResponsDatas;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return listResponsDatas == null ? 0 : listResponsDatas.size();
    }


    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.article_list_item
                , parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, int position) {
        try {
            ListResponsData responsData = listResponsDatas.get(position);
            if (responsData != null) {
                Glide.with(myContext).load(responsData.getPhoto())
                        .into(holder.imageView_list_item);
                holder.textView_article_title.setText(responsData.getTitle());
                holder.textView_article_subtitle.setText(responsData.getAuthor());
                holder.setClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(myContext, ScrollingActivity.class);
                        myContext.startActivity(intent);
                    }
                });
               holder.imageView_list_item.setAspectRatio(Float.parseFloat(responsData.getAspect_ratio()));
            }
        } catch (Exception e) {
            e.getMessage();
        }


    }

    public void addArticle(ListResponsData data) {
        listResponsDatas.add(data);
        notifyItemInserted(listResponsDatas.size());
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemClickListener clickListener;
        @Bind(R.id.imageView_list_item)
        ImageViewFit imageView_list_item;

        @Bind(R.id.textView_article_title)
        TextView textView_article_title;
        @Bind(R.id.textView_article_subtitle)
        TextView textView_article_subtitle;


        public ArticleViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition(), false);
        }

        public void setClickListener(ItemClickListener itemClickListener1) {
            this.clickListener = itemClickListener1;
        }
    }

    public interface ItemClickListener {

        void onClick(View view, int position, boolean isLongClick);
    }
}


