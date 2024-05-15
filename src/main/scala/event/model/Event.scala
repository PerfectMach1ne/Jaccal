package main.scala.event.model

import java.time.LocalDateTime

abstract class Event:
  val eventDate: LocalDateTime
  val eventName: String = "New event"

trait SimpleEvent extends Event:
  ???

trait PeriodEvent extends Event:
  ???