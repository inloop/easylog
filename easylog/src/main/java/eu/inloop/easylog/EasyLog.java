package eu.inloop.easylog;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import java.util.Locale;

public class EasyLog {

    /**
     * Format string for log messages.
     * <p>
     * The format is: <Thread> <Class>.<method>(): <message>
     */
    private static final String FORMAT = "%-30s%s.%s(): %s"; //$NON-NLS-1$

    /**
     * Log tag for use with {@link Log}.
     */
    private static volatile String sLogTag; //$NON-NLS-1$

    /**
     * If true, logging is enabled, else disabled.
     */
    private static boolean sDebug = true; // TODO find better solution than: BuildConfig.DEBUG;

    /**
     * Initializes logging.
     * <p>
     * This should be called from {@link android.app.Application#onCreate()}. (It may also need to
     * be called from {@link android.content.ContentProvider#onCreate()} due to when the provider is
     * initialized.)
     *
     * @param context Application context.
     * @param debug If true, logging is enabled, else disabled. Is recommended set debug from BuildConfig.DEBUG.
     * @param logTag Log tag.
     */
    public static void init(final Context context, boolean debug, String logTag) {
        if (context == null) {
            throw new AssertionError("Context can't be null");
        }

        sLogTag = logTag;
        sDebug = debug;
        if (sDebug) {
            if(Build.VERSION.SDK_INT >= 9){
                setStrictMode(true);
            }

//TODO look into this dependency
//            android.support.v4.app.FragmentManager.enableDebugLogging(true);
//            android.support.v4.app.LoaderManager.enableDebugLogging(true);

            if (Build.VERSION.SDK_INT >= 11) {
                enableDebugLoggingHoneycomb();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void enableDebugLoggingHoneycomb() {
        android.app.FragmentManager.enableDebugLogging(true);
        android.app.LoaderManager.enableDebugLogging(true);
    }

    /**
     * Sets StrictMode on/off.
     *
     * @param state true to enable strict mode, false to disable strict mode
     */
    /* package */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    static void setStrictMode(final boolean state) {
        if (state) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        } else {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
            StrictMode.setVmPolicy(StrictMode.VmPolicy.LAX);
        }
    }

    /**
     * Log a message.
     *
     * @param msg message to log. This message is expected to be a format string if varargs are
     *            passed in.
     * @param args optional arguments to be formatted into {@code msg}.
     */
    public static void v(final String msg, final Object... args) {
        logMessage(Log.VERBOSE, msg, args, null);
    }

    /**
     * Log a message.
     *
     * @param msg message to log.
     * @param err an exception that occurred, whose trace will be printed with the log message.
     */
    public static void v(final String msg, final Throwable err) {
        logMessage(Log.VERBOSE, msg, null, err);
    }

    /**
     * Log a message.
     *
     * @param msg message to log. This message is expected to be a format string if varargs are
     *            passed in.
     * @param args optional arguments to be formatted into {@code msg}.
     */
    public static void d(final String msg, final Object... args) {
        logMessage(Log.DEBUG, msg, args, null);
    }

    /**
     * Log a message.
     *
     * @param msg message to log.
     * @param err an exception that occurred, whose trace will be printed with the log message.
     */
    public static void d(final String msg, final Throwable err) {
        logMessage(Log.DEBUG, msg, null, err);
    }

    /**
     * Log a message.
     *
     * @param msg message to log. This message is expected to be a format string if varargs are
     *            passed in.
     * @param args optional arguments to be formatted into {@code msg}.
     */
    public static void i(final String msg, final Object... args) {
        logMessage(Log.INFO, msg, args, null);
    }

    /**
     * Log a message.
     *
     * @param msg message to log.
     * @param err an exception that occurred, whose trace will be printed with the log message.
     */
    public static void i(final String msg, final Throwable err) {
        logMessage(Log.INFO, msg, null, err);
    }

    /**
     * Log a message.
     *
     * @param msg message to log. This message is expected to be a format string if varargs are
     *            passed in.
     * @param args optional arguments to be formatted into {@code msg}.
     */
    public static void w(final String msg, final Object... args) {
        logMessage(Log.WARN, msg, args, null);
    }

    /**
     * Log a message.
     *
     * @param msg message to log.
     * @param err an exception that occurred, whose trace will be printed with the log message.
     */
    public static void w(final String msg, final Throwable err) {
        logMessage(Log.WARN, msg, null, err);
    }

    /**
     * Log a message.
     *
     * @param msg message to log. This message is expected to be a format string if varargs are
     *            passed in.
     * @param args optional arguments to be formatted into {@code msg}.
     */
    public static void e(final String msg, final Object... args) {
        logMessage(Log.ERROR, msg, args, null);
    }

    /**
     * Log a message.
     *
     * @param msg message to log.
     * @param err an exception that occurred, whose trace will be printed with the log message.
     */
    public static void e(final String msg, final Throwable err) {
        logMessage(Log.ERROR, msg, null, err);
    }

    /**
     * Helper for varargs in log messages.
     *
     * @param msg The format string.
     * @param args The format arguments.
     * @return A string formatted with the arguments.
     */
    private static String formatMessage(final String msg, final Object[] args) {
        String output = msg;

        try {
            for (final Object x : args) {
                output = String.format(Locale.US, output, x);
            }
        } catch (final Exception e) {
            output = String.format(Locale.US, msg, args);
        }
        return output;
    }

    private static void logMessage(final int logLevel, final String message,
                                   final Object[] messageFormatArgs, final Throwable err) {

        if (!sDebug && logLevel < Log.WARN) return;

        final String preppedMessage = formatMessage(message, messageFormatArgs);

        final StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        final String sourceClass = trace[4].getClassName();
        final String sourceMethod = trace[4].getMethodName();

        final String logcatLogLine =
                String.format(Locale.US, FORMAT, Thread.currentThread().getName(), sourceClass,
                        sourceMethod, preppedMessage);

        switch (logLevel) {
            case Log.VERBOSE: {
                if (null == err) {
                    Log.v(sLogTag, logcatLogLine);
                } else {
                    Log.v(sLogTag, logcatLogLine, err);
                }
                break;
            }
            case Log.DEBUG: {
                if (null == err) {
                    Log.d(sLogTag, logcatLogLine);
                } else {
                    Log.d(sLogTag, logcatLogLine, err);
                }
                break;
            }
            case Log.INFO: {
                if (null == err) {
                    Log.i(sLogTag, logcatLogLine);
                } else {
                    Log.i(sLogTag, logcatLogLine, err);
                }
                break;
            }
            case Log.WARN: {
                if (null == err) {
                    Log.w(sLogTag, logcatLogLine);
                } else {
                    Log.w(sLogTag, logcatLogLine, err);
                }
                break;
            }
            case Log.ERROR: {
                if (null == err) {
                    Log.e(sLogTag, logcatLogLine);
                } else {
                    Log.e(sLogTag, logcatLogLine, err);
                }
                break;
            }
            case Log.ASSERT: {
                if (null == err) {
                    Log.wtf(sLogTag, logcatLogLine);
                } else {
                    Log.wtf(sLogTag, logcatLogLine, err);
                }
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
    }

    /**
     * Private constructor prevents instantiation.
     *
     * @throws UnsupportedOperationException because this class cannot be instantiated.
     */
    private EasyLog() {
        throw new UnsupportedOperationException("This class is non-instantiable"); //$NON-NLS-1$
    }
}
