/**
  * Created by Nick on 28/06/2016.
  */
import java.sql.DriverManager
import OrderItem.OrderItemDescription
import scala.collection.mutable.Queue

object Stock extends Entity {

  //Set the table name to use the stock table
  var dbTableName = "stock"

  //Column names for the stock table
  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns +=("ProductID", "ItemName", "Price", "Quantity", "Location", "HasPorous")

  //Column types for the stock table
  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes +=("string", "string", "double", "int", "string", "string")

  //Collection of stock
  var stockItems = new Queue[stockItemDescription]

  //Case class to represent one stock item
  case class stockItemDescription(productID: String, itemName: String, price: Double, quantity: Int, location: String, hasPorous: String) extends EntityDescription {
    override var entityID: String = productID
  }

  //Create a new stockItemDescription (stock row) and add it to the collection of orders
  override def createEntity(): Unit = {
    var addNewStock = new stockItemDescription(stringParams(0), stringParams(1), doubleParams(0), intParams(0), stringParams(2), stringParams(3))
    stockItems += addNewStock
  }

  //Update the stock levels based on an order
  def decrementStock(ordID: String): Unit = {

    var reqItems = Queue[String]()
    var reqQuantities = Queue[Int]()

    //Find the items and their quantities for the required order
    def findItems(ordID: String, items: Queue[OrderItemDescription]): Unit = {
      if (items.isEmpty) {

      } else if (items.head.orderID == ordID) {
        reqItems += items.head.productID
        reqQuantities += items.head.quantity
        findItems(ordID, items.tail)
      } else {
        findItems(ordID, items.tail)
      }
    }
    //Call the nested function to find the required items and quantities
    findItems(ordID, OrderItem.orderItems)

    //Update the stock values
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.prepareStatement("UPDATE stock SET Quantity = (Quantity - ?) WHERE ProductID = ?")

      var counter = 0
      for (item <- reqItems) {
        statement.setInt(1, reqQuantities(counter))
        statement.setString(2, item)
        statement.addBatch()
        counter += 1
        println(statement)
      }
      statement.executeBatch()
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close

    reqItems.clear
    reqQuantities.clear
  }
}
