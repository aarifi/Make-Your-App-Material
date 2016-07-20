package com.example.xyzreader.ui.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyzreader.R;
import com.example.xyzreader.model.ListResponsData;
import com.example.xyzreader.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleDFragment extends Fragment {


    @Bind(R.id.imageView_details)
    ImageView imageView_details;
    @Bind(R.id.textView_title_details)
    TextView textView_title_details;
    @Bind(R.id.textView_body_details)
    TextView textView_body_details;
    @Bind(R.id.article_byline)
    TextView article_byline;

    public static ListResponsData listResponsData;

    public ArticleDFragment() {
        // Required empty public constructor
    }

    public static ArticleDFragment newInstance(ListResponsData data) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("ListResponsData", data);
        ArticleDFragment fragment = new ArticleDFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_article_d2, container, false);
        ButterKnife.bind(this, rootView);
        if (listResponsData != null)
            updateView();
        return rootView;
    }

    private void updateView() {
        if (listResponsData != null) {
            Glide.with(getActivity()).load(listResponsData.getPhoto()).into(imageView_details);
            textView_title_details.setText(listResponsData.getTitle());
            textView_body_details.setText(Html.fromHtml(listResponsData.getBody()));
            article_byline.setText("by " + listResponsData.getAuthor());
        }
        boolean curve = getActivity().getIntent().getBooleanExtra(Constants.EXTRA_CURVE, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setSharedElementEnterTransition(TransitionInflater.from(getActivity())
                    .inflateTransition(curve ? R.transition.shared_element_transition : R.transition.move));

        }

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.LEFT);
            slide.addTarget(R.id.imageView_details);
           slide.setDuration(300);
            slide.setInterpolator(AnimationUtils.loadInterpolator(getActivity(),
                    android.R.interpolator.linear_out_slow_in));

            getActivity().getWindow().setEnterTransition(slide);
        }
*/

    }

    @Override
    public void onStart() {
        super.onStart();
        listResponsData = getArguments().getParcelable("ListResponsData");
        if (listResponsData != null)
            updateView();
    }

}
