package controllers.parsers

import play.api.libs.json.{Json, JsValue}

/**
  * Created by mica on 30/05/16.
  */
trait Errors {
  protected def createError(message: String): JsValue = {
    Json.obj(
      "message" -> message
    )
  }
}
