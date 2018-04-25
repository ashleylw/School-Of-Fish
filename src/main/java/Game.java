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

public class Game {
	
	private String name;
	private String bigFish;
	public static PlayerModel playerModel;
	public static Log log;

    private int numRounds;
    private int roundLength;
    private int preRoundLength;

    private int currentRound;
    private int timeLeft;
    
    public Game() {
    	playerModel = new PlayerModel();
    	log = new Log();
    	update();
    }
    
    public void update() {
    	try {
    		getGameFromSheets();
    	} catch (IOException ioe) {
    		System.err.println("IOException in Game:\n" + ioe.getMessage());
    	} catch (GeneralSecurityException gse) {
    		System.err.println("GeneralSecurityException in Game:\n" + gse.getMessage());
    	}
    }
    
    public void setGameInfo(String name, String bigFish, int numRounds, String roundLength, String preRoundLength) {
    	int roundLengthInt = 0;
    	int preRoundLengthInt = 0;
    	
    	switch (roundLength) {
			case "3 minutes":
				roundLengthInt = 180;
				break;
			case "3 minutes 30 seconds":
				roundLengthInt = 210;
				break;
			case "4 minutes":
				roundLengthInt = 240;
				break;
			case "4 minutes 30 seconds":
				roundLengthInt = 270;
				break;
			case "5 minutes":
				roundLengthInt = 300;
				break;
			case "5 minutes 30 seconds":
				roundLengthInt = 330;
				break;
			case "6 minutes":
				roundLengthInt = 360;
				break;
			case "6 minutes 30 seconds":
				roundLengthInt = 390;
				break;
			case "7 minutes":
				roundLengthInt = 420;
				break;
			case "7 minutes 30 seconds":
				roundLengthInt = 450;
				break;
			case "8 minutes":
				roundLengthInt = 480;
				break;
			default:
				break;
		}
    	
    	switch (preRoundLength) {
	    	case "1 minute":
	    		preRoundLengthInt = 60;
				break;
			case "1 minute 30 seconds":
				preRoundLengthInt = 90;
				break;
			case "2 minutes":
				preRoundLengthInt = 120;
				break;
			case "2 minutes 30 seconds":
				preRoundLengthInt = 150;
				break;
	    	case "3 minutes":
	    		preRoundLengthInt = 180;
				break;
			case "3 minutes 30 seconds":
				preRoundLengthInt = 210;
				break;
			case "4 minutes":
				preRoundLengthInt = 240;
				break;
			case "4 minutes 30 seconds":
				preRoundLengthInt = 270;
				break;
			case "5 minutes":
				preRoundLengthInt = 300;
				break;
			default:
				break;
		}
    	
    	// WRITE NAME TO APPROPRIATE CELL IN GSHEET
		try {
			writeGameInfoToSheets(name, bigFish, numRounds, roundLengthInt, preRoundLengthInt);
			update();
		} catch (IOException ioe) {
    		System.err.println("IOException in PlayerModel:\n" + ioe.getMessage());
    	} catch (GeneralSecurityException gse) {
    		System.err.println("GeneralSecurityException in PlayerModel:\n" + gse.getMessage());
    	} 
	}
    
    private void writeGameInfoToSheets(String name, String bigFish, int numRounds, int roundLength, int preRoundLength) throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		
		Sheets service = new Sheets.Builder(HTTP_TRANSPORT, Main.JSON_FACTORY, Main.getCredentials(HTTP_TRANSPORT))
		        .setApplicationName(Main.APPLICATION_NAME)
		        .build();
		List<Request> requests = new ArrayList<>();
		
		List<CellData> values = new ArrayList<>();
		
		// Game Name
		values.add(new CellData()
		            .setUserEnteredValue(new ExtendedValue()
		                    .setStringValue(name)));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(832353790) // Game Info
		                            .setRowIndex(1)
		                            .setColumnIndex(0)) // Game Name
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// Big Fish
		values.clear();
		requests.clear();
		values.add(new CellData()
	            .setUserEnteredValue(new ExtendedValue()
	                    .setStringValue(bigFish)));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // Primary Display
		                            .setRowIndex(15)
		                            .setColumnIndex(2)) // Big Fish
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// numRounds
		values.clear();
		requests.clear();
		values.add(new CellData()
	            .setUserEnteredValue(new ExtendedValue()
	                    .setStringValue(numRounds+"")));
	
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(832353790) // Game Info
		                            .setRowIndex(1)
		                            .setColumnIndex(1)) // Number of Rounds
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// roundLength
		values.clear();
		requests.clear();
		values.add(new CellData()
	            .setUserEnteredValue(new ExtendedValue()
	                    .setStringValue(roundLength+"")));
	
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(832353790) // Game Info
		                            .setRowIndex(1)
		                            .setColumnIndex(2)) // Round Length
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// preRoundLength
		values.clear();
		requests.clear();
		values.add(new CellData()
	            .setUserEnteredValue(new ExtendedValue()
	                    .setStringValue(preRoundLength+"")));
	
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(832353790) // Game Info
		                            .setRowIndex(1)
		                            .setColumnIndex(3)) // PreRound Length
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// Current Round
		values.clear();
		requests.clear();
		values.add(new CellData()
	            .setUserEnteredValue(new ExtendedValue()
	                    .setStringValue(currentRound+"")));
	
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(832353790) // Game Info
		                            .setRowIndex(1)
		                            .setColumnIndex(4)) // Current Round
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// Seconds Left
		values.clear();
		requests.clear();
		values.add(new CellData()
	            .setUserEnteredValue(new ExtendedValue()
	                    .setStringValue(timeLeft+"")));
	
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(832353790) // Game Info
		                            .setRowIndex(1)
		                            .setColumnIndex(5)) // Seconds Left
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
	}
    
    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public void getGameFromSheets() throws IOException, GeneralSecurityException {
    	// Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0";
        String range = "Game Info!A2:F2";
        
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
        	List row = values.get(0);
        	
        	// Game Name
        	this.name = row.get(0) + "";
        	// Num Rounds
        	this.numRounds = Integer.valueOf(row.get(1) + "");
        	// Round Length
        	this.roundLength = Integer.valueOf(row.get(2) + "");
        	// Preround length
        	this.preRoundLength = Integer.valueOf(row.get(3) + "");
        	// Current Round
        	this.currentRound = Integer.valueOf(row.get(4) + "");
        	// Seconds Left
        	this.timeLeft = Integer.valueOf(row.get(5) + "");
        	
        }
        
        range = "Primary Display!C16:C16";
        
        response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        
        values = response.getValues();
        
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
        	List row = values.get(0);
        	this.bigFish = row.get(0) + "";
        }

    }
    
    /**
     * Getters
     */
    public String getName() {
    	return this.name;
    }

    public String getBigFish() {
        return this.bigFish;
    }
    
    public int getCurrentRound() {
    	return this.currentRound;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getNumRounds() {
        return numRounds;
    }
    
    public int getRoundLength() {
    	return roundLength;
    }
    
    public int getPreRoundLength() {
    	return preRoundLength;
    }

    /**
     * Setters
     */
    public void setName(String name) {
    	this.name = name;
    }

	public void setBigFish(String bigFish) {
		this.bigFish = bigFish;
	}

	public void setNumRounds(int numRounds) {
		this.numRounds = numRounds;
	}

	public void setRoundLength(int roundLength) {
		this.roundLength = roundLength;
	}

	public void setPreRoundLength(int preRoundLength) {
		this.preRoundLength = preRoundLength;
	}

	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
    
    
}

