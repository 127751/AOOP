import Controller.NumberleController;
import Model.INumberleModel;
import Model.NumberleModel;
import View.NumberleView;

/**
 * Main class for initializing and displaying the GUI for the Numberle game.
 * This class sets up the model, view, and controller, and runs the game in a GUI.
 */
public class GUIApp {
    /**
     * The main method that launches the GUI application.
     * It schedules the GUI creation to be run by the Swing event dispatcher thread for thread safety.
     *
     * @param args the command line arguments (not used in this application)
     * @ensures the GUI is initialized and displayed safely in the Swing event dispatch thread
     */
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        createAndShowGUI();
                    }
                }
        );
    }
    /**
     * Initializes the components of the Numberle game, including the model, controller, and view.
     * This method ensures that the game is set up and the initial state of the GUI is ready for interaction.
     *
     * @ensures a new game model and controller are created
     * @ensures the view is initialized with the model and controller
     * @ensures game state is ready to start and displayed correctly
     */
    public static void createAndShowGUI() {
        INumberleModel model = new NumberleModel();
        NumberleController controller = new NumberleController(model);
        while (!controller.isGameOver()) {

        }
        NumberleView view = new NumberleView(model, controller);
    }
}