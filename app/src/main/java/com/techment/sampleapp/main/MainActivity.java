package com.techment.sampleapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.techment.sampleapp.MyApplication;
import com.techment.sampleapp.R;
import com.techment.sampleapp.adapter.RecyclerViewAdapter;
import com.techment.sampleapp.component.ApplicationComponent;
import com.techment.sampleapp.component.DaggerMainActivityComponent;
import com.techment.sampleapp.component.MainActivityComponent;
import com.techment.sampleapp.helper.RecyclerOnScrollListener;
import com.techment.sampleapp.helper.Utility;
import com.techment.sampleapp.model.ResponseItems;
import com.techment.sampleapp.module.MainActivityContextModule;
import com.techment.sampleapp.module.MainActivityMvpModule;
import com.techment.sampleapp.qualifier.ActivityContext;
import com.techment.sampleapp.qualifier.ApplicationContext;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View, RecyclerViewAdapter.DataAdapterCallback {

    private ProgressBar itemProgressBar,progressBarMain;
    private SwipeRefreshLayout pullToRefresh;
    private RecyclerView recyclerView;

    @Inject
    public RecyclerViewAdapter recyclerViewAdapter;

    @Inject
    @ApplicationContext
    public Context context;

    @Inject
    @ActivityContext
    public Context activityContext;

    @Inject
    MainPresenter mainPresenter;

    private int pageSize = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        MainActivityComponent mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityContextModule(new MainActivityContextModule(this))
                .mainActivityMvpModule(new MainActivityMvpModule(this))
                .applicationComponent(applicationComponent)
                .build();

        mainActivityComponent.injectMainActivity(this);

        setToolbarTitle("0");/*setting count as 0 initially*/

        initializeViews();/*initialize view component*/

        /*calling API to load data if connected to internet*/
        if(Utility.checkInternetConnectivity(context)){
            progressBarMain.setVisibility(View.VISIBLE);
            mainPresenter.loadData(pageSize);
        }else {
            Utility.showToast(context,getString(R.string.no_internet));
        }

        /*setting up recycler adapter view*/
        setAdapterView();

        /*adding scrollbar listener*/
        recyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
            @Override
            protected void onLoadMore() {
                if (Utility.checkInternetConnectivity(context)) {
                    /*increment page number and calling API again - lazy loading*/
                    pageSize++;
                    mainPresenter.loadData(pageSize);
                }else {
                    Utility.showToast(context,getString(R.string.no_internet));
                }
            }
        });

        /*setting pull to refresh*/
        setPullToRefresh();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
        itemProgressBar = findViewById(R.id.itemProgressBar);
        progressBarMain = findViewById(R.id.progressBarMain);
    }

    private void setPullToRefresh() {
        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(Utility.checkInternetConnectivity(context)){
                    recyclerView.setVisibility(View.GONE);
                    refreshView();
                }else {
                    Utility.showToast(context,getString(R.string.no_internet));
                }
            }
        });
    }

    private void refreshView() {
        setToolbarTitle("0");/*setting count as 0 if refreshed*/
        /*initializing list scroll previous details*/
        RecyclerOnScrollListener.previousTotal = 0;
        RecyclerOnScrollListener.loading = false;
        pullToRefresh.setRefreshing(true);
        pageSize = 1;
        mainPresenter.loadData(pageSize);
    }

    private void setAdapterView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void changeTitle(String dataCount) {
        setToolbarTitle(dataCount);
    }

    @Override
    public void showData(int pageSize, List<ResponseItems> dataItem) {
        /*get details and display it on list*/
        recyclerViewAdapter.setData(pageSize,dataItem);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        Utility.showToast(context,getString(R.string.error_message));
    }

    @Override
    public void showProgress() {
        itemProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        itemProgressBar.setVisibility(View.GONE);
        progressBarMain.setVisibility(View.GONE);
        pullToRefresh.setRefreshing(false);
    }

    private void setToolbarTitle(String toolbarTitle){
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(toolbarTitle);
        }
    }
}
