/**
  * Created by Nick on 28/06/2016.
  */

import java.sql.{Connection, DriverManager}

import scala.collection.mutable

object Order extends Entity {

  val url = "jdbc:mysql://localhost:3306/nbgardens?autoReconnect=true&useSSL=false"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = "password"
  var connection: Connection = _


  case class OrderDescription(orderID: String, date: String, price: Double, customerName: String, customerID: String, address: String, status: String, staff: String) extends EntityDescription {


  }

  var orders = mutable.Queue[OrderDescription]()

  //Populate the orders array with all orders from the database
  def populateOrders(): mutable.Queue[OrderDescription] = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("Select * from orders")
      while (rs.next) {
        val newOrder = new OrderDescription(rs.getString("OrderID"), rs.getString("Date"), rs.getDouble("Price"), rs.getString("CustomerName"),
          rs.getString("CustomerID"), rs.getString("Address"), rs.getString("Status"), rs.getString("StaffAssigned"))
        orders.+=(newOrder)
      }
    }
    catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    orders
  }


}