/**
  * Created by Nick on 28/06/2016.
  */
import scala.collection.mutable.Queue

object StockOrder extends Entity{
  var dbTableName = "stockorder"
  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns += ("StockOrderID", "ItemName", "Quantity", "Price")
  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes += ("string", "string", "int", "double")

  var stockOrders = new Queue[StockOrderDescription]

  case class StockOrderDescription(stockOrderID: String, itemName: String, quantity : Int, price: Double) extends EntityDescription {

  }

  override def createEntity(): Unit = {
    var addNewStockOrder = new StockOrderDescription(stringParams(0), stringParams(1), intParams(0), doubleParams(0))
    stockOrders += addNewStockOrder
  }

}
