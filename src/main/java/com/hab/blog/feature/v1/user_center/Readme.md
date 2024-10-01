## 1. 用户管理模块

| 功能 | HTTP 方法 | API 路径 | 说明 |
|------|-----------|----------|------|
| 获取用户资料 | GET | /api/v1/user/profile | 返回当前用户的资料信息 |
| 更新用户资料 | PUT | /api/v1/user/profile | 更新用户的个人信息 |
| 绑定手机号 | POST | /api/v1/user/bind/phone | 绑定用户手机号 |
| 解绑手机号 | POST | /api/v1/user/unbind/phone | 解绑用户手机号 |
| 获取用户绑定信息 | GET | /api/v1/user/bindings | 获取用户的绑定信息 |
| 我的反馈 | GET | /api/v1/user/feedback | 获取用户提交的反馈记录 |
| 提交反馈 | POST | /api/v1/user/feedback | 提交新的反馈记录 |
| 购买记录 | GET | /api/v1/user/purchase-history | 获取用户的购买记录列表 |
| 我的账户明细 | GET | /api/v1/user/account-details | 获取用户的账户交易明细，包括收入、支出等 |
| 我的亲友列表 | GET | /api/v1/user/friends | 获取用户的亲友列表 |
| 添加亲友 | POST | /api/v1/user/friends | 添加亲友到亲友列表 |
| 删除亲友 | DELETE | /api/v1/user/friends/{friendId} | 从亲友列表中删除某个亲友 |
| 个人设置加载 | GET | /api/v1/user/settings | 获取用户的个人设置 |
| 更新个人设置 | PUT | /api/v1/user/settings | 更新用户的个人设置 |
| 我的权益使用次数 | GET | /api/v1/user/privileges | 获取用户权益使用的次数，例如高级功能的使用次数 |
| 更新头像 | PUT | /api/v1/user/profile/avatar | 更新用户头像 |

## 1. 绑定手机号
// POST	/api/v1/user/bind/phone	绑定用户手机号
// 检查是否绑定了手机号
// 如果绑定了 先调用api解绑手机号
// 否则直接绑定手机号
// 绑定手机号
// 请求 api 获取验证码
// 用户输入验证码校验， 校验成功
// 绑定成功
// 否则绑定失败

## 解绑手机号	POST	/api/v1/user/unbind/phone	解绑用户手机号
// 检查用户是否绑定了手机号，如果绑定了则调用发送验证码api解绑
// 用户输入验证码， 调用验证解绑api然后解除绑定， user表里面的手机号为null

## 2. 我的反馈 (Feedback)

### 获取我的反馈
- **HTTP 方法**: GET
- **API 路径**: /api/v1/user/feedback
- **描述**: 返回用户提交的所有反馈记录列表。
- **响应**: 反馈内容、提交时间、处理状态等信息。

### 提交反馈
- **HTTP 方法**: POST
- **API 路径**: /api/v1/user/feedback
- **描述**: 提交新的反馈信息。
- **请求参数**: 反馈的详细内容（如问题描述、截图等）。
- **响应**: 反馈提交成功的确认信息。

## 3. 购买记录 (Purchase History)

### 获取购买记录
- **HTTP 方法**: GET
- **API 路径**: /api/v1/user/purchase-history
- **描述**: 获取用户的购买记录，包括时间、商品或服务名称、支付金额等信息。
- **响应**: 购买记录列表。

## 4. 我的账户明细 (Account Details)

### 获取账户明细
- **HTTP 方法**: GET
- **API 路径**: /api/v1/user/account-details
- **描述**: 查看用户的账户明细，详细记录用户的收入、支出和余额变化情况。
- **响应**: 账户交易明细列表（包括交易时间、金额、类型等）。

## 5. 我的亲友列表 (Friends List)

### 获取亲友列表
- **HTTP 方法**: GET
- **API 路径**: /api/v1/user/friends
- **描述**: 返回用户已添加的亲友列表，显示亲友的基本信息。
- **响应**: 亲友列表。

### 添加亲友
- **HTTP 方法**: POST
- **API 路径**: /api/v1/user/friends
- **描述**: 用户通过提供亲友的邮箱或手机号添加亲友。
- **请求参数**: 亲友的邮箱或手机号。
- **响应**: 添加成功的确认信息。

### 删除亲友
- **HTTP 方法**: DELETE
- **API 路径**: /api/v1/user/friends/{friendId}
- **描述**: 删除指定的亲友。
- **请求参数**: 亲友的ID。
- **响应**: 删除成功的确认信息。

## 6. 个人设置 (User Settings)

### 加载个人设置
- **HTTP 方法**: GET
- **API 路径**: /api/v1/user/settings
- **描述**: 加载用户的个人设置，如通知偏好、语言设置等。
- **响应**: 返回用户的当前设置。

### 更新个人设置
- **HTTP 方法**: PUT
- **API 路径**: /api/v1/user/settings
- **描述**: 更新用户的个人设置。
- **请求参数**: 新的设置内容。
- **响应**: 设置更新成功的确认信息。

## 7. 我的权益使用次数 (Privilege Usage)

### 获取权益使用次数
- **HTTP 方法**: GET
- **API 路径**: /api/v1/user/privileges
- **描述**: 获取用户在特定时间段内使用产品权益的次数，比如高级功能的调用次数、免费服务的使用次数等。
- **响应**: 使用次数和相关详细信息。

## 8. 更新头像 (Update Avatar)

### 更新用户头像
- **HTTP 方法**: PUT
- **API 路径**: /api/v1/user/profile/avatar
- **描述**: 更新用户的个人头像。
- **请求参数**: 上传新的头像图片。
- **响应**: 更新后的头像URL或确认信息。