package models.tag

import anorm._
import anorm.SqlParser._
import org.joda.time.DateTime
import play.api.Play.current
import play.api.db.DB
import util.AnormExtension._

/**
 * Created by don on 14-08-19.
 */
case class TagGroup (id: Long, tagGroup: String, createdAt: DateTime)

object TagGroup {


  private val tagGroupParser: RowParser[TagGroup] = {
    get[Long]("id") ~
      get[String]("taggroup") ~
      get[DateTime]("createdAt") map {
      case id ~ taggroup ~ createdAt =>
        TagGroup(id, taggroup, createdAt)
    }
  }

  def load(id: Long): Option[TagGroup] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from taggroups WHERE id = {id}")
        .on('id -> id)
        .as(tagGroupParser.singleOpt)
    }
  }

  def findByGroup(tagGroupName: String): Option[TagGroup] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from taggroups WHERE taggroup = {taggroup}")
        .on('taggroup -> tagGroupName)
        .as(tagGroupParser.singleOpt)
    }
  }

  def list: List[TagGroup] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * from taggroups").as(tagGroupParser *)
    }
  }

  def save(tagGroup: TagGroup) {
    DB.withConnection { implicit connection =>
      SQL("""
    		  INSERT INTO taggroups (taggroup, createdAt)
            VALUES({taggroup}, {createdAt})
          """).on(
          'taggroup -> tagGroup.tagGroup,
          'createdAt -> tagGroup.createdAt
        ).executeUpdate
    }
  }

  def update(tagGroup: TagGroup) {
    DB.withConnection { implicit connection =>
      SQL("""
    		  UPDATE taggroups SET taggroup = {taggroup}
    		  WHERE id = {id}
          """).on(
          'id -> tagGroup.id,
          'taggroup -> tagGroup.tagGroup
        ).executeUpdate
    }
  }

  def delete(id: Int) {
    DB.withConnection { implicit connection =>
      SQL("""
          DELETE FROM taggroups where id = {id}
          """).on(
          'id -> id
        ).executeUpdate
    }
  }

}
