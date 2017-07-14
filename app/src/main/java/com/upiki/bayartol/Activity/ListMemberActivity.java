package com.upiki.bayartol.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.upiki.bayartol.R;

import java.util.List;

public class ListMemberActivity extends AppCompatActivity {

    RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_member);


    }

    public static void startThisActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ListMemberActivity.class));
    }

    class MemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int MEMBER = 0;
        private final int ADD = 1;

        private List<?> listMember;

        public MemberAdapter(List<?> listMember) {
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

            } else if (holder instanceof AddHolder) {

            }
        }

        class AddHolder extends RecyclerView.ViewHolder {
            public ImageView mAddButton;
            public TextView mAddLabel;

            public AddHolder(View itemView) {
                super(itemView);
                mAddButton = (ImageView) itemView.findViewById(R.id.add_new_member);
                mAddLabel = (TextView) itemView.findViewById(R.id.add_new_member_label);
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
