
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/mica/Desktop/WIX/to-do-list-app/conf/routes
// @DATE:Sun May 29 16:46:50 ART 2016

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseTodoController TodoController = new controllers.ReverseTodoController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseTodoController TodoController = new controllers.javascript.ReverseTodoController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
  }

}
