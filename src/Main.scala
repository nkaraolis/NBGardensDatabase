/**
  * Created by Nick on 28/06/2016.
  */

object Main {


  def main(args: Array[String]) {
    Order.populateEntity(Order)
    for(order <- Order.orders) {
      println(order.orderID)
    }
  }
}
