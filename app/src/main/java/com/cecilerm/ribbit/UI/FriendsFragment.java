package com.cecilerm.ribbit.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.cecilerm.ribbit.Adapters.UserAdapter;
import com.cecilerm.ribbit.R;
import com.cecilerm.ribbit.Util.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class FriendsFragment extends Fragment {

	public static final String TAG = FriendsFragment.class.getSimpleName();

	protected List<ParseUser> mFriends;
	protected ParseRelation<ParseUser> mFriendsRelation;
	protected ParseUser mCurrentUser;
    protected GridView mGridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_friends, container,
				false);
        mGridView = (GridView) rootView.findViewById(R.id.friendGrid);
        TextView emptyTextView = (TextView) rootView.findViewById(android.R.id.empty);
        mGridView.setEmptyView(emptyTextView);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseUser friend = mFriends.get(position);
                // start profile activity
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("username", friend.getUsername());
                intent.putExtra("firstName", friend.getString("firstName"));
                intent.putExtra("lastName", friend.getString("lastName"));
                intent.putExtra("age", friend.getString("age"));
                intent.putExtra("hometown", friend.getString("hometown"));
                startActivity(intent);
            }
        });
        return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		mCurrentUser = ParseUser.getCurrentUser();
		mFriendsRelation = mCurrentUser
				.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

		getActivity().setProgressBarIndeterminateVisibility(true);

		ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
		query.addAscendingOrder(ParseConstants.KEY_USERNAME);
		query.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> friends, ParseException e) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				if (e == null) {
					mFriends = friends;
					String[] usernames = new String[mFriends.size()];
					int i = 0;

					for (ParseUser user : mFriends) {
						usernames[i] = user.getUsername();
						i++;
					}
                    if (mGridView.getAdapter() == null) {
                        UserAdapter adapter = new UserAdapter(
                                getActivity(), mFriends);
                        mGridView.setAdapter(adapter);
                    } else {
                        ((UserAdapter)mGridView.getAdapter()).refill(mFriends);
                    }
				}

				else {
					Log.e(TAG, e.getMessage());
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setMessage(e.getMessage())
							.setTitle(R.string.error_title)
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}

			}
		});
    }
}
