
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
			first.setText(p.getName());
			/*
			ImageView icon = new ImageView(p.getImageUrl());
			icon.setFitWidth(10); icon.setFitHeight(10);
			first.setGraphic(icon);
			*/
			
			second.setText(p.getImageUrl());
			//should get from model, instead of hard-coding
			/*
			ImageView hog = new ImageView("images/" + p.getName()+".png");
			shrinkImage(hog);
			second.setGraphic(hog);
			*/
			
			setGraphic(row);
		}
		
		
	}
	private void shrinkImage(ImageView i) {
		i.setFitWidth(20);
		i.setPreserveRatio(true);
		
	}
	
}
