package com.miner.disco.basic.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author: wz1016_vip@163.com @Date: 2019/6/24
 */
public class IpUtil {
    private static String localIp;
    public static String getLocalNetWorkIp() {
        if (localIp != null) {
            return localIp;
        } else {
            try {
                Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
                InetAddress ip = null;

                label47:
                do {
                    NetworkInterface ni;
                    do {
                        do {
                            if (!netInterfaces.hasMoreElements()) {
                                break label47;
                            }

                            ni = (NetworkInterface)netInterfaces.nextElement();
                        } while(ni.isLoopback());
                    } while(ni.isVirtual());

                    Enumeration addresss = ni.getInetAddresses();

                    while(addresss.hasMoreElements()) {
                        InetAddress address = (InetAddress)addresss.nextElement();
                        if (address instanceof Inet4Address) {
                            ip = address;
                            break;
                        }
                    }
                } while(ip == null);

                if (ip != null) {
                    localIp = ip.getHostAddress();
                } else {
                    localIp = "127.0.0.1";
                }
            } catch (Exception var5) {
                localIp = "127.0.0.1";
            }

            return localIp;
        }
    }


    public static void main(String[] args) {
        System.out.println(getLocalNetWorkIp());
    }
}
