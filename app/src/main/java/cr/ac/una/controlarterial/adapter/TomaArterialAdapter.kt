package cr.ac.una.controlarterial.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import cr.ac.una.controlarterial.R
import cr.ac.una.controlarterial.entity.TomaArterial

class TomaArterialAdapter(context: Context, tomaArteriales: List<TomaArterial>) :
    ArrayAdapter<TomaArterial>(context, 0, tomaArteriales) {
    override fun getView(arterial: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val tomaArterial = getItem(arterial)

        //val _uuidTextView = view!!.findViewById<TextView>(R.id._uuid)
        val sistolicTextView = view!!.findViewById<TextView>(R.id.sistolic)
        val diastolicTextView = view.findViewById<TextView>(R.id.diastolic)
        val pulsoTextView = view.findViewById<TextView>(R.id.pulso)

        //_uuidTextView.text = tomaArterial!!._uuid.toString()
        sistolicTextView.text = tomaArterial!!.sistolic.toString()
        diastolicTextView.text = tomaArterial.diastolic.toString()
        pulsoTextView.text = tomaArterial.pulso.toString()





        return view
    }
}

