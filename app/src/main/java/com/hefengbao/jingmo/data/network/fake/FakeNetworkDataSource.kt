package com.hefengbao.jingmo.data.network.fake

import com.hefengbao.jingmo.common.network.AppDispatchers
import com.hefengbao.jingmo.common.network.Dispatcher
import com.hefengbao.jingmo.data.network.JvmUnitTestFakeAssetManager
import com.hefengbao.jingmo.data.network.NetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * [NetworkDataSource] implementation that provides static news resources to aid development
 */
class FakeNetworkDataSource @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
) : NetworkDataSource {
    // 暂时不用
}