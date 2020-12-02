package com.dbmsproject.controller;

import com.dbmsproject.connection.ManageConnection;
import com.dbmsproject.dataholders.Members;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditMembers implements Initializable {
	@FXML
	private TableView<Members> tv_members;
	@FXML
	private TableColumn<Members, Integer> col_sr_no;
	@FXML
	private TableColumn<Members, String> col_member_name;

	private TextInputDialog td;

	private Members currentlySelectedMember = null;

	public ManageConnection manageConnection;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		manageConnection = ManageConnection.createInstance();

		showMembersInTable();

	}

	public ObservableList<Members> getAllMembers() {
		ObservableList<Members> allMembersList = FXCollections.observableArrayList();
		String query = "SELECT * FROM members";
		ResultSet rs = manageConnection.executeQueryForResult(query);

		try {
			while (rs.next()) {

				Members members = new Members(rs.getInt("mem_id"), rs.getString("mem_name"));
				allMembersList.add(members);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return allMembersList;
	}


	public void getMemberName(ActionEvent actionEvent) {
		td = new TextInputDialog();
		td.setHeaderText("Enter name");
		td.setWidth(500.0);


		Optional<String> result = td.showAndWait();

		if (result.isPresent()) {
			addMember(td.getEditor().getText());
		} else {
			System.out.println("Cancel");
		}

	}

	public void addMember(String name) {
		String query = "INSERT INTO members(mem_name) values('" + name + "')";
		manageConnection.executeUpdateQuery(query);
		showMembersInTable();

	}

	public void deleteMember(ActionEvent actionEvent) {
		String query = "DELETE FROM members where mem_id = " + currentlySelectedMember.getMem_id();
		manageConnection.executeUpdateQuery(query);
		showMembersInTable();
	}


	public void showMembersInTable() {
		ObservableList<Members> allmembersList = getAllMembers();
		col_sr_no.setCellValueFactory(new PropertyValueFactory<Members, Integer>("mem_id"));
		col_member_name.setCellValueFactory(new PropertyValueFactory<Members, String>("memName"));
		tv_members.setItems(allmembersList);
	}


	public void handleMouseAction(MouseEvent mouseEvent) {
		currentlySelectedMember = tv_members.getSelectionModel().getSelectedItem();
	}


}
