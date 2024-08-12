package com.theberdakh.xmlapp.core.permissions


interface PermissionsHelper {

    interface Listener {
        fun onRequestPermissionsResult(requestCode: Int, result: PermissionsResult)
        fun onPermissionsRequestCancelled(requestCode: Int)
    }

    class PermissionsResult(
        val granted: List<MyPermission>,
        val denied: List<MyPermission>,
        val deniedDoNotAskAgain: List<MyPermission>
    )

    fun registerListener(listener: Listener)
    fun unregisterListener(listener: Listener)
    fun hasPermission(permission: MyPermission): Boolean
    fun hasAllPermissions(vararg  permissions: MyPermission): Boolean
    fun requestPermission(permission: MyPermission, requestCode: Int)
    fun requestAllPermissions(permissions: Array<MyPermission>, requestCode: Int)

}
