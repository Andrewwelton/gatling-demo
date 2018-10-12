package gatlingTestSuite

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BasicGatling extends Simulation {
  val httpProtocol = http
    .baseUrl("http://localhost:3000")
    .inferHtmlResources()
    .contentTypeHeader("application/json;charset=UTF-8")
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9")
    .disableResponseChunksDiscarding

  val scn = scenario("BasicGatling")
    .exec(http("Initial_Route")
      .get("/"))
    .exec(http("Get_Books")
      .get("/books"))
    .exec(http("Get_Book_Detail")
      .get("/book/1/detail"))
  
  setUp(scn.inject(rampUsers(10) over (10 seconds))).protocols(httpProtocol)
}