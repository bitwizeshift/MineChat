package com.rodusek.minechat.permissions;

/*****************************************************************************
 * An interface for managing pluggin permissions
 * 
 * 
 * 
 *****************************************************************************/
public interface PluginPermissible
{    
    /**
     * Checks if this object contains an override for the specified {@link
     * PluginPermission}
     *
     * @param permission PluginPermission to check
     * @return true if the permission is set, otherwise false
     */
    public boolean isPermissionSet(PluginPermission permission);

    /**
     * Gets the value of the specified permission, if set.
     * <p>
     * If a permission override is not set on this object, the default value
     * of the permission will be returned
     *
     * @param permission PluginPermission to get
     * @return Value of the permission
     */
    public boolean hasPermission(PluginPermission permission);
}
