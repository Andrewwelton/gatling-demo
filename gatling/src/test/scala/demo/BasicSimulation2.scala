package demo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.util.Random

class BasicSimulation2 extends Simulation {
  val httpProtocol = http
    .baseUrl("http://localhost:3000")
    .inferHtmlResources()
    .contentTypeHeader("application/json;charset=UTF-8")
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9")

  val scn = scenario("BasicSimulation2")
    .exec(http("Initial_Route")
      .get("/"))
    .exec(http("Get_Books")
      .get("/books")
      .check(
        status.not(401),
        status.not(500),
        jsonPath("$.books[*]").ofType[Map[String, Any]].findAll.saveAs("books")))
    .exec { session =>
      for {
        array <- session("books").validate[Seq[Map[String,Any]]]
        entry = array(Random.nextInt(array.size))
      } yield session.set("selectedBookId", entry("id"))
    }
    .exec(http("Get_Book_${selectedBookId}_Detail")
      .get("/book/${selectedBookId}/detail"))
  
  setUp(scn.inject(rampUsers(10) during (10 seconds))).protocols(httpProtocol)
}