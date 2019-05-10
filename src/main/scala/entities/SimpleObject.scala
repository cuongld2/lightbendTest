package entities


case class SimpleObject(id: String, value: String)
case class UpdateValueObject(value: Option[String])

//TODO: Reimpleent the get and set for value data including the value processing pipeline