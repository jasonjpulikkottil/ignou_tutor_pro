package com.jdots.ignoupro;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created for chatting on 16/12/2016.
 */

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private final int visibleThreshold = 10;
    private final int startingPageIndex = 0;
    RecyclerView.LayoutManager mLayoutManager;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int totalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (firstVisibleItemPosition - visibleThreshold) <= 0) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, view);
            loading = true;
        }
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

}
