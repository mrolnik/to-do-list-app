package modules

import javax.inject.{Inject, Singleton}

import models.{TodoHistory, Todo}

import scala.util.Try

/**
  * Created by mica on 28/05/16.
  */
@Singleton
class TodoModule @Inject()(db : DB){

  def addItem(description: String): Option[String] = db.addItem(description).map(_.toString)

  def updateItem(id: String, description: String): Try[Boolean] = db.updateItem(id, description)

  def getItem(id: String): Option[Todo] = db.getItem(id)

  def deleteItem(id: String): Try[Boolean] = db.deleteItem(id)

  def getAllItems: Option[List[Todo]] = db.getAllItems

  def getHistory: Option[List[TodoHistory]] = db.getHistory
}