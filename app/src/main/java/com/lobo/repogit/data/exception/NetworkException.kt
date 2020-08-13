package com.lobo.repogit.data.exception

import com.lobo.repogit.R
import com.lobo.repogit.core.helpers.Resources

class NetworkException : GeneralException(Resources.resourceHelper.getString(R.string.network_error))