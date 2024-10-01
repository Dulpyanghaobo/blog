
4. 密码和账号安全
   发送密码重置验证码
   POST /api/v1/auth/password/reset/code
   描述：向用户发送重置密码的验证码。
   请求参数：邮箱。
   响应：验证码发送成功的信息。

验证密码重置验证码
POST /api/v1/auth/password/reset/verify
描述：验证用户提交的重置密码验证码。
请求参数：验证码和用户邮箱。
响应：验证成功或失败的信息。

重置密码
POST /api/v1/auth/password/reset
描述：用户通过验证码重置密码。
请求参数：验证码和新密码。
响应：重置成功或失败的信息。

5. 微信登录与账号管理
   微信登录
   POST /api/v1/auth/wechat/login
   描述：用户通过微信登录。
   请求参数：微信登录的 code。
   响应：返回生成的 JWT token。
6. 账户清理
   清理账户
   POST /api/v1/auth/account/clear
   描述：用户请求清理账户数据。
   请求参数：token，用于验证操作权限。
   响应：账户清理成功或失败的信息。
