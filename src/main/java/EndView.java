import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EndView extends VBox {

	/**
     * JavaFX Visual Component
     */
    private Label resultsLabel;
    private ListView<HBox> playerResultsList;
    
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
	    
	    playerResultsList = new ListView<HBox>();
	    playerResultsList.setPrefWidth(400);
	    playerResultsList.setPrefHeight(300);
	    ObservableList<HBox> strings = FXCollections.observableArrayList();
	    for (Player p : Game.playerModel.getPlayerList()) {
	    	HBox row = new HBox();
		    row.setPadding(new Insets(5,5,5,5));
		    row.setSpacing(5);
		    row.setAlignment(Pos.CENTER_LEFT);
		    
	        ImageView rowImage = new ImageView(new Image("images/" + p.getRole() + ".png"));
	        rowImage.setFitHeight(60);
	        rowImage.setPreserveRatio(true);
	        Label rowLabel = new Label(p.getName() + " was the " + p.getRole().getName() + ". They " + (p.getWinStatus() ? "won" : "lost") + ".");
	        if (p.getWinStatus()) {
	        	row.setBackground(Main.LIGHTGREEN);
	        } else {
	        	row.setBackground(Main.LIGHTPINK);
	        }
	        rowLabel.setFont(new Font("Arial", 16));
	        
	        row.getChildren().addAll(rowImage, rowLabel);
	        strings.add(row);
	    }
	    playerResultsList.setItems(strings);
		
		restartLabel = new Label("Restart to play again!");
	    
		resultsLabel.setFont(Main.ARIAL_22);
		restartLabel.setFont(Main.ARIAL_22);
		
		this.getChildren().addAll(resultsLabel, playerResultsList, restartLabel);
	}

	
}
