package com.cecilerm.ribbit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cecilerm.ribbit.Util.ParseConstants;
import com.cecilerm.ribbit.R;
import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

public class MessageAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mMessages;

    public MessageAdapter(Context context, List<ParseObject> messages) {
        super(context, R.layout.message_item, messages);
        mContext = context;
        mMessages = messages;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.messageIcon);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.senderLabel);
            holder.timeStamp = (TextView) convertView.findViewById(R.id.timeLabel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject message = mMessages.get(position);
        if(message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)){
            holder.iconImageView.setImageResource(R.drawable.ic_picture);
        } else if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_VIDEO)){
            holder.iconImageView.setImageResource(R.drawable.ic_video);
        } else {
            holder.iconImageView.setImageResource(R.drawable.ic_action_chat);
        }
        holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));
        Date start = message.getDate(ParseConstants.KEY_CREATED_AT);
        try {
            long startTime = start.getTime();
            System.out.println(startTime);
            holder.timeStamp.setText(getRelativeTimeSpanString(startTime));

        } catch (NullPointerException e) {
            System.out.println("No date!");
        }

        return convertView;

    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView nameLabel;
        TextView timeStamp;
    }

    public void refill(List <ParseObject> messages) {
        mMessages.clear();
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }
}
