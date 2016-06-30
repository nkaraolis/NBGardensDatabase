/**
  * Created by Nick on 28/06/2016.
  */
import scala.collection.mutable.Queue

object OrderItem extends Entity {

  //Set the table name to use the order items table
  var dbTableName = "orderitems"

  //Column names for the order items table
  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns +=("OrderID", "ProductID", "Quantity", "PorousRequired", "Location")

  //Column types for the order items table
  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes +=("string", "string", "int", "string", "string")

  //Collection of order items
  var orderItems = new Queue[OrderItemDescription]

  //Case class to represent one item for an order
  case class OrderItemDescription(orderID: String, productID: String, quantity: Int, porousReq: String, location: String) extends EntityDescription {
    override var entityID: String = orderID
  }

  //Create a new orderItemDescription (order item row) and add it to the collection of order items
  override def createEntity(): Unit = {
    var addNewItem = new OrderItemDescription(stringParams(0), stringParams(1), intParams(0), stringParams(2), stringParams(3))
    orderItems += addNewItem
  }
}
