package nuanliu.com.modao.utils;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @创建者 SoleJoke
 * @创建时间 on 2017/3/28
 * @描述
 * @更新者 　$Author
 * @更新时间 　2017/3/28
 * @更新描述　　${ToDo}
 */

public class StringUtil {
    public static String getMoneyForm(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##0.00");
        return decimalFormat.format(value);
    }

    public static boolean isPhoneNum(String phoneNumber) {
        boolean isValid = false;
        String expression = "^1[3|4|5|7|8]\\d{9}$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;

    }


    public static String  getStringValue(double value,int scale) {
        if (value == -1000000) {
            return "——";
        } else {
            return  ArithUtils.round(value,scale)+"";
        }

    }

    public static String  getCompanyNameForDZ(String companyName) {
        if (SpUtil.getUser().getUsername().equals("hbdz")) {
            switch (companyName) {
                case "华建小区":
                    companyName = "景秀江山";
                    break;
                case "黄沟小区":
                    companyName = "博士园东区";
                    break;
                case "名仕华庭":
                    companyName = "博士园西区";
                    break;

            }
            return companyName;

        } else {
            return companyName;
        }
    }


    public static String encodeBase64File(File file) {


        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[(int) file.length()];
        try {
            inputFile.read(buffer);
            inputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String encodeToString = Base64.encodeToString(buffer, Base64.DEFAULT);
        return encodeToString;

    }

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0 || str.equalsIgnoreCase("null"));
    }
}
