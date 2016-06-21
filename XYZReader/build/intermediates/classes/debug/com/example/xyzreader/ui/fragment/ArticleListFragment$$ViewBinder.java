// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ArticleListFragment$$ViewBinder<T extends com.example.xyzreader.ui.fragment.ArticleListFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558569, "field 'recycler_view_articlelist'");
    target.recycler_view_articlelist = finder.castView(view, 2131558569, "field 'recycler_view_articlelist'");
  }

  @Override public void unbind(T target) {
    target.recycler_view_articlelist = null;
  }
}
