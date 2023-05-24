package cr.ac.una.app_control_arterial.dao

import cr.ac.una.app_control_arterial.entity.TomaArteriales
import cr.ac.una.app_control_arterial.entity.TomaArterial
import retrofit2.http.*


interface TomaArterialDAO {
   // @GET("tomaArteriales")
    //suspend fun getTomaArteriales(): List<TomaArterial>
   @GET("tomaArteriale")
   suspend fun getTomaArteriales(): TomaArteriales

    @GET("tomaArteriale/{uuid}")
    suspend fun getTomaArterial(@Path("uuid") uuid: String): TomaArterial

    @POST("tomaArteriale")
    suspend fun createTomaArterial( @Body tomaArteriales: List<TomaArterial>): TomaArteriales

    @PUT("tomaArteriale/{uuid}")
    suspend fun updateTomaArterial(@Path("uuid") uuid: String, @Body tomaArterial: TomaArterial): TomaArterial

    @DELETE("tomaArteriale/{uuid}")
    suspend fun deleteTomaArterial(@Path("uuid") uuid: String)

}