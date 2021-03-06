package com.mrb.alias.start;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mrb.alias.BuildConfig;
import com.mrb.alias.R;
import com.mrb.alias.results.Game;
import com.mrb.alias.results.ResultsActivity;
import com.mrb.alias.rules.RulesActivity;
import com.mrb.alias.team.TeamActivity;
import com.mrb.alias.utils.DataBaseHelper;
import com.mrb.alias.utils.SharedPreference;
import com.rey.material.widget.Button;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity implements StartView {

    @Bind(R.id.start_btnNewGame)
    Button btnNewGame;

    @Bind(R.id.start_btnRules)
    Button btnRules;

    @Bind(R.id.start_btnExit)
    Button btnExit;

    @Bind(R.id.start_btnContinue)
    Button btnContinue;

    @Bind(R.id.start_tvVersion)
    TextView tvVersion;

    private StartPresenter presenter;
    private SharedPreference sharedPreference;
    private DataBaseHelper dataBaseHelper;

    /**
     * On create activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        sharedPreference = new SharedPreference();

        presenter = new StartPresenterImpl(this);
        this.runListeners();

        presenter.onStart();

        //Debug mode only
        String version = getString(R.string.start_version) + BuildConfig.VERSION_NAME;
        tvVersion.setText(version);
    }

    /**
     * Run listeners on button click and etc.
     */
    protected void runListeners() {
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onNewGameButtonClick();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onExitButtonClick();
            }
        });
        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRulesButtonClick();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onContinueButtonClick();
            }
        });
    }

    /**
     * Navigate to Team activity
     */
    @Override
    public void navigateToTeam() {
        startActivity(new Intent(this, TeamActivity.class));
        finish();
    }

    /**
     * Navigate to Results activity
     */
    @Override
    public void navigateToResults() {
        startActivity(new Intent(this, ResultsActivity.class));
        finish();
    }

    /**
     * Navigate to Rules activity
     */
    @Override
    public void navigateToRules() {
        startActivity(new Intent(this, RulesActivity.class));
        finish();
    }

    /**
     * Exit from application
     */
    @Override
    public void exit() {
        finish();
    }

    /**
     * Clear Shared Preferences
     */
    @Override
    public void clearPreferences() {
        sharedPreference.clearPreferences(this);
    }

    /**
     * Hide Continue game button
     */
    @Override
    public void hideContinueButton() {
        btnContinue.setVisibility(View.GONE);
    }

    /**
     * Load Game
     */
    @Override
    public Game loadGame() {
        return sharedPreference.loadGame(this);
    }

    @Override
    public void showContinueGameDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.start_continue_dialog_title)
                .setMessage(R.string.start_continue_dialog_message)
                .setPositiveButton(R.string.start_dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onContinueDialogYesButtonClick();
                    }
                })
                .setNegativeButton(R.string.start_dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    @Override
    public void showExitGameDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.start_exit_dialog_title)
                .setMessage(R.string.start_exit_dialog_message)
                .setPositiveButton(R.string.start_dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onExitDialogYesButtonClick();
                    }
                })
                .setNegativeButton(R.string.start_dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    /**
     * Copy database if it is not exist
     */
    @Override
    public void copyDatabase() {
        dataBaseHelper = new DataBaseHelper(this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
    }

    /**
     * On destroy activity
     */
    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    /**
     * On resume activity
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * On back button pressed
     */
    @Override
    public void onBackPressed() {
        presenter.onBackButtonPressed();
    }
}
