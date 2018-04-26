
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

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class PlayerModel {
	
	private ObservableList<Player> players = FXCollections.observableArrayList(); // list of items

	public PlayerModel(){
		update();
	}
	
	public ObservableList<Player> getPlayerList(){
		return players;
	}
	
	public Player getPlayerWithName(String name) {
		if (players.size() > 0) {
			for (Player p : players) {
				if (p.getName().equalsIgnoreCase(name)) {
					return p;
				}
			}
		}
		return null;
	}
	
	public Player getPlayerWithRole(String role) {
		if (players.size() > 0) {
			for (Player p : players) {
				if (p.getRole().getName().equalsIgnoreCase(role)) {
					return p;
				}
			}
		}
		return null;
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
		// Name
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // 0 = Primary Display
		                            .setRowIndex(index + 2) // Row index
		                            .setColumnIndex(2)) // Column C
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		// Peek
		requests.add(new Request()
	            .setUpdateCells(new UpdateCellsRequest()
	                    .setStart(new GridCoordinate()
	                            .setSheetId(0) // 0 = Primary Display
	                            .setRowIndex(index + 2) // Row index
	                            .setColumnIndex(8)) // Column C
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
        final String range = "Primary Display!C3:R15";
        
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, Main.JSON_FACTORY, Main.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(Main.APPLICATION_NAME)
                .build();
        
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        
        List<List<Object>> values = response.getValues();
        
        if (values == null || values.isEmpty()) {
            System.out.println("No player data found.");
        } else if (Main.currentPlayer != null) {
        	players.clear();
            for (List row : values) {
                // Print column C (names of players), which correspond to index 0.
                //result += String.format("%s\n", row.get(0));
            	if (row.get(0).equals("")) continue;
            	
            	// 0 - Name
                Player p = new Player(row.get(0) + "", row.get(1) + "", values.indexOf(row));

                // 1 - Role
            	System.out.println(row.get(1) + "");
            	
            	// 2 - Alive Status
            	if (row.get(2).toString().equals("Yes")) {
            		p.setAliveStatus(true);
            	} else p.setAliveStatus(false);
            	
            	// 3 - Current Habitat
            	p.setCurrentHabitat(Habitat.getHabitatForName(row.get(3)+""));
            	
            	// 4 - Previous Habitat
            	p.setPreviousHabitat(Habitat.getHabitatForName(row.get(4)+""));
            	
            	// 6 - Peek
            	String pl = row.get(6)+"";
            	p.setPeekOne(pl);
            	
            	// 7 - Win Status
            	if (row.get(7).toString().equals("Winner")) {
            		p.setWinStatus(true);
            	} else p.setWinStatus(false);
            	
            	// 9 - Second Peek or Disguise
            	if (p.getRole() == Role.CRAB || p.getRole() == Role.REMORA || p.getRole() == Role.TURTLE) {
            		pl = row.get(9)+"";
                	p.setPeekTwo(p.getName());
                    p.setDisguise(p.getRole());
                } else if (p.getRole() == Role.OCTOPUS) {
                	p.setPeekTwo(p.getName());
                	p.setDisguise(Role.getRoleForName(row.get(9)+""));
                } else {
                	p.setPeekTwo(p.getName());
                	p.setDisguise(p.getRole());
                }
            	
            	// 11 - Prediction
            	if (p.getRole() == Role.TURTLE) {
            		p.setPrediction(Role.getRoleForName(row.get(11)+""));
            	} else p.setPrediction(p.getRole());
            	
            	// 12 - Prediction Check
            	// ???
            	
            	// 13 - Rounds Without Eating
            	p.setRoundsWithoutEating(Integer.valueOf(row.get(13)+""));
            	
            	// 14 - Non-eating Limit
            	p.setNonEatingLimit(Integer.valueOf(row.get(14)+""));
            	
            	// 15 - Next Habitat
            	p.setNextHabitat(Habitat.getHabitatForName(row.get(15)+""));
            	
            	// and the role fact
            	p.setRoleFact("Your role is the " + p.getRole().getName() + ".\nYou are " + (p.getAliveStatus() ? "alive." : "dead."));
                
        		players.add(p);
        		if (p.getIndex() == Main.currentPlayer.getSheetsIndex()) {
        			Main.currentPlayer.setListIndex(players.indexOf(p));
        		}
            }
        }

    }
    
}
