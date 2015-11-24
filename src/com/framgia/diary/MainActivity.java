package com.framgia.diary;

import java.util.List;

import com.framgia.diary.adapter.DiaryAdapter;
import com.framgia.diary.model.Diary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AbsActivity {
    private GridView gridView;

    @Override
    protected String getActionBarTitle() {
        return null;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setVerticalSpacing(10);
        gridView.setHorizontalSpacing(10);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity.onCreate(...).new OnItemClickListener() {...}",
                    "onItemClick: -----> dsds");
                Diary diary = (Diary) parent.getAdapter().getItem(position);
                onActionAddOrViewDiary(diary);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Diary> diaries = Diary.find(Diary.class, " deleted = 0");
        if (diaries == null)
            return;
        DiaryAdapter adapter = new DiaryAdapter(this, diaries);
        gridView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            onActionAddOrViewDiary(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActionAddOrViewDiary(Diary diary) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("isViewMode", diary != null);
        Bundle bundle = new Bundle();
        if (diary != null) {
            intent.putExtra("id", diary.getId());
            bundle.putSerializable(Diary.class.getName(), diary);
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
