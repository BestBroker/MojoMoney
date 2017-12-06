package com.mojomoney.mojomoney;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EntryAdapter extends
        RecyclerView.Adapter<EntryAdapter.ViewHolder>{

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView betragTextView;
        public TextView datumTextView;
        public ImageView picView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = itemView.findViewById(R.id.ViewName);
            betragTextView = itemView.findViewById(R.id.ViewBetrag);
            datumTextView = itemView.findViewById(R.id.ViewDatum);
            picView = itemView.findViewById(R.id.ViewImage);
            itemView.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it

                Entry tempEntry = mEntries.get(position);
                Intent intent = new Intent (mContext, ViewSingleEntryActivity.class);

                String id = "id";
                int message = tempEntry.getId();

                intent.putExtra(id, message);
                mContext.startActivity(intent);

                Toast.makeText(mContext, "Click...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // ... view holder defined above...

    // Store a member variable for the contacts
    private List<Entry> mEntries;
    // Store the context for easy access
    private Context mContext;

    private int mPixels;

    // Pass in the contact array into the constructor
    public EntryAdapter(Context context, List<Entry> entries) {
        mEntries = entries;
        mContext = context;

        Resources r = context.getResources();
        mPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }


    // ... constructor and member variables

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public EntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View entryView = inflater.inflate(R.layout.recycler_source, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(entryView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(EntryAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Entry entry = mEntries.get(position);

        // Set item views based on your views and data model
        TextView nameView = viewHolder.nameTextView;
        nameView.setText(entry.getName());
        TextView betragView = viewHolder.betragTextView;

        String betrag = Float.toString(entry.getBetrag());

        betragView.setText(betrag + " €");
        TextView datumView = viewHolder.datumTextView;
        datumView.setText(entry.getDatum());
        ImageView imageView = viewHolder.picView;
        if (!entry.getPath().equals(NewEntryActivity.NO_IMAGE_TAKEN)) {
            Bitmap myMap = ImageHandler.loadImageFromStorage(entry.getPath(), mPixels);
            imageView.setImageBitmap(myMap);
            imageView.setBackgroundColor(0xFFFFFF);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mEntries.size();
    }
}
