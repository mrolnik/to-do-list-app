package models

import java.util.Date

import play.api.libs.json.{JsValue, Json}

/**
  * Created by mica on 28/05/16.
  */
trait JsonTodo {
  protected val format = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
  def toJson :JsValue
}

case class Todo(id: Int, description: String, date : Date) extends JsonTodo{

  override def toJson : JsValue = {
    Json.obj(
      "id" -> this.id,
      "description" -> this.description,
      "date" -> format.format(this.date)
    )
  }
}

case class TodoHistory(id: Int, description: String, itemId: Int, date : Date) extends JsonTodo{

  override def toJson : JsValue = {
    Json.obj(
      "id" -> this.id,
      "description" -> this.description,
      "itemId" -> this.itemId,
      "date" -> format.format(this.date)
    )
  }
}