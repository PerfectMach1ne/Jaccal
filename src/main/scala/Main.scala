package main.scala

import main.java.mainui.WeekCalendarView
import main.java.mainui.LeftBarView
import main.java.mainui.MenuBar

import java.awt.event.KeyEvent

import scala.swing._
import scala.swing.event._
import scala.swing.event.Key.Modifier
import scala.swing.event.Key.Location

/**
  * SimpleSwingApplication object that creates the main Frame;
  * it then adds the legacy Java components, wrapping them with a compatibility method for Scala: Component.wrap().
  */
object Main extends SimpleSwingApplication:
  val WINDOW_HEIGHT: Int = 800 
  val WINDOW_WIDTH: Int = 1450

  lazy val top: Frame = new MainFrame {
    title = "Jaccal - Java & Scala desktop calendar app"
    preferredSize = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
    resizable = true

    contents = new BorderPanel {
      add(Component.wrap(WeekCalendarView()), BorderPanel.Position.Center)
      add(Component.wrap(LeftBarView()), BorderPanel.Position.West)

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