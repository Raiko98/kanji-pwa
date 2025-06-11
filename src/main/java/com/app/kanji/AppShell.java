package com.app.kanji;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.stereotype.Component;

@Component
@PWA(name = "Kanji Reading Finder", shortName = "KanjiFinder")
public class AppShell implements AppShellConfigurator {
}
