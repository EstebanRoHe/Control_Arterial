package cr.ac.una.controlarterial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import cr.ac.una.controlarterial.adapter.TomaArterialAdapter
import cr.ac.una.controlarterial.dao.TomaArterialDAO
import cr.ac.una.controlarterial.databinding.FragmentFirstBinding
import cr.ac.una.controlarterial.entity.TomaArterial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)// llama la regla en el el nav_graph
        }

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

        viewLifecycleOwner.lifecycleScope.launch {
            val apiService = retrofit.create(TomaArterialDAO::class.java)
            val tomaArterial = TomaArterial(null, 180, 300, 4000)
             var tomaArteriales = ArrayList<TomaArterial>()
             tomaArteriales.add(tomaArterial)
             apiService.createTomaArterial(tomaArteriales)

             //apiService.deleteTomaArterial("28df1d37-13e6-4007-800b-f0271261fd09")
            withContext(Dispatchers.Main) {
                val getAll = apiService.getTomaArteriales()
                val getAlltomaArteriales = getAll.items ?: emptyList()// Utilizar elvis operator para manejar la lista nula
                val listView = view.findViewById<ListView>(R.id.list_view)
                val adapter = context?.let { TomaArterialAdapter(it, getAlltomaArteriales) }
                listView.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}