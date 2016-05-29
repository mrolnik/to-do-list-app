
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/mica/Desktop/WIX/to-do-list-app/conf/routes
// @DATE:Sun May 29 05:26:14 ART 2016

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  HomeController_1: controllers.HomeController,
  // @LINE:8
  CountController_0: controllers.CountController,
  // @LINE:10
  AsyncController_2: controllers.AsyncController,
  // @LINE:13
  Assets_4: controllers.Assets,
  // @LINE:15
  TodoController_3: controllers.TodoController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_1: controllers.HomeController,
    // @LINE:8
    CountController_0: controllers.CountController,
    // @LINE:10
    AsyncController_2: controllers.AsyncController,
    // @LINE:13
    Assets_4: controllers.Assets,
    // @LINE:15
    TodoController_3: controllers.TodoController
  ) = this(errorHandler, HomeController_1, CountController_0, AsyncController_2, Assets_4, TodoController_3, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_1, CountController_0, AsyncController_2, Assets_4, TodoController_3, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """count""", """controllers.CountController.count"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """message""", """controllers.AsyncController.message"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add/""" + "$" + """desctiption<[^/]+>""", """controllers.TodoController.addItem(desctiption:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/""" + "$" + """id<[^/]+>""", """controllers.TodoController.getItem(id:Int)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """delete/""" + "$" + """id<[^/]+>""", """controllers.TodoController.deleteItem(id:Int)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """all""", """controllers.TodoController.getAllItems"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_1.index,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      """ An example controller showing a sample home page""",
      this.prefix + """"""
    )
  )

  // @LINE:8
  private[this] lazy val controllers_CountController_count1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("count")))
  )
  private[this] lazy val controllers_CountController_count1_invoker = createInvoker(
    CountController_0.count,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.CountController",
      "count",
      Nil,
      "GET",
      """ An example controller showing how to use dependency injection""",
      this.prefix + """count"""
    )
  )

  // @LINE:10
  private[this] lazy val controllers_AsyncController_message2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("message")))
  )
  private[this] lazy val controllers_AsyncController_message2_invoker = createInvoker(
    AsyncController_2.message,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AsyncController",
      "message",
      Nil,
      "GET",
      """ An example controller showing how to write asynchronous code""",
      this.prefix + """message"""
    )
  )

  // @LINE:13
  private[this] lazy val controllers_Assets_versioned3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned3_invoker = createInvoker(
    Assets_4.versioned(fakeValue[String], fakeValue[Asset]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      """ Map static resources from the /public folder to the /assets URL path""",
      this.prefix + """assets/""" + "$" + """file<.+>"""
    )
  )

  // @LINE:15
  private[this] lazy val controllers_TodoController_addItem4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add/"), DynamicPart("desctiption", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TodoController_addItem4_invoker = createInvoker(
    TodoController_3.addItem(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "addItem",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """add/""" + "$" + """desctiption<[^/]+>"""
    )
  )

  // @LINE:16
  private[this] lazy val controllers_TodoController_getItem5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TodoController_getItem5_invoker = createInvoker(
    TodoController_3.getItem(fakeValue[Int]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "getItem",
      Seq(classOf[Int]),
      "GET",
      """""",
      this.prefix + """get/""" + "$" + """id<[^/]+>"""
    )
  )

  // @LINE:17
  private[this] lazy val controllers_TodoController_deleteItem6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("delete/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TodoController_deleteItem6_invoker = createInvoker(
    TodoController_3.deleteItem(fakeValue[Int]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "deleteItem",
      Seq(classOf[Int]),
      "GET",
      """""",
      this.prefix + """delete/""" + "$" + """id<[^/]+>"""
    )
  )

  // @LINE:18
  private[this] lazy val controllers_TodoController_getAllItems7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("all")))
  )
  private[this] lazy val controllers_TodoController_getAllItems7_invoker = createInvoker(
    TodoController_3.getAllItems,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "getAllItems",
      Nil,
      "GET",
      """""",
      this.prefix + """all"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_HomeController_index0_route(params) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_1.index)
      }
  
    // @LINE:8
    case controllers_CountController_count1_route(params) =>
      call { 
        controllers_CountController_count1_invoker.call(CountController_0.count)
      }
  
    // @LINE:10
    case controllers_AsyncController_message2_route(params) =>
      call { 
        controllers_AsyncController_message2_invoker.call(AsyncController_2.message)
      }
  
    // @LINE:13
    case controllers_Assets_versioned3_route(params) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned3_invoker.call(Assets_4.versioned(path, file))
      }
  
    // @LINE:15
    case controllers_TodoController_addItem4_route(params) =>
      call(params.fromPath[String]("desctiption", None)) { (desctiption) =>
        controllers_TodoController_addItem4_invoker.call(TodoController_3.addItem(desctiption))
      }
  
    // @LINE:16
    case controllers_TodoController_getItem5_route(params) =>
      call(params.fromPath[Int]("id", None)) { (id) =>
        controllers_TodoController_getItem5_invoker.call(TodoController_3.getItem(id))
      }
  
    // @LINE:17
    case controllers_TodoController_deleteItem6_route(params) =>
      call(params.fromPath[Int]("id", None)) { (id) =>
        controllers_TodoController_deleteItem6_invoker.call(TodoController_3.deleteItem(id))
      }
  
    // @LINE:18
    case controllers_TodoController_getAllItems7_route(params) =>
      call { 
        controllers_TodoController_getAllItems7_invoker.call(TodoController_3.getAllItems)
      }
  }
}
