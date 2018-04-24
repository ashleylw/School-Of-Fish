
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class PlayerModel {
	
	private ObservableList<Player> players =FXCollections.observableArrayList (); // list of food items

	public PlayerModel(){
		update();
	}
	
	public ObservableList<Player> getPlayerList(){
		return players;
	}
	
	// WHERE WE GET PLAYERS FROM GSHEETS?????
	public void update() {
		
		try {
    		getPlayersFromSheets();
			/*
			label = new Label(printPlayerNames());
    	    this.add(label, 0, 0); // column=1 row=0
    	    */
    	} catch (IOException ioe) {
    		System.err.println("IOException in Sheets:\n" + ioe.getMessage());
    	} catch (GeneralSecurityException gse) {
    		System.err.println("GeneralSecurityException in Sheets:\n" + gse.getMessage());
    	}
		
	}
	
	/**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public String getPlayersFromSheets() throws IOException, GeneralSecurityException {
        String result = "";
    	
    	// Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0";
        final String range = "Primary Display!C2:C15";
        
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, Main.JSON_FACTORY, Main.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(Main.APPLICATION_NAME)
                .build();
        
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        
        List<List<Object>> values = response.getValues();
        
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            //result += "Name, Major\n";
        	players.clear();
            for (List row : values) {
                // Print column C (names of players), which correspond to index 0.
                //result += String.format("%s\n", row.get(0));
                Player p = new Player(row.get(0) + "", "filename");
        		players.add(p);
            }
        }
        
        return result;
    }

}
