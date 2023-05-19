package cr.ac.una.controlarterial.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.una.controlarterial.R
import cr.ac.una.controlarterial.entity.TomaArterial


class TomaArterialAdapter(var tomasArteriales: ArrayList<TomaArterial>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            ViewHolder(view)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = tomasArteriales[position]

        if (holder is HeaderViewHolder) {
            holder.bind()
        } else if (holder is ViewHolder) {
            val controlArterialItem = item
            holder.bind(controlArterialItem)
        }
    }
    override fun getItemCount(): Int {
        return tomasArteriales.size
    }
    fun updateData(newData: ArrayList<TomaArterial>) {
        tomasArteriales = newData
        if (!newData.isEmpty())
            if(newData[0]._uuid !=null)
                newData.add(0,TomaArterial(null,0,0,0))
        notifyDataSetChanged()
    }
    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(){
            val sistolicaTextView = itemView.findViewById<TextView>(R.id.sistolica)
            val distolicaTextView = itemView.findViewById<TextView>(R.id.distolica)
            val ritmoTextView = itemView.findViewById<TextView>(R.id.pulso)
            sistolicaTextView.text = "Sistólica"
            distolicaTextView.text = "Diastólica"
            ritmoTextView.text = "Pulso"
        }

    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val sistolicaTextView = itemView.findViewById<TextView>(R.id.sistolica)
        val distolicaTextView = itemView.findViewById<TextView>(R.id.distolica)
        val ritmoTextView = itemView.findViewById<TextView>(R.id.pulso)

        val orangeColor = Color.rgb(255, 165, 0);
        val cafeColor = Color.rgb(139, 69, 19);

        fun bind(tomaArterial: TomaArterial) {
            val sistolica = tomaArterial.sistolic
            val distolica = tomaArterial.diastolic
            if(sistolica < 120 && distolica < 80){
                itemView.setBackgroundColor (Color.GREEN)
            }else if((sistolica > 119 && sistolica < 130 ) && (distolica < 80)){
                itemView.setBackgroundColor (Color.YELLOW)
            }else if((sistolica >= 130 && sistolica <= 139 )&& (distolica >= 80 && distolica<= 89)){
                itemView.setBackgroundColor (orangeColor)
                //naranja
            }else if((sistolica >= 140 &&  sistolica <= 180) && (distolica >= 90 && distolica <= 120)){
                itemView.setBackgroundColor (cafeColor)
                //cafe
            }else if(sistolica > 180 && distolica > 120){
                itemView.setBackgroundColor (Color.RED)
            }
            sistolicaTextView.text = tomaArterial.sistolic.toString()
            distolicaTextView.text = tomaArterial.diastolic.toString()
            ritmoTextView.text = tomaArterial.pulso.toString()
        }
    }
}
