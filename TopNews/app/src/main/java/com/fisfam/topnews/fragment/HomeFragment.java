package com.fisfam.topnews.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
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
import com.fisfam.topnews.utils.NetworkCheck;

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
    private Call<News> mCallNews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);

        initUiComponents();
        showRefreshing(true);

        new Handler().postDelayed(this::requestData, 500);

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
        mSwipeRefreshLayout = mRootView.findViewById(R.id.swipe_refresh);
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
            if (mCallNews != null && mCallNews.isExecuted()) {
                mCallNews.cancel();
                mHomeAdapter.resetData();
            }
            requestData();
        });
    }

    private void requestData() {
        // Disable failed view if it was enabled from last request
        showFailedView(false, "", R.drawable.img_failed);

        showRefreshing(true);

        NewsService newsService =
                NewsServiceGenerator.createService(NewsService.class, getString(R.string.api_key));
        mCallNews = newsService.getTopHeadlines(
                "gb", "technology", null, null, 10, 1);

        //TODO: move this request out of UI Thread
        mCallNews.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@Nullable Call<News> call, @NonNull Response<News> response) {
                News news = response.body();

                if (news == null) {
                    Log.e(TAG, "onResponse: No news is good news");
                    handleFailRequest();
                    return;
                }

                mHomeAdapter.addData(new Section(getString(R.string.section_topics)));
                for (final Articles articles : news.getArticles()) {
                    mHomeAdapter.addData(articles);
                }

                showRefreshing(false);
            }

            @Override
            public void onFailure(@Nullable Call<News> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
                if (!call.isCanceled()) {
                    handleFailRequest();
                }
            }
        });
    }

    private void handleFailRequest() {
        showRefreshing(false);
        if (NetworkCheck.isNetworkAvailable(getActivity())){
            showFailedView(true, getString(R.string.failed_text), R.drawable.img_failed);
        } else {
            showFailedView(true, getString(R.string.no_internet_text), R.drawable.img_no_internet);
        }
    }

    private void showFailedView(boolean show, String message, @DrawableRes int icon) {
        View lyt_failed = mRootView.findViewById(R.id.lyt_failed);

        ((ImageView) mRootView.findViewById(R.id.failed_icon)).setImageResource(icon);
        ((TextView) mRootView.findViewById(R.id.failed_message)).setText(message);
        if (show) {
            mHomeRecyclerView.setVisibility(View.INVISIBLE);
            lyt_failed.setVisibility(View.VISIBLE);
        } else {
            mHomeRecyclerView.setVisibility(View.VISIBLE);
            lyt_failed.setVisibility(View.GONE);
        }
        (mRootView.findViewById(R.id.failed_retry)).setOnClickListener(view -> requestData());
    }

    private void showRefreshing(final boolean show) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(show));
        if (show) {
            mShimmerFrameLayout.setVisibility(View.VISIBLE);
            mShimmerFrameLayout.startShimmer();
            return;
        }
        mShimmerFrameLayout.setVisibility(View.INVISIBLE);
        mShimmerFrameLayout.stopShimmer();
    }
}
