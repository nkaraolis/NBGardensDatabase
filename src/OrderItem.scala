/**
  * Created by Nick on 28/06/2016.
  */
import java.sql.{Connection, DriverManager}
import scala.collection.mutable.Queue

object OrderItem extends Entity{

    var dbTableName = "orderitems"
    override var dbTableColumns: Queue[String] = new Queue[String]
    dbTableColumns += ("OrderID", "ProductID", "Quantity", "PorousRequired", "Location")
    override var dbTableTypes: Queue[String] = new Queue[String]
    dbTableTypes += ("string", "string", "int", "string", "string")

    var orderItems = new Queue[OrderItemDescription]

    case class OrderItemDescription(orderID: String, productID: String, quantity: Int, porousReq: String, location: String) extends EntityDescription {

    }

    override def createEntity(): Unit = {
      var addNewOrder = new OrderItemDescription(stringParams(0), stringParams(1), intParams(0), stringParams(2), stringParams(3))
      orderItems += addNewOrder
    }
}
