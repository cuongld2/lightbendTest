package serializer

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import entities.{SimpleObject, UpdateValueObject}

trait ImageJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val PortoFolioFormats = jsonFormat2(SimpleObject)
  implicit val UpdateValueFormats = jsonFormat1(UpdateValueObject)
}
object ImageJsonSupport extends ImageJsonSupport