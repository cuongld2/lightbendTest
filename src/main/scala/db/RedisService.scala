package db

import com.redis._
import entities.{SimpleObject, UpdateValueObject}

import scala.concurrent.{ExecutionContext, Future}


class RedisService(implicit val executionContext: ExecutionContext) extends RedisModel {

  //Initiate connection with DB
  val client = new RedisClient("localhost", 6379)

  var simpleObject = Vector.empty[SimpleObject]

  /***
    * Given a particular SimpleObject check if exist and if not exist in DB
    * Add as a new set, if key exist
    * @param image
    * @return
    */
   def set(image: SimpleObject): Future[Option[String]] = Future {
     //Extracting from a future using foreach
     simpleObject.find(_.id == image.id) match {
       case  Some(i) => None
       case None =>
         simpleObject = simpleObject :+ image
         Some(image.id)
     }

  }

  def update(id: String,updateValue: UpdateValueObject): Future[Option[SimpleObject]] = {

    def updateEntity(image: SimpleObject) : SimpleObject ={
      val value = updateValue.value.getOrElse(image.value)
      SimpleObject(id,value)
    }

    getEntity(id).flatMap{
      simple =>
        simple match {
          case None => Future {None}
          case Some(image) =>
            val updatedValue = updateEntity(image)
            deleteObject(id).flatMap { _ =>
              set(updatedValue).map(_ => Some(updatedValue))
            }
    }
    }
  }

   def get(id: String): Option[String] =  client.get(id)

  /***
    * Helper function to get data from Redis Database
    * @param id
    * @return
    */
  def getEntity(id: String) :Future[Option[SimpleObject]] = Future {
    simpleObject.find(_.id == id)
    }

  def deleteObject(id: String): Future[Unit] = Future{
    simpleObject = simpleObject.filterNot(_.id == id)

  }

}



//for { res <- get(id)}
//yield Some(SimpleObject(id, res.getOrElse("Not Found")))
