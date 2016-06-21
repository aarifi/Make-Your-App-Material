// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ScrollingActivity$$ViewBinder<T extends com.example.xyzreader.ScrollingActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558528, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131558528, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131558544, "field 'content_fragment_article_details'");
    target.content_fragment_article_details = finder.castView(view, 2131558544, "field 'content_fragment_article_details'");
    view = finder.findRequiredView(source, 2131558545, "field 'imageView_details'");
    target.imageView_details = finder.castView(view, 2131558545, "field 'imageView_details'");
  }

  @Override public void unbind(T target) {
    target.toolbar = null;
    target.content_fragment_article_details = null;
    target.imageView_details = null;
  }
}
