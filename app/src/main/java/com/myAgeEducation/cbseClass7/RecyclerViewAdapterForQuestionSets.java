package com.myAgeEducation.cbseClass7;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class RecyclerViewAdapterForQuestionSets extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    Activity context;
    private ArrayList<String> _setNames;
    private ArrayList<String> _rewardList;
    private ArrayList<String> _setResults;
    private int _rewardedCount;
    private Context _context;

    private List<Object> _sets;
    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    // The unified native ad view type.
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;

    RecyclerViewAdapterForQuestionSets(Context context, List<Object> sets, List<String> setNames, List<String> rewardsList, List<String> setResults) {
        super();
        this._setNames = (ArrayList<String>)setNames;
        _setResults = (ArrayList<String>)setResults;
        _sets = sets;

        _rewardList = (ArrayList<String>)rewardsList;
        for(int i=0; i <rewardsList.size(); i++)
        {
            if(rewardsList.get(i).equals("rewarded"))
            {
                _rewardedCount++;
            }
        }

        _context = context;
    }

    @Override
    public int getItemCount() {
        //return _setNames.size();
        return _sets.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Object recyclerViewItem = _sets.get(position);
        if (recyclerViewItem instanceof NativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE;
        }
        return MENU_ITEM_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_question_sets, parent, false);
        return new RecyclerViewAdapterForQuestionSets.ViewHolder(view);*/

        switch (viewType) {
            case UNIFIED_NATIVE_AD_VIEW_TYPE:
                View unifiedNativeLayoutView = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.ad_unified,
                        viewGroup, false);
                return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);
            case MENU_ITEM_VIEW_TYPE:
                // Fall through.
            default:
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.listitem_question_sets, viewGroup, false);
                return new ViewHolder(menuItemLayoutView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case UNIFIED_NATIVE_AD_VIEW_TYPE:
                NativeAd nativeAd = (NativeAd) _sets.get(position);
                populateNativeAdView(nativeAd, ((UnifiedNativeAdViewHolder) holder).getAdView());
                break;
            case MENU_ITEM_VIEW_TYPE:
                // fall through
            default:
                final ViewHolder defaultViewHolder = (ViewHolder) holder;
                String text = (String) _sets.get(position);
                defaultViewHolder.textViewQuestionSetNumber.setText(text);

            String text2 = _rewardList.get(position);
            final String versionCode = "v" + com.myAgeEducation.cbseClass7.BuildConfig.VERSION_CODE;

            if (TextUtils.equals(text2, "rewarded")) {
                defaultViewHolder.imageViewLock.setVisibility(View.GONE);
                defaultViewHolder.imageViewLockOpen.setVisibility(View.VISIBLE);
                defaultViewHolder.textViewWatchVideo.setVisibility(View.GONE);
                defaultViewHolder.textViewMarks.setVisibility(View.VISIBLE);
                defaultViewHolder.textViewMarksObtained.setVisibility(View.VISIBLE);
                defaultViewHolder.textViewMarksObtained.setText(_setResults.get(position));
                defaultViewHolder.textViewQuestionSetNumber.setTextColor(Color.rgb(255, 255, 102)); // yellow color
                defaultViewHolder.textViewUnlockMessage.setText("rewarded");
                defaultViewHolder.linearLayoutParent.setAlpha(1.0f); //normal
                defaultViewHolder.mView.setEnabled(true);
            } else if (TextUtils.equals(text2, "next to unlock")) {
                defaultViewHolder.imageViewLock.setVisibility(View.VISIBLE);
                defaultViewHolder.imageViewLockOpen.setVisibility(View.GONE);
                defaultViewHolder.textViewUnlockMessage.setText("Unlock this set by watching the adjoining Video Ad");
                defaultViewHolder.textViewWatchVideo.setEnabled(true);
                defaultViewHolder.textViewWatchVideo.setVisibility(View.VISIBLE);
                defaultViewHolder.textViewMarksObtained.setVisibility(View.GONE);
                defaultViewHolder.textViewMarks.setVisibility(View.GONE);
                defaultViewHolder.textViewQuestionSetNumber.setEnabled(true);
                defaultViewHolder.linearLayoutParent.setAlpha(1.0f); //normal
                defaultViewHolder.textViewQuestionSetNumber.setTextColor(Color.rgb(176, 171, 152));
                defaultViewHolder.mView.setEnabled(true);
            } else {
                defaultViewHolder.imageViewLock.setVisibility(View.VISIBLE);
                defaultViewHolder.imageViewLockOpen.setVisibility(View.GONE);
                defaultViewHolder.textViewUnlockMessage.setText("Unlock this set by watching the adjoining Video Ad");
                defaultViewHolder.textViewWatchVideo.setEnabled(false);
                defaultViewHolder.textViewWatchVideo.setVisibility(View.VISIBLE);
                defaultViewHolder.textViewMarksObtained.setVisibility(View.GONE);
                defaultViewHolder.textViewMarks.setVisibility(View.GONE);
                defaultViewHolder.textViewQuestionSetNumber.setEnabled(false);
                defaultViewHolder.textViewQuestionSetNumber.setTextColor(Color.rgb(176, 171, 152));
                defaultViewHolder.linearLayoutParent.setAlpha(0.5f);
                defaultViewHolder.mView.setEnabled(false);
            }

                defaultViewHolder.mView.setOnClickListener(v -> {
                    String unlockText = defaultViewHolder.textViewUnlockMessage.getText().toString().toLowerCase();
                    if (unlockText.contains("unlock")) {
                        Util.displayAlert("Please unlock this Question Set by watching the Ad Video", "Question Set Locked", _context);
                    } else {
                        if(Util.Subject.equalsIgnoreCase("maths")) {

                        }
                        else {
                            ////
                            ((QuestionSets) _context).getQuestions(position + 1);

                            /////
                        }
                    }
                });

                defaultViewHolder.textViewWatchVideo.setOnClickListener(v -> {
                    defaultViewHolder.textViewUnlockMessage.setText("Unlock this set by watching the adjoining Video Ad");
                    defaultViewHolder.textViewUnlockMessage.setTextColor(Color.rgb(176, 171, 152));
                    String error = "";
                    try {
                        error = ((QuestionSets) _context).onVideoClick(position + 1);
                    } catch (Exception ignored) {

                    }
                    FirebaseManager firebaseManager = new FirebaseManager();
                    String value = "";
                    if (TextUtils.isEmpty(error)) {
                        defaultViewHolder.textViewUnlockMessage.setTextColor(Color.rgb(176, 171, 152));
                        value = Util.ClassName + versionCode + "-" + Util.getCurrentDateTime();
                        firebaseManager.write(value, "watch-ad-video-clicked/" + UUID.randomUUID().toString());
                    } else {
                        defaultViewHolder.textViewUnlockMessage.setText("ERROR: Unable to load Ad Video, please try after sometime.");
                        defaultViewHolder.textViewUnlockMessage.setTextColor(Color.rgb(255, 160, 10));
                        value = Util.ClassName + "-failed-" + Util.getCurrentDateTime();
                        firebaseManager.write(value, "watch-ad-video-clicked/" + UUID.randomUUID().toString());
                    }
                });

                defaultViewHolder.textViewQuestionSetNumber.setOnClickListener(v -> {
                    String unlockText = defaultViewHolder.textViewUnlockMessage.getText().toString().toLowerCase();
                    if (unlockText.contains("unlock")) {
                        Util.displayAlert("Please unlock this Question Set by watching the Ad Video", "Question Set Locked", _context);
                    } else {
                        ((QuestionSets) _context).getQuestions(position + 1);
                    }
                });

                defaultViewHolder.imageViewLockOpen.setOnClickListener(v -> {
                    String unlockText = defaultViewHolder.textViewUnlockMessage.getText().toString().toLowerCase();
                    if (unlockText.contains("unlock")) {
                        Util.displayAlert("Please unlock this Question Set by watching the Ad Video", "Question Set Locked", _context);
                    } else {
                        ((QuestionSets) _context).getQuestions(position + 1);
                    }
                });
            break;
        }
    }

    private void populateNativeAdView(NativeAd nativeAd,  NativeAdView adView) {
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        NativeAd.Image icon = nativeAd.getIcon();

        if (icon == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;

        final TextView textViewQuestionSetNumber;
        final TextView textViewMarksObtained;
        final TextView textViewMarks;
        final LinearLayout linearLayoutParent;
        final ImageView imageViewLock;
        final ImageView imageViewLockOpen;
        final TextView textViewUnlockMessage;
        final TextView textViewWatchVideo;

        ViewHolder(View view) {
            super(view);
            mView = view;

            textViewQuestionSetNumber = view.findViewById(R.id.textViewQuestionSetNumber);
            textViewMarksObtained = view.findViewById(R.id.textViewMarksObtained);
            linearLayoutParent = view.findViewById(R.id.linearLayoutParent);
            imageViewLock = view.findViewById(R.id.imageViewLock);
            imageViewLockOpen = view.findViewById(R.id.imageViewLockOpen);
            textViewUnlockMessage = view.findViewById(R.id.textViewUnlockMessage);
            textViewMarks = view.findViewById(R.id.textViewMarks);
            textViewWatchVideo = view.findViewById(R.id.textViewWatchVideo);
        }
    }
}
