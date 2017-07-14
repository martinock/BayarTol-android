package com.upiki.bayartol.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.upiki.bayartol.R;
import com.upiki.bayartol.api.ApiClass.User;

import java.util.List;

public class ListMemberActivity extends AppCompatActivity {

    RecyclerView mListMember;
    ProgressBar mLoading;

    List<User> listMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_member);

        // initialization view
        mListMember = (RecyclerView) findViewById(R.id.list_member);
        mLoading = (ProgressBar) findViewById(R.id.loading);

        // set up recyclerview adapter
        MemberAdapter memberAdapter = new MemberAdapter(listMembers);
        mListMember.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        mListMember.setAdapter(memberAdapter);

    }

    public static void startThisActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ListMemberActivity.class));
    }

    class MemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int MEMBER = 0;
        private final int ADD = 1;

        private List<User> listMember;

        public MemberAdapter(List<User> listMember) {
            this.listMember = listMember;
        }

        @Override
        public int getItemCount() {
            return listMember.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == listMember.size() + 1) {
                return ADD;
            } else {
                return MEMBER;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == MEMBER) {
                return new MemberHolder(inflater.inflate(R.layout.row_member, parent, false));
            } else if (viewType == ADD) {
                return new AddHolder(inflater.inflate(R.layout.row_add, parent, false));
            } else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MemberHolder) {
                MemberHolder memberHolder = (MemberHolder) holder;
                memberHolder.mName.setText(listMember.get(position).name);
                memberHolder.mPhoneNumber.setText(listMember.get(position).phone_number);
            } else if (holder instanceof AddHolder) {
                final AddHolder addHolder = (AddHolder) holder;
                addHolder.mAddLabel.setVisibility(View.VISIBLE);
                addHolder.mAddButton.setVisibility(View.VISIBLE);
                addHolder.mFieldContainer.setVisibility(View.GONE);
                addHolder.mAddContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addHolder.mAddLabel.setVisibility(View.GONE);
                        addHolder.mAddButton.setVisibility(View.GONE);
                        addHolder.mFieldContainer.setVisibility(View.VISIBLE);

                    }
                });
                if (addHolder.mAddSubmit.getVisibility() == View.VISIBLE) {
                    addHolder.mAddSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // post add new member
                        }
                    });
                }
            }
        }

        class AddHolder extends RecyclerView.ViewHolder {
            public ImageView mAddButton;
            public TextView mAddLabel;
            public LinearLayout mAddContainer;
            public LinearLayout mFieldContainer;
            public Button mAddSubmit;

            public AddHolder(View itemView) {
                super(itemView);
                mAddButton = (ImageView) itemView.findViewById(R.id.add_new_member);
                mAddLabel = (TextView) itemView.findViewById(R.id.add_new_member_label);
                mAddContainer = (LinearLayout) itemView.findViewById(R.id.add_container);
                mFieldContainer = (LinearLayout) itemView.findViewById(R.id.add_field_container);
                mAddSubmit = (Button) itemView.findViewById(R.id.add_new_member_button);
            }
        }

        class MemberHolder extends RecyclerView.ViewHolder {

            public TextView mName;
            public TextView mPhoneNumber;

            public MemberHolder(View itemView) {
                super(itemView);
                mName = (TextView) itemView.findViewById(R.id.name);
                mPhoneNumber = (TextView) itemView.findViewById(R.id.phone_number);
            }
        }
    }
}
