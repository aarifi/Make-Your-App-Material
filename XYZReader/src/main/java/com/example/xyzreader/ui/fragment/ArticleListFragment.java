package com.example.xyzreader.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xyzreader.R;
import com.example.xyzreader.adapters.ArticleAdapter;
import com.example.xyzreader.api.ApiClient;
import com.example.xyzreader.data.FirebaseDatabaseXyzReader;
import com.example.xyzreader.model.ListResponsData;
import com.example.xyzreader.model.UtilsData;
import com.example.xyzreader.utils.XYZSharedPref;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ArticleListFragment extends Fragment implements ArticleAdapter.DataObserverListener {
    private static final String TAG = ArticleListFragment.class.getSimpleName();


    //region Declare Varable

    @Bind(R.id.recycler_view_articlelist)
    RecyclerView recycler_view_articlelist;
    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @Bind(R.id.imageView_empty_recyclerView)
    ImageView imageView_empty_recyclerView;
    @Bind(R.id.textView_empty_recyclerView)
    TextView textView_empty_recyclerView;

    private List<ListResponsData> responsDataList = new ArrayList<>();
    private ArticleAdapter articleAdapter;

    private UtilsData utilsData;
    private ProgressDialog progressDialog;
    //endregion

    public ArticleListFragment() {
        // Required empty public constructor
    }

    public static ArticleListFragment newInstance(String param1, String param2) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        ButterKnife.bind(this, view);
        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().logIn();
        if (!XYZSharedPref.getxyzSharedPref(getActivity()).getIsAddDataFromDropBox()) {
            FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().isAddDataFromDropBox.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    utilsData = dataSnapshot.getValue(UtilsData.class);

                    if (!utilsData.isAddDataFromDropBox()) {
                        showProgresDialog();
                        getDataFromDropBox();
                    } else {
                        getDataFromFirebase();
                        XYZSharedPref.getxyzSharedPref(getActivity()).saveIsAddDataFromDropBox(true);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            getDataFromFirebase();
        }
        swipeContainer.setColorSchemeResources(R.color.primary);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromFirebase();
                waitForStopSwipecontainer();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_article_list, menu);

        MenuItem item = menu.findItem(R.id.action_item_minicards);
        item.setChecked(XYZSharedPref.getxyzSharedPref(getActivity()).geMiniCardIsChecked());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_item_minicards) {
            item.setChecked(!item.isChecked());
            XYZSharedPref.getxyzSharedPref(getActivity()).saveMiniCardIsChecked(item.isChecked());
            getDataFromFirebase();
            return true;
        } else if (id == R.id.action_item_refresh) {
            swipeContainer.setRefreshing(true);
            waitForStopSwipecontainer();
            getDataFromFirebase();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showProgresDialog() {
        progressDialog = new ProgressDialog(getActivity(), android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Getting data from DropBox and Save to Firebase...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success

                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 15000);
    }

    //region Getting Data and save to Firebase
    public void getDataFromDropBox() {
        ApiClient.getApiClientInstance(getActivity()).getBooksApiInterfaceMethod().getData(new Callback<List<ListResponsData>>() {
            @Override
            public void success(List<ListResponsData> listResponsDatas, Response response) {
                responsDataList = listResponsDatas;
                for (ListResponsData list : responsDataList) {
                    FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().articleRef.push().setValue(list);
                }
                XYZSharedPref.getxyzSharedPref(getActivity()).saveIsAddDataFromDropBox(true);
                UtilsData utilsData = new UtilsData();
                utilsData.setAddDataFromDropBox(true);
                FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().isAddDataFromDropBox.setValue(utilsData);
                progressDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Retrofit Erro", error.getMessage());
            }
        });
    }

    //Getting data from Firebase
    public void getDataFromFirebase() {

        swipeContainer.setRefreshing(true);

        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().articleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (responsDataList != null) {

                    responsDataList.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ListResponsData post = postSnapshot.getValue(ListResponsData.class);
                    responsDataList.add(post);
                }

                addDataToRecyclerView();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("Error Firebase", databaseError.getMessage());
            }
        });

        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().articleRef.keepSynced(true);


    }

    public void refreshDataOnCRUD() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                ListResponsData listResponsData = dataSnapshot.getValue(ListResponsData.class);
                articleAdapter.addArticle(listResponsData);

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                Comment newComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();
                ListResponsData listResponsData = dataSnapshot.getValue(ListResponsData.class);
                articleAdapter.removeItem(listResponsData);
                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(getActivity(), "Failed to load article.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().articleRef.addChildEventListener(childEventListener);
    }
    //endregion

    //region Add data to RecyclerView and aniamte
    public void addDataToRecyclerView() {
        articleAdapter = new ArticleAdapter(responsDataList, getActivity(), this);
        recycler_view_articlelist.setAdapter(articleAdapter);
        recycler_view_articlelist.setLayoutManager(new LinearLayoutManager(getActivity()));
        StaggeredGridLayoutManager staggeredGridLayoutManager;
        swipeContainer.setRefreshing(false);
        if (XYZSharedPref.getxyzSharedPref(getActivity()).geMiniCardIsChecked()) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        } else {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        }
        recycler_view_articlelist.setLayoutManager(staggeredGridLayoutManager);
        if (responsDataList != null) {
            animateViewsIn();
        }


    }

    private void checkRecyclerViewIsEmpty(int adapterSize) {
        if (adapterSize == 0) {
            imageView_empty_recyclerView.setVisibility(View.VISIBLE);
            textView_empty_recyclerView.setVisibility(View.VISIBLE);
        } else {
            imageView_empty_recyclerView.setVisibility(View.GONE);
            textView_empty_recyclerView.setVisibility(View.GONE);

        }
    }

    //animate item on RecylerView
    private void animateViewsIn() {
        ViewGroup root = (ViewGroup) getActivity().findViewById(R.id.framelayout_article_list_root);
        int count = root.getChildCount();
        float offset = getResources().getDimensionPixelSize(R.dimen.offset_y);
        Interpolator interpolator =
                AnimationUtils.loadInterpolator(getActivity(), android.R.interpolator.linear_out_slow_in);

        // loop over the children setting an increasing translation y but the same animation
        // duration + interpolation
        for (int i = 0; i < count; i++) {
            View view = root.getChildAt(i);
            view.setVisibility(View.VISIBLE);
            view.setTranslationY(offset);
            view.setAlpha(0.85f);
            // then animate back to natural position
            view.animate()
                    .translationY(0f)
                    .alpha(1f)
                    .setInterpolator(interpolator)
                    .setDuration(1000L)
                    .start();
            // increase the offset distance for the next view
            offset *= 1.5f;
        }
    }

    private void waitForStopSwipecontainer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onDataObserver(int adapterSize) {
        checkRecyclerViewIsEmpty(adapterSize);
    }

    //endregion

}
