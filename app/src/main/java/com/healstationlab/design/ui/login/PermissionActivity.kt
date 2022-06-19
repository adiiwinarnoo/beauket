package com.healstationlab.design.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityMainBinding
import java.util.ArrayList

class PermissionActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cornfirmView.setOnClickListener {
            permission()
        }
    }

    private fun permission(){
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                val intent = Intent(this@PermissionActivity, SignUpPopupActivity::class.java)
                val sns = intent.getStringExtra("type")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra("type", sns)
                startActivity(intent)
                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                Toast.makeText(this@PermissionActivity, "권한을 허락해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleMessage("앱을 사용하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다.앱을 사용하시려면 [앱 설정]-[권한] 항목에서 권한을 허용해주세요.")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }
}