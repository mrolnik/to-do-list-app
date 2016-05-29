package models

/**
  * Created by mica on 28/05/16.
  */
case class TodoId(id: Int)
case class TodoDescription(id: String)
case class Todo(id: TodoId, description: TodoDescription)
