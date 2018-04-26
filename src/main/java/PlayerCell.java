
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PlayerCell extends ListCell<Player> {

	private HBox row;
	private Label first;
	private Label second;
	
	public PlayerCell() {
		row = new HBox(); 
		row.setSpacing(4);
		first = new Label();
		first.setTextFill(Color.DARKCYAN);
		second = new Label();
		second.setTextFill(Color.CRIMSON);
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
			if (p.getAliveStatus()) {
				this.setDisable(false);
			} else this.setDisable(true);;
			
			first.setText(p.getName());
			
			// IF currentPlayer is Big Fish, show Role
			// ELSE dont
			
			if (Main.currentPlayer.getSheetsIndex() == 13) { // Big Fish
				second.setText(p.getRole().toString());
			} else { // Player
				second.setText("");
			}
			setGraphic(row);
		}
		
		
	}
	
}
