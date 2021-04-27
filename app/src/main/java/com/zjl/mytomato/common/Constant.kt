package com.zjl.mytomato.common

object Constant {
    //添加todo失败
    const val ADD_TODO_FAIL = 0

    //添加todo成功
    const val ADD_TODO_SUCCESS = 1

    //更新todo失败
    const val UPDATE_TODO_FAIL = 2

    //随机图片url
    const val BASE_PIC_URL = "https://source.unsplash.com/1600x900/?nature/"

    //todo布局为线性布局
    const val LINEARLAYOUT = 4

    //todo布局为网格布局
    const val GRIDLAYOUT = 5

    //定时任务添加成功
    const val ADD_TIMED_TASK_SUCCESS = 6

    //定时任务添加失败
    const val ADD_TIMED_TASK_FAIL = 7

    //注册成功
    const val REGISTER_SUCCESS = 8

    ///注册失败
    const val REGISTER_FAIL = 9

    //网络异常
    const val NETWORK_ERROR = 10

    //登入时用户名或密码错误
    const val LOGIN_ERROR = 11

    //登入成功
    const val LOGIN_SUCCESS = 12

    //同步到网络失败
    const val UPDATE_TO_NETWORK_FAIL = 13

    //同步到网络成功
    const val UPDATE_TO_NETWORK_SUCCESS = 14

    //清除数据成功
    const val CLEAR_SUCCESS = 15

    //云端没有数据
    const val CLEAR_NO_DATA = 16

    //成就列表
    val achievementList = arrayListOf<String>("无", "初出茅庐", "小有所成", "渐入佳境", "一往无前")
    fun getAchievement(time: Int) = when (time) {
        0 -> achievementList[0]
        in 1..10 -> achievementList[1]
        in 11..60 -> achievementList[2]
        in 61..180 -> achievementList[3]
        else -> achievementList[4]
    }
}