package com.example.ihass.pressureproject.Classes;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ihass.pressureproject.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Measurement> mDataset;

    public MyAdapter() {
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        GraphView graphView;

        public ViewHolder(View ItemView) {
            super(ItemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            graphView = itemView.findViewById(R.id.graph);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Measurement> myDataset) {
        this.mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        // Here we put the view of the layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked b    y the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //getting the product of the specified position
        Measurement measure = mDataset.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText("Up: " + String.valueOf(measure.getUpperMeasure()));
        holder.textViewShortDesc.setText("Low: " + String.valueOf(measure.getLowerMeasure()));
        holder.textViewRating.setText(measure.getTime());
        holder.textViewPrice.setText(measure.getDay());
        holder.graphView.addSeries(new BarGraphSeries<DataPoint>(new DataPoint[]{new DataPoint(measure.getTime(), measure.getUpperMeasure())}));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}