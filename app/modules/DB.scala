package modules

import java.time.Instant
import java.util.Date
import javax.inject.{Inject, Singleton}

import models.Todo
import play.api.db.Database
import anorm.SQL
import scala.util.{Success, Try}

/**
  * Created by mica on 28/05/16.
  */
@Singleton
class DB @Inject()(db : Database){

  db.withConnection {implicit c =>
    SQL(
      """
        DROP TABLE TODO IF EXISTS
      """
    ).execute()

    SQL(
      """
        CREATE TABLE TODO (
        id MEDIUMINT NOT NULL AUTO_INCREMENT,
        description VARCHAR(255) NOT NULL,
        PRIMARY KEY (id));
      """
    ).execute()

  }

  def getItem(id: Int): Try[Option[Todo]] = {
    db.withConnection { implicit c =>
      val a = SQL( s"SELECT * FROM TODO WHERE ID=${id}").executeQuery()
      Success(Some(Todo(id, "descripcion", Date.from(Instant.now))))
    }
  }

  def addItem(description: String) : Try[Int] = {
    db.withConnection { implicit c =>
      val a = SQL("INSERT INTO TODO (description) VALUES ({description})")
              .on("description" -> description).executeInsert()

      Success(1)
    }
    /*db.withConnection { conn =>
      val stmt = conn.createStatement()
  stmt.pr
      val rs = stmt.executeQuery(s"INSERT INTO TODO (description) VALUES ('${description}')")

      while (rs.next()) {
        resp += rs.getString("DESCRIPTION")
      }
}
      */
  }

}
