
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class GameScene {
    
    /**
     * Variables related to JavaFX Visual Component
     */
    private Main main;
    private Scene scene;
    private BorderPane root;
    private StackPane top;
    private LogView left;
    private PlayerListView right;
    private GameButtonsView bottom;
    
    private MainGameView mainGameCenter;
    private CreateGameView createGameCenter;
    private JoinGameView joinGameCenter;
    
    private Label title;
    private Label label;
    
    private Timeline timeline;
    private Timeline playerListTimeline;
    
	public GameScene() {
		setUp();
	}
    public void setUp() {
		root = new BorderPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setBackground(Main.LIGHTCYAN);
		
		setUpView();
		setUpEventHandling();
		
		scene = new Scene(root, 1200, 700);
		
		if (Main.game.getName().equals("")) {
			root.setCenter(createGameCenter);
		} else {
			Main.currentPlayer.setSheetsIndex(-1);
			root.setCenter(joinGameCenter);
		}
	}
    
    public Scene getScene() {
    	return scene;
    }
    
    private void setUpView() {
    	top = new StackPane();
    	mainGameCenter = new MainGameView();
    	createGameCenter = new CreateGameView();
    	joinGameCenter = new JoinGameView();
    	left = new LogView();
    	right = new PlayerListView();
    	bottom = new GameButtonsView();
    	
    	title = new Label("School Of Fish");
    	title.setFont(new Font("Arial", 32));
    	top.getChildren().add(title);
    	
    	root.setTop(top);
    	root.setLeft(left);
    	root.setRight(right);
    	root.setBottom(bottom);
    }
    
    private void setUpEventHandling() {
    	joinGameCenter.playerNameTF.setOnKeyReleased(e -> {
    		if (!joinGameCenter.playerNameTF.getText().equals("") && Game.playerModel.getPlayerList().size() < 13) {
    			bottom.joinGameButton.setDisable(false);
    		} else {
    			bottom.joinGameButton.setDisable(true);
    		}
    	});
    	
    	createGameCenter.createGameButton.setOnMouseClicked(e -> {
    		createGameCenter.pendingLabel.setText("Waiting for players...");
    		Main.game = new Game();
    		Main.game.setGameInfo(createGameCenter.gameNameTF.getText(), createGameCenter.bigFishTF.getText(),
    				createGameCenter.roundNumberCB.getSelectionModel().getSelectedItem(),
    				createGameCenter.roundLengthCB.getSelectionModel().getSelectedItem(),
    				createGameCenter.preRoundLengthCB.getSelectionModel().getSelectedItem());
    		createGameCenter.createGameButton.setDisable(true);
    		// initiate updating Player List to see joining players
    		playerListTimeline.play();
    	});
    	
    	bottom.startGameButton.setOnMouseClicked(e -> {
    		setGameCenter();
    		bottom.startGameButton.setDisable(true);
    		bottom.leaveGameButton.setDisable(false);
    		
    		// Get everyone's peeks
    		// Actually everyone makes peeks in first preRound
    		
    		// Start the first preRound
    		Main.game.nextRound();
    		
    		// Set 
    	});
    	
    	bottom.joinGameButton.setOnMouseClicked(e -> {
    		// Find available index for current player using random number between 0 and 12
    		int i = Main.randomIntBetween(0, 12);
    		boolean free = false;
    		while (!free) {
    			if (Game.playerModel.getPlayerList().size() > 0) {
					for (Player p : Game.playerModel.getPlayerList()) {
						if (p.getIndex() == i) {
							free = false;
							i = Main.randomIntBetween(0, 12);
							break;
						}
						free = true;
					}
    			} else free = true;
    		}
    		Game.playerModel.addPlayerAtIndex(joinGameCenter.playerNameTF.getText(), i);
    		Main.currentPlayer.setSheetsIndex(i);
    		Main.currentPlayer.setListIndex(-1);
    		setGameCenter();
    		bottom.joinGameButton.setDisable(true);
    		bottom.leaveGameButton.setDisable(false);
    	});
    	
    	bottom.leaveGameButton.setOnMouseClicked(e -> {
    		// calls stop() in Main
    		Platform.exit();
    	});
    	
    	playerListTimeline = new Timeline(new KeyFrame(
    	        Duration.millis(5000),
    	        ae -> {
    	        	Main.game.update();
    				Game.playerModel.update();
    				PlayerListView.updatePlayerList();
    				if (Game.playerModel.getPlayerList().size() == 13) { // Have all 13 players!
    					createGameCenter.pendingLabel.setText("Players found!");
    					createGameCenter.pendingLabel.setTextFill(Color.DARKCYAN);
    					bottom.startGameButton.setDisable(false);
    				} else { // Still need more players!!!!
    					createGameCenter.pendingLabel.setText("Waiting for players...");
    					createGameCenter.pendingLabel.setTextFill(Color.CRIMSON);
    					bottom.startGameButton.setDisable(true);
    				}
    	        }));
    	playerListTimeline.setCycleCount(Animation.INDEFINITE);
    }
    
    private void setGameCenter() {
    	mainGameCenter = new MainGameView();
		root.setCenter(mainGameCenter);
		playerListTimeline.stop();
		callForUpdates();
    }
    
    private void callForUpdates() {
    	int delay = 10;
    	timeline = new Timeline(new KeyFrame(
    	        Duration.millis(delay * 1000),
    	        ae -> {
    	        	
    	        	if (Main.currentPlayer.getSheetsIndex() == 13) { // If youre the Big Fish
    	        		if (Main.game.getTimeLeft() > 0) {
	    	        		Main.game.setTimeLeft(Main.game.getTimeLeft() - delay);
	    	        		Main.game.writeGameData();
    	        		} else {
    	        			Main.game.nextRound();
    	        		}
    	        	}
    	        	
    	        	Main.game.update();
    				Game.playerModel.update();
    				PlayerListView.updatePlayerList();
    				
    				mainGameCenter = new MainGameView();
    	    		root.setCenter(mainGameCenter);
    	    		
    	    		if (Math.abs(Main.game.getCurrentRound()) > Main.game.getNumRounds()) {
    	    			// set up EndView
    	    			root.setCenter(new EndView());
    	    			
    	    			// stop timer
    	    			timeline.stop();
    	    		}
    	        }));
    	timeline.setCycleCount(Animation.INDEFINITE);
    	timeline.play();
    }

} 