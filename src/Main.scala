/**
  * Created by Nick on 28/06/2016.
  */

object Main {
  def main(args: Array[String]) {
    StockOrder.populateEntity(StockOrder)
    for(order <- StockOrder.stockOrders){
      println(order.itemName, order.quantity)
    }

  }
}
