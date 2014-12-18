package controllers

import play.api.Play._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation._
import anorm._
import play.api.mvc.{Action, Controller}
import models.{User, ObjectiveForm, Objective}
import play.api.Logger

/**
 * Created by don on 14-08-19.
 */
object Objectives extends Controller with Secured {

  val objectiveForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "parentId" -> optional(longNumber),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    )(ObjectiveForm.apply)(ObjectiveForm.unapply)
  )

  def listAll() = IsAuthenticated { username => implicit request =>
    val user = User.findByUsername(current.configuration.getString("company.username").getOrElse(""))
//    val username = request.session.get("username").get
//    Logger.warn("getting objectives for " + selectedUsername)
//    if (selectedUsername != "all-users") {
//      user.map { user =>
//        Ok(views.html.objectives.list(User.list, user, Objective.findByUsername(selectedUsername)))
//      }.getOrElse(Forbidden)
//    }
//    else {
      Logger.warn("Returning all objectives")
      user.map { user =>
        Ok(views.html.objectives.list(User.list, user, Objective.list))
      }.getOrElse(Forbidden)
//    }
  }

  def list(selectedUsername: String) = IsAuthenticated { username => implicit request =>
    val user = if (selectedUsername != "all-users") { User.findByUsername(selectedUsername) } else {
      User.findByUsername(current.configuration.getString("company.username").getOrElse("")) }
    val username = request.session.get("username").get
    Logger.warn("getting objectives for " + selectedUsername)
    if (selectedUsername != "all-users") {
      user.map { user =>
        Ok(views.html.objectives.list(User.list, user, Objective.findByUsername(selectedUsername)))
      }.getOrElse(Forbidden)
    }
    else {
        Logger.warn("Returning all objectives")
        user.map { user =>
          Ok(views.html.objectives.list(User.list, user, Objective.list))
        }.getOrElse(Forbidden)
      }
  }

  def create = IsAuthenticated { username => implicit request =>
    Ok(views.html.objectives.create(User.list, Objective.findCompanyObjectives, objectiveForm))
  }

  def save = IsAuthenticated { username => implicit request =>
    objectiveForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.objectives.create(User.list, Objective.findCompanyObjectives, formWithErrors)),
      objective => {
        ObjectiveForm.save(objective)
        Redirect(routes.Objectives.list(objective.username)).flashing("success" -> "Objective successfully created!")
      })
  }

}
