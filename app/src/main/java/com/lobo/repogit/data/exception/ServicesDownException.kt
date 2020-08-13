package com.lobo.repogit.data.exception

import com.lobo.repogit.R
import com.lobo.repogit.core.helpers.Resources

class ServicesDownException :
    GeneralException(Resources.resourceHelper.getString(R.string.services_down_message))