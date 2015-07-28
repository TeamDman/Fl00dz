//https://github.com/BuildCraft/BuildCraft/blob/47cba60750998eb300fcf7b4fdf213afb5655e1f/common
// /buildcraft/factory/BlockFloodGate.java
//Licence 1.0, or MMPL [I think]
//hi sae <3

package com.teamdman_9201.flow;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created by TeamDman on 2015-07-26.
 */
public class BlockFloodGate extends BlockContainer {
    private IIcon valve, transparent, blocked;
    private boolean[] blockedSides = new boolean[6];

    public BlockFloodGate() {
        super(Material.iron);
        setHardness(5F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileFloodGate();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        return blockedSides[side] == true ? blocked : side == 0 ? valve : transparent;
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
        blockedSides = new boolean[6];
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_,
                p_149749_6_);
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float par7, float par8, float par9) {
        System.out.println("activated");
        if (super.onBlockActivated(world, i, j, k, entityplayer, side, par7, par8, par9)) {
            return true;
        }

        // Drop through if the player isn't sneaking
        if (!entityplayer.isSneaking()) {
            return false;
        }

        TileEntity tile = world.getTileEntity(i, j, k);

        if (tile instanceof TileFloodGate) {
            TileFloodGate floodGate = (TileFloodGate) tile;
            // Restart the flood gate if it's a wrench
            Item equipped = entityplayer.getCurrentEquippedItem() != null ? entityplayer.getCurrentEquippedItem().getItem() : null;
            //if (equipped instanceof IToolWrench && ((IToolWrench) equipped).canWrench(entityplayer, i, j, k)) {
            System.out.println(equipped);
//            if (equipped == Items.stick) { // stick as temp wrench item
                if (side == 1) {
                    floodGate.rebuildQueue();
                } else {
                    floodGate.switchSide(ForgeDirection.getOrientation(side));
                }
                blockedSides = floodGate.getBlocked();
            System.out.println(blockedSides.toString());
                //            ((IToolWrench) equipped).wrenchUsed(entityplayer, i, j, k);
                return true;
//            }
        }

        return false;
    }



    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileFloodGate) {
            ((TileFloodGate) tile).onNeighborBlockChange(block);
        }
    }

    //    @Override
    //    @SideOnly(Side.CLIENT)
    //    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
    //        if (renderPass == 1) {
    //            if (side != 1) {
    //                TileEntity tile = world.getTileEntity(x, y, z);
    //                if (tile instanceof TileFloodGate) {
    //                    return ((TileFloodGate) tile).isSideBlocked(side) ? transparent : valve;
    //                }
    //            }
    //            return transparent;
    //        } else {
    //            return super.getIcon(world, x, y, z, side);
    //        }
    //    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);
        valve = register.registerIcon("flow:FloodGateValve");
        transparent = register.registerIcon("flow:FloodGateSide");
        blocked = register.registerIcon("flow:FloodGateBlocked");
    }
}