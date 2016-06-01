package modules

import java.sql.Connection
import java.util.Date
import javax.inject.{Inject, Singleton}

import anorm.{SQL, SqlParser, SqlStringInterpolation}
import models.{Todo, TodoHistory}
import play.Logger
import play.api.db.Database

import scala.util.{Failure, Success, Try}

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
        CREATE TABLE IF NOT EXISTS TODOS(
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
        description VARCHAR(255) NOT NULL,
        itemId MEDIUMINT NOT NULL,
        itemDescription VARCHAR(255),
        date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (id));
      """
    ).execute()

  }

  def trackAdd(itemId: Long, itemDescription: String)(implicit c :Connection) = {
    SQL"INSERT INTO HISTORY (description, itemId, itemDescription) VALUES ('ADD', $itemId, $itemDescription)".executeInsert()
  }

  def trackUpdate(itemId: String, itemDescription: String)(implicit c :Connection) = {
    SQL"INSERT INTO HISTORY (description, itemId, itemDescription) VALUES ('UPDATE', $itemId, $itemDescription)".executeInsert()
  }

  def trackDelete(itemId: String)(implicit c :Connection) = {
    SQL"INSERT INTO HISTORY (description, itemId) VALUES ('DELETE', $itemId)".executeInsert()
  }

  def addItem(description: String) : Option[Long] = {
    try {
      db.withTransaction { implicit c =>
        val idOption: Option[Long] = SQL"INSERT INTO TODOS (description) VALUES ($description)".executeInsert()
        idOption.map(trackAdd(_, description))
        idOption
      }
    }catch{
      case e: Exception  => {
        Logger.error("Error adding item", e)
        None
      }
    }
  }

  def updateItem(id: String, description: String) : Try[Boolean] = {
    try {
      db.withTransaction { implicit c =>
        val updates = SQL"UPDATE TODOS SET DESCRIPTION=$description WHERE ID=$id".executeUpdate()

        updates match {
          case i if i > 0 => {
            trackUpdate(id, description)
            Success(true)
          }
          case _ => Success(false)
        }
      }
    }catch{
      case e: Exception  => {
        Logger.error("Error updating item", e)
        Failure(e)
      }
    }
  }

  def getItem(id: String): Option[Todo] = {
    try {
      db.withConnection { implicit c =>
        val result =  SQL"SELECT * FROM TODOS WHERE ID=$id".executeQuery()
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

  def deleteItem(id: String): Try[Boolean] = {
    try {
      db.withTransaction { implicit c =>

        SQL"DELETE FROM TODOS WHERE ID=$id".executeUpdate() match {
          case i if i > 0 => {
            trackDelete(id)
            Success(true)
          }
          case _ => Success(false)
        }
      }
    }catch{
      case e: Exception  => {
        Logger.error("Error deleting item", e)
        Failure(e)
      }
    }
  }

  def getAllItems : Option[List[Todo]] = {
    try {
      db.withConnection { implicit c =>
          val result =  SQL"SELECT * FROM TODOS".foldWhile(List[Todo]()) {
            (list, row) => list.::(Todo(row[Int]("id").toString, row[String]("description"), row[Date]("date"))) -> true
          }

          result match {
            case Right(list) => Some(list)
            case Left(_)  => None
          }
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
        val result =  SQL"SELECT * FROM HISTORY".foldWhile(List[TodoHistory]()) {
          (list, row) => {
            list.::(TodoHistory(row[Int]("id").toString, row[String]("description"), row[Int]("itemId").toString, row[Option[String]]("itemDescription"), row[Date]("date"))) -> true
          }
        }

        result match {
          case Right(list) => Some(list)
          case Left(_)  => None
        }
      }
    }catch{
      case e:Exception  => {
        Logger.error("Error retrieving history", e)
        None
      }
    }
  }
}
