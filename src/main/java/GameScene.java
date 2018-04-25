
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

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
    
    private AnimationTimer timer;
    
	public GameScene() {
		getGameData();
		setUp();
		
		if (Main.game.getName().equals("null")) {
			root.setCenter(createGameCenter);
		} else {
			root.setCenter(joinGameCenter);
		}
		
		callForUpdates();
	}
	
	private void getGameData() {
		Main.game = new Game();
	}
    
    public void setUp() {
		root = new BorderPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setBackground(Main.LIGHTCYAN);
		
		setUpView();
		
		scene = new Scene(root, 1200, 700);
	}
    
    public Scene getScene() {
    	return scene;
    }
    
    public void setUpView() {
    	top = new StackPane();
    	mainGameCenter = new MainGameView();
    	createGameCenter = new CreateGameView();
    	joinGameCenter = new JoinGameView();
    	left = new LogView();
    	right = new PlayerListView();
    	bottom = new GameButtonsView();
    	
    	joinGameCenter.playerNameTF.setOnKeyReleased(e -> {
    		if (!joinGameCenter.playerNameTF.getText().equals("") && Game.playerModel.getPlayerList().size() < 13) {
    			bottom.joinGameButton.setDisable(false);
    		} else {
    			bottom.joinGameButton.setDisable(true);
    		}
    	});
    	
    	createGameCenter.createGameButton.setOnMouseClicked(e -> {
    		Main.game = new Game();
    		createGameCenter.createGameButton.setDisable(true);
    		bottom.startGameButton.setDisable(false);
    	});
    	
    	bottom.startGameButton.setOnMouseClicked(e -> {
    		root.setCenter(mainGameCenter);
    	});
    	
    	bottom.joinGameButton.setOnMouseClicked(e -> {
    		// Find available index for current player using random number between 0 and 12
    		int i = Main.randomIntBetween(0, 12);
    		boolean free = false;
    		while (!free) {
    			for (Player p : Game.playerModel.getPlayerList()) {
    				if (p.getIndex() == i) {
    					free = false;
    					i = Main.randomIntBetween(0, 12);
    					break;
    				}
    				free = true;
    			}
    		}
    		Main.currentPlayer.setIndex(i);
    		Game.playerModel.addPlayerAtIndex(joinGameCenter.playerNameTF.getText(), i);
    		
    		root.setCenter(mainGameCenter);
    	});
    	
    	title = new Label("School Of Fish");
    	title.setFont(new Font("Arial", 32));
    	top.getChildren().add(title);
    	
    	root.setTop(top);
    	root.setLeft(left);
    	root.setRight(right);
    	root.setBottom(bottom);
    }
    
    private void callForUpdates() {
		timer = new AnimationTimer(){
			@Override
			public void handle(long now) {
				update();
			}
			private void update() {
				
			}
		};
		timer.start();
    }
}