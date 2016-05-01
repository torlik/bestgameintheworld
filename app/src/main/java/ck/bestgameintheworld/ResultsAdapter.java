package ck.bestgameintheworld;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import ck.bestgameintheworld.R;

/**
 * Created by Torlik on 2016.04.18..
 */
public class ResultsAdapter extends BaseAdapter {

    private List<Result> results;

    public ResultsAdapter(List<Result> items) {
        results = items;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int i) {
        return results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        Result result = results.get(i);
        ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.result_layout, null);
            holder = new ViewHolder();
            holder.name = (AppCompatTextView) convertView.findViewById(R.id.name);
            holder.score = (AppCompatTextView) convertView.findViewById(R.id.score);
            convertView.setTag(holder);
        }
        else        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText("Name: " + result.getName());
        holder.score.setText("Score: " + result.getScore());
        return convertView;
    }

    class ViewHolder{
        AppCompatTextView name, score;
    }
}
