package ck.bestgameintheworld;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private ListView listView;
    private ResultsAdapter adapter;
    private DatabaseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        handler = new DatabaseHandler(this);
        Cursor cursor = handler.getResultData();
        List<Result> items = getResultsFromCursor(cursor);
        adapter = new ResultsAdapter(items);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }

    private List<Result> getResultsFromCursor(Cursor cursor) {
        List<Result> results = new ArrayList<Result>();
        while (cursor.moveToNext()) {
            results.add(new Result(cursor.getString(1), cursor.getInt(2)));
        }
        return results;
    }
}
