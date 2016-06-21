// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.adapters;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ArticleAdapter$ArticleViewHolder$$ViewBinder<T extends com.example.xyzreader.adapters.ArticleAdapter.ArticleViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558529, "field 'imageView_list_item'");
    target.imageView_list_item = finder.castView(view, 2131558529, "field 'imageView_list_item'");
    view = finder.findRequiredView(source, 2131558530, "field 'textView_article_title'");
    target.textView_article_title = finder.castView(view, 2131558530, "field 'textView_article_title'");
    view = finder.findRequiredView(source, 2131558531, "field 'textView_article_subtitle'");
    target.textView_article_subtitle = finder.castView(view, 2131558531, "field 'textView_article_subtitle'");
  }

  @Override public void unbind(T target) {
    target.imageView_list_item = null;
    target.textView_article_title = null;
    target.textView_article_subtitle = null;
  }
}
