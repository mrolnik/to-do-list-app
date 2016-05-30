package controllers

import org.scalatestplus.play._
import play.api.libs.json.JsValue
import play.api.test.Helpers._
import play.api.test._

/**
  * Created by mica on 29/05/16.
  */
class TodoControllerTest extends PlaySpec with OneAppPerTest{
  val PREFIX = "/todo"
  val URL_ADD = PREFIX
  val URL_DELETE = PREFIX + "/"
  val URL_GET_ALL = PREFIX + "/all"
  val HEADERS = Seq("Content-Type"->"application/json")
  val format = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

  "TodoController" should {

    "Add item" in {
      /**
        *   No json
        * */
      status(route(app, FakeRequest(POST, URL_ADD)).get) mustBe BAD_REQUEST
      /**
        *   Ok
        * */
      val fakeRequestWithDescription = route(app, FakeRequest(
          POST,
          URL_ADD,
          FakeHeaders(HEADERS),
          """ {"description": "Buy some milk!"} """)
      ).get
      status(fakeRequestWithDescription) mustBe OK
      contentAsString(fakeRequestWithDescription) mustBe "1"

      /**
        *   Wrong json
        * */
      status(route(app, FakeRequest(
          POST,
          URL_ADD,
          FakeHeaders(HEADERS),
          """ {"descriptsion": "Buy some milk!"} """)
      ).get) mustBe BAD_REQUEST

      /**
        *   Wrong headers
        * */
      status(route(app, FakeRequest(
        POST,
        URL_ADD,
        FakeHeaders(),
        """ {"description": "Buy some milk!"} """)
      ).get) mustBe BAD_REQUEST
    }

    "Get item" in {
    }

    "Delete item" in {
      /**
        *   No item founded
        * */
      status(route(app, FakeRequest(DELETE, URL_DELETE + "8")).get) mustBe NOT_FOUND
      /**
        *   Ok
        * */
      route(app, FakeRequest(
        POST,
        URL_ADD,
        FakeHeaders(HEADERS),
        """ {"description": "Buy some milk!"} """)
      ).get
      status(route(app, FakeRequest(
        DELETE,
        URL_DELETE + "1"
      )).get) mustBe OK

      /**
        *   Second delete
        * */
      status(route(app, FakeRequest(
        DELETE,
        URL_DELETE + "1"
      )).get) mustBe NOT_FOUND
    }

    "Get all items" in {
      route(app, FakeRequest(POST, URL_ADD,FakeHeaders(HEADERS),""" {"description": "Buy some milk!"} """)).get
      route(app, FakeRequest(POST, URL_ADD,FakeHeaders(HEADERS),""" {"description": "Buy some more milk!"} """)).get
      val fr = route(app, FakeRequest(GET, URL_GET_ALL)).get
      status(fr) mustBe OK
      val a = contentAsJson(fr).as[List[JsValue]]

      (a(0) \ "id").as[Int] mustBe 2
      (a(0) \ "description").as[String] mustBe "Buy some milk!"
val b = (a(0) \ "date").as[String]
      val date = format.parse(b)

    }

    "Get history" in {

    }

  }
}
