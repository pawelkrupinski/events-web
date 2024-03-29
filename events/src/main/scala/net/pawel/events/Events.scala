package net.pawel.events

import net.pawel.events.ExtractUrls.extractUrls
import net.pawel.events.util.Utils.parallelize
import net.pawel.events.domain.{Organizer, OrganizerType}

import java.time.temporal.ChronoUnit
import scala.collection.parallel.CollectionConverters._
import scala.io.Source

object Events {
  lazy val ticketTailor = new TicketTailor()
  lazy val eventBrite = new EventBrite()
  lazy val uri = classOf[TicketTailor].getResource("/wa-group-chat.txt").toURI
  lazy val text = Source.fromFile(uri).getLines().mkString("\n")
  lazy val allUrls = parallelize(extractUrls(text).toList)
  lazy val ticketTailorEvents = ticketTailor.fetchCurrentEvents(allUrls)
  lazy val ticketTailorOrganizers = ticketTailor.fetchOrganizers(allUrls)
  lazy val eventBriteEvents = eventBrite.fetchCurrentEvents(allUrls)
  lazy val eventBriteOrganizers = eventBrite.fetchOrganizers(allUrls)
  lazy val allOrganizers = (ticketTailorOrganizers ++ eventBriteOrganizers).toSet

  def allEvents = List(
    () => ticketTailorEvents,
    () => eventBriteEvents
  ).par.flatMap(_()).toList
    .filterNot(event => event.end.minus(3, ChronoUnit.DAYS).isAfter(event.start))
    .sortBy(_.start)

  def main(args: Array[String]): Unit = {
    println(ticketTailorEvents.mkString("\n"))
//    println(eventBriteOrganizers.mkString("\n"))
//    facebookEvents
  }
}

object RankUrls {
  lazy val urlRegex = """https?://(.+?)(/.*)?$""".r

  def main(args: Array[String]): Unit = {
    println(Events.allUrls.map(url => {
      urlRegex.findFirstMatchIn(url).get.group(1)
    }).groupBy(identity).toList.map {
      case (url, list) => (url, list.size)
    }.sortBy(_._2).reverse.mkString("\n"))
  }
}

object FindMeetup {
  def main(args: Array[String]): Unit = {
    println(Events.allUrls.filter(_.contains("meetu")).mkString("\n"))
  }
}



