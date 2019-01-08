package com.goals.domaine

import java.time.{OffsetDateTime, ZoneOffset}

class Goal private (private val description: String, private val startDate: OffsetDateTime, private val endDate: Option[OffsetDateTime]) {

  override def toString: String = s"$description, Starts : $startDate and ends $endDate"
}

object Goal {

  def apply(description: String): Goal = new Goal(description, OffsetDateTime.now(ZoneOffset.UTC), None)

}