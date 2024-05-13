package Controller;

import Model.INumberleModel;
import View.NumberleView;

import java.util.HashMap;

// Controller.NumberleController.java
public class NumberleController {
    private INumberleModel model;
    private NumberleView view;

    /*@
      @ requires model != null;
      @ ensures this.model == model;
      @*/
    public NumberleController(INumberleModel model) {
        this.model = model;
    }

    /*@
      @ requires view != null;
      @ ensures this.view == view;
      @*/
    public void setView(NumberleView view) {
        this.view = view;
    }

    /*@
      @ requires input != null;
      @*/
    public void processInput(String input) {
        model.processInput(input);
    }

    /*@
      @ ensures \result == model.isGameOver();
      @*/
    public boolean isGameOver() {
        return model.isGameOver();
    }

    /*@
      @ ensures \result == model.isGameWon();
      @*/
    public boolean isGameWon() {
        return model.isGameWon();
    }

    /*@
      @ ensures \result.equals(model.getTargetNumber());
      @*/
    public String getTargetNumber() {
        return model.getTargetNumber();
    }

    /*@
      @ ensures \result == model.getCurrentGuess();
      @*/
    public StringBuilder getCurrentGuess() {
        return model.getCurrentGuess();
    }

    /*@
      @ ensures \result == model.getRemainingAttempts();
      @*/
    public int getRemainingAttempts() {
        return model.getRemainingAttempts();
    }

    /*@
      @ ensures model.getRemainingAttempts() == INumberleModel.MAX_ATTEMPTS;
      @*/
    public void startNewGame() {
        model.startNewGame();
    }

    /*@
      @ ensures \result == model.getTopColor();
      @*/
    public int[] getTopcolor() {
        return model.getTopColor();
    }

    /*@
      @ ensures \result == model.getButtonStatus();
      @*/
    public HashMap<Character, Integer> getButtonStatus() {
        return model.getButtonStatus();
    }

    /*@
      @ ensures \result == model.getFlags();
      @*/
    public boolean[] getFlags() {
        return model.getFlags();
    }
}
