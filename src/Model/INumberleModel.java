package Model;

import java.util.HashMap;

public interface INumberleModel {
    /*@ public invariant MAX_ATTEMPTS == 6; @*/
    int MAX_ATTEMPTS = 6;

    /*@ public normal_behavior
      @ ensures \result == true;
      @*/
    boolean invariant();

    /*@ public normal_behavior
      @ ensures \fresh(\result);
      @*/
    void initialize();

    /*@ public normal_behavior
      @ requires input != null;
      @*/
    void processInput(String input);

    /*@ public normal_behavior
      @ ensures \result == true || \result == false;
      @*/
    boolean isGameOver();

    /*@ public normal_behavior
      @ ensures \result == true || \result == false;
      @*/
    boolean isGameWon();

    /*@ public normal_behavior
      @ ensures \result != null;
      @*/
    String getTargetNumber();

    /*@ public normal_behavior
      @ ensures \result != null;
      @*/
    StringBuilder getCurrentGuess();

    /*@ public normal_behavior
      @ ensures \result >= 0;
      @*/
    int getRemainingAttempts();

    /*@ public normal_behavior
      @ ensures getRemainingAttempts() == MAX_ATTEMPTS;
      @*/
    void startNewGame();

    /*@ public normal_behavior
      @ ensures \result != null;
      @ ensures \result.length == 2;
      @*/
    int[] getTopColor();

    /*@ public normal_behavior
      @ ensures \result != null;
      @*/
    HashMap<Character, Integer> getButtonStatus();

    /*@ public normal_behavior
      @ ensures \result != null;
      @ ensures \result.length > 0;
      @*/
    boolean[] getFlags();
}
