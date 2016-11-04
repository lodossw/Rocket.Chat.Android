package chat.rocket.android.ws;

import org.json.JSONArray;

import java.util.UUID;

import bolts.Task;
import chat.rocket.android.helper.OkHttpHelper;
import chat.rocket.android_ddp.DDPClient;
import chat.rocket.android_ddp.DDPClientCallback;
import chat.rocket.android_ddp.DDPSubscription;
import rx.Observable;

public class RocketChatWebSocketAPI {
    private final DDPClient mDDPClient;
    private final String mHostName;

    private RocketChatWebSocketAPI(String hostname) {
        mDDPClient = new DDPClient(OkHttpHelper.getClientForWebSocket());
        mHostName = hostname;
    }

    public static RocketChatWebSocketAPI create(String hostname) {
        return new RocketChatWebSocketAPI(hostname);
    }

    public Task<DDPClientCallback.Connect> connect() {
        return mDDPClient.connect("wss://" + mHostName + "/websocket");
    }

    public boolean isConnected() {
        return mDDPClient.isConnected();
    }

    public void close() {
        mDDPClient.close();
    }


    public Task<DDPSubscription.Ready> subscribe(final String name, JSONArray param) {
        return mDDPClient.sub(UUID.randomUUID().toString(), name, param);
    }

    public Task<DDPSubscription.NoSub> unsubscribe(final String id) {
        return mDDPClient.unsub(id);
    }

    public Observable<DDPSubscription.Event> getSubscriptionCallback() {
        return mDDPClient.getSubscriptionCallback();
    }

}