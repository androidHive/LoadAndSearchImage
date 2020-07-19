package com.example.searchimage.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.searchimage.Activity.OpenImgActivity;
import com.example.searchimage.PojoCalss.PojoData;
import com.example.searchimage.R;

import java.util.ArrayList;

public class RecylerViewImgAdapter extends RecyclerView.Adapter<RecylerViewImgAdapter.contactViewHolder> {

    Context context;
    public static ArrayList<PojoData> gridDataArrayList;
    public final int TYPE_MOVIE = 0;
    boolean isLoading = false, isMoreDataAvailable = true;

    public RecylerViewImgAdapter(Context context, ArrayList<PojoData> gridDataArrayList) {
        this.context = context;
        this.gridDataArrayList = gridDataArrayList;
    }

    @Override
    public void onViewDetachedFromWindow(contactViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public RecylerViewImgAdapter.contactViewHolder onCreateViewHolder(ViewGroup parent, int itemViewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        return new contactViewHolder(layoutInflater.inflate(R.layout.recycler_adapter, parent, false));

    }

    @Override
    public void onBindViewHolder(final RecylerViewImgAdapter.contactViewHolder holder, final int position) {

        try {

            RequestOptions myOptions = new RequestOptions()
                    .centerCrop();
            Glide.with(context)
                    .asBitmap()
                    .apply(myOptions)
                    .load(gridDataArrayList.get(position).getLink())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OpenImgActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("imgId", gridDataArrayList.get(position).getImgid());
                    intent.putExtra("link", gridDataArrayList.get(position).getLink());
                    ((Activity) context).startActivityForResult(intent, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return gridDataArrayList.size();
    }

    public static PojoData getItem(int position) {
        return gridDataArrayList.get(position);
    }

    public class contactViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public contactViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }
}