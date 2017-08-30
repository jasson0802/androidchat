package com.itcr.chat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jason on 19/8/2017.
 */

public class MessageAdapter extends BaseAdapter {
    Context context;
    List<MessageItem> messageItems;
    private static LayoutInflater inflater = null;

    public MessageAdapter(Context context, List<MessageItem> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.messageItems = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return messageItems.indexOf(getItem(position));
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView profile_pic;
        TextView member_name;
        TextView lastMessage;
        TextView time;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.message_item, null);
            holder = new ViewHolder();

            holder.member_name = (TextView) convertView
                    .findViewById(R.id.name);
            holder.profile_pic = (ImageView) convertView
                    .findViewById(R.id.pic);
            holder.lastMessage = (TextView) convertView.findViewById(R.id.last);
            holder.time = (TextView) convertView
                    .findViewById(R.id.time);

            MessageItem row_pos = messageItems.get(position);

            //holder.profile_pic.setImageResource(row_pos.getProfilePicUrl()); Setear imagen url
            holder.member_name.setText(row_pos.getMemberName());
            holder.lastMessage.setText(row_pos.getLastMessage());
            holder.time.setText(row_pos.getLastMessageTime());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}

