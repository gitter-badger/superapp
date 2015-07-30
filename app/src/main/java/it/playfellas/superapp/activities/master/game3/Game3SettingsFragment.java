package it.playfellas.superapp.activities.master.game3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.playfellas.superapp.R;
import it.playfellas.superapp.activities.master.StartGameListener;
import it.playfellas.superapp.activities.master.game2.Config2;

public class Game3SettingsFragment extends Fragment {

    public static final String TAG = Game3SettingsFragment.class.getSimpleName();

    @Bind(R.id.difficultySpinner)
    public Spinner difficultySpinner;

    @Bind(R.id.densitySeekBar)
    public SeekBar densitySeekBar;
    @Bind(R.id.consecutiveAnswerSeekBar)
    public SeekBar consecutiveAnswerSeekBar;
    @Bind(R.id.stagesSeekBar)
    public SeekBar stagesSeekBar;

    @Bind(R.id.increasingSpeeCheckBox)
    public CheckBox increasingSpeedCheckBox;

    @Bind(R.id.startButton)
    public Button startButton;

    private SharedPreferences sharedPref;

    private Config3 config;

    /**
     * Method to obtain a new Fragment's instance.
     *
     * @return This Fragment instance.
     */
    public static Game3SettingsFragment newInstance() {
        return new Game3SettingsFragment();
    }

    public Game3SettingsFragment() {
        config = new Config3();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.game3_settings_fragment, container, false);

        //ButterKnife bind version for fragments
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_key_game3), Context.MODE_PRIVATE);

        this.initDifficultySpinner();

        readPreferences();
    }

    /**
     * Method to read preferences
     */
    private void readPreferences() {
        config.setRule(sharedPref.getInt("rule", 0));
        config.setDifficulty(sharedPref.getInt("difficulty", 4));
        config.setDensity(sharedPref.getInt("density", 4));
        config.setConsecutiveAnswer(sharedPref.getInt("consecutiveAnswer", 4));
        config.setStageNumber(sharedPref.getInt("stageNumber", 4));
        config.setIncreasingSpeed(sharedPref.getBoolean("increasingSpeed", false));

        difficultySpinner.setSelection(config.getDifficulty());
        densitySeekBar.setProgress(config.getDensity());
        consecutiveAnswerSeekBar.setProgress(config.getConsecutiveAnswer());
        stagesSeekBar.setProgress(config.getStageNumber());
        increasingSpeedCheckBox.setChecked(config.isIncreasingSpeed());
    }

    /**
     * Method to save preferences
     */
    private void savePreferences() {
        config.setDifficulty(difficultySpinner.getSelectedItemPosition());
        config.setDensity(densitySeekBar.getProgress());
        config.setConsecutiveAnswer(consecutiveAnswerSeekBar.getProgress());
        config.setStageNumber(stagesSeekBar.getProgress());
        config.setIncreasingSpeed(increasingSpeedCheckBox.isChecked());

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("rule", config.getRule());
        editor.putInt("difficulty", config.getDifficulty());
        editor.putInt("density", config.getDensity());
        editor.putInt("consecutiveAnswer", config.getConsecutiveAnswer());
        editor.putInt("stageNumber", config.getStageNumber());
        editor.putBoolean("increasingSpeed", config.isIncreasingSpeed());
        editor.apply();
    }


    private void initDifficultySpinner() {
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.difficultySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.difficulty_string_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @OnClick(R.id.startButton)
    public void onClickStartButton(View view) {
        ((StartGameListener) getActivity()).startGame3();
        savePreferences();
    }
}