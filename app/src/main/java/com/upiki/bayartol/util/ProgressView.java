package com.upiki.bayartol.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.upiki.bayartol.R;

/**
 * Created by taufic on 7/27/2017.
 */

public class ProgressView extends LinearLayout {


    private ViewGroup mLoadingContainer;
    private TextView mErrorLoadingMsg;
    private Button mRetryButton;

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.view_progressbar, null);

        mLoadingContainer = (RelativeLayout) view.findViewById(R.id.loading_container);
        mErrorLoadingMsg = (TextView) view.findViewById(R.id.error_text);
        mRetryButton = (Button) view.findViewById(R.id.retry_button);

        addView(view);
    }

    public void startProgressBar() {
        setVisibility(VISIBLE);
        mLoadingContainer.setVisibility(VISIBLE);
        mErrorLoadingMsg.setVisibility(GONE);
        mRetryButton.setVisibility(GONE);
    }

    public void stopProgressBar() {
        setVisibility(GONE);
    }

    public void stopShowError(String errorMessage, boolean isRetry) {
        mLoadingContainer.clearAnimation();
        mLoadingContainer.setVisibility(GONE);
        mRetryButton.setVisibility(isRetry? VISIBLE : GONE);
        mErrorLoadingMsg.setVisibility(VISIBLE);
        mErrorLoadingMsg.setText(errorMessage);
    }

    public void setRefresh(OnClickListener onClickListener) {
        mRetryButton.setOnClickListener(onClickListener);
    }

}
