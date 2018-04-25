
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.ValueRange;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlayerModel {
	
	private ObservableList<Player> players =FXCollections.observableArrayList (); // list of items

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
    	} catch (IOException ioe) {
    		System.err.println("IOException in PlayerModel:\n" + ioe.getMessage());
    	} catch (GeneralSecurityException gse) {
    		System.err.println("GeneralSecurityException in PlayerModel:\n" + gse.getMessage());
    	}
		
	}
	
	public void addPlayerAtIndex(String name, int index) {
		// WRITE NAME TO APPROPRIATE CELL IN GSHEET
		try {
			writePlayerToSheets(name, index);
			update();
		} catch (IOException ioe) {
    		System.err.println("IOException in PlayerModel:\n" + ioe.getMessage());
    	} catch (GeneralSecurityException gse) {
    		System.err.println("GeneralSecurityException in PlayerModel:\n" + gse.getMessage());
    	} 
	}
	
	private void writePlayerToSheets(String name, int index) throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		
		Sheets service = new Sheets.Builder(HTTP_TRANSPORT, Main.JSON_FACTORY, Main.getCredentials(HTTP_TRANSPORT))
		        .setApplicationName(Main.APPLICATION_NAME)
		        .build();
		List<Request> requests = new ArrayList<>();
		
		List<CellData> values = new ArrayList<>();
		
		values.add(new CellData()
		            .setUserEnteredValue(new ExtendedValue()
		                    .setStringValue(name)));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // 0 = Primary Display
		                            .setRowIndex(index + 2) // Row index
		                            .setColumnIndex(2)) // Column C
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
	}
	
	/**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public void getPlayersFromSheets() throws IOException, GeneralSecurityException {
    	
    	// Build a new authorized API client service.
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0";
        final String range = "Primary Display!C3:D15";
        
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
            	if (row.get(0).equals("")) continue;
                Player p = new Player(row.get(0) + "", row.get(1) + "", values.indexOf(row));
        		players.add(p);
            }
        }

    }
    
}
