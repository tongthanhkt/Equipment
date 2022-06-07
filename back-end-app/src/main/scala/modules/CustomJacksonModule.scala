package modules

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.ObjectMapper
import com.twitter.finatra.jackson.modules.ScalaObjectMapperModule


object CustomJacksonModule extends ScalaObjectMapperModule {
  override protected def additionalMapperConfiguration(mapper: ObjectMapper): Unit = {
    super.additionalMapperConfiguration(mapper)
    mapper.setSerializationInclusion(Include.NON_NULL)
    mapper.setSerializationInclusion(Include.NON_ABSENT)
    //        mapper.setSerializationInclusion(Include.NON_EMPTY) // Not use because value of properties of user/team will not show
    //    mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
    mapper.configOverride(classOf[Map[_, _]]).setInclude(JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL))
  }
}
