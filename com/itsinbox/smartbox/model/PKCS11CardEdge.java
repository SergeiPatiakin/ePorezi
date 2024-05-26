package com.itsinbox.smartbox.model;

import com.itsinbox.smartbox.SmartBox;
import com.itsinbox.smartbox.utils.Utils;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

public class PKCS11CardEdge extends PKCS11Card {

    public static final String[] KNOWN_ATRS = new String[]{
            "3B:FF:94:00:00:81:31:80:43:80:31:80:65:B0:85:02:01:F3:12:0F:FF:82:90:00:79",
            "3B:F8:13:00:00:81:31:FE:45:4A:43:4F:50:76:32:34:31:B7",
            "3B:FA:13:00:00:81:31:FE:45:4A:43:4F:50:32:31:56:32:33:31:91",
            "3B:7D:94:00:00:80:31:80:65:B0:83:11:C0:A9:83:00:90:00",
            "3B:7D:94:00:00:80:31:80:65:B0:83:11:00:C8:83:00",
            "3B:9E:96:80:31:FE:45:53:43:45:20:38:2E:30:2D:43:31:56:30:0D:0A:6F"
    };

    protected String getPKCS11ModuleName() {
        return "CardEdge";
    }

    protected String getPKCS11ModulePath(int osFamily) {
        switch (osFamily){
            case 2:
                return this.searchModulePaths(new String[]{"/usr/lib/libnstpkcs11.so"});
            case 4:
                ArrayList<String> paths = new ArrayList<>();
                RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
                List<String> arguments = runtimeMxBean.getInputArguments();
                for (String argument : arguments) {
                    if(argument.contains("libnstpkcs11.dylib"))
                    {
                        paths.add(argument.substring(argument.indexOf('/')));
                    }
                }
                paths.add("/usr/local/lib/libnstpkcs11.dylib");
                return this.searchModulePaths(paths.toArray(new String[0]));
            default:
                return null;
        }
    }
}