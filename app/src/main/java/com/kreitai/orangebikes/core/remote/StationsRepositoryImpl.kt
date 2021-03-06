/*
 * MIT License
 *
 * Copyright (c) 2020 Kreitai OÜ
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.kreitai.orangebikes.core.remote

import com.kreitai.orangebikes.core.domain.StationsResult
import com.kreitai.orangebikes.core.remote.model.StationList

class StationsRepositoryImpl constructor(serviceProvider: ServiceProvider) :
    SmartBikeRemoteRepository<StationList>(serviceProvider), StationsRepository {

    private var type: Int = 0

    override fun getDeferredResponseAsync(serviceProvider: ServiceProvider) =
        serviceProvider.service.getStationsAsync("en", type)

    override suspend fun getStations(type: Int): StationsResult {
        this.type = type
        val response = fetchData()
        return if (response is NetworkResult.Success) {
            StationsResult.Success(response.data.stations)
        } else {
            StationsResult.Failure(response.toString())
        }
    }

}