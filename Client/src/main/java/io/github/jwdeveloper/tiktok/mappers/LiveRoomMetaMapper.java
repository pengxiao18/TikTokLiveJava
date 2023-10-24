/*
 * Copyright (c) 2023-2023 jwdeveloper jacekwoln@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.jwdeveloper.tiktok.mappers;

import com.google.gson.JsonObject;
import io.github.jwdeveloper.tiktok.live.LiveRoomMeta;

public class LiveRoomMetaMapper {
    /**
     * 0 - Unknown
     * 1 - ?
     * 2 - Online
     * 3 - ?
     * 4 - Offline
     */
    public LiveRoomMeta map(JsonObject input) {
        var liveRoomMeta = new LiveRoomMeta();

        if (!input.has("data")) {
            return liveRoomMeta;
        }
        var data = input.getAsJsonObject("data");


        if (data.has("status")) {
            var status = data.get("status");
            var statusId = status.getAsInt();
            var statusValue = switch (statusId) {
                case 0 -> LiveRoomMeta.LiveRoomStatus.HostNotFound;
                case 2 -> LiveRoomMeta.LiveRoomStatus.HostOnline;
                case 4 -> LiveRoomMeta.LiveRoomStatus.HostOffline;
                default-> LiveRoomMeta.LiveRoomStatus.HostNotFound;
            };
            liveRoomMeta.setStatus(statusValue);
        }
        else
        {
            liveRoomMeta.setStatus(LiveRoomMeta.LiveRoomStatus.HostNotFound);
        }

        if (data.has("age_restricted")) {
            var element = data.getAsJsonObject("age_restricted");
            var restricted = element.get("restricted").getAsBoolean();
            liveRoomMeta.setAgeRestricted(restricted);
        }
        return liveRoomMeta;
    }
}