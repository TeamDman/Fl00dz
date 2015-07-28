package com.teamdman_9201.fl00dz;

import com.sun.corba.se.impl.util.Version;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by TeamDman on 2015-07-26.
 */
@Mod(name = "Flow", version = Version.VERSION, modid = "Flow")
public class Flow {
    public static Block floodgate;

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        floodgate = new BlockFloodGate();
        GameRegistry.registerBlock(floodgate,"FloodGate");
        GameRegistry.registerTileEntity(TileFloodGate.class,"FloodGate");
        floodgate.setBlockName("FloodGate");
        GameRegistry.addRecipe(new ItemStack(floodgate),"AAA","ABA","AAA",'A', Blocks.iron_block,
                'B', Items.bucket);
    }
}
