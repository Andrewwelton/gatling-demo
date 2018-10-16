package demo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import com.typesafe.config._
import scala.util.Random
import collection.JavaConversions._

class BasicSimulation3 extends Simulation {
  val conf = ConfigFactory.load("basicConfig")

  val httpProtocol = http
    .baseUrl(conf.getString("config.baseUrl"))
    .inferHtmlResources()
    .contentTypeHeader("application/json;charset=UTF-8")
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9")

  val scn = scenario("BasicSimulation3")
    .exec(http("Initial_Route")
      .get("/"))
    .exec(http("Get_Books")
      .get("/books")
      .check(
        status.not(401),
        status.not(500),
        jsonPath("$.books[*]").ofType[Map[String, Any]].findAll.saveAs("books")))
    .exec(session => {
      val bookIds = conf.getIntList("config.bookIds").toList
      session.set("selectedBookId", Random.shuffle(bookIds).head)
    })
    .exec(http("Get_Book_${selectedBookId}_Detail")
      .get("/book/${selectedBookId}/detail"))
  
  setUp(scn.inject(rampUsers(10) during (10 seconds))).protocols(httpProtocol)
}