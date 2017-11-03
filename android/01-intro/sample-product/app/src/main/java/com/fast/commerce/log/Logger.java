package com.fast.commerce.log;

import android.util.Log;

import com.fast.commerce.util.Preconditions;

public class Logger {

    private static final CustomLogger sLogger = new CustomLogger("Fast");

    private Logger() {}

    static private class CustomLogger {

        /**
         * The maximum length of any tag used, as defined by
         * {@link android.util.Log#isLoggable(String, int) Log.isLoggable}
         */
        public static final int MAX_TAG_LENGTH = 23;

        private static final String NO_PREFIX = null;

        /**
         * The primary log tag to used to control log emission for this CustomLogger.
         */
        private final String mLogTag;

        /**
         * The message prefix to be included with all log messages.
         */
        private final String mPrefix;

        /**
         * Creates a logger which will only emit messages if the specified log tag allows it at the
         * requested log level, and will prefix all log messages with the provided prefix.
         *
         * @param logTag The primary log tag for this logger, which controls log emission. This tag
         *               must be shorter than {@link #MAX_TAG_LENGTH}.
         * @param messagePrefix a message prefix to include with every logged message.
         */
        public CustomLogger(String logTag, String messagePrefix) {
            Preconditions.checkNotNull(logTag, "log tag cannot be null");
            Preconditions.checkArgument(logTag.length() <= MAX_TAG_LENGTH,
                    "tag \"%s\" is longer than the %d character maximum",
                    logTag,
                    MAX_TAG_LENGTH);
            mLogTag = logTag;

            if (messagePrefix == null || messagePrefix.length() <= 0) {
                mPrefix = NO_PREFIX;
            } else {
                mPrefix = messagePrefix;
            }
        }

        public CustomLogger(String logTag) {
            this(logTag, NO_PREFIX);
        }

        /**
         * Convenience method to create a new logger from a template logger, changing the
         * message prefix to the provided value.
         *
         * @param prefix the new message prefix to use.
         * @return a new instance of CustomLogger with the same primary tag as the template logger,
         * but with a different message prefix.
         */
        public CustomLogger withMessagePrefix(String prefix) {
            return new CustomLogger(mLogTag, prefix);
        }

        /**
         * @return the primary log tag set in the constructor.
         */
        public String getTag() {
            return mLogTag;
        }

        /**
         * Checks whether a log message can be emitted for the specified log level.
         *
         * @param level The log level to check, from the enum defined in {@link Log}.
         * @return Whether a log message can be emitted.
         */
        public boolean canLog(int level) {
            return Log.isLoggable(mLogTag, level);
        }

        /**
         * Emits the specified log message using the specified tag at log level {@link Log#DEBUG},
         * if the primary tag is enabled for debug logging.
         *
         * @param tag     The log tag used to emit the message. This tag must be shorter than
         *                {@link #MAX_TAG_LENGTH}.
         * @param message The message to emit.
         */
        public void d(String tag, String message) {
            if (canLog(Log.DEBUG)) {
                Log.d(tag, prefix(message));
            }
        }

        /**
         * Emits the specified log message to the specified tag at log level {@link Log#DEBUG},
         * if the primary tag is enabled for debug logging.
         *
         * @param tag     The log tag used to emit the message. This tag must be shorter than
         *                {@link #MAX_TAG_LENGTH}.
         * @param message The message to emit.
         * @param thr     A Throwable to include with the message.
         */
        public void d(String tag, String message, Throwable thr) {
            if (canLog(Log.DEBUG)) {
                Log.d(tag, prefix(message), thr);
            }
        }

        /**
         * Emits a formatted message to the specified tag at log level {@link Log#DEBUG}, if the primary
         * tag is enabled for debug logging.
         *
         * @param tag                 The log tag used to emit the message. This tag must be shorter than
         *                            {@link #MAX_TAG_LENGTH}.
         * @param messageFormatString The message format string, matching the
         *                            {@link http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax format
         *                            spec} defined for {@link String#format(String, Object[])}.
         * @param messageParams       The parameters for the format string.
         */
        public void dfmt(String tag, String messageFormatString, Object... messageParams) {
            if (canLog(Log.DEBUG)) {
                Log.d(tag, prefixfmt(messageFormatString, messageParams));
            }
        }

        /**
         * Emits the specified log message to the specified tag at log level {@link Log#VERBOSE},
         * if the primary tag is enabled for verbose logging.
         *
         * @param tag     The log tag used to emit the message. This tag must be shorter than
         *                {@link #MAX_TAG_LENGTH}.
         * @param message The message to emit.
         */
        public void v(String tag, String message) {
            if (canLog(Log.VERBOSE)) {
                Log.v(tag, prefix(message));
            }
        }

        /**
         * Emits the specified log message and Throwable to the specified tag at log level
         * {@link Log#VERBOSE}, if the primary tag is enabled for verbose logging.
         *
         * @param tag     The log tag used to emit the message. This tag must be shorter than
         *                {@link #MAX_TAG_LENGTH}.
         * @param message The message to emit.
         * @param thr     A Throwable to include with the message.
         */
        public void v(String tag, String message, Throwable thr) {
            if (canLog(Log.VERBOSE)) {
                Log.v(tag, prefix(message), thr);
            }
        }

        /**
         * Emits a formatted message to the specified tag at log level {@link Log#VERBOSE}, if the
         * primary tag is enabled for verbose logging.
         *
         * @param messageFormatString The message format string, matching the
         *                            {@link http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax format
         *                            spec} defined for {@link String#format(String, Object[])}.
         * @param messageParams       The parameters for the format string.
         */
        public void vfmt(String tag, String messageFormatString, Object... messageParams) {
            if (canLog(Log.VERBOSE)) {
                Log.v(tag, prefixfmt(messageFormatString, messageParams));
            }
        }

        /**
         * Emits the specified log message to the specified tag at log level {@link Log#INFO},
         * if the primary tag is enabled for info logging.
         *
         * @param tag     The log tag used to emit the message. This tag must be shorter than
         *                {@link #MAX_TAG_LENGTH}.
         * @param message The message to emit.
         */
        public void i(String tag, String message) {
            if (canLog(Log.INFO)) {
                Log.i(tag, prefix(message));
            }
        }

        /**
         * Emits the specified log message and Throwable to the specified tag at log level
         * {@link Log#INFO}, if the primary tag is enabled for info logging.
         *
         * @param tag     The log tag used to emit the message. This tag must be shorter than
         *                {@link #MAX_TAG_LENGTH}.
         * @param message The message to emit.
         * @param thr     A Throwable to include with the message.
         */
        public void i(String tag, String message, Throwable thr) {
            if (canLog(Log.INFO)) {
                Log.i(tag, prefix(message), thr);
            }
        }

        /**
         * Emits a formatted message to the specified tag at log level {@link Log#INFO}, if the
         * primary tag is enabled for info logging.
         *
         * @param tag                 The log tag used to emit the message. This tag must be shorter than
         *                            {@link #MAX_TAG_LENGTH}.
         * @param messageFormatString The message format string, matching the
         *                            {@link http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax format
         *                            spec} defined for {@link String#format(String, Object[])}.
         * @param messageParams       The parameters for the format string.
         */
        public void ifmt(String tag, String messageFormatString, Object... messageParams) {
            if (canLog(Log.INFO)) {
                Log.i(tag, prefixfmt(messageFormatString, messageParams));
            }
        }

        /**
         * Emits the specified log message to the specified tag at log level {@link Log#WARN},
         * if the primary tag is enabled for warn logging.
         *
         * @param tag     The log tag used to emit the message. This tag must be shorter than
         *                {@link #MAX_TAG_LENGTH}.
         * @param message The message to emit.
         */
        public void w(String tag, String message) {
            if (canLog(Log.WARN)) {
                Log.w(tag, prefix(message));
            }
        }

        /**
         * Emits the specified log message and Throwable to the specified tag at log level
         * {@link Log#WARN}, if the primary tag is enabled for warn logging.
         *
         * @param tag     The log tag used to emit the message. This tag must be shorter than
         *                {@link #MAX_TAG_LENGTH}.
         * @param message The message to emit.
         * @param thr     A Throwable to include with the message.
         */
        public void w(String tag, String message, Throwable thr) {
            if (canLog(Log.WARN)) {
                Log.w(tag, prefix(message), thr);
            }
        }

        /**
         * Emits a formatted message to the specified tag at log level {@link Log#WARN}, if the
         * primary tag is enabled for warn logging.
         *
         * @param tag                 The log tag used to emit the message. This tag must be shorter than
         *                            {@link #MAX_TAG_LENGTH}.
         * @param messageFormatString The message format string, matching the
         *                            {@link http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax format
         *                            spec} defined for {@link String#format(String, Object[])}.
         * @param messageParams       The parameters for the format string.
         */
        public void wfmt(String tag, String messageFormatString, Object... messageParams) {
            if (canLog(Log.WARN)) {
                Log.w(mLogTag, prefixfmt(messageFormatString, messageParams));
            }
        }

        /**
         * Emits the specified log message to the specified tag at log level {@link Log#ERROR},
         * if the primary tag is enabled for erro logging.
         *
         * @param tag     The log tag used to emit the message. This tag must be shorter than
         *                {@link #MAX_TAG_LENGTH}.
         * @param message The message to emit.
         */
        public void e(String tag, String message) {
            if (canLog(Log.ERROR)) {
                Log.e(tag, prefix(message));
            }
        }

        /**
         * Emits the specified log message and Throwable to the specified tag at log level
         * {@link Log#ERROR}, if the primary tag is enabled for error logging.
         *
         * @param tag     The log tag used to emit the message. This tag must be shorter than
         *                {@link #MAX_TAG_LENGTH}.
         * @param message The message to emit.
         * @param thr     A Throwable to include with the message.
         */
        public void e(String tag, String message, Throwable thr) {
            if (canLog(Log.ERROR)) {
                Log.e(tag, prefix(message), thr);
            }
        }

        /**
         * Emits a formatted message to the specified tag at log level {@link Log#ERROR}, if the
         * primary tag is enabled for error logging.
         *
         * @param messageFormatString The message format string, matching the
         *                            {@link http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax format
         *                            spec} defined for {@link String#format(String, Object[])}.
         * @param messageParams       The parameters for the format string.
         */
        public void efmt(String tag, String messageFormatString, Object... messageParams) {
            if (canLog(Log.ERROR)) {
                Log.e(tag, prefixfmt(messageFormatString, messageParams));
            }
        }

        private String prefix(String message) {
            if (mPrefix == null) {
                return message;
            }
            return mPrefix.concat(message);
        }

        private String prefixfmt(String messageFormatString, Object... messageParams) {
            String msg = String.format(messageFormatString, messageParams);
            if (mPrefix == null) {
                return msg;
            }
            return mPrefix.concat(msg);
        }

    }
}

