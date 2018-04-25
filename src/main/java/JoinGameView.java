import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class JoinGameView extends GridPane{

	/**
     * JavaFX Visual Component
     */
    private Label gameRunningLabel;
    
    private Label gameNameLabel;
    private Label bigFishLabel;
    
    private VBox roundInfo;
    private Label roundNumberLabel;
    private Label roundLengthLabel;
    private Label preRoundLengthLabel;
    
    private Label playerNameLabel;
    public TextField playerNameTF;
    
	
	public JoinGameView() {
		setUp();
	}
	
	private void setUp() {
		this.setPadding(new Insets(20, 20, 20, 20));
		
		this.getColumnConstraints().add(new ColumnConstraints(300)); // column 0 is 100 wide
	    this.getColumnConstraints().add(new ColumnConstraints(300)); // column 1 is 100 wide
	    
	    this.getRowConstraints().add(new RowConstraints(75));
	    this.getRowConstraints().add(new RowConstraints(75));
	    this.getRowConstraints().add(new RowConstraints(75));
	    this.getRowConstraints().add(new RowConstraints(75));
	    this.getRowConstraints().add(new RowConstraints(75));
	    this.getRowConstraints().add(new RowConstraints(75));
	    
	    this.setHgap(10);
	    this.setVgap(10);
	    this.setAlignment(Pos.CENTER);
		
		gameRunningLabel = new Label("There is a game currently seeking players.\nEnter your name to Join!");
		GridPane.setHalignment(gameRunningLabel, HPos.CENTER);
		
		gameNameLabel = new Label("Game Name: " + Main.game.getName());
		GridPane.setHalignment(gameNameLabel, HPos.CENTER);
		
		bigFishLabel = new Label("Big Fish: " + Main.game.getBigFish());
		GridPane.setHalignment(bigFishLabel, HPos.CENTER);
		
		roundInfo = new VBox();
		roundInfo.setAlignment(Pos.CENTER);
		roundInfo.setSpacing(10);
		roundInfo.setPadding(new Insets(10, 10, 10, 10));
		roundInfo.setBackground(Main.LIGHTSEAGREEN);
		
		roundNumberLabel = new Label("Number of Rounds:\n\t" + Main.game.getNumRounds());
		roundLengthLabel = new Label("Round Length:\n\t" + Main.game.getRoundLength()/60 + " mins "
				+ Main.game.getRoundLength()%60 + " secs");
		preRoundLengthLabel = new Label("PreRound Length:\n\t" + Main.game.getPreRoundLength()/60 + " mins "
				+ Main.game.getPreRoundLength()%60 + " secs");
		
		roundInfo.getChildren().addAll(roundNumberLabel, roundLengthLabel, preRoundLengthLabel);
		
		playerNameLabel = new Label("Your Name:");
		playerNameLabel.setFont(Main.ARIAL_22);
		playerNameTF = new TextField();
		playerNameTF.setFont(Main.ARIAL_22);
		
		gameRunningLabel.setFont(Main.ARIAL_22);
		gameNameLabel.setFont(Main.ARIAL_22);
		bigFishLabel.setFont(Main.ARIAL_22);
		
		roundNumberLabel.setFont(Main.ARIAL_22);
		roundLengthLabel.setFont(Main.ARIAL_22);
		preRoundLengthLabel.setFont(Main.ARIAL_22);
		
		this.add(gameRunningLabel, 0, 0, 2, 1);
		this.add(gameNameLabel, 0, 1, 2, 1);
		this.add(bigFishLabel, 0, 2, 2, 1);
		
		this.add(roundInfo, 0, 3, 1, 3);
		this.add(playerNameLabel, 1, 3);
		this.add(playerNameTF, 1, 4);
	}

	
}
