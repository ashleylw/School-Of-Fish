
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MainGameView extends GridPane {
    
    /**
     * JavaFX Visual Component
     */
    private Label label;
    
	
	public MainGameView() {
		this.setPadding(new Insets(20, 20, 20, 20));
		
		setUp();
	}
	
	private void setUp() {
		/*try {
    		label = new Label(printPlayerNames());
    	    this.add(label, 0, 0); // column=1 row=0
    	} catch (IOException ioe) {
    		System.err.println("IOException in Sheets:\n" + ioe.getMessage());
    	} catch (GeneralSecurityException gse) {
    		System.err.println("GeneralSecurityException in Sheets:\n" + gse.getMessage());
    	}*/
	}

    
}
