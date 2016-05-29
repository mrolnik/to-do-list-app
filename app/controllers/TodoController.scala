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
  val format = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
  implicit val toDoWriter = new Writes[Todo] {
    def writes (item: Todo): JsValue = {
      Json.obj(
          "id" -> item.id,
          "description" -> item.description,
          "date" -> format.format(item.date)
        )
    }
  }


  def addItem(description: String) = Action {
    module.addItem(description) match {
      case Some(id) => Ok(id.toString)
      case _ => BadRequest("Something went wrong =( !")
    }
  }

  def getItem(id: Int) = Action {
    module.getItem(id) match {
      case Some(todoId) => Ok(Json.toJson(todoId)).as("application/json; charset=UTF-8")
      case _ => NotFound("Sorry! The item you are looking for doesn't exist!")
    }
  }

  def deleteItem(id: Int) = Action {
    module.deleteItem(id) match {
      case true => Ok("The item has been deleted correctly!")
      case false => NotFound("Sorry! The item you are looking for doesn't exist!")
    }
  }

  def getAllItems = Action {
    module.getAllItems match {
      case Some(itemsList) => {
        val list = itemsList map {
          item =>
            Json.toJson(item)
        }
        Ok(Json.toJson(list)).as("application/json; charset=UTF-8")
      }
      case _ => Ok(Json.toJson(List.empty)).as("application/json; charset=UTF-8")
    }
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
