/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated;


import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.generated.tables.NewTable;
import org.jooq.generated.tables.User;
import org.jooq.generated.tables.UserCreateWifi;
import org.jooq.generated.tables.UserInviteProfit;
import org.jooq.generated.tables.UserLinkOrder;
import org.jooq.generated.tables.UserMoneyOrder;
import org.jooq.generated.tables.UserProfit;
import org.jooq.generated.tables.UserWifiCount;
import org.jooq.generated.tables.UserWithdrawalOrder;
import org.jooq.generated.tables.WxCodeScene;
import org.jooq.generated.tables.records.NewTableRecord;
import org.jooq.generated.tables.records.UserCreateWifiRecord;
import org.jooq.generated.tables.records.UserInviteProfitRecord;
import org.jooq.generated.tables.records.UserLinkOrderRecord;
import org.jooq.generated.tables.records.UserMoneyOrderRecord;
import org.jooq.generated.tables.records.UserProfitRecord;
import org.jooq.generated.tables.records.UserRecord;
import org.jooq.generated.tables.records.UserWifiCountRecord;
import org.jooq.generated.tables.records.UserWithdrawalOrderRecord;
import org.jooq.generated.tables.records.WxCodeSceneRecord;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>codewifi</code> schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;
    public static final Identity<UserCreateWifiRecord, Integer> IDENTITY_USER_CREATE_WIFI = Identities0.IDENTITY_USER_CREATE_WIFI;
    public static final Identity<UserInviteProfitRecord, Integer> IDENTITY_USER_INVITE_PROFIT = Identities0.IDENTITY_USER_INVITE_PROFIT;
    public static final Identity<UserLinkOrderRecord, Integer> IDENTITY_USER_LINK_ORDER = Identities0.IDENTITY_USER_LINK_ORDER;
    public static final Identity<UserMoneyOrderRecord, Integer> IDENTITY_USER_MONEY_ORDER = Identities0.IDENTITY_USER_MONEY_ORDER;
    public static final Identity<UserProfitRecord, Integer> IDENTITY_USER_PROFIT = Identities0.IDENTITY_USER_PROFIT;
    public static final Identity<UserWifiCountRecord, Integer> IDENTITY_USER_WIFI_COUNT = Identities0.IDENTITY_USER_WIFI_COUNT;
    public static final Identity<UserWithdrawalOrderRecord, Integer> IDENTITY_USER_WITHDRAWAL_ORDER = Identities0.IDENTITY_USER_WITHDRAWAL_ORDER;
    public static final Identity<WxCodeSceneRecord, Integer> IDENTITY_WX_CODE_SCENE = Identities0.IDENTITY_WX_CODE_SCENE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<NewTableRecord> KEY_NEW_TABLE_PRIMARY = UniqueKeys0.KEY_NEW_TABLE_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserCreateWifiRecord> KEY_USER_CREATE_WIFI_PRIMARY = UniqueKeys0.KEY_USER_CREATE_WIFI_PRIMARY;
    public static final UniqueKey<UserInviteProfitRecord> KEY_USER_INVITE_PROFIT_PRIMARY = UniqueKeys0.KEY_USER_INVITE_PROFIT_PRIMARY;
    public static final UniqueKey<UserLinkOrderRecord> KEY_USER_LINK_ORDER_PRIMARY = UniqueKeys0.KEY_USER_LINK_ORDER_PRIMARY;
    public static final UniqueKey<UserMoneyOrderRecord> KEY_USER_MONEY_ORDER_PRIMARY = UniqueKeys0.KEY_USER_MONEY_ORDER_PRIMARY;
    public static final UniqueKey<UserProfitRecord> KEY_USER_PROFIT_PRIMARY = UniqueKeys0.KEY_USER_PROFIT_PRIMARY;
    public static final UniqueKey<UserWifiCountRecord> KEY_USER_WIFI_COUNT_PRIMARY = UniqueKeys0.KEY_USER_WIFI_COUNT_PRIMARY;
    public static final UniqueKey<UserWifiCountRecord> KEY_USER_WIFI_COUNT_WIFINO = UniqueKeys0.KEY_USER_WIFI_COUNT_WIFINO;
    public static final UniqueKey<UserWithdrawalOrderRecord> KEY_USER_WITHDRAWAL_ORDER_PRIMARY = UniqueKeys0.KEY_USER_WITHDRAWAL_ORDER_PRIMARY;
    public static final UniqueKey<WxCodeSceneRecord> KEY_WX_CODE_SCENE_PRIMARY = UniqueKeys0.KEY_WX_CODE_SCENE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<UserRecord, Integer> IDENTITY_USER = Internal.createIdentity(User.USER, User.USER.ID);
        public static Identity<UserCreateWifiRecord, Integer> IDENTITY_USER_CREATE_WIFI = Internal.createIdentity(UserCreateWifi.USER_CREATE_WIFI, UserCreateWifi.USER_CREATE_WIFI.ID);
        public static Identity<UserInviteProfitRecord, Integer> IDENTITY_USER_INVITE_PROFIT = Internal.createIdentity(UserInviteProfit.USER_INVITE_PROFIT, UserInviteProfit.USER_INVITE_PROFIT.ID);
        public static Identity<UserLinkOrderRecord, Integer> IDENTITY_USER_LINK_ORDER = Internal.createIdentity(UserLinkOrder.USER_LINK_ORDER, UserLinkOrder.USER_LINK_ORDER.ID);
        public static Identity<UserMoneyOrderRecord, Integer> IDENTITY_USER_MONEY_ORDER = Internal.createIdentity(UserMoneyOrder.USER_MONEY_ORDER, UserMoneyOrder.USER_MONEY_ORDER.ID);
        public static Identity<UserProfitRecord, Integer> IDENTITY_USER_PROFIT = Internal.createIdentity(UserProfit.USER_PROFIT, UserProfit.USER_PROFIT.ID);
        public static Identity<UserWifiCountRecord, Integer> IDENTITY_USER_WIFI_COUNT = Internal.createIdentity(UserWifiCount.USER_WIFI_COUNT, UserWifiCount.USER_WIFI_COUNT.ID);
        public static Identity<UserWithdrawalOrderRecord, Integer> IDENTITY_USER_WITHDRAWAL_ORDER = Internal.createIdentity(UserWithdrawalOrder.USER_WITHDRAWAL_ORDER, UserWithdrawalOrder.USER_WITHDRAWAL_ORDER.ID);
        public static Identity<WxCodeSceneRecord, Integer> IDENTITY_WX_CODE_SCENE = Internal.createIdentity(WxCodeScene.WX_CODE_SCENE, WxCodeScene.WX_CODE_SCENE.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<NewTableRecord> KEY_NEW_TABLE_PRIMARY = Internal.createUniqueKey(NewTable.NEW_TABLE, "KEY_new_table_PRIMARY", new TableField[] { NewTable.NEW_TABLE.IDNEW_TABLE }, true);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = Internal.createUniqueKey(User.USER, "KEY_user_PRIMARY", new TableField[] { User.USER.ID }, true);
        public static final UniqueKey<UserCreateWifiRecord> KEY_USER_CREATE_WIFI_PRIMARY = Internal.createUniqueKey(UserCreateWifi.USER_CREATE_WIFI, "KEY_user_create_wifi_PRIMARY", new TableField[] { UserCreateWifi.USER_CREATE_WIFI.ID }, true);
        public static final UniqueKey<UserInviteProfitRecord> KEY_USER_INVITE_PROFIT_PRIMARY = Internal.createUniqueKey(UserInviteProfit.USER_INVITE_PROFIT, "KEY_user_invite_profit_PRIMARY", new TableField[] { UserInviteProfit.USER_INVITE_PROFIT.ID }, true);
        public static final UniqueKey<UserLinkOrderRecord> KEY_USER_LINK_ORDER_PRIMARY = Internal.createUniqueKey(UserLinkOrder.USER_LINK_ORDER, "KEY_user_link_order_PRIMARY", new TableField[] { UserLinkOrder.USER_LINK_ORDER.ID }, true);
        public static final UniqueKey<UserMoneyOrderRecord> KEY_USER_MONEY_ORDER_PRIMARY = Internal.createUniqueKey(UserMoneyOrder.USER_MONEY_ORDER, "KEY_user_money_order_PRIMARY", new TableField[] { UserMoneyOrder.USER_MONEY_ORDER.ID }, true);
        public static final UniqueKey<UserProfitRecord> KEY_USER_PROFIT_PRIMARY = Internal.createUniqueKey(UserProfit.USER_PROFIT, "KEY_user_profit_PRIMARY", new TableField[] { UserProfit.USER_PROFIT.ID }, true);
        public static final UniqueKey<UserWifiCountRecord> KEY_USER_WIFI_COUNT_PRIMARY = Internal.createUniqueKey(UserWifiCount.USER_WIFI_COUNT, "KEY_user_wifi_count_PRIMARY", new TableField[] { UserWifiCount.USER_WIFI_COUNT.ID }, true);
        public static final UniqueKey<UserWifiCountRecord> KEY_USER_WIFI_COUNT_WIFINO = Internal.createUniqueKey(UserWifiCount.USER_WIFI_COUNT, "KEY_user_wifi_count_wifiNo", new TableField[] { UserWifiCount.USER_WIFI_COUNT.WIFI_NO }, true);
        public static final UniqueKey<UserWithdrawalOrderRecord> KEY_USER_WITHDRAWAL_ORDER_PRIMARY = Internal.createUniqueKey(UserWithdrawalOrder.USER_WITHDRAWAL_ORDER, "KEY_user_withdrawal_order_PRIMARY", new TableField[] { UserWithdrawalOrder.USER_WITHDRAWAL_ORDER.ID }, true);
        public static final UniqueKey<WxCodeSceneRecord> KEY_WX_CODE_SCENE_PRIMARY = Internal.createUniqueKey(WxCodeScene.WX_CODE_SCENE, "KEY_wx_code_scene_PRIMARY", new TableField[] { WxCodeScene.WX_CODE_SCENE.ID }, true);
    }
}