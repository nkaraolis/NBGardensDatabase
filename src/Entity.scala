import java.sql.{Connection, DriverManager}

import scala.collection.mutable

/**
  * Created by Nick on 28/06/2016.
  */
trait Entity {

  val url = "jdbc:mysql://localhost:3306/nbgardensv0.2?autoReconnect=true&useSSL=false"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = "password"
  var connection: Connection = _

  var dbTableName : String
  var dbTableColumns : mutable.Queue[String]
  var dbTableTypes: mutable.Queue[String]

  var stringParams = new mutable.Queue[String]
  var intParams = new mutable.Queue[Int]
  var doubleParams = new mutable.Queue[Double]

  def createEntity(): Unit = {}

  def populateEntity(entity: Entity): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("Select * from " + entity.dbTableName)
      while (rs.next) {
        //Collect data for each row
        var counter = 0
        while (counter<entity.dbTableColumns.length){
          if(entity.dbTableTypes(counter).equals("int")){
            intParams += rs.getInt(dbTableColumns(counter))
          }
          if(entity.dbTableTypes(counter).equals("string")){
            stringParams += rs.getString(dbTableColumns(counter))
          }
          if(entity.dbTableTypes(counter).equals("double")){
            doubleParams += rs.getDouble(dbTableColumns(counter))
          }
          counter += 1
        }
        entity.stringParams = stringParams
        entity.intParams = intParams
        entity.doubleParams = doubleParams
        entity.createEntity()
        stringParams.clear()
        intParams.clear()
        doubleParams.clear()
      }
    }
    catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
  }

}
