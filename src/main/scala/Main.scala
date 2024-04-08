package main.scala

import main.java.mainui.WeekViewCalendar
import main.java.mainui.LeftBarPanel
import main.java.mainui.MenuBar

import java.awt.event.KeyListener
import java.awt.event.KeyEvent

import scala.swing._
import scala.swing.event._

object Main extends SimpleSwingApplication, KeyListener:
  // override def main(args: Array[String]): Unit = super.main(args)

  val WINDOW_HEIGHT: Int = 800 
  val WINDOW_WIDTH: Int = 1450

  def top: Frame = new MainFrame() {
      title = "Jaccal - Java & Scala desktop calendar app"
      preferredSize = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
      resizable = true
      // setLocationRelativeTo(null)

      contents = new BorderPanel {
        // val weekView = WeekViewCalendar()
        // val leftBar = LeftBarPanel()
        add(Component.wrap(WeekViewCalendar()), BorderPanel.Position.Center)
        add(Component.wrap(LeftBarPanel()), BorderPanel.Position.West)
      }

      visible = true
  }

  override def keyTyped(e: KeyEvent): Unit = ()

  /*
   * Ctrl + W closes the main window.
   */
  override def keyPressed(e: KeyEvent): Unit = 
    if e.getKeyCode() == KeyEvent.VK_W && ((e.getModifiersEx () & 128) != 0) then top.dispose()

  override def keyReleased(e: KeyEvent): Unit = ()
