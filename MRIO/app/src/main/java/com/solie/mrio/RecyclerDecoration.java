package com.solie.mrio;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerDecoration extends RecyclerView.ItemDecoration {
    private final int divBottom;
    private final int divTop;
    private final int divRight;
    private final int divLeft;

    public RecyclerDecoration (int divBottom, int divRight, int divTop, int divLeft) {
        this.divBottom = divBottom;
        this.divRight = divRight;
        this.divTop = divTop;
        this.divLeft = divLeft;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount()) {
            outRect.bottom = divBottom;
            outRect.top = divTop;
            outRect.right = divRight;
            outRect.left = divLeft;
        }
    }
}
