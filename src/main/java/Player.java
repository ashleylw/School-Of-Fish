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

public class Player {

	private String name;
	private int index;
	
    private Role role;
    private String roleFact;
    private boolean aliveStatus;
    private Habitat currentHabitat;
    private Habitat previousHabitat;
    
    private boolean winStatus;

    // Every player can peek at the role of another player.
    // If a player's role is the CRAB, REMORA, or TURTLE, they can peek at two players
    // stores index of player in playerModel
    private String peekOne;
    private String peekTwo;

    // If a player is the Octopus, we need to keep track of who they're disguised as
    private Role disguise;
    
    // If a player is the Turtle, we need to keep track of who they predicted to win
    private Role prediction;
    
    private int roundsWithoutEating;
    private int nonEatingLimit;

    private Habitat nextHabitat;
    
    // TODO: need to have a way of keeping track of whether a player has eaten

    /**
     * A player can see the following:
     * - NAMES of people in their current location
     * - A choice to bite anyone in their current location
     * - NAMES of people who are dead
     * - in between rounds, only habitats they are ABLE to visit next
     */

    /**
     * Player Constructor
     * @param name of the player
     */
    public Player(String name, String role, int index) {
        this.name = name;
        this.index = index;

        this.role = Role.getRoleForName(role);

        aliveStatus = true;
        this.roleFact = "";
        
        currentHabitat = this.role.getHome();
        previousHabitat = this.role.getHome();

        peekOne = this.name;
        peekTwo = this.name;

        disguise = null;
        prediction = null;
    }
    
    /**
     * Move action performed in between rounds
     * @param h Habitat that this player wants to move to.
     */
    public void Move(Habitat h) {
        // TODO
    }
    
    /**
     * Bite action performed on another player in same habitat
     * A bite will FAIL if:
     * - p's Role is the FUGU (and this player will die)
     * - p's rank is higher than this.rank
     * - p's Role is a sea animal AND all (alive) sea animals are in the same habitat
     * If a bite SUCCEEDS, then:
     * - p dies
     * - this player HAS EATEN for this round
     * @param p Player to bite
     * @return boolean is bite is successful
     */
    public boolean bite(int i) {
        // check if Player with index i can be bitten by this Player
    	boolean bite = false;
    	try {
    		bite = checkIfPlayerCanBeBitten(i);
    	} catch (IOException ioe) {
     		System.err.println("IOException in Player (Index " + index + ") Bite:\n" + ioe.getMessage());
     	} catch (GeneralSecurityException gse) {
     		System.err.println("GeneralSecurityException in Player (Index " + index + ") Bite:\n" + gse.getMessage());
     	}
        return bite;
    }
    
    /**
     * @return true if player's role is POLARBEAR, OCTOPUS, SEALION, CRAB, or TURTLE
     */
    public boolean canVisitIsland() {
        return (this.role == Role.POLARBEAR) ||
                (this.role == Role.OCTOPUS) ||
                (this.role == Role.SEALION) ||
                (this.role == Role.CRAB) ||
                (this.role == Role.TURTLE);
    }
    
    public void writePlayerInfo() {
		try {
			writePlayerInfoToSheets();
		} catch (IOException ioe) {
    		System.err.println("IOException in Player Writing:\n" + ioe.getMessage());
    	} catch (GeneralSecurityException gse) {
    		System.err.println("GeneralSecurityException in Player Writing:\n" + gse.getMessage());
    	} 
    }
    
    private void writePlayerInfoToSheets() throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		
		Sheets service = new Sheets.Builder(HTTP_TRANSPORT, Main.JSON_FACTORY, Main.getCredentials(HTTP_TRANSPORT))
		        .setApplicationName(Main.APPLICATION_NAME)
		        .build();
		List<Request> requests = new ArrayList<>();
		
		List<CellData> values = new ArrayList<>();
		
		int rowNum = 0;
		switch(role) {
			case ORCA:
				rowNum = 2;
				break;
			case SHARK:
				rowNum = 3;
				break;
			case POLARBEAR:
				rowNum = 4;
				break;
			case EEL:
				rowNum = 5;
				break;
			case CRAB:
				rowNum = 6;
				break;
			case OCTOPUS:
				rowNum = 7;
				break;
			case FUGU:
				rowNum = 8;
				break;
			case REMORA:
				rowNum = 9;
				break;
			case TURTLE:
				rowNum = 10;
				break;
			case SEAURCHIN:
				rowNum = 11;
				break;
			case SEALION:
				rowNum = 12;
				break;
			case SEASTAR:
				rowNum = 13;
				break;
			case SEAHORSE:
				rowNum = 14;
				break;
		}
		
		// Alive Status
		String living = (aliveStatus ? "Yes" : "No");
		values.add(new CellData()
		            .setUserEnteredValue(new ExtendedValue()
		                    .setStringValue(living)));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // Primary Display
		                            .setRowIndex(rowNum)
		                            .setColumnIndex(4)) // Living
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// Current Habitat
		values.clear();
		requests.clear();
		values.add(new CellData()
		            .setUserEnteredValue(new ExtendedValue()
		                    .setStringValue(currentHabitat.getName())));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // Primary Display
		                            .setRowIndex(rowNum)
		                            .setColumnIndex(5)) // Current Habitat
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// Previous Habitat
		values.clear();
		requests.clear();
		values.add(new CellData()
		            .setUserEnteredValue(new ExtendedValue()
		                    .setStringValue(previousHabitat.getName())));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // Primary Display
		                            .setRowIndex(rowNum)
		                            .setColumnIndex(6)) // Previous Habitat
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// 8 - Peek
		values.clear();
		requests.clear();
		values.add(new CellData()
		            .setUserEnteredValue(new ExtendedValue()
		                    .setStringValue(peekOne)));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // Primary Display
		                            .setRowIndex(rowNum)
		                            .setColumnIndex(8)) // Peek
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// 9 - Win Status
		values.clear();
		requests.clear();
		String winning = (winStatus ? "Winner" : "Loser");
		values.add(new CellData()
		            .setUserEnteredValue(new ExtendedValue()
		                    .setStringValue(winning)));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // Primary Display
		                            .setRowIndex(rowNum)
		                            .setColumnIndex(9)) // Winning
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// 11 - Second Peek / Disguise
		values.clear();
		requests.clear();
		String peekOrDisguise = "";
		if (role == Role.OCTOPUS) {
			peekOrDisguise = disguise.getName();
		} else if (role == Role.CRAB || role == Role.REMORA || role == Role.TURTLE) {
			peekOrDisguise = peekTwo;
		}
		
		values.add(new CellData()
		            .setUserEnteredValue(new ExtendedValue()
		                    .setStringValue(peekOrDisguise)));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // Primary Display
		                            .setRowIndex(rowNum)
		                            .setColumnIndex(11)) // Second Peek / Disguise
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// 13 - Prediction
		values.clear();
		requests.clear();
		String pred = "";
		if (role == Role.TURTLE) {
			pred = prediction.getName();
		}
		values.add(new CellData()
		            .setUserEnteredValue(new ExtendedValue()
		                    .setStringValue(pred)));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // Primary Display
		                            .setRowIndex(rowNum)
		                            .setColumnIndex(13)) // Prediction
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// 15 - Rounds Without Eating
		values.clear();
		requests.clear();
		values.add(new CellData()
		            .setUserEnteredValue(new ExtendedValue()
		                    .setStringValue(roundsWithoutEating + "")));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // Primary Display
		                            .setRowIndex(rowNum)
		                            .setColumnIndex(15)) // Rounds WIthout Eating
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
		        .execute();
		
		// 17 - Next Habitat
		values.clear();
		requests.clear();
		values.add(new CellData()
		            .setUserEnteredValue(new ExtendedValue()
		                    .setStringValue(nextHabitat.getName())));
		
		requests.add(new Request()
		            .setUpdateCells(new UpdateCellsRequest()
		                    .setStart(new GridCoordinate()
		                            .setSheetId(0) // Primary Display
		                            .setRowIndex(rowNum)
		                            .setColumnIndex(17)) // Next Habitat
		                    .setRows(Arrays.asList(
		                            new RowData().setValues(values)))
		                    .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
		
		batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
		        .setRequests(requests);
		service.spreadsheets().batchUpdate("1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0", batchUpdateRequest)
			        .execute();
	}
    
    
    private boolean checkIfPlayerCanBeBitten(int index) throws IOException, GeneralSecurityException{
    	Role otherRole = Game.playerModel.getPlayerList().get(index).getRole();
    	int defender = 4;
    	String attacker = "C";
    	switch(otherRole) {
    		case ORCA:
    			defender = 4;
    			break;
    		case SHARK:
    			defender = 5;
    			break;
    		case POLARBEAR:
    			defender = 6;
    			break;
    		case EEL:
    			defender = 7;
    			break;
    		case CRAB:
    			defender = 8;
    			break;
    		case OCTOPUS:
    			defender = 9;
    			break;
    		case FUGU:
    			defender = 10;
    			break;
    		case REMORA:
    			defender = 11;
    			break;
    		case TURTLE:
    			defender = 12;
    			break;
    		case SEAURCHIN:
    			defender = 13;
    			break;
    		case SEALION:
    			defender = 14;
    			break;
    		case SEASTAR:
    			defender = 15;
    			break;
    		case SEAHORSE:
    			defender = 16;
    			break;
    	}
    	
    	switch(this.role) {
			case ORCA:
				attacker = "C";
				break;
			case SHARK:
				attacker = "D";
				break;
			case POLARBEAR:
				attacker = "E";
				break;
			case EEL:
				attacker = "F";
				break;
    	}
    	
    	// Build a new authorized API client service.
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0";
        int defender2 = defender + 2;
        final String range = "Bite Table!" + attacker + defender + ":" + attacker + defender2;
        
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, Main.JSON_FACTORY, Main.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(Main.APPLICATION_NAME)
                .build();
        
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        
        List<List<Object>> values = response.getValues();
        
        if (values == null || values.isEmpty()) {
            System.out.println("*\n*\n*\n*No " + range + " data found.*\n*\n*\n*");
            return false;
        } else {
        	List row = values.get(0);
        	String bite = row.get(0)+"";
        	System.out.println(bite);
        	
        	if (bite.equals("Success")) {
        		// Successful Bite
        		return true;
        	} else {
        		// Failed Bite
        		return false;
            }
        }
    	
    }
    
    
    
    /**
	 * Getters, setters, and toString
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public String getRoleFact() {
        return roleFact;
    }

    public void setRoleFact(String roleFact) {
        this.roleFact = roleFact;
    }

    public Habitat getCurrentHabitat() {
        return currentHabitat;
    }

    public void setCurrentHabitat(Habitat currentHabitat) {
        this.currentHabitat = currentHabitat;
    }

    public Habitat getPreviousHabitat() {
        return previousHabitat;
    }

    public void setPreviousHabitat(Habitat previousHabitat) {
        this.previousHabitat = previousHabitat;
    }
    
    public boolean getWinStatus() {
    	return winStatus;
    }
    
    public void setWinStatus(boolean winStatus) {
    	this.winStatus = winStatus;
    }
    
    public String getPeekOne() {
        return peekOne;
    }

    public void setPeekOne(String peekOne) {
        this.peekOne = peekOne;
    }
    
    // @return peekTwo IF the player's Role is CRAB, REMORA, or TURTLE
    public String getPeekTwo() {
        return peekTwo;
    }

    // @param peekTwo Player to peek and set as peekTwo (for CRAB, REMORA, and TURTLE only)
    public void setPeekTwo(String peekTwo) {
        this.peekTwo = peekTwo;
    }

    public boolean getAliveStatus() {
        return aliveStatus;
    }

    public void setAliveStatus(boolean aliveStatus) {
        this.aliveStatus = aliveStatus;
    }

    /**
     * @param r Role to set disguise for OCTOPUS player only
     */
    public void setDisguise(Role r) {
        this.disguise = r;
    }

    /**
     * @return Role of disguise (only applicable to octopus; if not the octopus, returns own role)
     */
    public Role getDisguise() {
        return this.disguise;
    }
    
    public void setPrediction(Role p) {
    	this.prediction = p;
    }
    
    public Role getPrediction() {
    	return this.prediction;
    }

	/**
	 * @return the roundsWithoutEating
	 */
	public int getRoundsWithoutEating() {
		return roundsWithoutEating;
	}

	/**
	 * @param roundsWithoutEating the roundsWithoutEating to set
	 */
	public void setRoundsWithoutEating(int roundsWithoutEating) {
		this.roundsWithoutEating = roundsWithoutEating;
	}

	/**
	 * @return the nonEatingLimit
	 */
	public int getNonEatingLimit() {
		return nonEatingLimit;
	}

	/**
	 * @param nonEatingLimit the nonEatingLimit to set
	 */
	public void setNonEatingLimit(int nonEatingLimit) {
		this.nonEatingLimit = nonEatingLimit;
	}

	/**
	 * @return the nextHabitat
	 */
	public Habitat getNextHabitat() {
		return nextHabitat;
	}

	/**
	 * @param nextHabitat the nextHabitat to set
	 */
	public void setNextHabitat(Habitat nextHabitat) {
		this.nextHabitat = nextHabitat;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + "]";
	}
	
}
