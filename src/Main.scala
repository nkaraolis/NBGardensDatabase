/**
  * Created by Nick on 28/06/2016.
  */
import Order.OrderDescription

import scala.collection.mutable.Queue

object Main {
  def main(args: Array[String]): Unit = {
    Order.populateEntity(Order)
    OrderItem.populateEntity(OrderItem)
    Stock.populateEntity(Stock)
    StockOrder.populateEntity(StockOrder)
    Order.notifyAccounts
  }
}
