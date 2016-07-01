/**
  * Created by Nick on 28/06/2016.
  */

import java.sql.{Connection, Date, DriverManager}
import scala.collection.mutable.Queue

//Represents customer orders in the system
object Order extends Entity {

  //Set the table name to use the orders table
  var dbTableName = "orders"

  //Column names for the orders table
  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns +=("OrderID", "Date", "Price", "CustomerName", "CustomerID", "Address", "Status", "StaffAssigned")

  //Column types for the orders table
  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes +=("string", "date", "double", "string", "string", "string", "string", "string")

  //Collection of orders
  var orders = new Queue[OrderDescription]

  //Collection of orders for the accounts department
  var accountsOrders = new Queue[OrderDescription]

  //Case class to represent one order
  case class OrderDescription(orderID: String, date: Date, price: Double, customerName: String, customerID: String, address: String, status: String, staff: String) extends EntityDescription {
    override var entityID: String = orderID
  }

  //Create a new orderDescription (order row) and add it to the collection of orders
  override def createEntity(): Unit = {
    var addNewOrder = new OrderDescription(stringParams(0), dateParams(0), doubleParams(0), stringParams(1), stringParams(2), stringParams(3), stringParams(4), stringParams(5))
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
    this.populateEntity(this)
  }

  //Update order status
  def updateOrderStatus(ordID: String, newStatus: String): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      statement.executeUpdate("UPDATE orders SET Status='" + newStatus + "' WHERE OrderID = '" + ordID + "'")
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    orders.clear()
    this.populateEntity(this)
  }

  //Select all the orders that have been dispatched
  def notifyAccounts(): Unit = {
    accountsOrders.clear()
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT * FROM orders WHERE Status = 'Dispatched'")
      while (rs.next) {
        var newOrder = new OrderDescription(rs.getString("OrderID"), rs.getDate("Date"), rs.getDouble("Price"), rs.getString("CustomerName"), rs.getString("CustomerID"), rs.getString("Address"), rs.getString("Status"), rs.getString("StaffAssigned"))
        accountsOrders += newOrder
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
  }

  //Find an order by a given order ID
  def findByID(entityDescID: String, ordersList: Queue[OrderDescription]): Queue[OrderDescription] = {
    var requiredOrder = new Queue[OrderDescription]
    if (ordersList.isEmpty) {
      requiredOrder
    } else if (entityDescID == ordersList.head.orderID) {
      requiredOrder += ordersList.head
      requiredOrder
    } else {
      this.findByID(entityDescID, ordersList.tail)
    }
  }
}