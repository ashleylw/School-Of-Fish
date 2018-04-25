import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
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
    
    public String getName() {
    	return this.name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }

    public String getBigFish() {
        return this.bigFish;
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

}

