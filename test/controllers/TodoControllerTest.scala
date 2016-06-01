package controllers

import java.time.Instant
import java.util.Date

import org.scalatestplus.play._
import org.specs2.mock.Mockito
import play.api.libs.json.JsValue
import play.api.test.Helpers._
import play.api.test._

/**
  * Created by mica on 29/05/16.
  */
class TodoControllerTest extends PlaySpec with OneAppPerTest with Mockito{
  val PREFIX = "/todos"
  val URL_ADD = PREFIX
  val URL_GET = PREFIX + "/"
  val URL_DELETE = PREFIX + "/"
  val URL_UPDATE = PREFIX + "/"
  val URL_GET_ALL = PREFIX
  val URL_GET_HISTORY = "/history/todos"
  val HEADERS = Seq("Content-Type"->"application/json")
  val format = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

  "TodoController" should {

    "add item" in {

      /** No json **/
      status(route(app, FakeRequest(POST, URL_ADD)).get) mustBe BAD_REQUEST

      /** Ok **/
      val frWithDescription = route(app, FakeRequest(POST, URL_ADD, FakeHeaders(HEADERS), """ {"description": "Buy some milk!"} """)).get
      status(frWithDescription) mustBe CREATED

      val json = contentAsJson(frWithDescription)
      (json \ "id").as[String] mustBe "1"

      /** Wrong json **/
      val frWithWrongDescription = route(app, FakeRequest(POST, URL_ADD, FakeHeaders(HEADERS), """ {"descriptsion": "Buy some milk!"} """) ).get
      status(frWithWrongDescription) mustBe BAD_REQUEST

      /** Wrong headers **/
      val frWithWrongHeaders = route(app, FakeRequest(POST, URL_ADD, FakeHeaders(), """ {"description": "Buy some milk!"} """)).get
      status(frWithWrongHeaders) mustBe BAD_REQUEST
    }

    "update item" in {

      /** No json **/
      status(route(app, FakeRequest(PUT, URL_UPDATE + 1)).get) mustBe BAD_REQUEST

      /** No id **/
      status(route(app, FakeRequest(PUT, URL_UPDATE)).get) mustBe NOT_FOUND

      /** Wrong id **/
      val frWithWrongId = route(app, FakeRequest(PUT, URL_UPDATE + 2, FakeHeaders(HEADERS), """ {"description": "Buy some more milk!"} """)).get
      status(frWithWrongId) mustBe NOT_FOUND

      /** Ok **/
      val frWithDescription = route(app, FakeRequest(PUT, URL_UPDATE + 1, FakeHeaders(HEADERS), """ {"description": "Buy some more milk!"} """)).get
      status(frWithDescription) mustBe OK

      /** Check recent update **/
      val frRecentUpdate = route(app, FakeRequest(GET, URL_GET + 1)).get
      status(frRecentUpdate) mustBe OK
      val responseRecentUpdate = contentAsJson(frRecentUpdate)
      (responseRecentUpdate \ "id").as[String] mustBe "1"
      (responseRecentUpdate \ "description").as[String] mustBe "Buy some more milk!"

      /** Wrong json **/
      val frWithWrongDescription = route(app, FakeRequest(PUT, URL_UPDATE + 1, FakeHeaders(HEADERS), """ {"descriptsion": "Buy some milk!"} """) ).get
      status(frWithWrongDescription) mustBe BAD_REQUEST

      /** Wrong headers **/
      val frWithWrongHeaders = route(app, FakeRequest(PUT, URL_UPDATE + 1, FakeHeaders(), """ {"description": "Buy some milk!"} """)).get
      status(frWithWrongHeaders) mustBe BAD_REQUEST
    }

    "delete item" in {
      /** No item founded **/
      status(route(app, FakeRequest(DELETE, URL_DELETE + "8")).get) mustBe NOT_FOUND

      /** Ok **/
      status(route(app, FakeRequest(DELETE, URL_DELETE + "1")).get) mustBe OK

      /** Second delete **/
      status(route(app, FakeRequest(DELETE, URL_DELETE + "1")).get) mustBe NOT_FOUND
    }
    "get all items" in {

      /** Empty list **/
      val frEmptyList = route(app, FakeRequest(GET, URL_GET_ALL)).get
      status(frEmptyList) mustBe OK
      contentAsJson(frEmptyList).as[List[JsValue]] mustBe empty

      /** Two elements in list **/
      status(route(app, FakeRequest(POST, URL_ADD, FakeHeaders(HEADERS),""" {"description": "Buy some milk!"} """)).get) mustBe CREATED
      Thread.sleep(1000)
      status(route(app, FakeRequest(POST, URL_ADD, FakeHeaders(HEADERS),""" {"description": "Buy some more milk!"} """)).get) mustBe CREATED
      val frTwoElements = route(app, FakeRequest(GET, URL_GET_ALL)).get
      status(frTwoElements) mustBe OK
      val list = contentAsJson(frTwoElements).as[List[JsValue]]

      list.size mustBe 2

      //ordered first last added
      val firstItem = list(0)
      (firstItem \ "id").as[String] mustBe "3"
      (firstItem \ "description").as[String] mustBe "Buy some more milk!"
      val firstItemDate = format.parse((firstItem \ "date").as[String])
      firstItemDate.before(Date.from(Instant.now)) mustBe true


      val secondItem = list(1)
      (secondItem \ "id").as[String] mustBe "2"
      (secondItem \ "description").as[String] mustBe "Buy some milk!"
      format.parse((secondItem \ "date").as[String]).before(firstItemDate) mustBe true

    }

    "get history" in {

      val frHistory = route(app, FakeRequest(GET, URL_GET_HISTORY)).get
      status(frHistory) mustBe OK
      val list = contentAsJson(frHistory).as[List[JsValue]]

      list.size mustBe 5

      /** Check ADD **/
      val add = list(4)
      (add \ "itemId").as[String] mustBe "1"
      (add \ "description").as[String] mustBe "ADD"
      (add \ "itemDescription").as[String] mustBe "Buy some milk!"
      format.parse((add \ "date").as[String]).before(Date.from(Instant.now)) mustBe true


      /** Check UPDATE **/
      val update = list(3)
      (update \ "itemId").as[String] mustBe "1"
      (update \ "description").as[String] mustBe "UPDATE"
      (update \ "itemDescription").as[String] mustBe "Buy some more milk!"
      format.parse((update \ "date").as[String]).before(Date.from(Instant.now)) mustBe true


      /** Check DELETE**/
      val delete = list(2)
      (delete \ "itemId").as[String] mustBe "1"
      (delete \ "description").as[String] mustBe "DELETE"
      format.parse((delete \ "date").as[String]).before(Date.from(Instant.now)) mustBe true

    }
  }
}
