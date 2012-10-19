package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def upload = Action(parse.temporaryFile) { request =>
    Logger.info("Got stuff from upload")
    val picture = request.body.file
    Logger.info("   size = " + picture.length())
    import java.io.File
    Ok("File uploaded: " + picture.getPath())
  }


}
