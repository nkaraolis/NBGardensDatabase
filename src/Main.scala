/**
  * Created by Nick on 28/06/2016.
  */

import Order._
import OrderItem._

object Main {
  def main(args: Array[String]) {
    Order.populateEntity(Order)
    for (order <- Order.orders) {
      println(order.orderID, order.customerName)
    }
    OrderItem.populateEntity(OrderItem)
    for (item <- OrderItem.orderItems) {
      println(item.orderID, item.productID)
    }
  }
}
