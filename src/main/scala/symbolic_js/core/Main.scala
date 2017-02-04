package symbolic_js.core

object Main extends App {
  val script = JSEngine.loadFromResources("/bener.js")
  script.foreach(s => JSEngine.execute(s))
}