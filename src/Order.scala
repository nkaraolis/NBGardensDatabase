/**
  * Created by Nick on 28/06/2016.
  */
import java.sql.{Connection, DriverManager}
import scala.collection.mutable.Queue

object Order extends Entity {

  var dbTableName = "orders"
  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns += ("OrderID", "Date", "Price", "CustomerName", "CustomerID", "Address", "Status", "StaffAssigned")
  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes += ("string", "string", "double", "string", "string", "string", "string", "string")

  var orders = new Queue[OrderDescription]

  case class OrderDescription(orderID: String, date: String, price: Double, customerName: String, customerID: String, address: String, status: String, staff: String) extends EntityDescription {

  }

  override def createEntity(): Unit = {
    var addNewOrder = new OrderDescription(stringParams(0), stringParams(1), doubleParams(0), stringParams(2), stringParams(3), stringParams(4), stringParams(5), stringParams(6))
    orders += addNewOrder
  }
}