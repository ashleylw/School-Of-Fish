import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class CreateGameView extends GridPane{

	/**
     * JavaFX Visual Component
     */
    private Label noGameRunningLabel;
    
    private Label gameNameLabel;
    public TextField gameNameTF;
    private Label bigFishLabel;
    public TextField bigFishTF;
    
    private GridPane roundInfo;
    private Label roundNumberLabel;
    public ComboBox<Integer> roundNumberCB;
    private Label roundLengthLabel;
    public ComboBox<String> roundLengthCB;
    private Label preRoundLengthLabel;
    public ComboBox<String> preRoundLengthCB;
    
    public Button createGameButton; 
    public Label pendingLabel;
	
	public CreateGameView() {	
	    Main.currentPlayer.setSheetsIndex(13);
	    
		setUp();
	}
	
	private void setUp() {
		this.setPadding(new Insets(20, 20, 20, 20));
		
		this.getColumnConstraints().add(new ColumnConstraints(300)); // column 0 is 100 wide
	    this.getColumnConstraints().add(new ColumnConstraints(300)); // column 1 is 100 wide
	    
	    this.getRowConstraints().add(new RowConstraints(50));
	    this.getRowConstraints().add(new RowConstraints(50));
	    this.getRowConstraints().add(new RowConstraints(50));
	    this.getRowConstraints().add(new RowConstraints(50));
	    this.getRowConstraints().add(new RowConstraints(50));
	    this.getRowConstraints().add(new RowConstraints(50));
	    this.getRowConstraints().add(new RowConstraints(50));
	    
	    this.setHgap(20);
	    this.setVgap(20);
	    this.setAlignment(Pos.CENTER);
		
		noGameRunningLabel = new Label("There is no game currently running. Create your own game!");
		GridPane.setHalignment(noGameRunningLabel, HPos.CENTER);
		
		gameNameLabel = new Label("Game Name:");
		GridPane.setHalignment(gameNameLabel, HPos.RIGHT);
		gameNameTF = new TextField();
		GridPane.setHalignment(gameNameTF, HPos.LEFT);
		
		bigFishLabel = new Label("Big Fish (Your Name):");
		GridPane.setHalignment(bigFishLabel, HPos.RIGHT);
		bigFishTF = new TextField();
		GridPane.setHalignment(bigFishTF, HPos.LEFT);		
		
		roundInfo = new GridPane();
		roundInfo.setAlignment(Pos.CENTER);
		roundInfo.setPadding(new Insets(10, 10, 10, 10));
		roundInfo.setBackground(Main.LIGHTSEAGREEN);
	    roundInfo.getRowConstraints().add(new RowConstraints(50));
	    roundInfo.getRowConstraints().add(new RowConstraints(50));
	    roundInfo.getRowConstraints().add(new RowConstraints(50));
	    roundInfo.setHgap(10);
	    roundInfo.setVgap(10);
		
		roundNumberLabel = new Label("Number of Rounds:");
		roundNumberCB = new ComboBox<Integer>();
		roundNumberCB.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8);
		roundNumberCB.getSelectionModel().select(0);
		
		roundLengthLabel = new Label("Round Length:");
		roundLengthCB = new ComboBox<String>();
		roundLengthCB.getItems().addAll("3 minutes", "3 minutes 30 seconds", "4 minutes", "4 minutes 30 seconds", "5 minutes",
				"5 minutes 30 seconds", "6 minutes", "6 minutes 30 seconds", "7 minutes", "7 minutes 30 seconds", "8 minutes");
		roundLengthCB.getSelectionModel().select("3 minutes");
		
		preRoundLengthLabel = new Label("PreRound Length:");
		preRoundLengthCB = new ComboBox<String>();
		preRoundLengthCB.getItems().addAll("1 minute", "1 minute 30 seconds", "2 minutes", "2 minutes 30 seconds", "3 minutes",
				"3 minutes 30 seconds", "4 minutes", "4 minutes 30 seconds", "5 minutes");
		preRoundLengthCB.getSelectionModel().select("1 minutes");
		
		roundInfo.add(roundNumberLabel, 0, 0);
		roundInfo.add(roundNumberCB, 1, 0);
		roundInfo.add(roundLengthLabel, 0, 1);
		roundInfo.add(roundLengthCB, 1, 1);
		roundInfo.add(preRoundLengthLabel, 0, 2);
		roundInfo.add(preRoundLengthCB, 1, 2);
		
		createGameButton = new Button("Create Game");
		createGameButton.setFont(Main.ARIAL_22);
		
		pendingLabel = new Label("");
		pendingLabel.setFont(Main.ARIAL_22);
		pendingLabel.setTextFill(Color.CRIMSON);
		
		noGameRunningLabel.setFont(Main.ARIAL_22);
		gameNameLabel.setFont(Main.ARIAL_22);
		gameNameTF.setFont(Main.ARIAL_22);
		bigFishLabel.setFont(Main.ARIAL_22);
		bigFishTF.setFont(Main.ARIAL_22);
		
		roundNumberLabel.setFont(Main.ARIAL_22);
		//roundNumberCB.setFont(Main.ARIAL_24);
		roundLengthLabel.setFont(Main.ARIAL_22);
		//roundLengthCB.setFont(Main.ARIAL_24);
		preRoundLengthLabel.setFont(Main.ARIAL_22);
		//preRoundLengthCB.setFont(Main.ARIAL_24);
		
		this.add(noGameRunningLabel, 0, 0, 2, 1);
		this.add(gameNameLabel, 0, 1);
		this.add(gameNameTF, 1, 1);
		this.add(bigFishLabel, 0, 2);
		this.add(bigFishTF, 1, 2);
		
		this.add(roundInfo, 0, 3, 2, 3);
		this.add(createGameButton, 0, 6);
		this.add(pendingLabel, 1, 6);
	}
	
}
