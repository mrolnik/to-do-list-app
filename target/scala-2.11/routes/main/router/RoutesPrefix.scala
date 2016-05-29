
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/mica/Desktop/WIX/to-do-list-app/conf/routes
// @DATE:Sun May 29 03:04:09 ART 2016


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
