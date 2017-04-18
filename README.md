###相关方法


----------


 - 检查相应权限是否被授权
	 checkSelfPermission(String  permissionName)
	 - PackageManager.PERMISSION_GRANTED　　　授权
	 - PackageManager.PERMISSION_DENIED　　　　拒绝
 - 是否要展示请求权限的理由
 shouldShowRequestPermissionRationale(String  permissionName)
 	 - false
	 	 - 此前应用没有请求过此权限，第一请求权限时
	 	 - 此前用户拒绝了此权限并选中了不再询问
	 - true
		 - 此前用户拒绝了此权限但没有勾选不再询问
 - 请求权限
 requestPermissions(String[] permissions, int requestCode)
 - 请求结果回调
 onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)

