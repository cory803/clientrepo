package org.chaos.mac;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class LoaderProperties {

    private static final boolean LOAD_FROM_URL = true;
    private static final String[] PROPERTIES_URLS =  { "https://dl.dropboxusercontent.com/u/344464529/Chaos/loader.props", "http://chaosps.com/loader.props", "https://dl.dropboxusercontent.com/u/344464529/Chaos/loader.props" };

    public static String app_name = "Client";
    public static String host_address = "127.0.0.1";
    public static int host_port = 43596;
    public static int archive_count = 10;
    public static String archive_entry_name = "client.jar";
    public static String save_file_name = "client.jar";
    public static String main_class = "Main";
    public static String server_version = "1.0";
    public static String[] jvm_args = {
            "-Xms256m",
            "-Xmx512m",
    };

    public static void load() {
        Properties props = new Properties();
        try {
            if (!LOAD_FROM_URL) {
                props.load(new FileInputStream(new File("loader.props")));
            } else {
                for (String url : PROPERTIES_URLS) {
                    boolean loaded;
                    try {
                        props.load(new URL(url).openStream());
                        loaded = true;
                    } catch (Exception e) {
                        loaded = false;
                        System.err.println("Failed to read properties from: " + url);
                    }
                    if (loaded) {
                        break;
                    }
                }
            }
            String prop = props.getProperty("app.name");
            if (prop != null) {
                app_name = prop;
            }
            prop = props.getProperty("host.address");
            if (prop != null) {
                host_address = prop;
            }
            prop = props.getProperty("host.port");
            if (prop != null) {
                host_port = Integer.parseInt(prop);
            }
            prop = props.getProperty("archive.count");
            if (prop != null) {
                archive_count = Integer.parseInt(prop);
            }
            prop = props.getProperty("archive.entry.name");
            if (prop != null) {
                archive_entry_name = prop;
            }
            prop = props.getProperty("save.file.name");
            if (prop != null) {
                save_file_name = prop;
            }
            prop = props.getProperty("main.class");
            if (prop != null) {
                main_class = prop;
            }
            prop = props.getProperty("jvm.args");
            if (prop != null) {
                jvm_args = prop.split(",");
            }
            prop = props.getProperty("server.version");
            if (prop != null) {
                server_version = prop;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}