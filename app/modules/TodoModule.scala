package modules

import models.TodoId
import javax.inject.{Inject, Singleton}
import scala.util.{Success, Try}

/**
  * Created by mica on 28/05/16.
  */
@Singleton
class TodoModule @Inject()(db : DB){

  def addItem(description: String): Try[TodoId] = {
    db.addItem(description) match {
      case Success()
    }

  }

}