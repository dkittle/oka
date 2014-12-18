package models.invite

import anorm.SqlParser._
import anorm._
import org.joda.time.DateTime
import play.api.Play.current
import play.api.db.DB
import util.AnormExtension._

import scala.math._

/**
 * Created by don on 2014-10-22.
 */
case class Invite(id: Long, email: String, hash: String, createdAt: DateTime)

case class EmailForInvite (email: String)

object Invite {

  def saveInvite(invite: EmailForInvite) {
    val hash = java.lang.Long.toString(abs(scala.util.Random.nextLong), 36) +
      java.lang.Long.toString(abs(scala.util.Random.nextLong), 36)
    val existingInvite = Invite.findByHash(hash)
    if (existingInvite == None || clearOutdatedInvite(existingInvite.get) == true) {
      DB.withConnection { implicit connection =>
        SQL("""
    		  INSERT INTO invites (email, hash, createdAt)
            VALUES({email}, {hash}, {createdAt})
            """).on(
            'email -> invite.email,
            'hash -> hash,
            'createdAt -> DateTime.now()
          ).executeUpdate
      }
    }
  }

  def clearOutdatedInvite(invite: Invite) : Boolean = {
    if (isInviteOudated(invite) == true) {
      Invite.delete(invite.id)
    }
    true
  }

  def isInviteOudated(invite: Invite) : Boolean = {
    val oldestAge = DateTime.now().minusDays(3)
    oldestAge.isAfter(invite.createdAt)
  }

  private val inviteParser: RowParser[Invite] = {
    get[Long]("id") ~
      get[String]("email") ~
      get[String]("hash") ~
      get[DateTime]("createdAt") map {
      case id ~ email ~ hash ~ createdAt =>
        Invite(id, email, hash, createdAt)
    }
  }

  def load(id: Long): Option[Invite] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from invites WHERE id = {id}")
        .on('id -> id)
        .as(inviteParser.singleOpt)
    }
  }

  def findByHash(hash: String): Option[Invite] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from invites WHERE hash = {hash}")
        .on('hash -> hash)
        .as(inviteParser.singleOpt)
    }
  }

  def findByEmail(email: String): Option[Invite] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from invites WHERE email = {email}")
        .on('email -> email)
        .as(inviteParser.singleOpt)
    }
  }

  def list: List[Invite] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from invites").as(inviteParser *)
    }
  }

  def save(invite: Invite) {
    DB.withConnection { implicit connection =>
      SQL("""
        INSERT INTO invites (email, hash, createdAt)
          VALUES({email}, {hash}, {createdAt})
          """).on(
          'email -> invite.email,
          'hash -> invite.hash,
          'createdAt -> invite.createdAt
        ).executeUpdate
    }
  }

  def update(invite: Invite) {
    DB.withConnection { implicit connection =>
      SQL("""
        UPDATE invites SET
          email = {email}, hash = {hash}, createdAt = {createdAt}
        WHERE id = {id}
          """).on(
          'id -> invite.id,
          'email -> invite.email,
          'hash -> invite.hash,
          'createdAt -> invite.createdAt
        ).executeUpdate
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit connection =>
      SQL("""
        DELETE FROM invites where id = {id}
          """).on(
          'id -> id
        ).executeUpdate
    }
  }

}