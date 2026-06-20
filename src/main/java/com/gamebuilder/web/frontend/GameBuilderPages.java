package com.gamebuilder.web.frontend;

import com.gamebuilder.web.frontend.page.DashboardPage;

public final class GameBuilderPages {

    private GameBuilderPages() {
    }

    public static String dashboard() {
        return DashboardPage.render();
    }
}
