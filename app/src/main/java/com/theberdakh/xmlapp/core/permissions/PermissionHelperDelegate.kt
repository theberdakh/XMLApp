package com.theberdakh.xmlapp.core.permissions

interface PermissionsHelperDelegate {
    /**
     * This function must be called from containing Activity's onRequestPermissionResult
     */
    fun onRequestPermissionsResult(requestCode: Int, androidPermissions: Array<String>, grantResults: IntArray)

}
