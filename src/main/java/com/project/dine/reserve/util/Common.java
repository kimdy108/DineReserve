package com.project.dine.reserve.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public class Common {
    public static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final Long EMPTY_SEQ = 0L;

    public static class UserInfo {
        public static String getUserIP(HttpServletRequest request) {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null) ip = request.getHeader("Proxy-Client-IP");
            if (ip == null) ip = request.getHeader("WL-Proxy-Client-IP");
            if (ip == null) ip = request.getHeader("HTTP_CLIENT_IP");
            if (ip == null) ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (ip == null) ip = request.getRemoteAddr();

            return ip;
        }

        public static String getUserOS(HttpServletRequest request) {
            String agent = request.getHeader("User-Agent");

            String os;
            if(agent.contains("NT 6.0")) os = "Windows Vista/Server 2008";
            else if(agent.contains("NT 5.2")) os = "Windows Server 2003";
            else if(agent.contains("NT 5.1")) os = "Windows XP";
            else if(agent.contains("NT 5.0")) os = "Windows 2000";
            else if(agent.contains("NT")) os = "Windows NT";
            else if(agent.contains("9x 4.90")) os = "Windows Me";
            else if(agent.contains("98")) os = "Windows 98";
            else if(agent.contains("95")) os = "Windows 95";
            else if(agent.contains("Win16")) os = "Windows 3.x";
            else if(agent.contains("Windows")) os = "Windows";
            else if(agent.contains("Linux")) os = "Linux";
            else if(agent.contains("Macintosh")) os = "Macintosh";
            else os = "";

            return os;
        }

        public static String getUserBrowser(HttpServletRequest request) {
            String agent = request.getHeader("User-Agent");

            String browser = null;
            if (agent != null) {
                if (agent.contains("Trident")) browser = "MSIE";
                else if (agent.contains("Chrome")) browser = "Chrome";
                else if (agent.contains("Opera")) browser = "Opera";
                else if (agent.contains("iPhone") && agent.contains("Mobile")) browser = "iPhone";
                else if (agent.contains("Android") && agent.contains("Mobile")) browser = "Android";
            }
            return browser;
        }
    }
}
