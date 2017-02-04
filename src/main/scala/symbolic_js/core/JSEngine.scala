package symbolic_js.core

import java.io.File

import scala.collection.JavaConversions.asScalaBuffer

import jdk.nashorn.internal.ir.Assignment
import jdk.nashorn.internal.ir.ExpressionStatement
import jdk.nashorn.internal.ir.IfNode
import jdk.nashorn.internal.ir.Statement
import jdk.nashorn.internal.parser.Parser
import jdk.nashorn.internal.runtime.Context
import jdk.nashorn.internal.runtime.ErrorManager
import jdk.nashorn.internal.runtime.Source
import jdk.nashorn.internal.runtime.options.Options

object JSEngine {

  // options for the Nashorn parser
  val options = new Options("nashorn");
  options.set("anon.functions", true);
  options.set("parse.only", true);
  options.set("scripting", true);

  /**
   *
   */
  def loadFromFile(file: File): Stream[Statement] = {
    val errors = new ErrorManager()
    val context = new Context(options, errors, Thread.currentThread().getContextClassLoader())
    val source = Source.sourceFor("file", file)
    val parser = new Parser(context.getEnv(), source, errors)
    val functionNode = parser.parse()
    val block = functionNode.getBody()
    block.getStatements().toStream
  }

  /**
   *
   */
  def loadFromResources(name: String): Stream[Statement] = {
    val file = new File(getClass.getResource(name).getPath)
    loadFromFile(file)
  }

  def execute(statement: Statement) {
    statement match {
      case statement:IfNode => {
        println(statement.getTest)
      }
      case statement:ExpressionStatement => {
        val expression = statement.getExpression()
        expression match {
          case expression:Assignment[_] => {
            val identifier_raw = expression.getAssignmentDest.toString
            val identifier = identifier_raw.slice(identifier_raw.indexOf('$'), identifier_raw.size)
            println(identifier)
            println(expression.getAssignmentSource.getType)
          }
        }
      }
    }
  }

}