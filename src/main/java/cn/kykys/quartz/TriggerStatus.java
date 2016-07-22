package cn.kykys.quartz;

/**
 * Created by kuangye on 2016/7/22.
 */
public class TriggerStatus {

    public static int STATE_NONE = -1;
    public static int STATE_NORMAL = 0;
    public static int STATE_PAUSED = 1;
    public static int STATE_COMPLETE = 2;
    public static int STATE_ERROR = 3;
    public static int STATE_BLOCKED = 4;
}
