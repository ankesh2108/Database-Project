package com.dbmsproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	@FXML
	private TextField tf_item_name;
	@FXML
	private TextField tf_quantity;
	@FXML
	private TextField tf_price;
	@FXML
	private DatePicker dp_date;
	@FXML
	private ComboBox cb_family_member;
	@FXML
	private TableView<Grocery> tableView_grocery;
	@FXML
	private TableColumn<Grocery, Integer> col_id;
	@FXML
	private TableColumn<Grocery, String> col_itemname;
	@FXML
	private TableColumn<Grocery, Integer> col_quantity;
	@FXML
	private TableColumn<Grocery, Float> col_price;
	@FXML
	private TableColumn<Grocery, String> col_date;
	@FXML
	private TableColumn<Grocery, String> col_orderedby;
	@FXML
	private Button btn_add;
	@FXML
	private Button btn_update;
	@FXML
	private Button btn_delete;

	private Grocery currentlySelectedGrocery=null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showGroceryInTable();
	}


	public Connection getConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbmsproject?autoReconnect=true&useSSL=false", "root", "7887");
			return conn;
		} catch (Exception e) {
			System.out.println("Error :" + e.getMessage());
			return null;
		}
	}


	public ObservableList<Grocery> getAllGroceries() {


		ObservableList<Grocery> groceryObservableList = FXCollections.observableArrayList();

		Connection conn = getConnection();
		String query = "SELECT * FROM grocery";
		Statement st;
		ResultSet resultSet;


		try {
			st = conn.createStatement();
			resultSet = st.executeQuery(query);

			while (resultSet.next()) {
				Grocery grocery = new Grocery(resultSet.getInt("id"),
						resultSet.getString("item_name"),
						resultSet.getInt("quantity"),
						resultSet.getFloat("price"),
						resultSet.getString("ordered_date"),
						resultSet.getString("ordered_by"));

				groceryObservableList.add(grocery);
			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return groceryObservableList;
	}

	public void showGroceryInTable() {
		ObservableList<Grocery> groceryList = getAllGroceries();

		col_id.setCellValueFactory(new PropertyValueFactory<Grocery, Integer>("id"));
		col_itemname.setCellValueFactory(new PropertyValueFactory<Grocery, String>("item_name"));
		col_quantity.setCellValueFactory(new PropertyValueFactory<Grocery, Integer>("quantity"));
		col_price.setCellValueFactory(new PropertyValueFactory<Grocery, Float>("price"));
		col_date.setCellValueFactory(new PropertyValueFactory<Grocery, String>("date"));
		col_orderedby.setCellValueFactory(new PropertyValueFactory<Grocery, String>("orderBy"));

		tableView_grocery.setItems(groceryList);
	}

	public void insertGrocery() {
		String date = dp_date.getValue() + "";

		String query = "INSERT INTO grocery(item_name, quantity, price, ordered_date, ordered_by) values('" + tf_item_name.getText() + "',"
				+ tf_quantity.getText() + ","
				+ tf_price.getText() + ",'"
				+ date + "','"
				+ cb_family_member.getValue() + "')";

		executeMyQuery(query);
		showGroceryInTable();
	}


	public void updateGrocery() {
		String query = "UPDATE grocery set item_name = '"+ tf_item_name.getText()
				+"', quantity= "+tf_quantity.getText()
				+", price= "+tf_price.getText()
				+", ordered_date= '"+dp_date.getValue()
				+"', ordered_by= '"+cb_family_member.getValue()
				+"' where id = "+currentlySelectedGrocery.getId();


		executeMyQuery(query);
		showGroceryInTable();
	}

	public void deleteGrocery() {
		String query = "DELETE FROM grocery where id = "+currentlySelectedGrocery.getId() ;

		executeMyQuery(query);
		showGroceryInTable();
	}

	private void executeMyQuery(String query) {
		Connection conn = getConnection();
		Statement st;

		try {
			st = conn.createStatement();
			st.executeUpdate(query);

		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
	}

	@FXML
	public void handleMouseAction(MouseEvent mouseEvent) {
		currentlySelectedGrocery = tableView_grocery.getSelectionModel().getSelectedItem();
		tf_item_name.setText(currentlySelectedGrocery.getItem_name());
		tf_quantity.setText(currentlySelectedGrocery.getQuantity()+"");
		tf_price.setText(currentlySelectedGrocery.getPrice()+"");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(currentlySelectedGrocery.getDate(), formatter);
		dp_date.setValue(date);
		cb_family_member.setValue(currentlySelectedGrocery.getOrderBy());

	}
}
