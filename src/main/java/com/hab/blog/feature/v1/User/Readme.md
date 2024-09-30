1用户管理模块

功能	HTTP 方法	API 路径	说明
获取用户资料	GET	/api/v1/user/profile	返回当前用户的资料信息
更新用户资料	PUT	/api/v1/user/profile	更新用户的个人信息
绑定手机号	POST	/api/v1/user/bind/phone	绑定用户手机号
解绑手机号	POST	/api/v1/user/unbind/phone	解绑用户手机号
获取用户绑定信息	GET	/api/v1/user/bindings	获取用户的绑定信息