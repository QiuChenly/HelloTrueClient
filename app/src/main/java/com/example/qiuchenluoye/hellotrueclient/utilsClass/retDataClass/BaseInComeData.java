package com.example.qiuchenluoye.hellotrueclient.utilsClass.retDataClass;

/**
 * Author: QiuChenluoye
 * Time: 2017/07/22,下午 03:03
 * Func: 基础返回数据抽象类
 * Using: 无解释
 */

abstract class BaseInComeData {
    String CreatTime;
    String ConsumptionType;
    String Money;

    public String getCreatTime() {
        return CreatTime;
    }

    public void setCreatTime(String creatTime) {
        CreatTime = creatTime;
    }

    public String getConsumptionType() {
        return ConsumptionType;
    }

    public void setConsumptionType(String consumptionType) {
        ConsumptionType = consumptionType;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getMoney() {
        return Money;
    }
}
