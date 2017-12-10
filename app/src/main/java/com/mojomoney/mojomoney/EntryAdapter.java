package com.mojomoney.mojomoney;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class EntryAdapter extends
        RecyclerView.Adapter<EntryAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTextView;
        public TextView betragTextView;
        public TextView datumTextView;
        public ImageView picView;

        public ViewHolder(View itemView) {

            super(itemView);

            nameTextView = itemView.findViewById(R.id.ViewName);
            betragTextView = itemView.findViewById(R.id.ViewBetrag);
            datumTextView = itemView.findViewById(R.id.ViewDatum);
            picView = itemView.findViewById(R.id.ViewImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {

                Entry tempEntry = mEntries.get(position);
                Intent intent = new Intent (mContext, ViewSingleEntryActivity.class);

                String id = "id";
                int message = tempEntry.getId();

                intent.putExtra(id, message);
                mContext.startActivity(intent);
            }
        }
    }

    private List<Entry> mEntries;

    private Context mContext;

    private int mPixels;

    public EntryAdapter(Context context, List<Entry> entries) {
        mEntries = entries;
        mContext = context;

        Resources r = context.getResources();
        mPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public EntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View entryView = inflater.inflate(R.layout.recycler_source, parent, false);

        ViewHolder viewHolder = new ViewHolder(entryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EntryAdapter.ViewHolder viewHolder, int position) {

        Entry entry = mEntries.get(position);

        TextView nameView = viewHolder.nameTextView;
        nameView.setText(entry.getName());
        TextView betragView = viewHolder.betragTextView;

        String betrag = Float.toString(entry.getBetrag());

        betragView.setText(betrag + " €");
        TextView datumView = viewHolder.datumTextView;
        datumView.setText(entry.getDatum());
        ImageView imageView = viewHolder.picView;
        if (!entry.getPath().equals(NewEntryActivity.NO_IMAGE_TAKEN)) {
            GlideApp
                    .with(mContext)
                    .load(entry.getPath())
                    .centerCrop()
                    .into(imageView);

            imageView.setBackgroundColor(0xFFFFFF);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }
}
