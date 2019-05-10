package resources

import akka.http.scaladsl.server.Route
import db.RedisService
import entities.{SimpleObject, UpdateValueObject}
import routing.MyResource


trait ResourceHandler extends MyResource {
  println("ResourceHandler being initialized")

  val redisModelService : RedisService

  def someRoutes: Route = pathPrefix("sampleroute") {
    pathEnd {
      post {
        entity(as[SimpleObject]) { image =>
          completeWithLocationHeader(
            resourceId = redisModelService.set(image),
            ifDefinedStatus = 201, ifEmptyStatus = 409
          )
        }
       }
      } ~
      path(Segment) { id =>
        println(id)
        get {
          complete(redisModelService.getEntity(id))
        } ~
        put{
          entity(as[UpdateValueObject]){
            update =>
              complete(redisModelService.update(id,update))
          }
        } ~
        delete{
          complete(redisModelService.deleteObject(id))
        }
    }
    }
  }
