/**
  * Created by Nick on 28/06/2016.
  */
import scala.collection.mutable.Queue

object Stock extends Entity{

  var dbTableName = "stock"

  override var dbTableColumns: Queue[String] = new Queue[String]
  dbTableColumns += ("ProductID", "ItemName", "Price", "Quantity", "Location")

  override var dbTableTypes: Queue[String] = new Queue[String]
  dbTableTypes += ("string", "string", "double", "int", "string")

  var stockItems = new Queue[StockItemDescription]

  case class StockItemDescription(productID: String, itemName: String, price: Double, quantity: Int, location: String) extends EntityDescription {

  }

  override def createEntity(): Unit = {
    var addNewStock = new StockItemDescription(stringParams(0), stringParams(1),doubleParams(0), intParams(0), stringParams(2))
    stockItems += addNewStock
  }
}
