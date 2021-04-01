package com.sungbo.letsreadnews;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsData> news_list;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDraweeView newsImage;
        private final TextView newsTitle;
        private final TextView newsContent;
        private final CardView cardView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            newsImage = (SimpleDraweeView) view.findViewById(R.id.news_image);
            newsTitle = (TextView) view.findViewById(R.id.news_title);
            newsContent = (TextView) view.findViewById(R.id.news_content);
            cardView = view.findViewById(R.id.card_view);

        }

        public TextView getTextView() {
            return newsTitle;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public NewsAdapter(List<NewsData> dataSet, Context context) {

        news_list = dataSet;

//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
//                .setDownsampleEnabled(true).build();
//        Fresco.initialize(context, config);

        Fresco.initialize(context);;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        LinearLayout view = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_news, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        NewsData news = news_list.get(position);

        viewHolder.newsTitle.setText(news.getTitle());
        if (news.getContent() != null){
            viewHolder.newsContent.setText(news.getContent());

        }

        if (news.getUrlToImage() != null){
            Log.d("News Adapter", news.getUrlToImage());
            Uri uri = Uri.parse(news.getUrlToImage());
            viewHolder.newsImage.setImageURI(uri);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(news.getInternetUrl())));

            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        // return localDataSet.length;
        return news_list == null ? 0 : news_list.size();
    }
}
