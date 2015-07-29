package it.playfellas.superapp.activities.master.game1;

import it.playfellas.superapp.activities.Config;
import lombok.Data;

/**
 * Created by Stefano Cappa on 29/07/15.
 */
@Data
public class Config1 extends Config {

    private int rule, difficulty, density, consecutiveAnswer, stageNumber, consecutiveAnswerChangeRule;
    private boolean increasingSpeed;
}
