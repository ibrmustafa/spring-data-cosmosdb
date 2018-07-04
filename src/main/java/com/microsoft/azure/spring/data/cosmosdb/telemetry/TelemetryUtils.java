/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.cosmosdb.telemetry;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;

import static com.microsoft.applicationinsights.core.dependencies.apachecommons.codec.digest.DigestUtils.sha256Hex;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelemetryUtils {

    private static final String UNKNOWN_MAC = "unknown-Mac-Address";

    private static String getMacAddress() {
        try {
            final InetAddress host = InetAddress.getLocalHost();
            final byte[] macBytes = NetworkInterface.getByInetAddress(host).getHardwareAddress();

            return Arrays.toString(macBytes);
        } catch (UnknownHostException | SocketException e) { // Ignore
            return UNKNOWN_MAC;
        }
    }

    public static String getHashMac() {
        final String mac = getMacAddress();

        if (mac.equals(UNKNOWN_MAC)) {
            return UNKNOWN_MAC;
        }

        return sha256Hex(mac);
    }

    public static void telemetryTriggerEvent(TelemetryTracker tracker, @NonNull String eventName,
                                             Map<String, String> properties) {
        if (tracker != null) {
            tracker.trackEvent(eventName, properties);
        }
    }
}