package com.lobo.repogit.core.helpers

import org.koin.core.KoinComponent
import org.koin.core.get

object Resources: KoinComponent {
    var resourceHelper: ResourceHelper = get()
}