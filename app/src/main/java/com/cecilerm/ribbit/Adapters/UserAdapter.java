package com.cecilerm.ribbit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cecilerm.ribbit.R;
import com.cecilerm.ribbit.Util.MD5Util;
import com.cecilerm.ribbit.Util.ParseConstants;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

public class UserAdapter extends ArrayAdapter<ParseUser> {

    protected Context mContext;
    protected List<ParseUser> mUsers;

    public UserAdapter(Context context, List<ParseUser> users) {
        super(context, R.layout.user_item, users);
        mContext = context;
        mUsers = users;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.user_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.userImageView);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseUser user = mUsers.get(position);
        String email = user.getEmail().toLowerCase();
        holder.nameLabel.setText(user.getUsername());
        if (email.isEmpty()){
            holder.iconImageView.setImageResource(R.drawable.avatar_empty);
        } else {
            // use Gravatar
           String hash = MD5Util.md5Hex(email);
           String gravatarUrl = "http://www.gravatar.com/avatar/" + hash + "?s=204&d=404";
           Picasso.with(mContext).load(gravatarUrl).placeholder(R.drawable.avatar_empty).into(holder.iconImageView);
        }

        return convertView;

    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView nameLabel;
    }

    public void refill(List <ParseUser> users) {
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }
}
