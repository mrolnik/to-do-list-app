package modules

import javax.inject.{Inject, Singleton}

import models.Todo

import scala.util.{Failure, Success}

/**
  * Created by mica on 28/05/16.
  */
@Singleton
class TodoModule @Inject()(db : DB){

  def addItem(description: String): Option[Int] = {
    db.addItem(description) match {
      case Success(todoId) => Some(todoId)
      case Failure(e) => None
    }
  }

  def getItem(id: Int): Option[Todo] = {
    db.getItem(id) match {
      case Success(someTodo) => someTodo
      case Failure(e) => None
    }
  }

}