package models.invite

import anorm._
import models.User
import org.joda.time.DateTime
import play.Logger
import play.api.Play.current
import play.api.db.DB
import util.AnormExtension._

import scala.math._

/**
 * Created by don on 2014-10-22.
 */
case class PasswordForInvite (email: String, name: String, username: String, password: String, confirm: String)

object PasswordForInvite {

  def save(invitedUser: PasswordForInvite) {
    val invite = Invite.findByEmail(invitedUser.email)
    if (invite != None)
      Invite.delete(invite.get.id)
    else
      Logger.warn("Cannot find an invite for " + invitedUser.email)
    val user = User(0, invitedUser.name, invitedUser.username, invitedUser.email, invitedUser.password, DateTime.now(), DateTime.now())
    User.save(user)
  }

}