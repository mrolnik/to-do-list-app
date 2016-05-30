package controllers

import javax.inject.Inject
import controllers.parsers.Errors
import models.Error
import modules.TodoModule
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

/**
  * Created by mica on 26/05/16.
  */
@Singleton
class TodoController @Inject()(module: TodoModule) extends Controller with Errors{
//crear error class
  def add = Action {
    request =>
      request.body.asJson map {
        bodyStr =>
          try {
            (bodyStr \ "description").as[String] match {
              case "" => BadRequest(createError("Forgot to add description?"))
              case description => module.addItem(description) map {
                id => Ok(Json.obj("id" -> id.toString))
              } getOrElse BadRequest("Something went wrong =( !")
            }
          } catch{
            case e => InternalServerError("Something went wrong! Try again later.")
          }
      } getOrElse BadRequest("Forgot to add description?")
  }

  def get(id: Int) = Action {
    module.getItem(id) map {
        todo => Ok(todo.toJson).as("application/json; charset=UTF-8")
    } getOrElse NotFound("Sorry! The item you are looking for doesn't exist!")
  }

  def delete(id: Int) = Action {
    if(module.deleteItem(id)) {
      Ok("The item has been deleted correctly!")
    }else {
      NotFound("Sorry! The item you are looking for doesn't exist!")
    }
  }

  def getAll = Action {
    module.getAllItems map {
      itemsList =>
        val list = itemsList map (item => item.toJson)
        Ok(Json.toJson(list)).as("application/json; charset=UTF-8")
    } getOrElse InternalServerError("Something went wrong")
  }

  def getHistory() = Action {
    module.getHistory map {
      historyList =>
        val list = historyList map (item => item.toJson)
        Ok(Json.toJson(list)).as("application/json; charset=UTF-8")
    } getOrElse InternalServerError("Something went wrong")
  }

}
