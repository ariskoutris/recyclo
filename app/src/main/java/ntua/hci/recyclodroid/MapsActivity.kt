package ntua.hci.recyclodroid

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val shmmy = LatLng(37.978452, 23.783654)
        val zoomLevel = 15f
        mMap.addMarker(MarkerOptions().position(shmmy).title(shmmy.toString()).snippet("Recycle Categories"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shmmy, zoomLevel))
        mMap.setOnMapLongClickListener { latLng ->
            addMarker(latLng)
        }
        mMap.setOnMarkerClickListener { marker ->
            removeMarker(marker)
            true
        }
        val butt = findViewById<Button>(R.id.button1)

        butt.setOnClickListener{
            val intent = Intent(this@MapsActivity,MainActivity::class.java)
            startActivity(intent)
        }

        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.categories_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                    // do nothing...
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selection = (parent.getChildAt(0) as TextView)
                selection.setTextColor(ContextCompat.getColor(this@MapsActivity, R.color.white))
                selection.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                selection.setTypeface(null, Typeface.BOLD)
                val selected = parent.getItemAtPosition(position)
            }
        }
    }

    private fun removeMarker(marker: Marker) {
        val dialog =
                AlertDialog.Builder(this)
                        .setTitle("Report Bin")
                        .setMessage(marker.position.toString())
                        .setNegativeButton("Change Categories", null)
                        .setNeutralButton("Cancel", null)
                        .setPositiveButton("Report", null)
                        .show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            marker.remove()
            dialog.dismiss()
        }

        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener{
            dialog.dismiss()
            pickCategories()
        }
    }

    private fun pickCategories() {
        val categoriesView = LayoutInflater.from(this).inflate(R.layout.dialog_categories, null)
        val catDialog =
                AlertDialog.Builder(this)
                .setTitle("Choose Categories")
                .setView(categoriesView)
                .setPositiveButton("OK", null)
                .show()
        catDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            val cb = categoriesView.findViewById<RadioButton>(R.id.radioButton).isChecked
            val cb2 = categoriesView.findViewById<RadioButton>(R.id.radioButton2).isChecked
            val cb3 = categoriesView.findViewById<RadioButton>(R.id.radioButton3).isChecked
            val cb4 = categoriesView.findViewById<RadioButton>(R.id.radioButton4).isChecked
            val cb5 = categoriesView.findViewById<RadioButton>(R.id.radioButton5).isChecked
            val cb6 = categoriesView.findViewById<RadioButton>(R.id.radioButton6).isChecked
            val cb7 = categoriesView.findViewById<RadioButton>(R.id.radioButton7).isChecked
            if(!cb && !cb2 && !cb3 && !cb4 && !cb5 && !cb6 && !cb7){
                Toast.makeText(this, "Please choose a category", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            catDialog.dismiss()
        }

    }

    private fun addMarker(latLng: LatLng){
        val dialog =
                AlertDialog.Builder(this)
                        .setTitle("Add Bin")
                        .setMessage(latLng.toString())
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Add", null)
                        .show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            mMap.addMarker(MarkerOptions().position(latLng).title(latLng.toString()).snippet("Recycle Categories"))
            dialog.dismiss()
            pickCategories()
        }
    }
}

