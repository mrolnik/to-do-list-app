package controllers

import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

/**
  * Created by mica on 29/05/16.
  */
class TodoControllerTest extends PlaySpec with OneAppPerTest{

  "TodoController" should {

    "Add item" in {
      val URL =  "/todo/"
      val HEADERS = Seq("Content-Type"->"application/json")

      /**
        *   No json
        * */
      status(route(app, FakeRequest(POST, URL)).get) mustBe BAD_REQUEST
      /**
        *   Ok
        * */
      val fakeRequestWithDescription = route(app, FakeRequest(
          POST,
          URL,
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
          URL,
          FakeHeaders(HEADERS),
          """ {"descriptsion": "Buy some milk!"} """)
      ).get) mustBe BAD_REQUEST

      /**
        *   Wrong headers
        * */
      status(route(app, FakeRequest(
        POST,
        URL,
        FakeHeaders(),
        """ {"description": "Buy some milk!"} """)
      ).get) mustBe BAD_REQUEST
    }

    "Get item" in {
    }

    "Delete item" in {
    }

    "Get all items" in {

    }

    "Get history" in {

    }

  }
}
