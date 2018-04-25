
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	
	/**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials/ folder.
     */
	public static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static final String CREDENTIALS_FOLDER = "credentials"; // Directory to store user credentials.
    public static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    public static final String CLIENT_SECRET_DIR = "client_secret.json";
    
    public static Game game;
    public static CurrentPlayer currentPlayer;
	
    /**
     * JavaFX Visual Components
     */
	private GameScene gs;
	private Stage stage;
	
	// backgrounds
	public static final Background LIGHTCYAN =
			new Background(new BackgroundFill(Color.LIGHTCYAN, new CornerRadii(0.0), new Insets(0,0,0,0)));
    public static final Background TEAL =
			new Background(new BackgroundFill(Color.TEAL, new CornerRadii(0.0), new Insets(0,0,0,0)));
	public static final Background DARKCYAN =
			new Background(new BackgroundFill(Color.DARKCYAN, new CornerRadii(0.0), new Insets(0,0,0,0)));
	public static final Background LIGHTSEAGREEN =
			new Background(new BackgroundFill(Color.LIGHTSEAGREEN, new CornerRadii(0.0), new Insets(0,0,0,0)));
	
	// fonts
	public static final Font ARIAL_22 = new Font("Arial", 22);

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		currentPlayer = new CurrentPlayer();
		
		gs = new GameScene();
		stage.setTitle("School Of Fish");
		
		showStartView();
		
		stage.show();
	}
	
	public void showStartView() {
		stage.setScene(gs.getScene());
	}
	
	public static int randomIntBetween(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	/**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If there is no client_secret.
     */
    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GameScene.class.getResourceAsStream(CLIENT_SECRET_DIR);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

	public static void main(String[] args) {
		launch(args);
	}
}
