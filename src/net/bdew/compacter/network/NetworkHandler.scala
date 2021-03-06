/*
 * Copyright (c) bdew, 2015 - 2017
 * https://github.com/bdew/compacter
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.compacter.network

import net.bdew.compacter.CompacterMod
import net.bdew.compacter.blocks.compacter.{ContainerCompacter, CraftMode, RecurseMode}
import net.bdew.lib.Misc
import net.bdew.lib.multiblock.data.RSMode
import net.bdew.lib.network.NetChannel

object NetworkHandler extends NetChannel(CompacterMod.channel) {
  regServerHandler {
    case (MsgSetRsMode(rsMode), player) =>
      Misc.asInstanceOpt(player.openContainer, classOf[ContainerCompacter]) foreach { cont =>
        cont.te.rsMode := rsMode
        cont.te.haveWork = true
      }
    case (MsgSetCraftMode(craftMode), player) =>
      Misc.asInstanceOpt(player.openContainer, classOf[ContainerCompacter]) foreach { cont =>
        cont.te.craftMode := craftMode
        cont.te.checkRecipes = true
      }
    case (MsgSetRecurseMode(recurseMode), player) =>
      Misc.asInstanceOpt(player.openContainer, classOf[ContainerCompacter]) foreach { cont =>
        cont.te.recurseMode := recurseMode
      }
  }
}

case class MsgSetRsMode(rsMode: RSMode.Value) extends NetworkHandler.Message

case class MsgSetCraftMode(craftMode: CraftMode.Value) extends NetworkHandler.Message

case class MsgSetRecurseMode(recurseMode: RecurseMode.Value) extends NetworkHandler.Message