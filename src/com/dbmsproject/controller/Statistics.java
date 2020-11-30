package com.dbmsproject.controller;

import com.dbmsproject.dataholders.DataForPieChart;
import com.dbmsproject.dataholders.Members;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class Statistics implements Initializable {

	@FXML
	private PieChart member_wise_pie_chart;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populateDataInPiechart();
	}


	public void populateDataInPiechart() {
		ObservableList<DataForPieChart> memberWiseData = getMemberWiseData();


		ObservableList<PieChart.Data> memberwisePieChart = FXCollections.observableArrayList();



		for (DataForPieChart dataForPieChart : memberWiseData) {
			PieChart.Data memberwisePieData = new PieChart.Data(dataForPieChart.getName(), dataForPieChart.getMoney());
			System.out.println(dataForPieChart.getName()+" : "+dataForPieChart.getMoney());
			memberwisePieChart.add(memberwisePieData);
		}

		member_wise_pie_chart.setData(memberwisePieChart);

		//Setting the title of the Pie chart
		member_wise_pie_chart.setTitle("Member wise total money spent");

		//setting the direction to arrange the data
		member_wise_pie_chart.setClockwise(true);

		//Setting the length of the label line
		member_wise_pie_chart.setLabelLineLength(10);

		//Setting the labels of the pie chart visible
		member_wise_pie_chart.setLabelsVisible(true);

		//Setting the start angle of the pie chart
		member_wise_pie_chart.setStartAngle(180);
		DecimalFormat df = new DecimalFormat("##.00");
		memberwisePieChart.forEach(data ->
				data.nameProperty().bind(
						Bindings.concat(
								data.getName(), " ₹", df.format(data.getPieValue())
						)
				)
		);

	}


	public ObservableList<DataForPieChart> getMemberWiseData() {
		ObservableList<DataForPieChart> memberWiseData = FXCollections.observableArrayList();
		Connection connection = getConnection();

		Statement st;
		ResultSet rs;
		String query = "SELECT members.mem_name, ROUND(SUM(grocery.price),"+2+") FROM grocery JOIN members ON grocery.mem_id = members.mem_id GROUP By members.mem_name";

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {

				DataForPieChart dataForPieChart = new DataForPieChart(rs.getString(1), rs.getFloat(2));
				memberWiseData.add(dataForPieChart);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return memberWiseData;
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
}
