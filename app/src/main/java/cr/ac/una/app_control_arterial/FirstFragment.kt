package cr.ac.una.app_control_arterial
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import cr.ac.una.app_control_arterial.adapter.TomaArterialAdapter
//import cr.ac.una.app_control_arterial.databinding.FragmentFirstBinding
import cr.ac.una.app_control_arterial.entity.TomaArterial
import cr.ac.una.app_control_arterial.viewModel.TomaArterialViewModel
import cr.ac.una.controlarterial.R
import cr.ac.una.controlarterial.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var tomaArterialViewModel : TomaArterialViewModel
    private lateinit var tomasArteriales :List<TomaArterial>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)// llama la regla en el el nav_graph
       }*/

        //Se inicializa el Adapter con una lista vacía
        val listView = view.findViewById<RecyclerView>(R.id.list_view)
        tomasArteriales = mutableListOf<TomaArterial>()
        var adapter =  TomaArterialAdapter(tomasArteriales as ArrayList<TomaArterial>)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(requireContext())

        //Se atacha al viewModel de la activiad principal
        tomaArterialViewModel = ViewModelProvider(requireActivity()).get(TomaArterialViewModel::class.java)

        //cada vez que el observador nota reporta un cambio en los datos se actualiza la lista de elementos
        tomaArterialViewModel.tomaArteriales.observe(viewLifecycleOwner) { elementos ->
            adapter.updateData(elementos as ArrayList<TomaArterial>)
            tomasArteriales = elementos
        }

        GlobalScope.launch(Dispatchers.Main) {
            tomaArterialViewModel.listar()!!//lista
        }

        // Crea el ItemTouchHelper
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position!=0) {
                    val tomaArterial = tomasArteriales[position]
                    val uuid = tomaArterial._uuid
                    viewLifecycleOwner.lifecycleScope.launch {
                        tomaArterialViewModel.eliminar(uuid.toString())//elimina
                    }
                    // Elimina el elemento cuando se detecta el deslizamiento hacia la derecha
                    (tomasArteriales as MutableList<TomaArterial>).removeAt(position)
                    adapter.updateData(tomasArteriales as ArrayList<TomaArterial>)
                }
            }

            // Sobrescribe el método para dibujar la etiqueta al deslizar
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                if (viewHolder is TomaArterialAdapter.ViewHolder) {
                    super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState, isCurrentlyActive)
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        val itemView = viewHolder.itemView
                        val paint = Paint()
                        paint.color = Color.RED
                        val deleteIcon = ContextCompat.getDrawable( requireContext(),android.R.drawable.ic_menu_delete)
                        val iconMargin = (itemView.height - deleteIcon!!.intrinsicHeight) / 2
                        val iconTop = itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
                        val iconBottom = iconTop + deleteIcon.intrinsicHeight

                        // Dibuja el fondo rojo
                        c.drawRect( itemView.left.toFloat(),itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat(),paint)

                        // Calcula las posiciones del icono de eliminar
                        val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                        val iconRight = itemView.right - iconMargin
                        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        // Dibuja el icono de eliminar
                        deleteIcon.draw(c)
                    }
                }
            }
        })
        // Adjunta el ItemTouchHelper al RecyclerView
        itemTouchHelper.attachToRecyclerView(listView)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





