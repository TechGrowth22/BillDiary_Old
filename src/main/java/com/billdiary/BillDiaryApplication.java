package com.billdiary;

import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.billdiary.utility.Constants;
import com.billdiary.utility.URLS;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages ="com.billdiary")
public class BillDiaryApplication extends Application{
	
	 final static Logger LOGGER = LoggerFactory.getLogger(BillDiaryApplication.class);

	 private ConfigurableApplicationContext context;
	 
	 
	 private Pane splashLayout;
	 private ProgressBar loadProgress;
	 private Label progressText;
	 private Stage mainStage;
	 private static final int SPLASH_WIDTH = 720;
	 private static final int SPLASH_HEIGHT = 227;
	 public static final String SPLASH_IMAGE ="";
	 private Parent rootNode;
	 
	 /**
	     * main Method thats starts the application
	     * @param args
	     */
    public static void main(String[] args) {
		launch(args); 
	}
	 
    @SuppressWarnings("restriction")
	@Override
    public void init() throws Exception {
    	ImageView splash = new ImageView(new Image(
                getClass().getResourceAsStream("/icon/BillDiary2.png")
        ));
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
        progressText = new Label("Will find friends for peanuts . . .");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle(
                "-fx-padding: 5; " +
                "-fx-background-color: cornsilk; " +
                "-fx-border-width:5; " +
                "-fx-border-color: " +
                    "linear-gradient(" +
                        "to bottom, " +
                        "chocolate, " +
                        "derive(chocolate, 50%)" +
                    ");"
        );
        splashLayout.setEffect(new DropShadow());
    
    }
 
    @Override
    public void start(Stage primaryStage) throws Exception {
    	SpringApplicationBuilder builder = new SpringApplicationBuilder(BillDiaryApplication.class);
    	builder.headless(false);
    	final Task<ObservableList<String>> splashfriendTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {
                ObservableList<String> foundFriends =
                        FXCollections.<String>observableArrayList();
                updateMessage("Initializing.");
                context = builder.run(getParameters().getRaw().toArray(new String[0]));
                for(int i = 0; i < 4; i++) {
                	Thread.sleep(4);
                	updateProgress(i + 1, 10);
                	foundFriends.add("A");
                	updateMessage("Initializing..");
                }
                
                updateMessage("Initializing...");
                Thread.sleep(4);
                updateMessage("All friends found.");
                return foundFriends;
            }
         };
    	
        showSplashScreen(primaryStage,splashfriendTask,() -> {
			try {
				showMainStage(splashfriendTask.valueProperty());
			} catch (IOException e) {
				LOGGER.error(e.getMessage(),e);
			}
		});
        new Thread(splashfriendTask).start();

    }
    
    private void showSplashScreen(final Stage initStage,Task<?> task,InitCompletionHandler initCompletionHandler) {
		progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();
                initCompletionHandler.complete();
            } 
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final javafx.geometry.Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.setResizable(true);
        initStage.show();			
	}
	public interface InitCompletionHandler {
        void complete();
    }
	
	
	/*@Autowired
	private SpringFxmlLoader loader;*/
	/**
	 * Main method thatopens login page
	 * @param friends
	 * @throws IOException 
	 */
	private void showMainStage(ReadOnlyObjectProperty<ObservableList<String>> friends) throws IOException {
		System.out.println("Spring Application context"+context);
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(context::getBean);
		InputStream fxmlStream = BillDiaryApplication.class.getResourceAsStream(URLS.LOGIN_PAGE);
		Parent root = (Parent) loader.load(fxmlStream);
		mainStage = new Stage(StageStyle.DECORATED);
		javafx.geometry.Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        double width = visualBounds.getWidth();
        double height = visualBounds.getHeight();
		Scene scene = new Scene(root,width, height-30); 
		mainStage.setTitle(Constants.APPLICATION_TITLE);
		mainStage.setScene(scene);
		mainStage.show();	
	}
    
 
    @Override
    public void stop() throws Exception {
        context.close();
    }

	
}
