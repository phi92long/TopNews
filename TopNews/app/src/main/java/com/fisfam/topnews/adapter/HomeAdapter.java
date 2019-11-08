package com.fisfam.topnews.adapter;

import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fisfam.topnews.R;
import com.fisfam.topnews.pojo.Articles;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ARTICLES = 100;
    private static final int VIEW_TYPE_TOPIC = 200;
    private static final int VIEW_TYPE_SECTION = 300;
    private static final int VIEW_TYPE_PROGRESS = 400;

    private List mItems = new ArrayList<>();
    private OnLoadMoreListener mOnLoadMoreListener;
    private OnItemClickListener mOnItemClickListener;

    public HomeAdapter(final Context context, final RecyclerView recyclerView) {
        super();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemViewType(int position) {
        Object o = mItems.get(position);

        if (o instanceof Articles) {
            return VIEW_TYPE_ARTICLES;
        }

/*        if (o instanceof TopicList) {
            return VIEW_TYPE_TOPIC;
        }

        if (o instanceof Section) {
            return VIEW_TYPE_SECTION;
        }

        if (o instanceof Progress) {
            return VIEW_TYPE_PROGRESS;
        }*/

        return 0;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ItemArticlesViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private TextView source;
        private TextView content;
        private ImageView image;
        private View rippleLayout;

        public ItemArticlesViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.article_title);
            date = v.findViewById(R.id.date);
            source = v.findViewById(R.id.txt_type);
            rippleLayout = v.findViewById(R.id.lyt_articles);
        }
    }

    public static class ItemTopicViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private RecyclerView recyclerView;

        public ItemTopicViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.topic_title);
            recyclerView = v.findViewById(R.id.topic_recycler_view);
        }
    }

    public static class ItemSectionViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public ItemSectionViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.section_title);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnItemClickListener {
        void onItemArticlesClick(View view, Articles articles, int position);

        //void onItemTopicClick(View view, Topic obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        mOnItemClickListener = mItemClickListener;
    }
}
