package com.myAgeEducation.cbseClass7.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.myAgeEducation.cbseClass7.QuestionSets;
import com.myAgeEducation.cbseClass7.R;
import com.myAgeEducation.cbseClass7.Util;
import com.myAgeEducation.cbseClass7.YoutubeVideoList;
import java.util.ArrayList;
import java.util.List;

public class ListViewAdapterForChapterList extends BaseAdapter//, YouTubeBaseActivity
{
    private Activity context;
    private ArrayList<Integer> chapterNumber;
    private ArrayList<String> chapterNames;
    private ArrayList<String> chapterImage;
    private String testType;

    public ListViewAdapterForChapterList(Activity context, List<String> image, List<String> chNames, List<Integer> chNumber, String type) {
        super();
        this.context = context;
        this.chapterImage = (ArrayList<String>)image;
        this.chapterNames = (ArrayList<String>)chNames;
        chapterNumber = (ArrayList<Integer>)chNumber;
        testType = type;
    }

    public int getCount() {
        return chapterNames.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView textViewChapterNumber;
        TextView buttonChapterName;
        ImageView imageViewChapterImage;
        TextView textViewMcq;
        TextView textViewVideos;
    }

    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        final ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.chapter_items, null);
            holder = new ViewHolder();
            holder.textViewChapterNumber = convertView.findViewById(R.id.textViewChapterNumber);
            holder.buttonChapterName = convertView.findViewById(R.id.buttonChapterName);
            holder.imageViewChapterImage = convertView.findViewById(R.id.imageViewChapterImage);
            holder.textViewMcq = convertView.findViewById(R.id.textViewMcq);
            holder.textViewVideos = convertView.findViewById(R.id.textViewVideos);
            //holder.buttonChapterReport = convertView.findViewById(R.id.buttonChapterReport);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        if(position < chapterNumber.size()) {
            String text = chapterNames.get(position);
            holder.buttonChapterName.setText(text);
            holder.textViewChapterNumber.setText(chapterNumber.get(position) + 1 + "");
        }

        if(position < chapterNumber.size()) {
            convertView.setTag(R.string.identifier, chapterNumber.get(position));
        }

        holder.textViewVideos.setOnClickListener(v -> openYouTubeVideoList(position));

        holder.textViewMcq.setOnClickListener(v -> {
            Intent intent = new Intent(context, QuestionSets.class);
            intent.putExtra("chapter_number", position + 1);
            String[] temp = Util.subjectChapters.split(";");
            if(temp.length > position) {
                intent.putExtra("chapter_name", temp[position]);
            }
            context.startActivity(intent);
        });

        holder.buttonChapterName.setOnClickListener(v -> {
            Intent intent = new Intent(context, QuestionSets.class);
            intent.putExtra("chapter_number", position + 1);
            String[] temp = Util.subjectChapters.split(";");
            if(temp.length > position) {
                intent.putExtra("chapter_name", temp[position]);
            }
            context.startActivity(intent);
        });


        if(Util.Subject.equalsIgnoreCase("maths") && Util.IsVideoAvailable)
        {
            //holder.textViewMcq.setVisibility(View.VISIBLE);
            holder.textViewVideos.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.textViewMcq.setVisibility(View.GONE);
            holder.textViewVideos.setVisibility(View.GONE);
        }

        return convertView;
    }

    private void openYouTubeVideoList(int position)
    {
        Intent intent = new Intent(context, YoutubeVideoList.class);
        intent.putExtra("chapter_number", position + 1);
        String[] temp = Util.subjectChapters.split(";");
        if(temp.length > position) {
            intent.putExtra("chapter_name", temp[position]);
        }
        context.startActivity(intent);
    }
}
