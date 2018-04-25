
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class PlayerListView extends VBox {

	private Label title;
	private ListView<Player> list;
	
	private HBox profile;
	private Button biteButton;
	private Label name;

	public PlayerListView() {
		this.setSpacing(20);
		this.setPadding(new Insets(20, 20, 20, 20));
		this.setBackground(Main.TEAL);
		this.setAlignment(Pos.TOP_CENTER);
		
		setUp();
		setUpListView();
	}
	
	private void setUp() {
		title = new Label("Player List");
		title.setTextFill(Color.WHITE);
		title.setFont(Main.ARIAL_22);
		this.getChildren().add(title);
		
		profile = new HBox();
		profile.setSpacing(20);
		profile.setAlignment(Pos.CENTER);
		name = new Label("");
		name.setAlignment(Pos.CENTER);
		name.setFont(new Font("Arial", 16));
		biteButton = new Button("BITE");
		biteButton.setOnMouseClicked(e -> {
			// TODO
		});
		biteButton.setDisable(true);
		
		profile.getChildren().addAll(name, biteButton);
		
		profile.setBackground(Main.LIGHTCYAN);
		profile.setPadding(new Insets(20,20,20,20));
		this.getChildren().add(profile);
	}
	
	// sets up listview 
	public void setUpListView(){
		list = new ListView<Player>();
		list.setPrefWidth(150);
		
		list.setItems(Game.playerModel.getPlayerList());
		list.setFocusTraversable(true);

		list.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {
			@Override 
			public ListCell<Player> call(ListView<Player> list) {
				// return a new PlayerCell() object here
				return new PlayerCell();
			}
		});

		list.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<Player>() {
				public void changed(ObservableValue<? extends Player> ov, 
						Player old_val, Player new_val) {
					
					// reset name label and enable/disable biteButton
					name.setText(new_val.getName());
					biteButton.setDisable(new_val.isAlive() /* ALSO CHECK IF IN SAME HABITAT */);
					
				}
			}
		);

		this.getChildren().add(list);
	}

}
