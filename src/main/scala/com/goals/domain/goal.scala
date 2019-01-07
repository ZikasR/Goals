package com.goals.domaine

import java.time.{OffsetDateTime, ZoneOffset}

class Goal {

  def description: String

  def startDate: OffsetDateTime

  def endDate: Option[OffsetDateTime]
}

object Goal {

  def apply(description: String): Goal = new Goal(description, OffsetDateTime.now(ZoneOffset.UTC), None)

}