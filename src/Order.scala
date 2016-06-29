/**
  * Created by Nick on 28/06/2016.
  */

import java.sql.{Connection, DriverManager}
import scala.collection.mutable.Queue

//Represents customer orders in the system
object Order extends Entity {

  //Set the table name to be orders
  var dbTableName = "orders"

  //Column names for the orders table
  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns +=("OrderID", "Date", "Price", "CustomerName", "CustomerID", "Address", "Status", "StaffAssigned")

  //Column types for the orders table
  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes +=("string", "string", "double", "string", "string", "string", "string", "string")

  //Collection of orders
  var orders = new Queue[OrderDescription]

  //Case class to represent one order
  case class OrderDescription(orderID: String, date: String, price: Double, customerName: String, customerID: String, address: String, status: String, staff: String) extends EntityDescription {

  }

  //Create a new order and add it to the collection of orders
  override def createEntity(): Unit = {
    var addNewOrder = new OrderDescription(stringParams(0), stringParams(1), doubleParams(0), stringParams(2), stringParams(3), stringParams(4), stringParams(5), stringParams(6))
    orders += addNewOrder
  }

  //Assign a staff member to an order
  def assignStaff(ordID: String, staffMember: String): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      statement.executeUpdate("UPDATE " + dbTableName + " SET staffAssigned='" + staffMember + "' WHERE OrderID = '" + ordID + "'")
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    orders.clear()
    Order.populateEntity(Order)
  }
}