package controllers.parsers

import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Controller

/**
  * Created by mica on 30/05/16.
  */
trait JsonResponses {
  self: Controller =>
  protected def createError(message: String): JsValue = Json.obj("error" -> message)

  protected def internalServerError = InternalServerError(createError("Oops! Something Went wrong. Try again later!")).as("application/json; charset=UTF-8")

  protected def missingParameters = BadRequest(createError("Missing parameters. Did you forget to send something?")).as("application/json; charset=UTF-8")

  protected def wrongId = NotFound(createError("Item not found. Wrong ID?")).as("application/json; charset=UTF-8")

  protected def created(json: JsValue) = Created(json).as("application/json; charset=UTF-8")


  protected def ok(json: JsValue) = Ok(json).as("application/json; charset=UTF-8")

}
