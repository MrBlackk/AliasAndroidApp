package com.mrb.alias.win;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mrb.alias.R;
import com.mrb.alias.results.Game;
import com.mrb.alias.start.StartActivity;
import com.mrb.alias.team.Team;
import com.mrb.alias.team.TeamAdapter;
import com.mrb.alias.utils.SharedPreference;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WinActivity extends AppCompatActivity implements WinView {

    @Bind(R.id.win_tvWinner)
    TextView tvWinner;

    @Bind(R.id.win_lvResults)
    ListView lvResults;

    @Bind(R.id.win_btnGoToMenu)
    Button btnGoToMenu;

    private SharedPreference sharedPreference;
    private WinPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        ButterKnife.bind(this);
        sharedPreference = new SharedPreference();

        presenter = new WinPresenterImpl(this);
        presenter.getWinner();
        presenter.getResults();

        runListeners();
    }

    private void runListeners(){
        btnGoToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onGoToMenuButtonClick();
            }
        });
    }

    @Override
    public void showWinner(String winner) {
        tvWinner.setText(winner);
    }

    @Override
    public Game loadGame() {
        return sharedPreference.loadGame(this);
    }

    @Override
    public void showResults(ArrayList<Team> arrayList) {
        TeamAdapter adapter = new TeamAdapter(this, arrayList, true);
        lvResults.setAdapter(adapter);
    }

    @Override
    public void navigateToMenu() {
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }

    @Override
    public void saveGame(Game game) {
        sharedPreference.saveGame(this,game);
    }
}
