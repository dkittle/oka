package util

import models.User
import play.api.Play.current
import com.typesafe.plugin._
import play.api.i18n.Messages
import play.api.Play.current


object EmailService {
  def sendEmail(contact: User) = {
    val mail = use[MailerPlugin].email
    mail.setSubject(Messages("contact.request.subject", contact.email))
    mail.setRecipient(Messages(contact.email))
    mail.setFrom(Messages("contact.request.email.from"))
    mail.send(Messages("contact.request.body", contact.email, contact.name, contact.createdAt))
  }
}
