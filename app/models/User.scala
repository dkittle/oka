package models

import anorm._
import anorm.SqlParser._
import org.joda.time.DateTime
import play.api.Play.current
import play.api.db.DB
import util.AnormExtension._
import java.security.MessageDigest

/**
 * Created by don on 14-08-19.
 */

case class User (id: Long, name: String, username: String, email: String, password: String, createdAt: DateTime, updatedAt: DateTime)

object User {


  private val userParser: RowParser[User] = {
      get[Long]("id") ~
      get[String]("name") ~
      get[String]("username") ~
      get[String]("email") ~
      get[String]("password") ~
      get[DateTime]("createdAt") ~
      get[DateTime]("updatedAt") map {
      case id ~ name ~ username ~ email ~ password ~ createdAt ~ updatedAt =>
        User(id, name, username, email, password, createdAt, updatedAt)
    }
  }

  def load(id: Long): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from users WHERE id = {id}")
        .on('id -> id)
        .as(userParser.singleOpt)
    }
  }

  def findByUsername(username: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from users WHERE username = {username}")
        .on('username -> username)
        .as(userParser.singleOpt)
    }
  }

  def authenticate(username: String, password: String): Option[User] = {
    val encrypted = hash(password)
    DB.withConnection { implicit connection =>
      SQL(
        """
         select * from users where
         username = {username} and password = {password}
        """
      ).on(
          'username -> username,
          'password -> encrypted
        ).as(User.userParser.singleOpt)
    }
  }

  def list: List[User] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from users").as(userParser *)
    }
  }

  def save(user: User) {
    val hashed = hash(user.password)
    DB.withConnection { implicit connection =>
      SQL("""
    		  INSERT INTO users (name, username, email, password, createdAt, updatedAt)
            VALUES({name}, {username}, {email}, {password}, {createdAt}, {updatedAt})
            """).on(
          'name -> user.name,
          'username -> user.username,
          'email -> user.email,
          'password -> hashed,
          'createdAt -> user.createdAt,
          'updatedAt -> user.updatedAt
        ).executeUpdate
    }
  }

  def update(user: User) {
    val encrypted = hash(user.password)
    DB.withConnection { implicit connection =>
      SQL("""
    		  UPDATE users SET
            name = {name}, username = {username}, email={email}, password = {password}, createdAt = {createdAt},
            updatedAt = {updatedAt}
    		  WHERE id = {id}
          """).on(
          'id -> user.id,
          'name -> user.name,
          'username -> user.username,
          'email -> user.email,
          'password -> encrypted,
          'createdAt -> user.createdAt,
          'updatedAt -> user.updatedAt
        ).executeUpdate
    }
  }

  def delete(id: Int) {
    DB.withConnection { implicit connection =>
      SQL("""
          DELETE FROM users where id = {id}
          """).on(
          'id -> id
        ).executeUpdate
    }
  }

  def hash(s: String) = {
    MessageDigest.getInstance("SHA").digest(s.getBytes).map("%02X".format(_)).mkString
  }

}
