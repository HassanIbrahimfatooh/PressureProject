package com.example.ihass.pressureproject.Classes;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ihass.pressureproject.R;
import com.jjoe64.graphview.GraphView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Measurement> mDataset;

    public MyAdapter() {
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        TextView DayText, UpperMeasureText, LowerMeasureText, TimeText;
        GraphView GraphView;

        ViewHolder(View ItemView) {
            super(ItemView);
            UpperMeasureText = itemView.findViewById(R.id.UpperMeasureText);
            LowerMeasureText = itemView.findViewById(R.id.LowerMeasureText);
            TimeText = itemView.findViewById(R.id.TimeText);
            DayText = itemView.findViewById(R.id.DayText);
            GraphView = itemView.findViewById(R.id.GraphView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Measurement> myDataset) {
        this.mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        // Here we put the view of the layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked b    y the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //getting the product of the specified position
        Measurement measure = mDataset.get(position);

        //binding the data with the viewholder views
        holder.UpperMeasureText.setText("Up: " + String.valueOf(measure.getUpperMeasure()));
        holder.LowerMeasureText.setText("Low: " + String.valueOf(measure.getLowerMeasure()));
        holder.TimeText.setText(measure.getTime());
        holder.DayText.setText(measure.getDay());
//        holder.GraphView.addSeries(new BarGraphSeries<DataPoint>(new DataPoint[]{new DataPoint(Double.parseDouble(measure.getTime()), measure.getUpperMeasure())}));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}