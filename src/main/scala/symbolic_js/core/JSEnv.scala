package symbolic_js.core

import scala.collection.mutable.HashMap

import symbolic_js.core.values.JSValue
import symbolic_js.core.values.JSNullValue

class JSEnv(val parent: Option[JSEnv]) {

  private val variables = HashMap[String, JSValue]()

  def update(identifier: String, value: JSValue) {
    variables.put(identifier, value)
  }

  def lookup(identifier: String): JSValue = {
    val value = variables.get(identifier)
    if (!value.isEmpty) {
      value.get
    } else {
      if (parent.isEmpty) {
        JSNullValue
      } else {
        parent.get.lookup(identifier)
      }
    }
  }

  def fork(): (JSEnv, JSEnv) = {
    (new JSEnv(Some(this)), new JSEnv(Some(this)))
  }

}

object JSEnv {
  def merge(env1: JSEnv, env2: JSEnv): JSEnv = {
    null
  }
}