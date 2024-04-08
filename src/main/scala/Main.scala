import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import scalafx.application.JFXApp3.PrimaryStage

object HelloStageDemo extends JFXApp3 {

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title.value = "Jaccal - Java & Scala desktop calendar app"
      width = 1450 
      height = 800
      scene = new Scene {
        fill = LightGreen
        content = new Rectangle {
          x = 25
          y = 40
          width = 100
          height = 100
          // fill <== when(hover) choose Green otherwise Red
        }
      }
    }
  }
}
