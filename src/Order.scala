/**
  * Created by Nick on 28/06/2016.
  */
import scala.collection.mutable

object Order extends Entity {

  case class OrderDescription(test : String, hello : Int) extends EntityDescription{

  }

  var orders = mutable.Queue[OrderDescription]
}
