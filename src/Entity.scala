import java.sql.{Connection, DriverManager}

import scala.collection.mutable._

/**
  * Created by Nick on 28/06/2016.
  */
trait Entity {

  val url = "jdbc:mysql://localhost:3306/nbgardens?autoReconnect=true&useSSL=false"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = "password"
  var connection: Connection = _

  var dbTableName : String
  var dbTableColumns : Array[String] = Array.empty
  var dbTableTypes: Array[String]

  def createEntity(parameters: Array[EntityDescription]): Unit = {}

  def populateEntity(entity: Entity): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("Select * from " + entity.dbTableName)
      while (rs.next) {
        //New row to add to the entity's collection
        val newEntityArgs = new Array[EntityDescription](1)
        //Collect data for each row
        var counter = 0
        while (counter<entity.dbTableColumns.length){
          if(entity.dbTableTypes(counter).equals("int")){
            newEntityArgs :+ rs.getInt(dbTableColumns(counter))
          }
          if(entity.dbTableTypes(counter).equals("string")){
            newEntityArgs :+ rs.getString(dbTableColumns(counter))
          }
          if(entity.dbTableTypes(counter).equals("double")){
            newEntityArgs :+ rs.getDouble(dbTableColumns(counter))
          }
          counter += 1
        }
        //Adds it to the collection
        entity.createEntity(newEntityArgs)
      }
    }
    catch {
      case e: Exception => e.printStackTrace
    }
    connection.close
  }

}
