package com.dbmsproject.controller;

import com.dbmsproject.connection.ManageConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
	@FXML
	private Label label_show_error;
	@FXML
	private TextField tf_username;
	@FXML
	private TextField tf_password;
	@FXML
	private Button btn_login;

	private ManageConnection manageConnection;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		manageConnection = ManageConnection.createInstance();
		label_show_error.setText("");
	}


	public void handleButtonAction(ActionEvent actionEvent) throws IOException {

		if (areFieldsEmpty()) {
			label_show_error.setText("Text fields cannot be empty");
			return;
		}

		

		Parent main_window = FXMLLoader.load(getClass().getClassLoader().getResource("com/dbmsproject/fxml/main_window.fxml"));
		Scene main_window_scene = new Scene(main_window, 1200, 800);
		Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		app_stage.setScene(main_window_scene);
		app_stage.setResizable(false);
		app_stage.show();
	}

	private boolean areFieldsEmpty() {

		if (tf_password.getText().trim().isEmpty() || tf_username.getText().trim().isEmpty())
			return true;
		else
			return false;
	}
}
