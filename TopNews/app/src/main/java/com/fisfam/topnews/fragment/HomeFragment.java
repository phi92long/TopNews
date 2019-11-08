package com.fisfam.topnews.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.fisfam.topnews.NewsService;
import com.fisfam.topnews.NewsServiceGenerator;
import com.fisfam.topnews.R;
import com.fisfam.topnews.adapter.HomeAdapter;
import com.fisfam.topnews.pojo.Articles;
import com.fisfam.topnews.pojo.News;
import com.fisfam.topnews.pojo.Section;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private View mRootView;
    private RecyclerView mHomeRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ShimmerFrameLayout mShimmerFrameLayout;
    private HomeAdapter mHomeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);

        initUiComponents();
        requestData();

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initUiComponents() {
        mShimmerFrameLayout = mRootView.findViewById(R.id.shimmer_home);
        //mShimmerFrameLayout.setVisibility(View.VISIBLE);
        //mShimmerFrameLayout.startShimmer();

        mSwipeRefreshLayout = mRootView.findViewById(R.id.swipe_refresh);
        //mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));

        mHomeRecyclerView = mRootView.findViewById(R.id.home_recycler_view);
        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeRecyclerView.setHasFixedSize(true);

        // inject the recycler view so adapter can check if last item reached.
        //TODO: Well there has to be a better way to check this?
        mHomeAdapter = new HomeAdapter(getActivity(), mHomeRecyclerView);
        mHomeRecyclerView.setAdapter(mHomeAdapter);

        mHomeAdapter.setOnLoadMoreListener(() -> {
            //TODO: load more articles
        });

        mHomeAdapter.setOnItemClickListener((view, articles, position) -> {
            //TODO: navigate to articles details activity
        });

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            //TODO: cancel ongoing call to News API, delete the data, request new page
        });
    }

    private void requestData() {
        NewsService newsService =
                NewsServiceGenerator.createService(NewsService.class, getString(R.string.api_key));
        Call<News> callNews = newsService.getTopHeadlines(
                "de", null, null, null, 5, 0);

        callNews.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = response.body();
                Log.d(TAG, "Received Headlines = " + news);

                // TODO: add data to adapter
                mHomeAdapter.addData(new Section(getString(R.string.section_topics)));
                mHomeAdapter.addData(news.getArticles().get(0));
                mHomeAdapter.addData(new Section(getString(R.string.section_featured)));
                mHomeAdapter.addData(news.getArticles().get(1));
                mHomeAdapter.addData(new Section(getString(R.string.section_recent)));
                mHomeAdapter.addData(news.getArticles().get(2));
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
