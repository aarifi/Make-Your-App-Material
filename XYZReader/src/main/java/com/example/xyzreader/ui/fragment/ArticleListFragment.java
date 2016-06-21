package com.example.xyzreader.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.xyzreader.R;
import com.example.xyzreader.adapters.ArticleAdapter;
import com.example.xyzreader.api.ApiClient;
import com.example.xyzreader.data.FirebaseDatabaseXyzReader;
import com.example.xyzreader.model.ListResponsData;
import com.example.xyzreader.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ArticleListFragment extends Fragment {

    @Bind(R.id.recycler_view_articlelist)
    RecyclerView recycler_view_articlelist;

    private List<ListResponsData> responsDataList = new ArrayList<>();
    private ArticleAdapter articleAdapter;


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
        getDataFromFirebase();
        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().logIn();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        ButterKnife.bind(this, view);

        ApiClient.getApiClientInstance(getActivity()).getBooksApiInterfaceMethod().getData(new Callback<List<ListResponsData>>() {
            @Override
            public void success(List<ListResponsData> listResponsDatas, Response response) {
                responsDataList = listResponsDatas;
                addDataToRecyclerView();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Retrofit Erro", error.getMessage());
            }
        });

        return view;
    }

    public void addDataToRecyclerView() {

        articleAdapter = new ArticleAdapter(responsDataList, getActivity());
        recycler_view_articlelist.setAdapter(articleAdapter);
        recycler_view_articlelist.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences settings = getActivity().getSharedPreferences("settings", 0);
        boolean isChecked = settings.getBoolean(Constants.SHARED_PREF_MENU_ITEM_CHECK, false);
        StaggeredGridLayoutManager staggeredGridLayoutManager;
        if (isChecked) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        } else {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        }


        recycler_view_articlelist.setLayoutManager(staggeredGridLayoutManager);
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
        SharedPreferences settings = getActivity().getSharedPreferences("settings", 0);
        boolean isChecked = settings.getBoolean(Constants.SHARED_PREF_MENU_ITEM_CHECK, false);
        MenuItem item = menu.findItem(R.id.action_item_minicards);
        item.setChecked(isChecked);

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
            SharedPreferences settings = getActivity().getSharedPreferences("settings", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(Constants.SHARED_PREF_MENU_ITEM_CHECK, item.isChecked());
            editor.commit();
            addDataToRecyclerView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getDataFromFirebase() {

        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().articleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (responsDataList != null) {

                    responsDataList.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ListResponsData post = postSnapshot.getValue(ListResponsData.class);
                    responsDataList.add(post);
                    System.out.println(post.getAuthor() + " - " + post.getTitle());
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

}
