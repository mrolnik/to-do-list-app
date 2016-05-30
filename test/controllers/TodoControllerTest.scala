package controllers

import java.time.Instant

import modules.TodoModule
import org.scalatestplus.play._
import org.specs2.mock.Mockito
import play.api.libs.json.JsValue
import play.api.test.Helpers._
import play.api.test._
import java.util.Date

/**
  * Created by mica on 29/05/16.
  */
class TodoControllerTest extends PlaySpec with OneAppPerTest with Mockito{
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
      format.parse((a(0) \ "date").as[String]).before(Date.from(Instant.now)) mustBe true

    }

    "Get history" in {

    }

    "sarasa module" in {
      val bodyStr = """ {"description": "Buy some milk!"} """
      val mockModule = mock[TodoModule]
      val controller = new TodoController(mockModule)

      mockModule.addItem(anyString) returns Some(1)

      val frq = FakeRequest().withBody(bodyStr).withHeaders("Content-Type" -> "application/json")
      val response = controller.add()(frq).run
      status(response) mustBe OK
      contentAsJson(response).as[]
    }

  }
}
