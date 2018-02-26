package rrdl.tp_android;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oc.hnapp.android.HNArticle;
import com.oc.hnapp.android.HNQueryCallback;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by moneem on 26/02/18.
 */

public class HNArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
implements HNQueryCallback{

    private final List<HNArticle> _articles=new ArrayList<HNArticle>();
    private static final int VIEW_TYPE_ARTICLE=0;
    public static final int VIEW_TYPE_PROGRESS=1;

    private boolean _hasmore=false;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType)
    {
        case VIEW_TYPE_ARTICLE:
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new HNArticleViewHolder(view);
        }
        case VIEW_TYPE_PROGRESS: {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_progress, parent, false);
            return new ProgressViewHolder(view);
        }
        default:
            throw new IllegalStateException("Unknown type" + viewType);
    }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HNArticleViewHolder)
            ((HNArticleViewHolder) holder).bind(_articles.get(position));
    }


    @Override
    public int getItemCount() {
return _articles.size()+(_hasmore?1:0);    }

    @Override
    public int getItemViewType(int position) {
        if(position<_articles.size()) {
            return VIEW_TYPE_ARTICLE;
        } else{
            return VIEW_TYPE_PROGRESS;}
    }

    @Override
    public void onArticlesReceived(List<HNArticle> list, boolean hasmore) {
        _articles.addAll(list);
        _hasmore=hasmore;
        notifyDataSetChanged();
    }
    public static class HNArticleViewHolder extends RecyclerView.ViewHolder {

        private final TextView _title;

        public HNArticleViewHolder(View view) {
            super(view);
            _title = (TextView) view.findViewById(R.id.title);
        }

        public void bind(HNArticle article) {
            _title.setText(article.title);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View itemView) { super(itemView); }
    }
}