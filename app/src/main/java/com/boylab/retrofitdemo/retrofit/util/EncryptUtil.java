package com.boylab.retrofitdemo.retrofit.util;

public class EncryptUtil {
    public static final boolean NEED_ENCRYPT = true;
    public static final boolean NEED_CHECK = true;

    public static final String TYPE_CODE = "utf-8";

    private static final byte[] NORMAL_TABLE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_/,\" =".getBytes();
    public static final int SEED_MASK = 29;

    private static final byte[][] SECU_TABLE = {
            "RtuLYG=8Dnv6J15z4l3ajqwXTEHVgysdAxMerZ/ioOF_cKNmbQSU9kWpf0I\" Ch2PB7,".getBytes(),
            "TwEWtu O=jynAHSXs0,DGxbhz/_8mo6LJcefBF\"kqlvrR3M2CZ4Y9dgp1iPQU5NIVa7K".getBytes(),
            "zNPr9lXAiGdp3K8BSwnuVEqYWJZ7\"f/x2TvUgtMjHI0D4CcFsL1kRa6Q_5me=O h,oby".getBytes(),
            "R8NjkOe/XYbK=DnQohSBpZiVvy_L Jfr1EA4Wx03MCHFaGmUP9\"s,uz6qdcTt7gI5lw2".getBytes(),
            "mDnzhXgS0=5QNx2Jda8coAu6w3 F/UiWO1_yrkVb\"fRM9ITPlqteHGBZCvEsLK7jp,4Y".getBytes(),
            "uUVcN_bTs hZrSn84zptCevMAk5XdFD=qL3Hl6y9G,imgWRI2BJxK0ojY7Of\"PQaE1w/".getBytes(),
            "DGgErhXVQc\"8bnm5eBHA,9=LasCyYziPd1uopR 7f03vJ/MFt4lxk6_O2NqUISZKjwWT".getBytes(),
            "wB=DcY/ieuhLH40Tgtdo1bI3P8qlns A\"GWaXC5,fj69zENO2FVJxy_MkRZp7QmvUSrK".getBytes(),
            "3oMw8Zrd_SJP01qADusT2m\"fcHpGRkY4y/,xja=gLUEe69VCi7nOWNl IhQX5btzKFvB".getBytes(),
            "o82N\"zGynqZ=xAMKWEbHPF1TIhvkf6wgBrRcYQ43sdVJ_ mjS,tD95XiUe0aOpl/LuC7".getBytes(),
            "7mKXNo,ub9QnHfa8d2_CEcrqRTvDlpiUweIB1Z564StV=Wg3zk/AjFGyMxhPL\"Y0sOJ ".getBytes(),
            "7cJngI/20=Xe_MiR9N6OyE8obzw t,KhSQ3DFLja\"ZPfuqAGH5Urkmvdx4Bs1YpTlWVC".getBytes(),
            "bzmSHA/4cLIWrRQdGa6t\"7fKegCv q_8lpNXsT0hOk9V3P=5oJwjEyBxM2u,D1FZnYiU".getBytes(),
            "7D0Ljo2pzgCA\"lVe3I Z_FEQsvOGk5,BxUMP8T/a9du=bHhWc6JNiKfwSrtXY41nRymq".getBytes(),
            "6P,/cr_OqnzkIle=uyxFt\"msS 43DXJABRoEaKgdVZGUiw7jQYbW5HvChpL28Nf9MT10".getBytes(),
            "_WCLFa5zKy2quSOoRh/mjDecGin1EBv\"VbIsAfg=,w0Qx9T XMl83NrZpP6tYkHUJ4d7".getBytes()};

    private static final int MAX_SEED = SECU_TABLE.length;
    private static final int NUMB_CHAR = NORMAL_TABLE.length;

    public static boolean check(int check, String... data) {
        return (getCheck(data) == check);
    }

    public static int getCheck(String... data) {
        int xor = 0;
        int add = 0;
        for (String str : data) {
            if (str != null && !str.isEmpty()) {
                byte[] bytes = str.getBytes();
                for (int i = 0; i < bytes.length; i++) {
                    xor = (xor ^ bytes[i]) & 0xff;
                    add = (add + bytes[i]) & 0xff;
                }
            }
        }
        return (Integer.valueOf(String.valueOf(xor) + String.valueOf(add)));
    }

    public static boolean check(int check, byte[] data, int start, int length) {
        if (NEED_CHECK) {
            return (getCheck(data, start, length) == check);
        }
        return true;
    }

    public static int getCheck(byte[] bytes, int start, int length) {
        int xor = 0;
        int add = 0;
        if (bytes != null) {
            for (int i = 0; i < length; i++) {
                xor = (xor ^ bytes[start + i]) & 0xff;
                add = (add + bytes[start + i]) & 0xff;
            }
        }
        return (Integer.valueOf(String.valueOf(xor) + String.valueOf(add)));
    }

    public static String encodeString(int seed, int salt, String src) {
        if (NEED_ENCRYPT) {
            if (src == null || src.isEmpty()) {
                return src;
            }
            byte[] srcBytes = src.getBytes();
            byte[] destBytes = new byte[srcBytes.length];
            for (int i = 0; i < srcBytes.length; i++) {
                destBytes[i] = encodeByte(srcBytes[i], seed, salt, i);
            }
            String dest = src;
            try {
                dest = new String(destBytes, TYPE_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dest;
        }
        return src;
    }

    public static String decodeString(int seed, int salt, String src) {
        if (NEED_ENCRYPT) {
            if (src == null || src.isEmpty()) {
                return src;
            }
            byte[] srcBytes = src.getBytes();
            byte[] destBytes = new byte[srcBytes.length];
            for (int i = 0; i < srcBytes.length; i++) {
                destBytes[i] = decodeByte(srcBytes[i], seed, salt, i);
            }
            String dest = src;
            try {
                dest = new String(destBytes, TYPE_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dest;
        }
        return src;
    }

    public static byte encodeByte(byte src, int seed, int salt, int count) {
        if (NEED_ENCRYPT) {
            int index = HexUtil.searchIndex(NORMAL_TABLE, src);
            if (index == -1) {
                return src;
            }
            seed %= MAX_SEED;
            salt ^= count;
            salt = (salt >> 1) % SEED_MASK + (salt >> 9) % (NUMB_CHAR - SEED_MASK + 1);
            index = index + salt;
            if (index >= NUMB_CHAR) {
                index -= NUMB_CHAR;
            }
            return SECU_TABLE[seed][index];
        }
        return src;
    }

    public static byte decodeByte(byte src, int seed, int salt, int count) {
        if (NEED_ENCRYPT) {
            seed %= MAX_SEED;
            int index = HexUtil.searchIndex(SECU_TABLE[seed], src);
            if (index == -1) {
                return src;
            }
            salt ^= count;
            salt = (salt >> 1) % SEED_MASK + (salt >> 9) % (NUMB_CHAR - SEED_MASK + 1);
            index = index >= salt ? index - salt : index + NUMB_CHAR - salt;
            return NORMAL_TABLE[index];
        }
        return src;
    }

}
