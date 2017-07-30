package com.upiki.bayartol.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.upiki.bayartol.R;
import com.upiki.bayartol.api.Api;
import com.upiki.bayartol.api.ApiClass.User;
import com.upiki.bayartol.api.BayarTolApi;
import com.upiki.bayartol.api.OrganizationApi;
import com.upiki.bayartol.page.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class ListMemberActivity extends AppCompatActivity {

    RecyclerView mListMember;
    ProgressBar mLoading;

    public List<User> listMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_member);

        // initialization view
        mListMember = (RecyclerView) findViewById(R.id.list_member);
        mLoading = (ProgressBar) findViewById(R.id.loading);

        listMembers = new ArrayList<>();
        User dummyUser = new User();
        dummyUser.name = "Silveriar";
        dummyUser.phone_number = "0813131313";
        listMembers.add(dummyUser);
        listMembers.add(dummyUser);

        // set up recyclerview adapter
        MemberAdapter memberAdapter = new MemberAdapter(listMembers);
        mListMember.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mListMember.setAdapter(memberAdapter);

    }

    public static void startThisActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ListMemberActivity.class));
    }

    class MemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int MEMBER = 0;
        private final int ADD = 1;

        private List<User> listMember;
        private boolean add;


        public MemberAdapter(List<User> listMember) {
            this.listMember = listMember;
            add = true;
        }

        @Override
        public int getItemCount() {
            return listMember.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == listMember.size()) {
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
                if (listMember.size() > 0) {
                    memberHolder.mName.setVisibility(View.VISIBLE);
                    memberHolder.mPhoneNumber.setVisibility(View.GONE);
                    memberHolder.mName.setText(listMember.get(position).name);
                    memberHolder.mPhoneNumber.setText(listMember.get(position).phone_number);
                } else {
                    memberHolder.mName.setVisibility(View.GONE);
                    memberHolder.mPhoneNumber.setVisibility(View.GONE);
                }
            } else if (holder instanceof AddHolder) {
                final AddHolder addHolder = (AddHolder) holder;
                addHolder.mFieldContainer.setVisibility(View.GONE);
                addHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (add) {
                            addHolder.mFieldContainer.setVisibility(View.VISIBLE);
                            addHolder.mAddButton.setImageResource(getResources().getIdentifier("ic_remove_circle", "drawable", getPackageName()));
                            add = false;
                        } else {
                            addHolder.mFieldContainer.setVisibility(View.GONE);
                            addHolder.mAddButton.setImageResource(getResources().getIdentifier("ic_add", "drawable", getPackageName()));
                            add = true;
                        }
                    }
                });
                if (addHolder.mAddSubmit.getVisibility() == View.VISIBLE) {
                    addHolder.mAddSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // post add new member
                            SharedPreferences sharedPreferences = getSharedPreferences(ProfileFragment.PROFILE, Context.MODE_PRIVATE);
                            String uid = sharedPreferences.getString(ProfileFragment.UID, "");
                            BayarTolApi.organizationApi.postAddMember(getApplicationContext(), uid, addHolder.mEmailField.getText().toString(), new Api.ApiListener<OrganizationApi.DataMember>() {
                                @Override
                                public void onApiSuccess(OrganizationApi.DataMember result, String rawJson) {
                                    listMember.clear();
                                    listMember.addAll(result.data);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onApiError(String errorMessage) {
                                    Toast.makeText(ListMemberActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        }

        class AddHolder extends RecyclerView.ViewHolder {
            public ImageView mAddButton;
            public LinearLayout mFieldContainer;
            public Button mAddSubmit;
            public EditText mEmailField;

            public AddHolder(View itemView) {
                super(itemView);
                mAddButton = (ImageView) itemView.findViewById(R.id.add_new_member);
                mFieldContainer = (LinearLayout) itemView.findViewById(R.id.add_field_container);
                mAddSubmit = (Button) itemView.findViewById(R.id.add_new_member_button);
                mEmailField = (EditText) itemView.findViewById(R.id.add_email_field);
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
