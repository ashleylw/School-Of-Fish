
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameScene {
    
    /**
     * Variables related to JavaFX Visual Component
     */
    private Main main;
    private Scene scene;
    private BorderPane root;
    private StackPane top;
    private MainGameView center;
    private GameListView left;
    private PlayerListView right;
    private LogView bottom;
    
    private Label title;
    private Label label;
    
	public GameScene() {
		setUp();
	}
    
    public void setUp() {
		root = new BorderPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setBackground(Main.LIGHTCYAN);
		
		setUpView();
		
		scene = new Scene(root, 1200, 700);
	}
    
    public Scene getScene() {
    	return scene;
    }
    
    public void setUpView() {
    	top = new StackPane();
    	center = new MainGameView();
    	left = new GameListView();
    	right = new PlayerListView();
    	bottom = new LogView();
    	
    	title = new Label("School Of Fish");
    	title.setFont(new Font("Arial", 24));
    	top.getChildren().add(title);
    	
    	root.setTop(top);
    	root.setCenter(center);
    	root.setLeft(left);
    	root.setRight(right);
    	root.setBottom(bottom);
    }
}