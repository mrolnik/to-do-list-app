package controllers

import javax.inject.Inject

import models.Todo
import modules.TodoModule
import play.api.libs.json.{JsValue, Json, Writes}
import play.api.mvc.{Action, Controller}

/**
  * Created by mica on 26/05/16.
  */

class TodoController @Inject()(module : TodoModule)extends Controller {

  implicit val toDoWriter = new Writes[Any] {
    def writes (item: Any): JsValue = {
      item match {
        case Todo(id, description, date) => Json.obj("id" -> id, "description" -> description, "date" -> date)
        case _ => Json.obj()

      }
    }
  }

  def addItem(description: String) = Action {
    module.addItem(description) match {
      case Some(id) => Ok(id.toString).as("application/json; charset=UTF-8")
      case _ => NotFound
    }
  }

  def getItem(id: Int) = Action {
    module.getItem(id) match {
      case Some(todoId) => Ok(Json.toJson(todoId)).as("application/json; charset=UTF-8")
      case _ => NotFound
    }
  }
  def getAllItems = Action {
    Ok("hola" )
  }
  def deleteItem(id: Int) = Action {
    Ok("deleted" + id)
  }

  /**
    * Show history controller
    *
    * [
    *   {id: 1, description:"do shopping"},
    *   {id: 2, description:"do shopping"},
    *   {id: 3, description:"do shopping"},
    *   {id: 4, description:"do shopping"},
    *   {id: 5, description:"do shopping"}
    * ]
    *
    */
  def showHistory() = Action {
    Ok("history")
  }

}
