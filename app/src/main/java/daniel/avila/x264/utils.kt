package daniel.avila.x264

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.isPermissionGranted(permission: List<String>): Boolean = permission.none { ContextCompat.checkSelfPermission(
    this,
    it
) != PackageManager.PERMISSION_GRANTED }

fun Activity.requestPermission(permission: List<String>, requestCode: Int) {
    ActivityCompat.requestPermissions(this, permission.toTypedArray(), requestCode)
}

fun IntArray.isPermissionsGranted(): Boolean = none { it != PackageManager.PERMISSION_GRANTED }
