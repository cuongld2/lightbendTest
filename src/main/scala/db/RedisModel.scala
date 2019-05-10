package db

import entities.SimpleObject
import scala.concurrent.Future


trait RedisModel {

   //Set a String/Array/Float with a key Redis
  def set(image: SimpleObject): Future[Option[String]]

   //Get value of a key from Redis
  def get(id: String): Option[String]

  // Push values to left  existing value of a key
//  def lrpush(value: SimpleObject): Future[Option[String]]
//
//   // Push values to right existing value of a key
//  def rpush(value: SimpleObject): Future[Option[String]]
//
//   // Get the value from a list at a particular index
//  def llindex(id: String, index: Int): Future[Option[String]]
//
//   // Get the length of the list with a particular key
//  def getllist(id: String): Future[Option[Int]]

}