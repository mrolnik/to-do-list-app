package modules

import java.sql.Connection
import java.util.Date
import javax.inject.{Inject, Singleton}

import anorm.{SQL, SqlParser}
import models.{TodoHistory, Todo}
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
    SQL(s"INSERT INTO HISTORY (description, itemId) VALUES ('${description}', ${itemId})").executeInsert()
  }

  def getItem(id: Int): Option[Todo] = {
    db.withConnection { implicit c =>
      try {
        val result =  SQL( s"SELECT * FROM TODO WHERE ID=${id}").executeQuery()
        val description = result.as(SqlParser.str(DESCRIPTION_COLUMN).single)
        val date = result.as(SqlParser.date(DATE_COLUMN).single)
        Some(Todo(id, description, date))
      }catch{
        case e:Exception  => {
          Logger.debug(e.getMessage())
          None
        }
      }
    }
  }

  def addItem(description: String) : Option[Long] = {
      db.withConnection { implicit c =>
        try {
          val a =  SQL(s"INSERT INTO TODO (description) VALUES ('${description}')").executeInsert()
          a match {
            case Some(id : Long) => trackTransaction("ITEM ADDED",id)
          }
          a

        }catch{
          case e:Exception  => {
            Logger.debug(e.getMessage())
            None
          }
        }
      }
  }

  def deleteItem(id: Int): Boolean = {
    db.withConnection { implicit c =>
      try {
        SQL(s"DELETE FROM TODO WHERE ID=${id}").executeUpdate() match {
          case i if i != 0 => {
            trackTransaction("ITEM DELETED", id)
            true
          }
          case _ => false
        }
      }catch{
        case e:Exception  => {
          Logger.debug(e.getMessage())
          false
        }
      }
    }
  }

  def getAllItems : Option[List[Todo]] = {
    db.withConnection { implicit c =>
      try {
        val result =  SQL( s"SELECT * FROM TODO").executeQuery()
        Some(result() map {
          row =>
            Todo(row[Int]("id"), row[String]("description"), row[Date]("date"))
        } toList)
      }catch{
        case e:Exception  => {
          Logger.debug(e.getMessage())
          None
        }
      }
    }
  }

  def getHistory :  Option[List[TodoHistory]] = {
    db.withConnection { implicit c =>
      try {
        val result =  SQL( s"SELECT * FROM HISTORY").executeQuery()
        Some(result() map {
          row =>
            TodoHistory(row[Int]("id"), row[String]("description"), row[Int]("itemId"), row[Date]("date"))
        } toList)
      }catch{
        case e:Exception  => {
          Logger.debug(e.getMessage())
          None
        }
      }
    }
  }

}
