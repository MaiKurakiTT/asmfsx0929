package com.hsd.asmfsx;

import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;

/**
 * Created by apple on 2016/11/6.
 */

public class UserInfoProvider implements EaseUI.EaseUserProfileProvider{
    private String name;
    private String icon;

    public UserInfoProvider(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    @Override
    public EaseUser getUser(String username) {
        EaseUser easeUser = new EaseUser(username);
        easeUser.setInitialLetter(name);
        easeUser.setAvatar(icon);
        return easeUser;
    }
}
