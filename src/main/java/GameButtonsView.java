
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class GameButtonsView extends HBox {

	public Button startGameButton;
	public Button joinGameButton;

	public GameButtonsView() {
		this.setSpacing(20);
		this.setPadding(new Insets(20, 20, 20, 20));
		this.setBackground(Main.TEAL);
		this.setAlignment(Pos.TOP_CENTER);
		
		setUp();
	}
	
	
	private void setUp() {
		startGameButton = new Button("Start Game");
		startGameButton.setFont(Main.ARIAL_22);
		startGameButton.setDisable(true);
		joinGameButton = new Button("Join Game");
		joinGameButton.setFont(Main.ARIAL_22);
		joinGameButton.setDisable(true);
		this.getChildren().addAll(startGameButton, joinGameButton);
	}
	
}
