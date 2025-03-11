package ntua.hci.recyclodroid
import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    private lateinit var layout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.constraintLayout)
        layout.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                val intent = Intent(this@MainActivity,MapsActivity::class.java)
                startActivity(intent)
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                Toast.makeText(
                        this@MainActivity,
                        "Swipe Right gesture detected",
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}