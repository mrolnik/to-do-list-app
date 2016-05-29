
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/mica/Desktop/WIX/to-do-list-app/conf/routes
// @DATE:Sun May 29 16:46:50 ART 2016

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:5
  Assets_0: controllers.Assets,
  // @LINE:7
  TodoController_1: controllers.TodoController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:5
    Assets_0: controllers.Assets,
    // @LINE:7
    TodoController_1: controllers.TodoController
  ) = this(errorHandler, Assets_0, TodoController_1, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, Assets_0, TodoController_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """todo/""", """controllers.TodoController.add"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """todo/all""", """controllers.TodoController.getAll"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """todo/history""", """controllers.TodoController.getHistory"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """todo/""" + "$" + """id<[^/]+>""", """controllers.TodoController.get(id:Int)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """todo/""" + "$" + """id<[^/]+>""", """controllers.TodoController.delete(id:Int)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:5
  private[this] lazy val controllers_Assets_versioned0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned0_invoker = createInvoker(
    Assets_0.versioned(fakeValue[String], fakeValue[Asset]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      """ Routes
 This file defines all application routes (Higher priority routes first)
 ~~~~
 Map static resources from the /public folder to the /assets URL path""",
      this.prefix + """assets/""" + "$" + """file<.+>"""
    )
  )

  // @LINE:7
  private[this] lazy val controllers_TodoController_add1_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("todo/")))
  )
  private[this] lazy val controllers_TodoController_add1_invoker = createInvoker(
    TodoController_1.add,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "add",
      Nil,
      "POST",
      """""",
      this.prefix + """todo/"""
    )
  )

  // @LINE:8
  private[this] lazy val controllers_TodoController_getAll2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("todo/all")))
  )
  private[this] lazy val controllers_TodoController_getAll2_invoker = createInvoker(
    TodoController_1.getAll,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "getAll",
      Nil,
      "GET",
      """""",
      this.prefix + """todo/all"""
    )
  )

  // @LINE:9
  private[this] lazy val controllers_TodoController_getHistory3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("todo/history")))
  )
  private[this] lazy val controllers_TodoController_getHistory3_invoker = createInvoker(
    TodoController_1.getHistory,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "getHistory",
      Nil,
      "GET",
      """""",
      this.prefix + """todo/history"""
    )
  )

  // @LINE:10
  private[this] lazy val controllers_TodoController_get4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("todo/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TodoController_get4_invoker = createInvoker(
    TodoController_1.get(fakeValue[Int]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "get",
      Seq(classOf[Int]),
      "GET",
      """""",
      this.prefix + """todo/""" + "$" + """id<[^/]+>"""
    )
  )

  // @LINE:11
  private[this] lazy val controllers_TodoController_delete5_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("todo/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TodoController_delete5_invoker = createInvoker(
    TodoController_1.delete(fakeValue[Int]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TodoController",
      "delete",
      Seq(classOf[Int]),
      "DELETE",
      """""",
      this.prefix + """todo/""" + "$" + """id<[^/]+>"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:5
    case controllers_Assets_versioned0_route(params) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned0_invoker.call(Assets_0.versioned(path, file))
      }
  
    // @LINE:7
    case controllers_TodoController_add1_route(params) =>
      call { 
        controllers_TodoController_add1_invoker.call(TodoController_1.add)
      }
  
    // @LINE:8
    case controllers_TodoController_getAll2_route(params) =>
      call { 
        controllers_TodoController_getAll2_invoker.call(TodoController_1.getAll)
      }
  
    // @LINE:9
    case controllers_TodoController_getHistory3_route(params) =>
      call { 
        controllers_TodoController_getHistory3_invoker.call(TodoController_1.getHistory)
      }
  
    // @LINE:10
    case controllers_TodoController_get4_route(params) =>
      call(params.fromPath[Int]("id", None)) { (id) =>
        controllers_TodoController_get4_invoker.call(TodoController_1.get(id))
      }
  
    // @LINE:11
    case controllers_TodoController_delete5_route(params) =>
      call(params.fromPath[Int]("id", None)) { (id) =>
        controllers_TodoController_delete5_invoker.call(TodoController_1.delete(id))
      }
  }
}
