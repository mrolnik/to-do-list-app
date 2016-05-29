package modules

import javax.inject.{Inject, Singleton}

import models.TodoId
import play.api.db
import play.api.db.Database

import scala.util.{Success, Try}

/**
  * Created by mica on 28/05/16.
  */
@Singleton
class DB @Inject()(db : Database){

  db.withConnection {conn=>
    val stmt = conn.createStatement()
    stmt.execute("CREATE TABLE TODO (" +
      "id MEDIUMINT NOT NULL AUTO_INCREMENT," +
      "description VARCHAR(255) NOT NULL," +
      "PRIMARY KEY (id));")

    //stmt.execute("INSERT INTO TODO (description) VALUES ('Buy some milk'), ('Pay the debts')")
  }

  def addItem(description: String) : Try[Int] = {

    db.withConnection { conn =>
      val stmt = conn.createStatement()
  stmt.pr
      val rs = stmt.executeQuery(s"INSERT INTO TODO (description) VALUES ('${description}')")

      while (rs.next()) {
        resp += rs.getString("DESCRIPTION")
      }
      Success(1)
    }
  }

}
