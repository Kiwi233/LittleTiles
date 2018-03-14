package com.creativemd.littletiles.common.utils.rotation;

import com.creativemd.littletiles.common.entity.EntityAnimation;
import com.creativemd.littletiles.common.entity.EntityDoorAnimation;
import com.creativemd.littletiles.common.tiles.LittleTile;
import com.creativemd.littletiles.common.utils.grid.LittleGridContext;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class SlidingDoorTransformation extends DoorTransformation {
	
	public EnumFacing direction;
	public int distance;
	public LittleGridContext context;
	
	public SlidingDoorTransformation() {
		
	}
	
	public SlidingDoorTransformation(EnumFacing direction, LittleGridContext context, int distance) {
		this.direction = direction;
		this.distance = distance;
	}

	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
		nbt.setInteger("direction", direction.ordinal());
		nbt.setInteger("distance", distance);
		context.set(nbt);
	}

	@Override
	protected void readFromNBT(NBTTagCompound nbt) {
		direction = EnumFacing.getFront(nbt.getInteger("direction"));
		distance = nbt.getInteger("distance");
		context = LittleGridContext.get(nbt);
	}

	@Override
	public void performTransformation(EntityDoorAnimation animation, double progress) {
		switch(direction)
		{
		case EAST:
			animation.posX = animation.getAxisPos().getX() - animation.startOffset.getX() - (distance*context.gridMCLength*(1-progress));
			break;
		case WEST:
			animation.posX = animation.getAxisPos().getX() - animation.startOffset.getX() + (distance*context.gridMCLength*(1-progress));
			break;
		case UP:
			animation.posY = animation.getAxisPos().getY() - animation.startOffset.getY() - (distance*context.gridMCLength*(1-progress));
			break;
		case DOWN:
			animation.posY = animation.getAxisPos().getY() - animation.startOffset.getY() + (distance*context.gridMCLength*(1-progress));
			break;
		case SOUTH:
			animation.posZ = animation.getAxisPos().getZ() - animation.startOffset.getZ() - (distance*context.gridMCLength*(1-progress));
			break;
		case NORTH:
			animation.posZ = animation.getAxisPos().getZ() - animation.startOffset.getZ() + (distance*context.gridMCLength*(1-progress));
			break;
		default:
			break;
		
		}
	}

	@Override
	public boolean equals(Object object) {
		if(object instanceof SlidingDoorTransformation)
			return ((SlidingDoorTransformation) object).direction == direction && ((SlidingDoorTransformation) object).distance == distance && ((SlidingDoorTransformation) object).context == context;
		return false;
	}
	
}
