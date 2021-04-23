package com.zjl.mytomato.entity

import cn.bmob.v3.BmobObject

data class User(
        var username: String,
        var password: String
): BmobObject()