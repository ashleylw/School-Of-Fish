
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
		MenuBar menuBar = new MenuBar();
        Menu mainMenu = new Menu("How To Play");
        MenuItem gameRules = new MenuItem("Game Rules");
        MenuItem animalRules = new MenuItem("Animal Rules");
		
        BorderPane root = new BorderPane();
        ImageView imageGR = new ImageView(new Image("images/GameRules.png"));
        ImageView imageAR = new ImageView(new Image("images/AnimalRules.png"));
        imageGR.setFitHeight(750);
        imageGR.setPreserveRatio(true);
        imageAR.setFitHeight(750);
        imageAR.setPreserveRatio(true);
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 1200, 750));
        
        gameRules.setOnAction(action -> {
            root.setCenter(imageGR);
            stage.setTitle("Game Rules");
            stage.show();
        });
        
        animalRules.setOnAction(action -> {
            root.setCenter(imageAR);
            stage.setTitle("Animal Rules");
            stage.show();
        });
        
        mainMenu.getItems().addAll(gameRules, animalRules);
        menuBar.getMenus().add(mainMenu);
        
        this.getChildren().add(menuBar);
		
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
