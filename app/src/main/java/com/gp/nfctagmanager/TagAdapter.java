package com.gp.nfctagmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagHolder> {
    private List<Tag> tags = new ArrayList<>();

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_item, parent, false);
        return new TagHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        Tag currentTag = tags.get(position);
        holder.textViewName.setText(currentTag.getName());
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
        notifyDataSetChanged();
    }

    class TagHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;

        public TagHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
        }
    }
}
