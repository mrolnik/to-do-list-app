package modules

import java.sql.Connection
import java.util.Date
import javax.inject.{Inject, Singleton}

import anorm.{SQL, SqlParser, SqlStringInterpolation}
import models.{Todo, TodoHistory}
import play.Logger
import play.api.db.Database

/**
  * Created by mica on 28/05/16.
  */
@Singleton
class DB @Inject()(db : Database){

  val ID_COLUMN = "id"
  val DESCRIPTION_COLUMN = "description"
  val DATE_COLUMN = "date"

  db.withConnection {implicit c =>
    SQL(
      """
        CREATE TABLE IF NOT EXISTS TODO (
        id MEDIUMINT NOT NULL AUTO_INCREMENT,
        description VARCHAR(255) NOT NULL,
        date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (id));
      """
    ).execute()

    SQL(
      """
        CREATE TABLE IF NOT EXISTS HISTORY (
        id MEDIUMINT NOT NULL AUTO_INCREMENT,
        itemId MEDIUMINT NOT NULL,
        description VARCHAR(255) NOT NULL,
        date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (id));
      """
    ).execute()

  }

  def trackTransaction(description: String, itemId: Long)(implicit c :Connection) = {
    SQL"INSERT INTO HISTORY (description, itemId) VALUES ('$description', $itemId)".executeInsert()
  }

  def getItem(id: Int): Option[Todo] = {
    try {
      db.withConnection { implicit c =>
        val result =  SQL"SELECT * FROM TODO WHERE ID=$id".executeQuery()
        val description = result.as(SqlParser.str(DESCRIPTION_COLUMN).single)
        val date = result.as(SqlParser.date(DATE_COLUMN).single)
        Some(Todo(id, description, date))
      }
    }catch{
      case e:Exception  => {
        Logger.error("Error retrieving item", e)
        None
      }
    }
  }

  def addItem(description: String) : Option[Long] = {
    try {
      db.withTransaction { implicit c =>
          val idOption = SQL"INSERT INTO TODO (description) VALUES ($description)".executeInsert()

          idOption match{
            case Some(id: Long) => trackTransaction("ITEM ADDED", id)
            case _ => None
          }
      }
    }catch{
      case e:Exception  => {
        Logger.error("Error adding item", e)
        None
      }
    }
  }

  def deleteItem(id: Int): Boolean = {
    try {
      db.withTransaction { implicit c =>

        SQL"DELETE FROM TODO WHERE ID=$id".executeUpdate() match {
          case i if i != 0 => {
            trackTransaction("ITEM DELETED", id)
            true
          }
          case _ => false
        }
      }
    }catch{
      case e:Exception  => {
        Logger.error("Error deleting item", e)
        false
      }
    }
  }

  def getAllItems : Option[List[Todo]] = {
    try {
      db.withConnection { implicit c =>
          val result =  SQL"SELECT * FROM TODO".executeQuery()
          val todos = result() map {
            row => Todo(row[Int]("id"), row[String]("description"), row[Date]("date"))
          }

          Some(todos.toList)
      }
    }catch{
      case e:Exception  => {
        Logger.error("Error retrieving all items", e)
        None
      }
    }
  }

  def getHistory :  Option[List[TodoHistory]] = {
    try {
      db.withConnection { implicit c =>
          val result =  SQL"SELECT * FROM HISTORY".executeQuery()
          val historyItems = result() map {
            row => TodoHistory(row[Int]("id"), row[String]("description"), row[Int]("itemId"), row[Date]("date"))
          }
          Some(historyItems.toList)
      }
    }catch{
      case e:Exception  => {
        Logger.error("Error retrieving history", e)
        None
      }
    }
  }

}
