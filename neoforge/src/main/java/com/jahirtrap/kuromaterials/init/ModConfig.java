package com.jahirtrap.kuromaterials.init;

import com.jahirtrap.configlib.TXFConfig;

public class ModConfig extends TXFConfig {
    @Entry(name = "Item Autorepair Interval", min = 0, max = Integer.MAX_VALUE, itemDisplay = "kuromaterials:zurite_ingot")
    public static int itemAutorepairInterval = 30;
    @Entry(name = "Item Autorepair Amount", min = 0, max = Integer.MAX_VALUE, itemDisplay = "kuromaterials:zurite_ingot")
    public static int itemAutorepairAmount = 1;
}
