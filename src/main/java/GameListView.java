
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GameListView extends VBox {

	private Label title;

	public GameListView() {
		this.setPadding(new Insets(20, 20, 20, 20));
		this.setBackground(Main.TEAL);
		this.setAlignment(Pos.TOP_CENTER);
		
		setUp();
	}
	
	
	private void setUp() {
		title = new Label("Game List");
		title.setTextFill(Color.WHITE);
		this.getChildren().add(title);
	}
	
}
