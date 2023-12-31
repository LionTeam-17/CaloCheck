package calocheck.base.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class Ut {
    public static class check {
        public static boolean isDouble(String str) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public static boolean isInteger(String str) {
            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    public static class json {
        public static String toStr(Map map) {
            try {
                return new ObjectMapper().writeValueAsString(map);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static class time {
        public static boolean isToday(LocalDateTime time1, LocalDateTime time2) {
            long diffDays = Math.abs(ChronoUnit.DAYS.between(time1.toLocalDate(), time2.toLocalDate()));
            return diffDays < 1;
        }

        public static Boolean isRead(LocalDateTime time1, LocalDateTime time2) {
            // 두개의 시간의 차이를 초로 환산
            long diff = Math.abs(ChronoUnit.SECONDS.between(time1, time2));
            long diffMinutes = diff / (60) % 60; // 분 부분만
            long diffHours = diff / (60 * 60) % 24; // 시간 부분만
            long diffDays = diff / (60 * 60 * 24); // 나머지는 일 부분으로
            if (diffMinutes > 10) return true;
            if (diffHours > 0) return true;

            return diffDays > 0;
        }
    }

    public static class reflection {
        public static boolean setFieldValue(Object o, String fieldName, Object value) {
            Field field = null;

            try {
                field = o.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                return false;
            }

            field.setAccessible(true);

            try {
                field.set(o, value);
            } catch (IllegalAccessException e) {
                return false;
            }

            return true;
        }

        public static <T> T getFieldValue(Object o, String fieldName, T defaultValue) {
            Field field = null;

            try {
                field = o.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                return defaultValue;
            }

            field.setAccessible(true);

            try {
                return (T) field.get(o);
            } catch (IllegalAccessException e) {
                return defaultValue;
            }
        }

        public static <T> T call(Object obj, String methodName, Object... args) {
            try {
                Class<?>[] argTypes = Arrays.stream(args)
                        .map(Object::getClass)
                        .toArray(Class<?>[]::new);

                Method method = obj.getClass().getDeclaredMethod(methodName, argTypes);
                method.setAccessible(true);
                return (T) method.invoke(obj, args);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public static <T> T callArr(Object obj, String methodName, Object... args) {
            return call(obj, methodName, new Object[]{args});
        }
    }

    public static class hash {
        private static final MessageDigest md;

        static {
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        public static String sha256(String str) {
            // Convert the input string to bytes and update the MessageDigest
            byte[] inputBytes = str.getBytes(StandardCharsets.UTF_8);
            byte[] hashBytes = md.digest(inputBytes);

            // Convert the hashed bytes to a Base64 encoded string
            return Base64.getEncoder().encodeToString(hashBytes);
        }
    }

    public static class url {
        public static String encode(String str) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return str;
            }
        }

        public static String modifyQueryParam(String url, String paramName, String paramValue) {
            url = deleteQueryParam(url, paramName);
            url = addQueryParam(url, paramName, paramValue);

            return url;
        }

        public static String addQueryParam(String url, String paramName, String paramValue) {
            if (url.contains("?") == false) {
                url += "?";
            }

            if (url.endsWith("?") == false && url.endsWith("&") == false) {
                url += "&";
            }

            url += paramName + "=" + paramValue;

            return url;
        }

        private static String deleteQueryParam(String url, String paramName) {
            int startPoint = url.indexOf(paramName + "=");
            if (startPoint == -1) return url;

            int endPoint = url.substring(startPoint).indexOf("&");

            if (endPoint == -1) {
                return url.substring(0, startPoint - 1);
            }

            String urlAfter = url.substring(startPoint + endPoint + 1);

            return url.substring(0, startPoint) + urlAfter;
        }
    }
}
