
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
import javafx.scene.text.Text;
import javafx.util.Callback;

public class PlayerListView extends VBox {

	private Label title;
	private static ListView<Player> list;
	
	private HBox profile;
	private Button biteButton;
	private Label name;
	
	private Label biteInfo;

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
		//biteButton.setBackground(Main.NAVY);
		//biteButton.setTextFill(Color.WHITE);
		biteButton.setOnMouseClicked(e -> {
			
			Player attacker = Game.playerModel.getPlayerList().get(Main.currentPlayer.getSheetsIndex());
			int defenderIndex = Game.playerModel.getPlayerList().indexOf(Game.playerModel.getPlayerWithName(name.getText()));
			if (attacker.bite(defenderIndex)) {
				// TODO HANDLE BITE SITUATION!!!
				// rounds without eating is now 0
				attacker.setRoundsWithoutEating(0);
				Player defender = Game.playerModel.getPlayerList().get(defenderIndex);
				biteInfo.setText("You successfully bit\n" + defender.getName()
						+ ".\nThey are now dead.");
				biteInfo.setTextFill(Color.LIGHTGREEN);
				defender.setAliveStatus(false);
				defender.setCurrentHabitat(Habitat.GRAVE);
				defender.writePlayerInfo();
			} else {
				// Check if the defender is the Fugu
				Player defender = Game.playerModel.getPlayerList().get(defenderIndex);
				if (defender.getRole() == Role.FUGU) {
					// attacker dies
					biteInfo.setText("You unfortunately bit\n" + Game.playerModel.getPlayerList().get(defenderIndex).getName()
							+ ".\nYou are now dead.");
					biteInfo.setTextFill(Color.LIGHTPINK);
					attacker.setAliveStatus(false);
					attacker.setCurrentHabitat(Habitat.GRAVE);
					attacker.writePlayerInfo();
				} else {
					biteInfo.setText("You failed to bite\n" + defender.getName()
					+ ".\nNothing happened.");
				}
			}
			
		});
		biteButton.setDisable(true);
		
		profile.getChildren().addAll(name, biteButton);
		
		profile.setBackground(Main.LIGHTCYAN);
		profile.setPadding(new Insets(20,20,20,20));
		
		biteInfo = new Label("");
		biteInfo.setFont(new Font("Arial", 16));
		biteInfo.setPrefHeight(200);
		biteInfo.setTextFill(Color.WHITE);
		
		this.getChildren().addAll(profile);
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
					
					if (new_val != null) {
						// reset name label and enable/disable biteButton
						name.setText(new_val.getName());
						// if player is alive and in same habitat, enable biteButton
						biteButton.setDisable(!(
								new_val.getAliveStatus() &&
								Main.game.getCurrentRound() > 0 &&
								Game.playerModel.getPlayerList().get(Main.currentPlayer.getSheetsIndex()).getAliveStatus() &&
								!Game.playerModel.getPlayerList().get(Main.currentPlayer.getSheetsIndex()).getName().equals(new_val.getName()) &&
								(Game.playerModel.getPlayerList().get(Main.currentPlayer.getSheetsIndex()).getCurrentHabitat() ==
									new_val.getCurrentHabitat())
								));
					}
					
				}
			}
		);

		this.getChildren().addAll(list, biteInfo);
	}
	
	public static void updatePlayerList() {
		System.out.println("UPDATING PLAYER LIST");
		list.setItems(Game.playerModel.getPlayerList());
		list.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {
			@Override 
			public ListCell<Player> call(ListView<Player> list) {
				// return a new PlayerCell() object here
				return new PlayerCell();
			}
		});
	}

}
