/**
  * Created by Nick on 28/06/2016.
  */

import java.sql.{Connection, DriverManager}

import scala.collection.mutable

object Order extends Entity {

  var dbTableName = "orders"
 
  var dbTableTypes = {"int"}

  var orders = mutable.Queue[OrderDescription]()

  case class OrderDescription(orderID: String, date: String, price: Double, customerName: String, customerID: String, address: String, status: String, staff: String) extends EntityDescription {

  }

  override def createEntity(parameters: Array[EntityDescription]): Unit = {
    orders +: parameters
  }
}