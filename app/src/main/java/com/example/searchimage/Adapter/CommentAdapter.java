package com.example.searchimage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.searchimage.PojoCalss.PojoComment;
import com.example.searchimage.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.contactViewHolder> {

    Context context;
    public static ArrayList<PojoComment> commentArrayList;

    public CommentAdapter(Context context, ArrayList<PojoComment> commentArrayList) {
        this.context = context;
        this.commentArrayList = commentArrayList;
    }

    @Override
    public void onViewDetachedFromWindow(CommentAdapter.contactViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public CommentAdapter.contactViewHolder onCreateViewHolder(ViewGroup parent, int itemViewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.activity_commentadapter, parent, false);
        return new CommentAdapter.contactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CommentAdapter.contactViewHolder holder, final int position) {
        try {
            holder.mTxtComment.setText((position + 1) + " : " + commentArrayList.get(position).getComment());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    public static PojoComment getItem(int position) {
        return commentArrayList.get(position);
    }

    public class contactViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtComment;

        public contactViewHolder(View itemView) {
            super(itemView);
            mTxtComment = (TextView) itemView.findViewById(R.id.mTxtComment);
        }
    }
}
