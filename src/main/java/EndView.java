import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class EndView extends VBox {

	/**
     * JavaFX Visual Component
     */
    private Label resultsLabel;
    private ListView<String> playerResultsList;
    
    private Label restartLabel;
	
	public EndView() {
		setUp();
	}
	
	private void setUp() {
		this.setSpacing(20);
		this.setPadding(new Insets(20, 20, 20, 20));
	    this.setAlignment(Pos.CENTER);
	    
	    resultsLabel = new Label();
	    if (Main.currentPlayer.getSheetsIndex() == 13) {
	    	resultsLabel.setText("Game Over! Here are the results:");
	    } else {
		    if (Game.playerModel.getPlayerList().get(Main.currentPlayer.getSheetsIndex()).getWinStatus()) {
		    	// You won!
		    	resultsLabel.setText("You won!");
		    } else {
		    	// You lost.
		    	resultsLabel.setText("You lost.");
		    }
	    }
	    
	    playerResultsList = new ListView<String>();
	    playerResultsList.setPrefWidth(400);
	    playerResultsList.setPrefHeight(300);
	    ObservableList<String> strings = FXCollections.observableArrayList();
	    for (Player p : Game.playerModel.getPlayerList()) {
	    	strings.add(p.getName() + " was the " + p.getRole().getName() + ". They " + (p.getWinStatus() ? "won" : "lost") + ".");
	    }
	    playerResultsList.setItems(strings);
		
		restartLabel = new Label("Restart to play again!");
	    
		resultsLabel.setFont(Main.ARIAL_22);
		restartLabel.setFont(Main.ARIAL_22);
		
		this.getChildren().addAll(resultsLabel, playerResultsList, restartLabel);
	}

	
}
