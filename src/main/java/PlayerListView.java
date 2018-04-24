
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class PlayerListView extends VBox {

	private Label title;
	private ListView<Player> list;

	public PlayerListView() {
		this.setPadding(new Insets(20, 20, 20, 20));
		this.setBackground(Main.TEAL);
		this.setAlignment(Pos.TOP_CENTER);
		
		setUp();
		setUpListView();
	}
	
	private void setUp() {
		title = new Label("Player List");
		title.setTextFill(Color.WHITE);
		this.getChildren().add(title);
	}
	
	// sets up listview 
	public void setUpListView(){
		list = new ListView<Player>();
		list.setPrefWidth(150);
		PlayerModel fL = new PlayerModel();
		list.setItems(fL.getPlayerList());
		list.setFocusTraversable(true);

		// DONE: set cell factory to create FoodListCells()
		list.setCellFactory(new Callback<ListView<Player>, 
				ListCell<Player>>() {
			@Override 
			public ListCell<Player> call(ListView<Player> list) {
				// return a new FoodListCell() object here
				return new PlayerCell();
			}
		}
				);

		// DONE: when a cell is selected, switch displayed current food label and picture displayed
		list.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Player>() {
					public void changed(ObservableValue<? extends Player> ov, 
							Player old_val, Player new_val) {
						/*
						// DONE: reset image view and text
						foodLabel.setText(new_val.getName());
						foodImageView.setImage(new_val.getImage());

						// DONE: reset accessibility for current food label and image view
						foodLabel.setAccessibleHelp(new_val.getLabelAccessibleText());
						foodImageView.setAccessibleText(new_val.getImageAccessibleText());
						*/
					}
				});

		this.getChildren().add(list);
	}

}
