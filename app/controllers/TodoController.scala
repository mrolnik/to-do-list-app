package controllers

import javax.inject.Inject
import modules.TodoModule
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

/**
  * Created by mica on 26/05/16.
  */

class TodoController @Inject()(module: TodoModule) extends Controller {

  def add = Action {
    request =>
      request.body.asJson map {
        bodyStr =>
          (bodyStr \ "description").as[String] match {
            case "" => BadRequest("Forgot to add description?")
            case description => module.addItem(description) match {
              case Some(id) => Ok(id.toString)
              case _ => BadRequest("Something went wrong =( !")
            }
          }
      } getOrElse BadRequest("Forgot to add description?")
  }

  def get(id: Int) = Action {
    module.getItem(id) match {
      case Some(todoId) => Ok(todoId.toJson).as("application/json; charset=UTF-8")
      case _ => NotFound("Sorry! The item you are looking for doesn't exist!")
    }
  }

  def delete(id: Int) = Action {
    module.deleteItem(id) match {
      case true => Ok("The item has been deleted correctly!")
      case false => NotFound("Sorry! The item you are looking for doesn't exist!")
    }
  }

  def getAll = Action {
    module.getAllItems match {
      case Some(itemsList) => {
        val list = itemsList map (item => item.toJson)
        Ok(Json.toJson(list)).as("application/json; charset=UTF-8")
      }
      case _ => Ok("No items!")
    }
  }

  def getHistory() = Action {
    module.getHistory match {
      case Some(historyList) => {
        val list = historyList map (item => item.toJson)
        Ok(Json.toJson(list)).as("application/json; charset=UTF-8")
      }
      case _ => Ok("No history founded")
    }
  }

}
