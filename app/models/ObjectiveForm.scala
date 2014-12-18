package models

import org.joda.time.DateTime

/**
 * Created by don on 2014-10-22.
 */
case class ObjectiveForm(username: String, parentId: Option[Long], name: String, description: String)


object ObjectiveForm {

  def save(objectiveForm: ObjectiveForm) {
    val user = User.findByUsername(objectiveForm.username)
    val parentId: Option[Long]
      = if (objectiveForm.parentId.isDefined && objectiveForm.parentId.get == 0) Option.empty else objectiveForm.parentId
    val objective = Objective(0, user.get.id, parentId, objectiveForm.name, objectiveForm.description, DateTime.now(),
      DateTime.now())
    Objective.save(objective)
  }

}
