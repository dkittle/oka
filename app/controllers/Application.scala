package controllers

import controllers.Objectives._
import models.invite.Invite
import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action { implicit request =>
    if (request.session.get(Security.username) != None)
      Redirect(routes.Objectives.list(request.session.get(Security.username).get))
    else
      Redirect(routes.Auth.login()).withNewSession
  }

}