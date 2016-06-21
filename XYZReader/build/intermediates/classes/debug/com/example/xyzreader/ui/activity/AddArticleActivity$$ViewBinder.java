// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.ui.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddArticleActivity$$ViewBinder<T extends com.example.xyzreader.ui.activity.AddArticleActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558533, "field 'input_author'");
    target.input_author = finder.castView(view, 2131558533, "field 'input_author'");
    view = finder.findRequiredView(source, 2131558535, "field 'input_title'");
    target.input_title = finder.castView(view, 2131558535, "field 'input_title'");
    view = finder.findRequiredView(source, 2131558537, "field 'input_date'");
    target.input_date = finder.castView(view, 2131558537, "field 'input_date'");
    view = finder.findRequiredView(source, 2131558539, "field 'input_body'");
    target.input_body = finder.castView(view, 2131558539, "field 'input_body'");
    view = finder.findRequiredView(source, 2131558541, "field 'input_image'");
    target.input_image = finder.castView(view, 2131558541, "field 'input_image'");
    view = finder.findRequiredView(source, 2131558542, "field 'imageButton_open_gallery' and method 'setOnClick_imageButton_open_gallery'");
    target.imageButton_open_gallery = finder.castView(view, 2131558542, "field 'imageButton_open_gallery'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.setOnClick_imageButton_open_gallery();
        }
      });
    view = finder.findRequiredView(source, 2131558543, "field 'btn_save', method 'setOnClick_btnsave', and method 'setOnClick_btn_save'");
    target.btn_save = finder.castView(view, 2131558543, "field 'btn_save'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.setOnClick_btnsave();
          target.setOnClick_btn_save();
        }
      });
  }

  @Override public void unbind(T target) {
    target.input_author = null;
    target.input_title = null;
    target.input_date = null;
    target.input_body = null;
    target.input_image = null;
    target.imageButton_open_gallery = null;
    target.btn_save = null;
  }
}
