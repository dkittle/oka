package controllers

import models.invite.{Invite, EmailForInvite, PasswordForInvite}
import org.joda.time.DateTime
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation._
import anorm._
import models.User

/**
 * Created by don on 2014-10-22.
 */
object Invites extends Controller with Secured {

//  val loginForm = Form(
//    tuple(
//      "username" -> text,
//      "password" -> text
//    ) verifying ("Invalid username or password", result => result match {
//      case (username, password) => check(username, password)
//    })
//  )


  val emailForinviteForm = Form(
    mapping(
      "email" -> email
    )(EmailForInvite.apply)(EmailForInvite.unapply)
  )

  val userForm = Form(
    mapping(
      "email" -> email,
      "name" -> nonEmptyText,
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "confirm" -> nonEmptyText
    )(PasswordForInvite.apply)(PasswordForInvite.unapply)
  )


  def create = IsAuthenticated { username => implicit session =>
    Ok(views.html.invites.create(User.list, emailForinviteForm))
  }

  def complete(hash: String) = IsAuthenticated { username => implicit session =>
    val invite = Invite.findByHash(hash)
    if (invite == None) {
      Redirect(routes.Invites.list).flashing("success" -> "Could not find that invite.")
    }
    if (Invite.isInviteOudated(invite.get) == true) {
      Redirect(routes.Invites.list).flashing("success" -> "That is an outdated invite.")
    }
    Ok(views.html.invites.complete(User.list, invite.get.email, userForm))
  }

  def save = IsAuthenticated { username => implicit session =>
    emailForinviteForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.invites.create(User.list, formWithErrors)),
      invite => {
        Invite.saveInvite(invite)
        Redirect(routes.Invites.list).flashing("success" -> "Invite successfully created.")
      })
  }

  def list = IsAuthenticated { username => implicit session =>
    Ok(views.html.invites.list(User.list, Invite.list))
  }

}
