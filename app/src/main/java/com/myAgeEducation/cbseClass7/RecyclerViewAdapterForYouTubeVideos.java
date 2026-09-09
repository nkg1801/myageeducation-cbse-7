package com.myAgeEducation.cbseClass7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapterForYouTubeVideos extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    //private ArrayList<String> _videoList;
    //private ArrayList<String> _videoDescription;
    private final ArrayList<YouTubeVideoDetails> _youtubeVideoList;
    private final Context _context;

    private List<Object> _sets;
    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    // The unified native ad view type.
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;

    //RecyclerViewAdapterForQuestionSets(Context context, List<String> setNames, List<String> rewardsList, List<String> setResults) {
    RecyclerViewAdapterForYouTubeVideos(Context context, List<YouTubeVideoDetails> videoList)//, List<String> videoDescription) {
    {
        super();
        //this._videoList = (ArrayList<String>)videoList;
        //_videoDescription = (ArrayList<String>)videoDescription;
        _context = context;
        _youtubeVideoList = (ArrayList<YouTubeVideoDetails>) videoList;
    }

    @Override
    public int getItemCount() {
        return _youtubeVideoList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        /*Object recyclerViewItem = _sets.get(position);
        if (recyclerViewItem instanceof UnifiedNativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE;
        }*/
        return MENU_ITEM_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_question_sets, parent, false);
        return new RecyclerViewAdapterForQuestionSets.ViewHolder(view);*/

        /*switch (viewType) {
            case UNIFIED_NATIVE_AD_VIEW_TYPE:
                View unifiedNativeLayoutView = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.ad_unified,
                        viewGroup, false);
                return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);
            case MENU_ITEM_VIEW_TYPE:
                // Fall through.
            default:*/
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.listitem_youtube_video_list, viewGroup, false);
                return new ViewHolder(menuItemLayoutView);
        //}
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder defaultViewHolder = (ViewHolder) holder;
        String text = _youtubeVideoList.get(position).getVideoDescription();
        defaultViewHolder.textVideoDescription.setText(text);

        String thumbnailUrl = "https://img.youtube.com/vi/" + _youtubeVideoList.get(position).getVideoLink() +"/0.jpg";
        Picasso.get().
                load(thumbnailUrl) //thumbnail for the video
                .fit()
                //.placeholder(R.drawable.youtube)
                .error(R.drawable.youtube)
                .into(defaultViewHolder.imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (defaultViewHolder.progressBar != null) {
                            defaultViewHolder.progressBar.setVisibility(View.GONE);
                        }
                        defaultViewHolder.playButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

            defaultViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openYouTubePlayerActivity(_youtubeVideoList.get(position).getVideoLink());
                }
            });
    }

    private void populateNativeAdView(NativeAd nativeAd,  NativeAdView adView) {
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        ((TextView) Objects.requireNonNull(adView.getBodyView())).setText(nativeAd.getBody());
        ((Button) Objects.requireNonNull(adView.getCallToActionView())).setText(nativeAd.getCallToAction());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        NativeAd.Image icon = nativeAd.getIcon();

        if (icon == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) Objects.requireNonNull(adView.getStarRatingView()))
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            Objects.requireNonNull(adView.getAdvertiserView()).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) Objects.requireNonNull(adView.getAdvertiserView())).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView imageView;
        final TextView textVideoDescription;
        final ProgressBar progressBar;
        final ImageView playButton;

        ViewHolder(View view) {
            super(view);
            mView = view;

            imageView = view.findViewById(R.id.imageView);
            textVideoDescription = view.findViewById(R.id.textVideoDescription);
            progressBar = view.findViewById(R.id.progressBar);
            playButton = view.findViewById(R.id.playButton);
        }
    }

    private void openYouTubePlayerActivity(String youTubelink)
    {
        /*Intent intentLogin = new Intent(_context, YouTubePlayerActivity.class);
        intentLogin.putExtra("link", youTubelink);
        _context.startActivity(intentLogin);*/
        Util.watchYoutubeVideo(_context, youTubelink);
    }
}
