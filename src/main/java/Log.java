import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Log {
	
	private ObservableList<String> logList =FXCollections.observableArrayList (); // list of items

	public Log(){
		update();
	}
	
	public ObservableList<String> getLogList(){
		return logList;
	}
	
	// WHERE WE GET LOG FROM GSHEETS?????
	public void update() {
		
		try {
    		getLogFromSheets();
    	} catch (IOException ioe) {
    		System.err.println("IOException in PlayerModel:\n" + ioe.getMessage());
    	} catch (GeneralSecurityException gse) {
    		System.err.println("GeneralSecurityException in PlayerModel:\n" + gse.getMessage());
    	}
		
	}
	
	/**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public void getLogFromSheets() throws IOException, GeneralSecurityException {
    	
    	// Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1h7XeOnC2ITdCYz921GWol7OZfNnUpAn0e1Dgl4ExzP0";
        final String range = "Log!A1:B";
        
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
            for (List row : values) {
        		logList.add(row.get(0) + "");
            }
        }

    }
    
}
