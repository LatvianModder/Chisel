package com.cricketcraft.chisel.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.cricketcraft.chisel.block.tileentity.TileEntityAutoChisel;
import com.cricketcraft.chisel.network.message.base.MessageCoords;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageAutoChisel extends MessageCoords {

	public MessageAutoChisel() {
	}

	ItemStack base;
	boolean playSound;
	
	public MessageAutoChisel(TileEntityAutoChisel tile, boolean playSound) {
		super(tile);
		this.base = tile.getStackInSlot(TileEntityAutoChisel.BASE);
		if (base != null) {
			base = base.copy();
		}
		this.playSound = playSound;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeBoolean(playSound);
		ByteBufUtils.writeItemStack(buf, base);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		this.playSound = buf.readBoolean();
		this.base = ByteBufUtils.readItemStack(buf);
	}

	public static class Handler implements IMessageHandler<MessageAutoChisel, IMessage> {

		public IMessage onMessage(MessageAutoChisel message, MessageContext ctx) {

			TileEntity te = message.getTileEntity(ctx);
			if (te instanceof TileEntityAutoChisel) {
				((TileEntityAutoChisel) te).doChiselAnim(message.base, message.playSound);
			}
			return null;
		}
	}

}