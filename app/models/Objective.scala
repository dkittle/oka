package models

import anorm.SqlParser._
import anorm._
import org.joda.time.DateTime
import play.api.Play.current
import play.api.db.DB
import util.AnormExtension._

/**
 * Created by don on 14-08-19.
 */
case class Objective (id: Long, userId: Long, parentId: Option[Long], name: String, description: String, createdAt: DateTime,
                      updatedAt: DateTime)



object Objective {

  private val objectiveParser: RowParser[Objective] = {
    get[Long]("id") ~
    get[Long]("userId") ~
    get[Option[Long]]{"parentId"} ~
    get[String]("name") ~
    get[String]("description") ~
    get[DateTime]("createdAt") ~
    get[DateTime]("updatedAt") map {
      case id ~ userId ~ parentId ~ name ~ description ~ createdAt ~ updatedAt =>
        Objective(id, userId, parentId, name, description, createdAt, updatedAt)
    }
  }

  private val objectiveSeqParser: RowParser[(String, String)] = {
    get[Long]("id") ~
      get[String]("name") map {
      case id ~ name =>
        (id.toString, name)
    }
  }

  def load(id: Long): Option[Objective] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from objectives WHERE id = {id}")
        .on('id -> id)
        .as(objectiveParser.singleOpt)
    }
  }

  def findByUsername(username: String): List[Objective] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT o.* from objectives o left join users u on o.userId = u.id WHERE u.username = {username}")
        .on('username -> username)
        .as(objectiveParser *)
    }
  }

  def findCompanyObjectives: List[Objective] = {
    findByUsername(current.configuration.getString("company.username").getOrElse(""))
  }

  def list: List[Objective] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from objectives").as(objectiveParser *)
    }
  }

  def save(objective: Objective) {
    DB.withConnection { implicit connection =>
      SQL("""
    		  INSERT INTO objectives (userId, parentId, name, description, createdAt, updatedAt)
            VALUES({userId}, {parentId}, {name}, {description}, {createdAt}, {updatedAt})
          """).on(
          'userId -> objective.userId,
          'parentId -> objective.parentId,
          'name -> objective.name,
          'description -> objective.description,
          'createdAt -> objective.createdAt,
          'updatedAt -> objective.updatedAt
        ).executeUpdate
    }
  }

  def update(objective: Objective) {
    DB.withConnection { implicit connection =>
      SQL("""
    		  UPDATE objectives SET
            userId = {userId}, name = {name}, description = {description}, createdAt = {createdAt},
            updatedAt = {updatedAt} WHERE id = {id}
          """).on(
          'id -> objective.id,
          'userId -> objective.userId,
          'parentId -> objective.parentId,
          'name -> objective.name,
          'description -> objective.description,
          'createdAt -> objective.createdAt,
          'updatedAt -> objective.updatedAt
        ).executeUpdate
    }
  }

  def delete(id: Int) {
    DB.withConnection { implicit connection =>
      SQL("""
          DELETE FROM objectives where id = {id}
          """).on(
          'id -> id
        ).executeUpdate
    }
  }


}
