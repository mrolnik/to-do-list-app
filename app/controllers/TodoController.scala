package controllers

import javax.inject.{Inject, Singleton}

import controllers.parsers.JsonResponses
import modules.TodoModule
import play.api.libs.json.{JsResultException, Json}
import play.api.mvc.{Action, Controller}

import scala.util.{Failure, Success}

/**
  * Created by mica on 26/05/16.
  */
@Singleton
class TodoController @Inject()(module: TodoModule) extends Controller with JsonResponses{

  def add = Action {
    request =>
      request.body.asJson map {
        bodyStr =>
          try {
            (bodyStr \ "description").as[String] match {
              case "" => missingParameters
              case description => module.addItem(description) map {
                id => created(Json.obj("id" -> id))
              } getOrElse internalServerError
            }
          } catch{
            case e: JsResultException => missingParameters
            case e: Throwable => internalServerError
          }
      } getOrElse missingParameters
  }

  def update(id: String) = Action {
    request =>
      request.body.asJson map {
        bodyStr =>
          try {
            (bodyStr \ "description").as[String] match {
              case "" => missingParameters
              case description => {
                module.updateItem(id, description) match {
                  case Success(true) => Ok
                  case Success(false) => wrongId
                  case Failure(_) => internalServerError
                }
              }
            }
          } catch{
            case e: JsResultException => missingParameters
            case e: Throwable => internalServerError
          }
      } getOrElse missingParameters
  }

  def delete(id: String) = Action {
    module.deleteItem(id) match {
      case Success(true) => Ok
      case Success(false) => wrongId
      case Failure(_) => internalServerError
    }
  }

  def getAll = Action {
    module.getAllItems map {
      itemsList =>
        val list = itemsList map (item => item.toJson)
        ok(Json.toJson(list))
    } getOrElse internalServerError
  }

  def getHistory() = Action {
    module.getHistory map {
      historyList =>
        val list = historyList map (item => item.toJson)
        ok(Json.toJson(list))
    } getOrElse internalServerError
  }


  def get(id: String) = Action {
    module.getItem(id) map {
      todo => ok(todo.toJson)
    } getOrElse wrongId
  }
}
