package cr.ac.una.controlarterial.dao

import cr.ac.una.controlarterial.entity.TomaArteriales
import cr.ac.una.controlarterial.entity.TomaArterial
import retrofit2.http.*


interface TomaArterialDAO {
   // @GET("tomaArteriales")
    //suspend fun getTomaArteriales(): List<TomaArterial>
   @GET("tomaArteriales")
   suspend fun getTomaArteriales(): TomaArteriales

    @GET("tomaArteriales/{uuid}")
    suspend fun getTomaArterial(@Path("uuid") uuid: String): TomaArterial

    @POST("tomaArteriales")
    suspend fun createTomaArterial( @Body tomaArteriales: List<TomaArterial>): TomaArteriales

    @PUT("tomaArteriales/{uuid}")
    suspend fun updateTomaArterial(@Path("uuid") uuid: String, @Body tomaArterial: TomaArterial): TomaArterial

    @DELETE("tomaArteriales/{uuid}")
    suspend fun deleteTomaArterial(@Path("uuid") uuid: String)

}