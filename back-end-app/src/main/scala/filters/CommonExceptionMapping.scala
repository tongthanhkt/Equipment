package filters

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.util.logging.Logging
import utils.Utils

import javax.inject.Inject

class CommonExceptionMapping @Inject()(response: ResponseBuilder) extends ExceptionMapper[Exception] with Logging {
  override def toResponse(request: Request, exception: Exception): Response = {
    exception match {
      case _ =>
        error(s"${request.contentString} => ${Utils.getStackTraceAsString(exception)}", exception)
        //println(s"${request.contentString} => ${Utils.getStackTraceAsString(exception)}")
        response.badRequest.json(Map(
          "error"-> exception.getMessage
        ))
    }
  }
}
