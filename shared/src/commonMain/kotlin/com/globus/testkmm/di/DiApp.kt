package com.globus.testkmm.di

import org.kodein.di.DI

object DiApp {

   private var di: DI? = null

    fun initModules(vararg modules: DI.Module) {
        di = DI.from(modules = modules.toList().plus(repositoryModule))
    }

    fun di() : DI{
        return di ?: throw IllegalStateException("DI is not initialised")
    }
}