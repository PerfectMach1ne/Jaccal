package main.scala

import main.java.legacyui.WeekCalendarView
import main.java.legacyui.MenuBar
import newui.LeftBarView

import java.awt.event.KeyEvent

import scala.swing._
import scala.swing.event._
import scala.swing.event.Key.Modifier
import scala.swing.event.Key.Location


/**
  * SimpleSwingApplication object that creates the main Frame;
  * it then adds the legacy Java components, wrapping them with a compatibility method for Scala: Component.wrap().
  */
object Main extends SwingApplication:

  val WINDOW_HEIGHT: Int = 800 
  val WINDOW_WIDTH: Int = 1450 - 2 // evil pixel math

  override def startup(args: Array[String]): Unit = {
    println("a?")

    val t = top
    if (t.size == new Dimension(0,0)) t.pack()
    t.visible = true
  }

  // Methods from https://github.com/scala/scala-swing/blob/a41024194d5a034ecac304aeecd1354b50e05700/src/main/scala/scala/swing/SimpleSwingApplication.scala#L25
  // This way we get the full classic SimpleSwingApplication featureset, but a customizable startup method.
  def resourceFromClassloader(path: String): java.net.URL =
    this.getClass.getResource(path)

  def resourceFromUserDirectory(path: String): java.io.File =
    new java.io.File(util.Properties.userDir, path)

  lazy val top: Frame = new MainFrame {
    title = "Jaccal - Java & Scala desktop calendar app"
    preferredSize = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
    minimumSize = new Dimension(WINDOW_WIDTH - 800, WINDOW_HEIGHT - 300)
    resizable = true

    contents = new BorderPanel {
      add(newui.LeftBarView, BorderPanel.Position.West)
      add(Component.wrap(WeekCalendarView()), BorderPanel.Position.Center)

      /**
        * MainFrame is not a child of Component, which has the keys Object and listensTo() method,
        * as well as a variety of other methods for handling events in the Scala wrapper of Java Swing.
        * Thus in order to make it possible to close the window with a Ctrl+W shortcut,
        * we need to first wrap everything in anything that inherits a Component class.
        * In order for the API to actually respond to the events, a Component also needs to be focused.
        * An example of such object would be BorderPanel, which is what conveniently happens to be used there.
        */
      listenTo(this.keys)

      reactions += {
        case KeyPressed(_, Key.W, Modifier.Control, _) =>
          dispose()
      }

      focusable = true
      requestFocus()
    }

    visible = true
  }
end Main