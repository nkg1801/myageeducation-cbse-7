package com.myAgeEducation.cbseClass7;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.myAgeEducation.cbseClass7.adapters.ListViewAdapterForSkuList;

import java.util.ArrayList;

/**
 * Created by INNAGUP1 on 2/23/2018.
 */

public class SkuList extends Activity
{
    private BaseAdapter _listAdapter;
    ListView _listView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sku_list);
        _listView = (ListView) findViewById(android.R.id.list);
        populateAdapter();
    }

    private void populateAdapter() {
        ArrayList<String> skuTitle = new ArrayList<>();
        ArrayList<String> skuPrice = new ArrayList<>();
        ArrayList<String> skuDetail = new ArrayList<>();

        skuTitle.add("Premium");
        skuTitle.add("Maths");
        skuTitle.add("Computers");
        skuTitle.add("GK");
        skuTitle.add("Score");
        skuTitle.add("Mock Exams");
        skuTitle.add("Rate this app");
        skuTitle.add("Get More");
        skuTitle.add("Exit");

        skuPrice.add("INR 100");
        skuPrice.add("INR 10");
        skuPrice.add("INR 10");
        skuPrice.add("INR 10");
        skuPrice.add("INR 10");
        skuPrice.add("INR 10");
        skuPrice.add("INR 10");
        skuPrice.add("INR 10");
        skuPrice.add("INR 10");

        skuDetail.add("With Premium upgrades you can download all the Mock Exam Papers. Tap this item for more details");
        skuDetail.add("Maths");
        skuDetail.add("Computers");
        skuDetail.add("GK");
        skuDetail.add("Score");
        skuDetail.add("Mock Exams");
        skuDetail.add("Rate this app");
        skuDetail.add("Get More");
        skuDetail.add("Exit");

        _listAdapter = new ListViewAdapterForSkuList(SkuList.this, skuTitle, skuPrice, skuDetail);
        _listView.setAdapter(_listAdapter);
    }

}
