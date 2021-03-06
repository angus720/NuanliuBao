package nuanliu.com.modao.utils;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


@SuppressLint("SimpleDateFormat")
public class DateUtil {

    /**
     *
     * @param str 原格式2017-02-15~2017-02-22 转换为  02/15~0222
     * @return
     */
    public static String getDateUntilDate(String str) {
        String[] split = str.split("~");
        String[] date1 = split[0].split("-");
        String[] date2 = split[1].split("-");
        String s = date1[1] + "/" + date1[2] +"~" + date2[1] + "/" + date2[2];
        return s ;
    }
    /**
     *
     * @param str 原格式2017-02-15 转换为  02/15
     * @return
     */
    public static String getMonthAndDay(String str) {
        String[] split = str.split("-");
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        String s = month + "/" + day ;
        return s ;
    }

    /**
     *
     * @param str 原格式2017-02-15 转换为  2月16日，星期四
     * @return
     */
    public static String getMonthDayOfWeek(String str) {
        String[] split = str.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        String strday = DateUtil.getWeekendOfDay(year, month - 1, day);
        String s = month + "月" + day + "日" + "," + strday;
        return s ;
    }

    /**
     * 获取当前时间格式yyyyMMddHHmmssSSS 的时间
     */
    public static String getCurrentTimeStrMills() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public static String stringToTime(String string) {
        try {
            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(string);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 是否是今天
     */
    public static boolean isToday(final Date date) {
        return isTheDay(date, now());
    }

    /**
     * 是否是指定日期
     */
    public static boolean isTheDay(final Date date, final Date day) {
        return date.getTime() >= dayBegin(day).getTime()
                && date.getTime() <= dayEnd(day).getTime();
    }

    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     */
    public static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取今天 00:00:00.000 的时间
     */
    public static Date dayBegin() {
        return dayBegin(now());
    }
    public static String getWeekendOfDay() {

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);
        String stringday = null;
        switch (day) {
            case 1:
                stringday="星期天";
                break;
            case 2:
                stringday="星期一";
                break;
            case 3:
                stringday="星期二";
                break;
            case 4:
                stringday="星期三";
                break;
            case 5:
                stringday="星期四";
                break;
            case 6:
                stringday="星期五";
                break;
            case 7:
                stringday="星期六";
                break;
        }
        return stringday;
    }

    /**
     *
     * @param year
     * @param month  month， base 0
     * @param day
     * @return
     */
    public static String getWeekendOfDay(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(year,month,day);
        int dayofweek = c.get(Calendar.DAY_OF_WEEK);
        String stringday = null;
        switch (dayofweek) {
            case 1:
                stringday="星期天";
                break;
            case 2:
                stringday="星期一";
                break;
            case 3:
                stringday="星期二";
                break;
            case 4:
                stringday="星期三";
                break;
            case 5:
                stringday="星期四";
                break;
            case 6:
                stringday="星期五";
                break;
            case 7:
                stringday="星期六";
                break;
        }
        return stringday;
    }


    /**
     * 获取当前时间
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     */
    public static Date dayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 获取今天 23:59:59.999 的时间
     */
    public static Date dayEnd() {
        return dayEnd(now());
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        return formatter.parse(strTime);
    }

    public static String DateToString(Date date, String formatType) {
        return new SimpleDateFormat(formatType).format(date);
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Long stringToLong(String strTime, String formatType)
            throws ParseException {
        return stringToDate(strTime, formatType).getTime();
    }

    public static String stringToLongTime(String string) {
        try {
            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(string);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String StringToDoubleLineTime(String string) {
        try {
            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(string);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd#HH:mm:ss");
            String[] split = format.format(date).split("#");
            return new StringBuffer().append(split[0]).append("\n").append(split[1]).toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param string  转换为小时
     * @return
     */

    public static String stringToHMS(String string) {

        Date date=new Date(Long.parseLong(string)*1000L);
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     *
     * @param value 转换为 年月日
     * @return
     */
    public static String stringToYMD(String value) {

        Date date=new Date(Long.parseLong(value)*1000L);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     *
     * @param value 转换为 自定义类型的 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String stringToType(String value, String type) {

        Date date=new Date(Long.parseLong(value)*1000L);
        SimpleDateFormat dateFormat=new SimpleDateFormat(type);
        return dateFormat.format(date);
    }

    /**
     * 将date转换为置顶类型的
     * @param date
     * @param type
     * @return
     */
    public static String getTime(Date date, String type) {
        //        yyyy年MM月dd日  yyyy-MM-dd
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(date);
    }

    public static String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() / 1000;
        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return new StringBuilder().append(format(hour)).append(":")
                .append(format(minute)).append(":")
                .append(format(second)).toString();
    }

    public static String formatLongToTimeFormatStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() / 1000;
        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return new StringBuilder().append(format(hour)).append(" 时 ")
                .append(format(minute)).append(" 分 ")
                .append(format(second)).append(" 秒 ").toString();
    }

    private static String format(int a) {
        if (a < 10) {
            return "0" + a;
        } else {
            return String.valueOf(a);
        }
    }

    /**
     * 某个日期与今天比较
     *
     * @param date
     * @return date在今天之前，返回true;
     * @throws Exception
     */
    public static boolean compareToday(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date d1 = null;
        try {
            d1 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d1.getTime() - new Date().getTime() < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 日期比较
     *
     * @param date1
     * @param date2
     * @return date1在date2之前，return true;
     */
    public static boolean compareDate(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parse1 = null;
        Date parse2 = null;
        try {
            parse1 = format.parse(date1);
            parse2 = format.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (parse1.before(parse2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前时间格式 yyyy-MM-dd HH:mm:ss 的时间
     *
     * @return
     */
    public static String getCurrentTimeStr() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 获取当前时间+固定日期格式 yyyy-MM-dd HH:mm:ss 的时间
     *
     * @return
     */
    public static String getCurrentTimeStr(int date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, date);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    /**
     * 获取当前时间格式 yyyyMMddHHmmss 的时间
     *
     * @return
     */
    public static String getCurrentTimeMill() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 获取当前时间格式 yyyyMMdd 的时间
     *
     * @return
     */
    public static String getDateStr() {
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
    }
    /**
     * 获取当前时间格式 yyyyMMdd 的时间
     *
     * @return
     */
    public static String getDateStr(String type) {
        return new SimpleDateFormat(type).format(new Date());
    }

    /**
     * 获取随机生成的6位数
     *
     * @return
     */
    public static String getRandomNum() {
        // 产生6随机数
        Random random = new Random();
        StringBuffer num = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int a = random.nextInt(9);
            num.append(a);
        }
        return num.toString();
    }

    /**
     * 钱格式化为两位的Double
     *
     * @param str
     * @return
     */
    public static String getDouble(String str) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Double.valueOf(str));
    }

    /**
     * 得到未来某个日期距离现在的天数
     */
    public static String getStillDay(String endTime) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        double day = 0;
        try {
            Date date = myFormatter.parse(endTime);
            day = (double) (date.getTime() - new Date().getTime()) / (double) (24 * 60 * 60 * 1000);
            if (day != 0 && day < 1) {
                return "1";
            } else {
                return (int) day + "";
            }
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 计算两个时间差
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return xx天xx时xx分xx秒
     */
    public static String calTimeDistan(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return "";
        }
        String distance = "";
        long time = endDate.getTime() - startDate.getTime();
        long day = 0;// 天
        long hour = 0;// 时
        long min = 0;// 分
        long second = 0;// 秒

        if (time > 0) {
            day = (time / (1000 * 60 * 60 * 24)) > 0 ? time / (1000 * 60 * 60 * 24) : 0;
            hour = ((time - day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60)) > 0 ? ((time - day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60)) : 0;
            min = ((time - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60) / (1000 * 60)) > 0 ? ((time - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60) / (1000 * 60)) : 0;
            second = ((time - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60 - min * 1000 * 60) / (1000)) > 0 ? ((time - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60 - min * 1000 * 60) / (1000))
                    : 0;
        }
        distance = day + "天" + hour + "时" + min + "分" + second + "秒";

        return distance;
    }

    /**
     * 生产字母数字随机数
     *
     * @param length
     * @return
     */
    public static String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if ("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return "Q" + val;
    }

    /**
     * 计算一段时间的具体表达
     *
     * @param time
     * @return xx天xx时xx分xx秒
     */
    public static String timeToStr(long time) {
        String distance = "";
        long day = 0;// 天
        long hour = 0;// 时
        long min = 0;// 分
        long second = 0;// 秒

        if (time > 0) {
            day = (time / (1000 * 60 * 60 * 24)) > 0 ? time / (1000 * 60 * 60 * 24) : 0;
            hour = ((time - day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60)) > 0 ? ((time - day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60)) : 0;
            min = ((time - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60) / (1000 * 60)) > 0 ? ((time - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60) / (1000 * 60)) : 0;
            second = ((time - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60 - min * 1000 * 60) / (1000)) > 0 ? ((time - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60 - min * 1000 * 60) / (1000))
                    : 0;
        }
        if (day > 0) {
            distance += day + "天";
        }
        if (hour > 0) {
            distance += hour + "时";
        }
        if (min > 0) {
            distance += min + "分";
        }
        if (second > 0) {
            distance += second + "秒";
        }

        return distance;
    }

}