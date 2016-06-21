// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.ui.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.example.xyzreader.ui.activity.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558526, "field 'drawer'");
    target.drawer = finder.castView(view, 2131558526, "field 'drawer'");
    view = finder.findRequiredView(source, 2131558527, "field 'navigationView'");
    target.navigationView = finder.castView(view, 2131558527, "field 'navigationView'");
  }

  @Override public void unbind(T target) {
    target.drawer = null;
    target.navigationView = null;
  }
}
