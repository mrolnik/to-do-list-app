package modules

import javax.inject.{Inject, Singleton}

import models.{TodoHistory, Todo}

/**
  * Created by mica on 28/05/16.
  */
@Singleton
class TodoModule @Inject()(db : DB){

  def addItem(description: String): Option[Long] = db.addItem(description)

  def getItem(id: Int): Option[Todo] = db.getItem(id)

  def deleteItem(id: Int): Boolean = db.deleteItem(id)

  def getAllItems: Option[List[Todo]] = db.getAllItems

  def getHistory: Option[List[TodoHistory]] = db.getHistory
}