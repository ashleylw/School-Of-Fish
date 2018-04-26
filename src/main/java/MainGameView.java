
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainGameView extends GridPane {
    
    /**
     * JavaFX Visual Component
     */
    private Label gameName;
    private Label bigFish;
    
    private VBox peekInfo;
    private Label peekOneLabel;
    private ComboBox<String> peekOneCB;
    
    private VBox roundInfo;
    private Label roundNumber;
    private Label timeLeft;
    
    private Label habitatLabel;
    private Label roleFactLabel;
    
    private HBox nextHabitat;
    private Label nextHabitatLabel;
    private ComboBox<String> nextHabitatCB;
    
	
	public MainGameView() {	
		setUp();
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
		if (!Main.game.getName().equals("")) {
			gameName.setText(Main.game.getName());
		}
		
		bigFish = new Label("Big Fish: Name");
		GridPane.setHalignment(bigFish, HPos.CENTER);
		if (Main.game.getBigFish() != null) {
			bigFish.setText("Big Fish: " + Main.game.getBigFish());
		}
		
		peekInfo = new VBox();
		peekInfo.setAlignment(Pos.CENTER);
		peekInfo.setSpacing(20);
		peekInfo.setPadding(new Insets(20, 20, 20, 20));
		peekInfo.setBackground(Main.LIGHTBLUE);
		
		if (Main.currentPlayer.getSheetsIndex() != 13 && Main.currentPlayer.getListIndex() != -1) {
			Player cp = Game.playerModel.getPlayerList().get(Main.currentPlayer.getListIndex());
			peekOneCB = new ComboBox<String>();
			for (Player p : Game.playerModel.getPlayerList()) {
				peekOneCB.getItems().add(p.getName());
			}
			peekOneCB.setPlaceholder(new Text("Choose Player to Peek"));
			peekOneLabel = new Label("Choose Player to Peek");
			peekOneLabel.setFont(new Font("Arial", 16));
			
			if (Game.playerModel.getPlayerList().size() > 0 && cp != null) {
				// If Player has peekOne, setUp that view
				if (cp.getPeekOne().equals(cp.getName())) {
					peekOneCB.setOnAction(e -> {
						peekOneCB.setDisable(true);
						String peekName = peekOneCB.getSelectionModel().getSelectedItem();
						cp.setPeekOne(peekName);
						cp.writePlayerInfo();
						peekOneLabel.setText(Game.playerModel.getPlayerWithName(peekName).getRole().getName());
					});
				} else {
					peekOneCB.getSelectionModel().select(cp.getPeekOne());
					peekOneCB.setDisable(true);
					peekOneLabel.setText(Game.playerModel.getPlayerWithName(cp.getPeekOne()).getRole().getName());
				}
				
				if (Main.game.getCurrentRound() != -1) {
					peekOneCB.setDisable(true);
				}
				
				peekInfo.getChildren().addAll(peekOneCB, peekOneLabel);
			}
		} else {
			// nothing...
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
		if (Main.currentPlayer.getSheetsIndex() != 13 && Main.currentPlayer.getListIndex() != -1) {
			if (Game.playerModel.getPlayerList().size() > 0 &&
					Game.playerModel.getPlayerList().get(Main.currentPlayer.getListIndex()) != null) {
				//habitatLabel.setText(Game.playerModel.getPlayerList().get(Main.currentPlayer.getListIndex()).getCurrentHabitat().getName());
			}
		} else {
			habitatLabel.setText("");
		}
		
		roleFactLabel = new Label("Role-Specific Fact");
		GridPane.setHalignment(roleFactLabel, HPos.CENTER);
		if (Main.currentPlayer.getSheetsIndex() != 13 && Main.currentPlayer.getListIndex() != -1) {
			if (Game.playerModel.getPlayerList().size() > 0 &&
					Game.playerModel.getPlayerList().get(Main.currentPlayer.getListIndex()) != null) {
				String roleFact = Game.playerModel.getPlayerList().get(Main.currentPlayer.getListIndex()).getRoleFact();
				roleFactLabel.setText(roleFact);
				ImageView graphic = new ImageView(new Image("images/" 
						+ Game.playerModel.getPlayerList().get(Main.currentPlayer.getListIndex()).getRole() 
						+ ".png"));
				graphic.setFitHeight(100);
		        graphic.setPreserveRatio(true);
				roleFactLabel.setGraphic(graphic);
			}
		} else if (Main.currentPlayer.getSheetsIndex() == 13) {
			roleFactLabel.setText("You are the BIG FISH");
		} else {
			roleFactLabel.setText("Getting your role...");
		}
		
		nextHabitat = new HBox();
		nextHabitat.setAlignment(Pos.CENTER);
		nextHabitat.setSpacing(20);
		nextHabitat.setPadding(new Insets(20, 20, 20, 20));
		nextHabitat.setBackground(Main.LIGHTSEAGREEN);
		
		nextHabitatLabel = new Label("Next Habitat:");
		
		if (Main.currentPlayer.getSheetsIndex() != 13 && Main.currentPlayer.getListIndex() != -1) {
			nextHabitatCB = new ComboBox<String>();
			Player p = Game.playerModel.getPlayerList().get(Main.currentPlayer.getListIndex());
			
			if (Game.playerModel.getPlayerList().size() > 0 && p != null) {
				if (p.getCurrentHabitat() != p.getRole().getHome() && p.getPreviousHabitat() != p.getRole().getHome()) {
					nextHabitatCB.getItems().add(p.getRole().getHome().getName());
				} else {
					nextHabitatCB.getItems().addAll("Ocean", "Kelp Forest", "Reef");
					if (p.canVisitIsland()) nextHabitatCB.getItems().addAll("Island");
				}
				nextHabitatCB.getSelectionModel().select(p.getNextHabitat().getName());
				
				// If Player chooses where to Move, change nextHabitat for transitioning period
				nextHabitatCB.setOnAction(e -> {
					p.setNextHabitat(Habitat.getHabitatForName(nextHabitatCB.getSelectionModel().getSelectedItem()));
					p.writePlayerInfo();
				});
				
				if (Main.game.getCurrentRound() >= 0) {
					nextHabitatCB.setDisable(true);
					if (Main.game.getTimeLeft() == Main.game.getRoundLength()) {
						p.setPreviousHabitat(p.getCurrentHabitat());
						p.setCurrentHabitat(p.getNextHabitat());
						p.writePlayerInfo();
					}
					habitatLabel.setText(Game.playerModel.getPlayerList().get(Main.currentPlayer.getListIndex()).getCurrentHabitat().getName());
				} else {
					habitatLabel.setText("Moving to " + p.getNextHabitat().getName());
				}
				
				nextHabitat.getChildren().addAll(nextHabitatLabel, nextHabitatCB);
			}
		} else {
			// nothing...
		}
		
		
		gameName.setFont(Main.ARIAL_22);
		bigFish.setFont(Main.ARIAL_22);
		roundNumber.setFont(Main.ARIAL_22);
		timeLeft.setFont(Main.ARIAL_22);
		habitatLabel.setFont(Main.ARIAL_22);
		roleFactLabel.setFont(new Font("Arial", 20));
		nextHabitatLabel.setFont(new Font("Arial", 18));
		
		this.add(gameName, 0, 0, 3, 1);
		this.add(bigFish, 0, 1, 3, 1);
		this.add(peekInfo, 3, 0, 2, 2);
		this.add(roundInfo, 0, 2, 3, 3);
		this.add(habitatLabel, 3, 2, 3, 1);
		this.add(roleFactLabel, 3, 3, 3, 1);
		this.add(nextHabitat, 3, 4, 3, 1);
	}
    
}
