package cr.ac.una.app_control_arterial.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import cr.ac.una.app_control_arterial.AuthInterceptor
import cr.ac.una.app_control_arterial.dao.TomaArterialDAO
import cr.ac.una.app_control_arterial.entity.TomaArterial
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TomaArterialViewModel : ViewModel() {

    private val _tomaArterial: MutableLiveData<List<TomaArterial>> = MutableLiveData()
    val tomaArteriales: LiveData <List<TomaArterial>> = _tomaArterial
    lateinit var apiService : TomaArterialDAO

    suspend fun listar(){
        intService()
        val getAll = apiService.getTomaArteriales()
        _tomaArterial.postValue(getAll.items)
    }

    suspend fun agregar(tomaArterial: List<TomaArterial>) {
        intService()
        apiService.createTomaArterial(tomaArterial)

    }

    suspend fun eliminar(_uuid :String) {
        intService()
        apiService.deleteTomaArterial(_uuid)
    }

    fun intService(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor("kCjtfUb-MAbbs6ksB_ey1p_Ha1NEAQFBqVOofhOP02N0-f1HYg"))//llave
            .addInterceptor(interceptor)
            .build()

        val gson = GsonBuilder().setPrettyPrinting().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://crudapi.co.uk/api/v1/")//ruta
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            apiService = retrofit.create(TomaArterialDAO::class.java)
    }
}