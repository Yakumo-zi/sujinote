package com.suji.adv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.suji.R;

import java.util.List;

public class ADVAdapter extends BaseAdapter {
    private List<ADVEntity> data;
    private LayoutInflater inflater;

    public ADVAdapter(Context context, List<ADVEntity> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.advinfo_item, parent, false);
            holder = new ViewHolder();
            holder.platform = convertView.findViewById(R.id.item_platform);
            holder.time = convertView.findViewById(R.id.item_time);
            holder.cpm = convertView.findViewById(R.id.item_cpm);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ADVEntity entity = data.get(position);
        holder.platform.setText(entity.getPlatform());
        holder.time.setText(entity.getTime());
        holder.cpm.setText(String.valueOf(entity.getCpm()));

        return convertView;
    }
    public void updateData(List<ADVEntity> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView platform;
        TextView time;
        TextView cpm;
    }
}
