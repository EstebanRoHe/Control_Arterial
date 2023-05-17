package cr.ac.una.controlarterial
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cr.ac.una.controlarterial.adapter.TomaArterialAdapter
import cr.ac.una.controlarterial.databinding.FragmentFirstBinding
import cr.ac.una.controlarterial.entity.TomaArterial
import cr.ac.una.controlarterial.viewModel.TomaArterialViewModel
import kotlinx.coroutines.launch


class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private lateinit var viewModel: TomaArterialViewModel
    private  lateinit var adapter: TomaArterialAdapter
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

            val listView = view.findViewById<ListView>(R.id.list_view)
            viewModel = ViewModelProvider(this).get(TomaArterialViewModel::class.java)
            adapter = TomaArterialAdapter(requireContext(), mutableListOf<TomaArterial>())
            listView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listar()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tomaArteriales.observe(viewLifecycleOwner) {
                adapter.clear()
                adapter.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val tomaArterial = adapter.getItem(position)
            tomaArterial?.let {
                val uuid = it._uuid
                if (uuid != null) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.eliminar(uuid)
                        Toast.makeText(requireContext(), "Se elimino la toma arterial con el id "+uuid+" correctamente", Toast.LENGTH_SHORT).show()
                        viewModel.listar()
                    }
                }
            }
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

//apiService.deleteTomaArterial("28df1d37-13e6-4007-800b-f0271261fd09")
/*

 listView.setOnItemClickListener { _, _, position, _ ->
            val tomaArterial = adapter.getItem(position)
            print("??????entra!!!!!!!!!!!!!!!!  "+tomaArterial?._uuid)
            val id = tomaArterial?._uuid ?: ""
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.eliminar(id)
            }
        }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)// llama la regla en el el nav_graph
        }

            val listView = view.findViewById<ListView>(R.id.list_view)
            viewModel = ViewModelProvider(this).get(TomaArterialViewModel::class.java)
            adapter = TomaArterialAdapter(requireContext(), mutableListOf<TomaArterial>())
            listView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listar()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tomaArteriales.observe(viewLifecycleOwner) {
                adapter.clear()
                adapter.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }

        val deleteButton = view.findViewById<ImageButton>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val item = adapter.getItem(position)
                if (item is TomaArterial) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.eliminar(item)
                        Toast.makeText(context, "Ítem eliminado", Toast.LENGTH_SHORT).show()
                        viewModel.listar()
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}






 listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = adapter.getItem(position)
            selectedItem?.let { item ->
                val tomaArterial = item as? TomaArterial
                tomaArterial?.let {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.eliminar(it)
                        Toast.makeText(requireContext(), "Ítem eliminado", Toast.LENGTH_SHORT).show()
                        viewModel.listar()
                    }
                }
            }
        }
        */