package com.myAgeEducation.cbseClass7.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myAgeEducation.cbseClass7.R;

import java.util.ArrayList;

public class ListViewAdapterForSkuList extends BaseAdapter
{
    Activity context;
    ArrayList<String> _skuTitle;
    ArrayList<String> _skuPrice;
    ArrayList<String> _skuDetail;
    private Context _context;

    public ListViewAdapterForSkuList(Activity context, ArrayList<String> skuTitle, ArrayList<String> skuPrice, ArrayList<String> skuDetail) {
        super();
        this.context = context;
        this._skuTitle = skuTitle;
        this._skuPrice = skuPrice;
        this._skuDetail = skuDetail;
        _context = context;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return _skuTitle.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView textViewSkuTitle;
        TextView textViewSkuPrice;
        TextView textViewSkuDetail;
    }

    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        final ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listitem_in_app_purchase, null);
            holder = new ViewHolder();
            holder.textViewSkuTitle = (TextView) convertView.findViewById(R.id.textViewSku);
            holder.textViewSkuPrice = (TextView) convertView.findViewById(R.id.textViewSkuPrice);
            holder.textViewSkuDetail = (TextView) convertView.findViewById(R.id.textViewSkuDetails);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewSkuTitle.setText(_skuTitle.get(position));
        holder.textViewSkuPrice.setText(_skuPrice.get(position));
        holder.textViewSkuDetail.setText(_skuDetail.get(position));
        return convertView;
    }
}
