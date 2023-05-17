package cr.ac.una.controlarterial


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cr.ac.una.controlarterial.databinding.FragmentSecondBinding
import cr.ac.una.controlarterial.entity.TomaArterial
import cr.ac.una.controlarterial.viewModel.TomaArterialViewModel
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var viewModel: TomaArterialViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        viewModel = ViewModelProvider(this).get(TomaArterialViewModel::class.java)

        val editDiastolic = view.findViewById<EditText>(R.id.diastolic)
        val editSistolic = view.findViewById<EditText>(R.id.sistolic)
        val editPulso = view.findViewById<EditText>(R.id.pulso)


        binding.btnSave.setOnClickListener {

            val diastolic = editDiastolic.text.toString().toIntOrNull()
            val sistolic = editSistolic.text.toString().toIntOrNull()
            val pulso = editPulso.text.toString().toIntOrNull()

            if (diastolic != null && sistolic != null && pulso != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val tomaArterial = TomaArterial(null, diastolic, sistolic, pulso)
                    val tomaArteriales = listOf(tomaArterial)
                    viewModel.agregar(tomaArteriales)
                    Toast.makeText(requireContext(), "Se agregó correctamente", Toast.LENGTH_SHORT).show()
                    editDiastolic.setText("")
                    editSistolic.setText("")
                    editPulso.setText("")
                }
            } else {
                Toast.makeText(requireContext(), "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}