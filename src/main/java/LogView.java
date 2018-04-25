
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class LogView extends VBox {
	
	private Label title;
	private ListView<String> log;

	public LogView() {
		this.setSpacing(20);
		this.setPadding(new Insets(20, 20, 20, 20));
		this.setBackground(Main.TEAL);
		this.setAlignment(Pos.TOP_CENTER);
		
		setUp();
		setUpListView();
	}
	
	
	private void setUp() {
		title = new Label("Log");
		title.setTextFill(Color.WHITE);
		title.setFont(Main.ARIAL_22);
		this.getChildren().add(title);
	}
	
	// sets up listview 
	public void setUpListView(){
		log = new ListView<String>();
		log.setPrefWidth(150);
		
		log.setItems(Game.log.getLogList());
		log.setFocusTraversable(true);

		log.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override 
			public ListCell<String> call(ListView<String> list) {
				// return a new PlayerCell() object here
				return new LogCell();
			}
		});

		log.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> ov, 
						String old_val, String new_val) {
					
				}
			}
		);

		this.getChildren().add(log);
	}
	
	public class LogCell extends ListCell<String> {

		private HBox row;
		private Text first;
		private Label second;
		
		public LogCell() {
			row = new HBox(); 
			row.setSpacing(4);
			first = new Text();
			first.setWrappingWidth(130);
			second = new Label();
			row.getChildren().addAll(first, second);
		}

		@Override
		public void updateItem(String s, boolean empty) {
			super.updateItem(s, empty);
			if(empty) {
				setText(null);
	            setGraphic(null);
			}
			else {
				//first label: name + picture
				first.setText(this.getIndex() + ": " + s);
				setGraphic(row);
			}
			
			
		}
		
	}
}
