package com.project.dine.reserve.config;

import com.project.dine.reserve.service.component.LoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartupTask implements ApplicationListener<ApplicationReadyEvent> {
    private final LoggerService loggerService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        loggerService.writeLogger("info", "\n" +
                "                                                                                                                                             \n " +
                "   ██╗ ██╗    ██████╗ ██╗███╗   ██╗███████╗    ██████╗ ███████╗███████╗███████╗██████╗ ██╗   ██╗███████╗    ██╗ ██╗   \n " +
                "  ██╔╝██╔╝    ██╔══██╗██║████╗  ██║██╔════╝    ██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║   ██║██╔════╝    ╚██╗╚██╗  \n " +
                " ██╔╝██╔╝     ██║  ██║██║██╔██╗ ██║█████╗      ██████╔╝█████╗  ███████╗█████╗  ██████╔╝██║   ██║█████╗       ╚██╗╚██╗ \n " +
                " ╚██╗╚██╗     ██║  ██║██║██║╚██╗██║██╔══╝      ██╔══██╗██╔══╝  ╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██╔══╝       ██╔╝██╔╝ \n " +
                "  ╚██╗╚██╗    ██████╔╝██║██║ ╚████║███████╗    ██║  ██║███████╗███████║███████╗██║  ██║ ╚████╔╝ ███████╗    ██╔╝██╔╝  \n " +
                "   ╚═╝ ╚═╝    ╚═════╝ ╚═╝╚═╝  ╚═══╝╚══════╝    ╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝    ╚═╝ ╚═╝   \n " +
                "                                                                                                                                             \n ");
        loggerService.writeLogger("info", "Run Dine Reserve Server");
    }
}
