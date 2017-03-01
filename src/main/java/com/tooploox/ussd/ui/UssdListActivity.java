package com.tooploox.ussd.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.tooploox.ussd.R;
import com.tooploox.ussd.data.UssdStorage;
import com.tooploox.ussd.domain.Ussd;
import com.tooploox.ussd.utils.Predicate;
import com.tooploox.ussd.utils.Strings;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UssdListActivity extends AppCompatActivity {

    class ActivityViews {

        @BindView(R.id.recycler_view)
        RecyclerView recyclerView;

        @BindString(R.string.empty_result)
        String emptyResult;
    }

    class DialogViews {

        @BindView(R.id.dialog_et_ussd_query)
        EditText etUssdQuery;

        @BindView(R.id.dialog_et_regex)
        EditText etRegex;
    }

    class UiEventsReactor {

        @OnClick(R.id.button_add)
        public void onAddUssdButtonClicked(View button) {
            showAddUssdDialog();
        }

        public void onDialogDoneButtonClicked(DialogInterface dialog, int which) {
            if (etPredicate.apply(dialogViews.etRegex) || etPredicate.apply(dialogViews.etUssdQuery))
                return;

            Ussd ussd = new Ussd();
            ussd.setCode(dialogViews.etUssdQuery.getText().toString());
            ussd.setRegex(dialogViews.etRegex.getText().toString());
            ussd.setResult(activityViews.emptyResult);
            presenter.addUssd(ussd);

            dialog.dismiss();
        }

        public void onUssdItemClicked() {

        }
    }

    private Predicate<EditText> etPredicate = et -> et.getText() == null || Strings.isNullOrEmpty(et.getText().toString());
    private ActivityViews activityViews = new ActivityViews();
    private DialogViews dialogViews = new DialogViews();
    private UiEventsReactor eventsReactor = new UiEventsReactor();
    private UssdListAdapter adapter = new UssdListAdapter();
    private UssdStorage storage = new UssdStorage();
    private Presenter presenter = new Presenter(storage, adapter);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity);

        ButterKnife.bind(activityViews, this);
        ButterKnife.bind(eventsReactor, this);

        activityViews.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityViews.recyclerView.setAdapter(adapter);
    }

    public void showAddUssdDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, eventsReactor::onDialogDoneButtonClicked)
                .create();
        dialog.show();

        ButterKnife.bind(dialogViews, dialog);
    }

//    private String match(Ussd ussd) {
//        Matcher matcher = Pattern
//                .compile(ussd.getRegex())
//                .matcher(ussd.getResponse());
//        return matcher.find()
//                ? matcher.group()
//                : activityViews.emptyResult;
//    }
}