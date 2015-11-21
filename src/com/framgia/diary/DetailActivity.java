package com.framgia.diary;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.framgia.diary.customview.SecondLineEditText;
import com.framgia.diary.model.Diary;
import com.framgia.diary.util.FontServices;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

public class DetailActivity extends AbsActivity {
    private boolean isModeView = true;
    private Diary diary;
    private SecondLineEditText content, createdDate;

    @Override
    protected String getActionBarTitle() {
        return null;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        isModeView = getIntent().getBooleanExtra("isViewMode", false);
        long id = getIntent().getLongExtra("id", 0L);
        if (id > 0) {
            diary = Diary.findById(Diary.class, id);
        }
        content = (SecondLineEditText) findViewById(R.id.content);
        createdDate = (SecondLineEditText) findViewById(R.id.createdDate);
        FontServices.getInstance(this).setTypeface(content, createdDate);
        updateContent();
        invalidateOptionsMenu();
    }

    private void updateContent() {
        String date =
            new SimpleDateFormat("dd-MM/yyyy HH:mm").format(new Date(System.currentTimeMillis()));
        if (diary != null && !TextUtils.isEmpty(diary.content)) {
            content.setText(diary.content);
            if (diary.created_date > 0)
                date =
                    new SimpleDateFormat("dd-MM/yyyy HH:mm").format(new Date(diary.created_date));
        }
        createdDate.setText(" Created on " + date);
    }

    private void updateMenu(Menu menu) {
        menu.findItem(R.id.action_save).setVisible(!isModeView);
        menu.findItem(R.id.action_edit).setVisible(isModeView);
        content.setEditable(!isModeView);
        content.setCursorVisible(!isModeView);
        content.setSelection(content.length());
        onHideKeyboard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        updateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            onActionSave();
        } else if (id == R.id.action_edit) {
            onActionEdit();
        } else if (id == R.id.action_delete) {
            onActionDelete();
        }
        return true;
    }

    private void onActionDelete() {
        if (diary == null)
            return;
        diary.deleted = true;
        long deletedId = diary.save();
        if (deletedId > 0)
            finish();
        else
            showError();
    }

    private void showError() {
        final AlertDialog dialog =
            new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                .setMessage("Error! Please contact developer to get support.\n\n")
                .setCancelable(true).setPositiveButton("OK", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void onActionEdit() {
        isModeView = false;
        invalidateOptionsMenu();
    }

    private void onActionSave() {
        if (TextUtils.isEmpty(content.getText().toString()))
            return;
        if (diary == null) {
            diary = new Diary();
            diary.created_date = System.currentTimeMillis();
        }
        diary.update_date = System.currentTimeMillis();
        diary.content = content.getText().toString();
        long savedId = diary.save();
        if (savedId > 0) {
            isModeView = true;
            invalidateOptionsMenu();
        } else {
            showError();
        }
    }

    public void onHideKeyboard() {
        InputMethodManager inputManager =
            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() == null)
            return;
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
