
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PlayerCell extends ListCell<Player> {

	private HBox row;
	private Label first;
	private Label second;
	
	public PlayerCell() {
		row = new HBox(); 
		row.setSpacing(4);
		first = new Label();
		second = new Label();
		row.getChildren().addAll(first, second);
	
	}

	@Override
	public void updateItem(Player p, boolean empty) {
		super.updateItem(p, empty);
		if(empty) {
			setText(null);
            setGraphic(null);
		}
		else {
			//first label: name + picture
			first.setText(p.getIndex() + ". " + p.getName());
			second.setText(p.getRole().toString());
			
			setGraphic(row);
		}
		
		
	}
	
}
