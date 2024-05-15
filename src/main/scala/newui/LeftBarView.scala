package main.scala.newui

import main.java.minicalendar.MiniCalendarPanel

import java.awt.Color

import scala.swing._
import scala.swing.event._

object LeftBarView extends FlowPanel(FlowPanel.Alignment.Center)(WrapperComponent.asInstanceOf[Component]):
  val _PARENT_PANEL_WIDTH = 250
  val _PARENT_PANEL_HEIGHT = 850

  preferredSize = new Dimension(_PARENT_PANEL_WIDTH, _PARENT_PANEL_HEIGHT)
  background = Color.darkGray // Imperfect solution. Change to Color.blue to see what I meant.
end LeftBarView

object WrapperComponent extends FlowPanel:
  val __PARENT_PANEL_WIDTH = 250
  val __PARENT_PANEL_HEIGHT = 850
  val _CENTER_ALIGNMENT: Float = 0.5
  
  preferredSize = new Dimension(__PARENT_PANEL_WIDTH, __PARENT_PANEL_HEIGHT)
  background = Color.darkGray

  val miniCalendar = MiniCalendarPanel() 
  miniCalendar.setAlignmentX(_CENTER_ALIGNMENT)

  contents += {
    Component.wrap(miniCalendar)    
  }
end WrapperComponent 