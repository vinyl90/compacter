/*
 * Copyright (c) bdew, 2015 - 2017
 * https://github.com/bdew/compacter
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.compacter

import java.io.File

import net.bdew.compacter.config._
import net.bdew.compacter.network.NetworkHandler
import net.bdew.lib.gui.GuiHandler
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.network.NetworkRegistry
import org.apache.logging.log4j.Logger

@Mod(modid = CompacterMod.modId, version = "COMPACTER_VER", name = "Compacter", dependencies = "required-after:bdlib", modLanguage = "scala", acceptedMinecraftVersions = "[1.12,1.12.2]")
object CompacterMod {
  var log: Logger = null
  var instance = this

  final val modId = "compacter"
  final val channel = "bdew.compacter"

  var configDir: File = null
  var guiHandler = new GuiHandler

  def logDebug(msg: String, args: Any*) = log.debug(msg.format(args: _*))
  def logInfo(msg: String, args: Any*) = log.info(msg.format(args: _*))
  def logWarn(msg: String, args: Any*) = log.warn(msg.format(args: _*))
  def logError(msg: String, args: Any*) = log.error(msg.format(args: _*))
  def logWarnException(msg: String, t: Throwable, args: Any*) = log.warn(msg.format(args: _*), t)
  def logErrorException(msg: String, t: Throwable, args: Any*) = log.error(msg.format(args: _*), t)

  @EventHandler
  def preInit(event: FMLPreInitializationEvent) {
    log = event.getModLog
    configDir = new File(event.getModConfigurationDirectory, "compacter")
    TuningLoader.loadConfigFiles()
    Config.load()
    Machines.load()
    Blocks.load()
  }

  @EventHandler
  def init(event: FMLInitializationEvent) {
    NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler)
    NetworkHandler.init()
  }

  @EventHandler
  def postInit(event: FMLPostInitializationEvent): Unit = {
    TuningLoader.loadDelayed()
  }
}
