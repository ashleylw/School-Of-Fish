
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class MainGameView extends GridPane {
    
    /**
     * JavaFX Visual Component
     */
    private Label gameName;
    private Label bigFish;
    
    private VBox roundInfo;
    private Label roundNumber;
    private Label timeLeft;
    
    private Label habitatLabel;
    private Label roleFactLabel;
    
	
	public MainGameView() {	
	    // change later
	    Main.currentPlayer.setIndex(0);
	    
		setUp();
		//checkForBigFish();
	}
	
	private void setUp() {
		this.setPadding(new Insets(20, 20, 20, 20));
		
		this.getColumnConstraints().add(new ColumnConstraints(100)); // column 0 is 100 wide
	    this.getColumnConstraints().add(new ColumnConstraints(150)); // column 1 is 100 wide
	    this.getColumnConstraints().add(new ColumnConstraints(150));
	    this.getColumnConstraints().add(new ColumnConstraints(150));
	    this.getColumnConstraints().add(new ColumnConstraints(100));
	    
	    this.getRowConstraints().add(new RowConstraints(75));
	    this.getRowConstraints().add(new RowConstraints(75));
	    this.getRowConstraints().add(new RowConstraints(75));
	    this.getRowConstraints().add(new RowConstraints(75));
	    this.getRowConstraints().add(new RowConstraints(75));
	    
	    this.setHgap(10);
	    this.setVgap(10);
	    this.setAlignment(Pos.CENTER);
		
		gameName = new Label("Game Name");
		GridPane.setHalignment(gameName, HPos.CENTER);
		if (!Main.game.getName().equalsIgnoreCase("null")) {
			gameName.setText(Main.game.getName());
		}
		
		bigFish = new Label("Big Fish: Name");
		GridPane.setHalignment(bigFish, HPos.CENTER);
		if (!Main.game.getBigFish().equalsIgnoreCase("null")) {
			bigFish.setText("Big Fish: " + Main.game.getBigFish());
		}
		
		roundInfo = new VBox();
		roundInfo.setAlignment(Pos.CENTER);
		roundInfo.setSpacing(20);
		roundInfo.setPadding(new Insets(20, 20, 20, 20));
		roundInfo.setBackground(Main.LIGHTSEAGREEN);
		
		roundNumber = new Label("Round " + Main.game.getCurrentRound());
		timeLeft = new Label(Main.game.getTimeLeft()/60 + " mins " + Main.game.getTimeLeft()%60 + " secs");
		roundInfo.getChildren().addAll(roundNumber, timeLeft);
		
		habitatLabel = new Label("Habitat");
		GridPane.setHalignment(habitatLabel, HPos.CENTER);
		if (Game.playerModel.getPlayerList().get(Main.currentPlayer.getIndex()) != null) {
			habitatLabel.setText(Game.playerModel.getPlayerList().get(Main.currentPlayer.getIndex()).getCurrentHabitat().toString());
		}
		
		roleFactLabel = new Label("Role-Specific Fact");
		GridPane.setHalignment(roleFactLabel, HPos.CENTER);
		if (Game.playerModel.getPlayerList().get(Main.currentPlayer.getIndex()) != null) {
			String role = Game.playerModel.getPlayerList().get(Main.currentPlayer.getIndex()).getRole().toString();
			roleFactLabel.setText("Your role is the " + role);
		}
		
		gameName.setFont(Main.ARIAL_22);
		bigFish.setFont(Main.ARIAL_22);
		roundNumber.setFont(Main.ARIAL_22);
		timeLeft.setFont(Main.ARIAL_22);
		habitatLabel.setFont(Main.ARIAL_22);
		roleFactLabel.setFont(Main.ARIAL_22);
		
		this.add(gameName, 0, 0, 5, 1);
		this.add(bigFish, 0, 1, 5, 1);
		this.add(roundInfo, 0, 2, 3, 3);
		this.add(habitatLabel, 3, 2, 3, 1);
		this.add(roleFactLabel, 3, 3, 3, 2);
	}
    
}
