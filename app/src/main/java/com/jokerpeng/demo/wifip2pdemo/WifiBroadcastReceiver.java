package com.jokerpeng.demo.wifip2pdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengXiaoJie on 2017/5/26.16 40..
 */

public class WifiBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager wifiP2pManager;
    private Channel channel;
    private Activity activity;
    private PeerListListener peerListListener;

    public WifiBroadcastReceiver(WifiP2pManager wifiP2pManager, Channel channel, Activity activity) {
        this.wifiP2pManager = wifiP2pManager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("+++++++++++++","    ++++++++  "+action);
        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1);
            if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED){//启用

            }else{//未启用

            }
        }else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){
            if (wifiP2pManager != null) {
                Log.e("+++++++++++++",""+action);
                wifiP2pManager.requestPeers(channel, peerListListener);
            }
        }else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){

        }else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){

        }else if(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION.equals(action)){
            if (wifiP2pManager != null) {
                Log.e("+++++++++++++",""+action);
                wifiP2pManager.requestPeers(channel, peerListListener);
            }
        }
        peerListListener = new PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                List<WifiP2pDevice> list = new ArrayList<>();
                list.addAll(peers.getDeviceList());
                Log.e("**************",""+list.size());
                if(list.size()>0){
                    WifiP2pDevice device = list.get(0);
                    Log.e("+++++++++++",device.deviceAddress.toString());
                    connectPeer(device);
                }
            }
        };
    }

    private void connectPeer(WifiP2pDevice device){
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        wifiP2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }

}
