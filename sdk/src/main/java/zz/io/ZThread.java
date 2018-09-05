package zz.io;

/**
 * Created by Administrator on 2016/3/18.
 * 默认后台线程，无异常抛出
 */
public class ZThread extends Thread {
    public ZThread() {
        zInit();
    }

    public ZThread(Runnable runnable) {
        super(runnable);
        zInit();
    }

    private void zInit() {
        setDaemon(true);
        setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                ex.printStackTrace();
            }
        });
    }
}
