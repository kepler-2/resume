package com.kepler.resume.adapter;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kepler.resume.R;
import com.kepler.resume.datebaseHelper.URLSetterGetter;
import com.kepler.resume.datebaseHelper.UrlDAO;
import com.kepler.resume.support.Logger;
import com.kepler.resume.support.Params;

import java.util.List;

/**
 * Created by Amit on 07-03-2017.
 */

public class UrlListingAdapter extends BaseAdapter {
    private String url;
    private List<URLSetterGetter> urlSetterGetterList;
    private Context context;

    public UrlListingAdapter(Context context) {
        this.context = context;
        url = PreferenceManager.getDefaultSharedPreferences(context).getString(Params.URL, Params.URL_AMITKUMARJAISWAL);

        notifyNewUrl();
    }

    @Override
    public int getCount() {
        return urlSetterGetterList.size();
    }

    @Override
    public URLSetterGetter getItem(int i) {
        return urlSetterGetterList.get(i);
    }

    private void removeItem(int i) {
        urlSetterGetterList.remove(i);
    }

    @Override
    public long getItemId(int i) {
        return urlSetterGetterList.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.url_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.textView);
            viewHolder.imageButton = (ImageButton) view.findViewById(R.id.imageButton);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (url.equalsIgnoreCase(getItem(i).getURL()))
            view.setBackgroundColor(Color.GRAY);
        else
            view.setBackgroundColor(Color.WHITE);
        viewHolder.textView.setText(getItem(i).getURL());
        viewHolder.textView.setSelected(true);
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (urlSetterGetterList.get(i).getURL().contains(Params.AMITKUMARJAISWAL)) {
                    Logger.makeSimpleToast(context, "Can't be Deleted");
                    return;
                }
                UrlDAO urlDAO = new UrlDAO(context);
                if (urlDAO.remove_url(getItem(i).getId())) {
                    removeItem(i);
                    notifyDataSetChanged();
                    Logger.makeSimpleToast(context, "Deleted");
                }
                urlDAO.close();
                Logger.makeSimpleToast(context, "Deleted");

            }
        });
        return view;
    }

    public void notifyNewUrl() {
        if (urlSetterGetterList != null) {
            urlSetterGetterList.clear();
            urlSetterGetterList = null;
        }
        UrlDAO urlDAO = new UrlDAO(context);
        urlSetterGetterList = urlDAO.get_url(1);
        urlDAO.close();
    }

    public void setDefault(int pos) {
        this.url = getItem(pos).getURL();
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView textView;
        ImageButton imageButton;
    }
}
