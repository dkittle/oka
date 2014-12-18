package controllers

import controllers.Invites._
import models.invite.PasswordForInvite
import play.api.mvc.{Action, Controller}
import models.User

/**
 * Created by don on 14-08-19.
 */
object Users extends Controller with Secured {

  def createUser = IsAuthenticated { username => implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.invites.complete(User.list, formWithErrors.get.email, formWithErrors)),
      invite => {
        PasswordForInvite.save(invite)
        Redirect(routes.Objectives.list(invite.username)).flashing("success" -> "User successfully created!")
      })
  }

}
