package controllers

import javax.inject.Inject

import models.{Todo, TodoDescription, TodoId}
import modules.TodoModule
import play.api.mvc.{Controller, Action}
import play.api.libs.json.{JsValue, Writes, Json}

import scala.util.{Failure, Success}

/**
  * Created by mica on 26/05/16.
  */

class TodoController @Inject()(module : TodoModule)extends Controller {

  implicit val toDoWriter = new Writes[Any] {
    def writes (item: Any): JsValue = {
      item match {
        case TodoId(id) => Json.obj("id" -> id)
        case TodoDescription(description) => Json.obj("description" -> description)
       // case Todo(id, description) => Json.obj(Json.toJson(id), Json.toJson(description))
        case _ => Json.obj()

      }
    }
  }

  def addItem(description: String) = Action {
    module.addItem(description) match {
      case Success(response) => Ok(Json.toJson(response)).as("application/json; charset=UTF-8")
      case Failure(e) => NotFound
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
