package com.dbmsproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditMembers implements Initializable {
	@FXML
	private TableView tv_members;
	@FXML
	private TableColumn col_sr_no;
	@FXML
	private TableColumn col_member_name;

	private TextInputDialog td;

	List<String> mem = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showMembersInTable();

	}


	public void getMemberName(ActionEvent actionEvent) {
		td = new TextInputDialog();
		td.setHeaderText("Enter name");
		td.setWidth(500.0);


		Optional<String> result = td.showAndWait();
		if (result.isPresent()) {
			System.out.println(td.getEditor().getText());
		} else {
			System.out.println("Cancel");
		}

	}

	public void deleteMember(ActionEvent actionEvent) {

	}





	public void showMembersInTable() {

	}
}
