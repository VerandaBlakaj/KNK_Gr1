package home;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Locale;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Home extends Application {
	public static Scene home_scene;
	private Stage mainStage;
	private java.sql.Connection dbConnection;

	private TableView<Adresat> table = new TableView<Adresat>();
	private final ObservableList<Adresat> data = FXCollections.observableArrayList();

	//

	private void setConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/knk?useSSL=false", "root", "1234");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		setConnection();
		mainStage = primaryStage;
		HBox logo_pane = new HBox(10);
		GridPane menu_pane = new GridPane();
		ImageView logo = new ImageView(
				new Image("file:///C:/Users/DataProgNet/git/-Registering-and-finding-addresses-in-Kosovo/Images/logo.png"));

		logo.setFitHeight(45);
		logo.setFitWidth(45);
		Label logo_label = I18N.getLabel("logo_label");
		logo_label.setStyle("-fx-text-fill: white");
		logo_label.setFont(Font.font("Serif", FontWeight.BOLD, FontPosture.ITALIC, 20));
		logo_pane.getChildren().addAll(logo, logo_label);
		logo_pane.setStyle("-fx-background-color:#40E0D0");
		logo_pane.setPrefSize(250, 75);

		ImageView home_image = new ImageView(
				new Image("file:///C:/Users/DataProgNet/git/-Registering-and-finding-addresses-in-Kosovo/Images/home1.png"));
		ImageView modify_image = new ImageView(
				new Image("file:///C:/Users/DataProgNet/git/-Registering-and-finding-addresses-in-Kosovo/Images/edit1.png"));
		ImageView login_image = new ImageView(
				new Image("file:///C:/Users/DataProgNet/git/-Registering-and-finding-addresses-in-Kosovo/Images/login.png"));
		ImageView help_image = new ImageView(
				new Image("file:///C:/Users/DataProgNet/git/-Registering-and-finding-addresses-in-Kosovo/Images/info1.png"));
		Button home_button = I18N.getButton("home_button");

		Button login_button = I18N.getButton("login_button");

		Button help_button = I18N.getButton("help_button");

		home_button.setFont(Font.font("Serif", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
		home_button.setStyle("-fx-text-fill:white; -fx-border-color: transparent; -fx-background-color: transparent;");

		login_button.setFont(Font.font("Serif", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
		login_button.setStyle("-fx-text-fill:white; -fx-border-color: transparent; -fx-background-color: transparent;");

		login_button.setOnAction(e -> {
			Login obj = new Login();
			obj.open();
			mainStage.setScene(Login.login_scene);
		});
		menu_pane.setOnKeyPressed(ev -> {
			if (ev.isControlDown() && ev.getCode() == KeyCode.L) {
				Login obj = new Login();
				obj.open();
				mainStage.setScene(Login.login_scene);

			} else if (ev.isControlDown() && ev.getCode() == KeyCode.H) {
				new Help().open();
			}
		});

		help_button.setFont(Font.font("Serif", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
		help_button.setStyle("-fx-text-fill:white; -fx-border-color: transparent; -fx-background-color: transparent;");
		help_button.setOnAction(e -> {
			new Help().open();
		});

		menu_pane.setAlignment(Pos.TOP_CENTER);
		menu_pane.setPadding(new Insets(15.5, 0, 0.5, 0.5));
		menu_pane.setHgap(4.5);
		menu_pane.setVgap(5.5);
		menu_pane.setStyle("-fx-background-color:#40E0D0");
		menu_pane.add(home_image, 0, 0);
		menu_pane.add(home_button, 1, 0);
//		menu_pane.add(modify_image, 0, 1);
//		menu_pane.add(modify_button, 1, 1);
		menu_pane.add(login_image, 0, 2);
		menu_pane.add(login_button, 1, 2);
		menu_pane.add(help_image, 0, 3);
		menu_pane.add(help_button, 1, 3);
		menu_pane.setMinHeight(519);

		VBox left_pane = new VBox();
		left_pane.getChildren().addAll(logo_pane, menu_pane);

		Pane user_pane = new Pane();
		user_pane.setMinHeight(570);
		// *****************************************
		Group group = new Group();
//		stage.setTitle("Table View Sample");

		final Label label = new Label("Addresses");
		label.setFont(new Font("Arial", 20));

		table.setEditable(true);

		TableColumn rajonicol = I18N.getColumn("rajonicol");
		rajonicol.setMinWidth(100);
		rajonicol.setCellValueFactory(new PropertyValueFactory<>("Rajoni"));

		TableColumn komunacol = I18N.getColumn("komunacol");
		komunacol.setMinWidth(70);
		komunacol.setCellValueFactory(new PropertyValueFactory<>("Komuna"));

		TableColumn lagjjacol = I18N.getColumn("lagjjacol");
		lagjjacol.setMinWidth(100);
		lagjjacol.setCellValueFactory(new PropertyValueFactory<>("Lagjja"));

		TableColumn rrugacol = I18N.getColumn("rrugacol");
		rrugacol.setMinWidth(150);
		rrugacol.setCellValueFactory(new PropertyValueFactory<>("Rruga"));

		TableColumn objekticol = I18N.getColumn("objekticol");
		objekticol.setMinWidth(15);
		objekticol.setCellValueFactory(new PropertyValueFactory<>("Objekti"));

		TableColumn latitudecol = I18N.getColumn("latitudecol");
		latitudecol.setMinWidth(50);
		latitudecol.setCellValueFactory(new PropertyValueFactory<>("Latitude"));

		TableColumn longitudecol = I18N.getColumn("longitudecol");
		longitudecol.setMinWidth(50);
		longitudecol.setCellValueFactory(new PropertyValueFactory<>("Longitude"));

		try {

			String query = "select  Ra.EmriRajonit as Rajoni, K.EmriKomunes as Komuna, Lagjja, R.EmriRruges as Rruga, O.NumriHyrjes as"
					+ " Objekti, adresat.Latitude as Latitude, adresat.Longitude as Longitude from rajonet Ra, komunat K, rruget R, adresat, objektet O "
					+ " where adresat.idKomuna=K.idKomunat and adresat.idRruges=R.idRruget and adresat.idRajoni=Ra.idRajoni and adresat.idObjektit=O.idObjekti ";

			java.sql.PreparedStatement pst = dbConnection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				data.add(new Adresat(rs.getString("Rajoni"), rs.getString("Komuna"), rs.getString("Lagjja"),
						rs.getString("Rruga"), rs.getInt("Objekti"), rs.getDouble("Latitude"),
						rs.getDouble("Longitude")));
				table.setItems(data);
			}
			pst.close();
			rs.close();

		} catch (Exception ex) {
			System.err.println(ex);
		}

		FilteredList<Adresat> flAdresat = new FilteredList(data, p -> true);// Pass the data to a filtered list
		table.setItems(flAdresat);// Set the table's items using the filtered list
		table.getColumns().addAll(rajonicol, komunacol, lagjjacol, rrugacol, objekticol, latitudecol, longitudecol);

		// Adding ChoiceBox and TextField here!
		ComboBox<String> comboBox = new ComboBox();
		comboBox.getItems().addAll("Rajoni", "Komuna", "Rruga");
		comboBox.setValue("Komuna");

		TextField textField = new TextField();
		textField.setPromptText("Search here!");
		textField.setOnKeyReleased(keyEvent -> {
			switch (comboBox.getValue())// Switch on choiceBox value
			{
			case "Rajoni":
				flAdresat.setPredicate(
						p -> p.getRajoni().toLowerCase().contains(textField.getText().toLowerCase().trim()));// filter
				// table
				// by
				// first
				// name
				break;
			case "Komuna":
				flAdresat.setPredicate(
						p -> p.getKomuna().toLowerCase().contains(textField.getText().toLowerCase().trim()));// filter
				// table
				// by
				// first
				// name
				break;
			case "Rruga":
				flAdresat.setPredicate(
						p -> p.getRruga().toLowerCase().contains(textField.getText().toLowerCase().trim()));// filter
				// table by
				// first
				// name
				break;
			}
		});

		comboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {// reset table and
																									// textfield when
																									// new choice is
																									// selected
			if (newVal != null) {
				textField.setText("");
				flAdresat.setPredicate(p -> true);// This is same as saying flPerson.setPredicate(p->true);
			}
		});
		HBox hBox = new HBox(comboBox, textField);// Add choiceBox and textField to hBox
		hBox.setAlignment(Pos.CENTER);// Center HBox
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table, hBox);

		ScrollPane scp = new ScrollPane();
		scp.setContent(vbox);
		scp.setPrefSize(603, 570);

		scp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		group.getChildren().addAll(scp);

		user_pane.getChildren().add(group);
//		************************tabela e userit ***************************************8

		HBox language_pane = new HBox(5);
		ImageView uk_flag = new ImageView(new Image(
				"file:///C:/Users/DataProgNet/git/-Registering-and-finding-addresses-in-Kosovo/Images/united-kingdom.png"));
		ImageView ks_flag = new ImageView(new Image(
				"file:///C:/Users/DataProgNet/git/-Registering-and-finding-addresses-in-Kosovo/Images/kosovo.png"));
		uk_flag.setFitHeight(24);
		uk_flag.setFitWidth(24);
		ks_flag.setFitHeight(24);
		ks_flag.setFitWidth(24);

		Button english_button = new Button("English");
		english_button.setFont(Font.font("Serif", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12));
		english_button
				.setStyle("-fx-text-fill:white; -fx-border-color: transparent; -fx-background-color: transparent;");
		english_button.setOnAction(e -> {
			I18N.setLocale(new Locale("en"));
		});

		Button albanian_button = new Button("Shqip");
		albanian_button.setFont(Font.font("Serif", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12));
		albanian_button
				.setStyle("-fx-text-fill:white; -fx-border-color: transparent; -fx-background-color: transparent;");
		albanian_button.setOnAction(e -> {
			I18N.setLocale(new Locale("al"));
		});

		language_pane.setAlignment(Pos.BOTTOM_RIGHT);
		language_pane.setMaxHeight(20);
		language_pane.setMinWidth(600);
		language_pane.getChildren().addAll(uk_flag, english_button, ks_flag, albanian_button);
		language_pane.setStyle("-fx-background-color:#40E0D0");

		VBox right_pane = new VBox();
		right_pane.getChildren().addAll(user_pane, language_pane);

		HBox main_pane = new HBox();
		main_pane.getChildren().addAll(left_pane, right_pane);

		home_scene = new Scene(main_pane, 850, 594);

		primaryStage.setScene(home_scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.getIcons().add(
				new Image("file:///C:/Users/DataProgNet/git/-Registering-and-finding-addresses-in-Kosovo/Images/logo.png"));
		primaryStage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);

	}

	public static class Adresat {
//		private final SimpleIntegerProperty ID;
		private final SimpleStringProperty Rajoni;
		private final SimpleStringProperty Komuna;
		private final SimpleStringProperty Lagjja;
		private final SimpleStringProperty Rruga;
		private final SimpleIntegerProperty Objekti;
		private final SimpleDoubleProperty Latitude;
		private final SimpleDoubleProperty Longitude;

		private Adresat(String Rajoni, String Komuna, String Lagjja, String Rruga, int Objekti, double Latitude,
				double Longitude) {
//		this.ID = new SimpleIntegerProperty(ID);
			this.Rajoni = new SimpleStringProperty(Rajoni);
			this.Komuna = new SimpleStringProperty(Komuna);
			this.Lagjja = new SimpleStringProperty(Lagjja);
			this.Rruga = new SimpleStringProperty(Rruga);
			this.Objekti = new SimpleIntegerProperty(Objekti);
			this.Latitude = new SimpleDoubleProperty(Latitude);
			this.Longitude = new SimpleDoubleProperty(Longitude);

		}

//		public int getId()
//		{
//		return ID.get();
//		}

		public String getRajoni() {
			return Rajoni.get();
		}

		public String getKomuna() {
			return Komuna.get();
		}

		public String getLagjja() {
			return Lagjja.get();
		}

		public String getRruga() {
			return Rruga.get();
		}

		public int getObjekti() {
			return Objekti.get();
		}

		public double getLatitude() {
			return Latitude.get();
		}

		public double getLongitude() {
			return Longitude.get();
		}
	}

}