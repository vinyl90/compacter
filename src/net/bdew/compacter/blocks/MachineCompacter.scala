package net.bdew.compacter.blocks

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.bdew.lib.gui.GuiProvider
import net.bdew.lib.machine.{Machine, PoweredMachine}
import net.minecraft.entity.player.EntityPlayer

object MachineCompacter extends Machine("Compacter", BlockCompacter) with GuiProvider with PoweredMachine {
  override def guiId: Int = 1
  override type TEClass = TileCompacter

  @SideOnly(Side.CLIENT)
  override def getGui(te: TileCompacter, player: EntityPlayer) = new GuiCompacter(te, player)
  override def getContainer(te: TileCompacter, player: EntityPlayer): AnyRef = new ContainerCompacter(te, player)
}
