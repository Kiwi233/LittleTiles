package com.creativemd.littletiles.common.packet;

import com.creativemd.creativecore.common.packet.CreativeCorePacket;
import com.creativemd.littletiles.common.blocks.ILittleTile;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.utils.PlacementHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class LittleRotatePacket extends CreativeCorePacket{

	public LittleRotatePacket() {

	}

	public ForgeDirection direction;

	public LittleRotatePacket(ForgeDirection direction) {
		this.direction = direction;
	}

	@Override
	public void writeBytes(ByteBuf buf) {
		writeDirection(buf, direction);
	}

	@Override
	public void readBytes(ByteBuf buf) {
		direction = readDirection(buf);
	}

	@Override
	public void executeClient(EntityPlayer player) {
		execute(player);
	}

	public void execute(EntityPlayer player)
	{
		if(PlacementHelper.isLittleBlock(player.getHeldItem()))
		{
			ItemStack stack = player.getHeldItem();

			ILittleTile itile = null;

			if(stack.getItem() instanceof ILittleTile)
			{
				itile = (ILittleTile)stack.getItem();
			}else if(Block.getBlockFromItem(stack.getItem()) instanceof ILittleTile){
				itile = (ILittleTile)Block.getBlockFromItem(stack.getItem());
			}

			if(itile != null)
			{
				itile.rotateLittlePreview(stack, direction);
				LittleStructure structure = itile.getLittleStructure(stack);
				if(structure != null)
				{
					structure.onRotate(player.worldObj, player, stack, direction);
					NBTTagCompound nbt = new NBTTagCompound();
					structure.writeToNBT(nbt);
					stack.stackTagCompound.setTag("structure", nbt);
				}
			}
		}
	}

	@Override
	public void executeServer(EntityPlayer player) {
		execute(player);
	}

}
