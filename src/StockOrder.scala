/**
  * Created by Nick on 28/06/2016.
  */
import java.sql.{Date, DriverManager}
import scala.collection.mutable.Queue

object StockOrder extends Entity {

  //Set the table name to use the orders table
  var dbTableName = "stockorder"

  //Column names for the stock orders table
  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns +=("StockOrderID","ProductID", "ItemName", "Quantity", "Price", "Date", "ForPorous")

  //Column types for the stock orders table
  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes +=("string", "string",  "string", "int", "double", "date", "string")

  //Collection of stock orders
  var stockOrders = new Queue[StockOrderDescription]

  //Case class to represent one stock order
  case class StockOrderDescription(stockOrderID: String, productID: String, itemName: String, quantity: Int, price: Double, ordDate: Date, forPorous: String) extends EntityDescription {
    override var entityID: String = stockOrderID
  }

  //Create a new stockOrderDescription (stock order row) and add it to the collection of stock orders
  override def createEntity(): Unit = {
    var addNewStockOrder = new StockOrderDescription(stringParams(0), stringParams(1), stringParams(2), intParams(0), doubleParams(0), dateParams(0), stringParams(3))
    stockOrders += addNewStockOrder
  }

  //Add a new stock order to the database and update the stock orders collection afterwards
  def addNewStock(prodID: String, quantity: Int): Unit = {
    //Update the stock values
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      statement.executeUpdate("UPDATE stock SET Quantity = (Quantity + " +  quantity + ") WHERE ProductID = '" + prodID + "'")
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
    stockOrders.clear()
    this.populateEntity(this)
  }
}
